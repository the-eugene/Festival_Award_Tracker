package com.example.festivalawardtracker;

import android.annotation.SuppressLint;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class containing all the student information
 */
public class Student extends Person {
    public List<String> teacherIDs = new ArrayList<>();
    public List<String> parentIDs = new ArrayList<>();
    public List<Instrument> instruments = new ArrayList<>();
    public List<Award> awards = new ArrayList<>();
    public List<Performance> performances = new ArrayList<>();

    /**
     * no argument constructor for firebase
     */
    public Student() {
        super();
    }

    /**
     * Adds a parent to a student
     * @param parent Person object representing a parent
     */
    public void addParent(@NotNull Person parent){
        if(parent.ID == null)
            DBManager.Parents.put(null,parent);
        parentIDs.add(parent.ID);
    }

    /**
     * @return int count of the parents the student has
     */
    public int countParents(){return parentIDs.size();}

    /**
     * Adds a teacher to the Student.
     * @param teacher The Teacher object representing a teacher
     */
    public void addTeacher(Teacher teacher){
        if(teacher.ID == null)
            DBManager.Parents.put(null,teacher);
        teacherIDs.add(teacher.ID);
        DBManager.Students.put(ID,this); //instead of save
    }

    /**
     * Adds an Instrument to the student's instrument list
     * @param i Instrument object to be added
     */
    public void addInstrument(Instrument i) {
        instruments.add(i);
    }

    /**
     * Adds an instrument based on a string value of the instrument
     * @param i string representing the instrument to be added
     */
    public void addInstrument(String i){
         instruments.add(Instrument.valueOf(i));
    }

    /**
     * Generates an award summary string with the types and counts of awards the student has received
     * @return a formatted multiline string with events and awards
     */
    public String retrieveAwardSummary(){
        Map <String,Integer> TAP=new HashMap<>();
        Map <String,Integer> CCS=new HashMap<>();
        Map <String,Performance> LST_STAT=new HashMap<>();
        EventDescription ed;
        for (Award award:awards){
            ed = DBManager.EventDescriptions.get(DBManager.Events.get(award.eventID).eventDescriptionID);
            if(TAP.containsKey(ed.ID)) continue;
            TAP.put(ed.ID,totalAccumulatedPoints(ed));
            CCS.put(ed.ID,findCCS(ed));
            for (int i=performances.size(); i-- >0;){
                if(performances.get(i).retrieveEvent().eventDescriptionID.equals(ed.ID)){
                    LST_STAT.put(ed.ID,performances.get(i));
                }
            }
        }
        Log.d ("Summary", getFullName()+" "+TAP.size());
        List<String> result=new ArrayList<>();
        for(String ID:TAP.keySet()){
            @SuppressLint("DefaultLocale") String row=String.format(
                    "%s %s:%d TAP:%d CCS:%d CUP:%s",
                    DBManager.EventDescriptions.get(ID).name,
                    LST_STAT.get(ID).level,
                    LST_STAT.get(ID).rating,
                    TAP.get(ID),
                    CCS.get(ID),
                    (TAP.get(ID)/15)>0?Award.lookUpCupAwardType(5).toString():"none"
                    );
            result.add(row);
        }
        Collections.sort(result);
        return String.join("\n",result);
    }

    /**
     * Adds a performance (and generate the appropriate awards) to the student
     * @deprecated In V2 this will be spit into two separate steps with being able to set levels ahead of time, and adding awards as a separate step
     * @param eventID String eventID of the event being added
     * @param date the date of the performance
     * @param level the difficulty level of the piece being performed
     * @param rating the integer rating by the judges - 5 being the highest and 1 the lowest
     */
    public void addPerformance(String eventID, LocalDate date, String level, int rating){
        Performance p=new Performance(eventID,date,level, rating);
        performances.add(p);
        addAward(p); //TODO V2, Add awards as a separate step
        DBManager.Students.put(ID,this); //instead of save
    }

    /**
     * @return a formatted string listing the instruments the student plays
     */
    public String instrumentList(){
        String[] s=new String[instruments.size()];
        for (int i=0;i<instruments.size();i++) {
            s[i]=instruments.get(i).toString();
        }
        return String.join(", ",s);
    }

    /**
     * Takes a performance and adds appropriate awards based on their rating and taking into account the previous
     * history of their performances in that particular event
     * @deprecated this needs to be reworked in V2
     * @param performance the performance object for which the awards will be added
     */
    public void addAward(@NotNull Performance performance) {
        Event event=performance.retrieveEvent();
        SchoolYear year=event.retrieveYear();
        EventDescription description=event.retrieveDescription();
        Festival festival=description.retrieveFestival();
        if (festival.isNFMC){
            //deal with cups
            int TAP = totalAccumulatedPoints(description);
            int PAP = TAP-performance.rating;
            Log.d("award", "Checking NFMC Cups, CAP: "+performance.rating+" PAP:"+PAP+" TAP: "+TAP);
            int cupLevel=TAP/15;
            if(TAP/15 > PAP/15) {
                Log.d("award", "Getting a cup! CupLevel: " + cupLevel);
                awards.add(new Award(Award.lookUpCupAwardType(cupLevel),
                        ID,
                        performance.date,
                        event.ID,
                        performances.indexOf(performance)
                        )
                );
            }
            //deal with Certificates
            Award.AwardType type;
            if (performance.rating!=5){
                type= Award.AwardType.NFMC_CERT;
            } else {
                Performance lastYearsPerformance = retrieveLastYearsPerformance(event);
                if(lastYearsPerformance!=null && lastYearsPerformance.rating==5){
                    type= Award.AwardType.CONSECUTIVE_SUPERIOR_CERT;
                } else {
                    type= Award.AwardType.SUPERIOR_CERT;
                }
            }
            awards.add(
                    new Award(type,
                            ID,
                            performance.date,
                            event.ID,
                            performances.indexOf(performance)
                    )
            );
            Log.d("award",type.toString());
        } else {
            awards.add(
                    new Award(Award.AwardType.OTHER_PARTICIPATION,
                    ID,
                    performance.date,
                    event.ID,
                    performances.indexOf(performance)
                )
            );
        }
    }

    /**
     * count the uninterrupted chain of superior ratings for an event.
     * Getting a rating less than 5 resets the chain and would reset the consecutive superiors to 0
     * @param performance performance object to start with (and go backwards)
     * @return number of current consecutive superiors
     */
    public int findCCS(Performance performance){
        int result=0;
        String edID=performance.retrieveEvent().eventDescriptionID;
        for(int i=performances.indexOf(performance); i>=0; i--){
            Performance p=performances.get(i);
            if(p.retrieveEvent().eventDescriptionID.equals(edID)){
                if (p.rating<5) return result;
                result++;
            }
        }
        return result;
    }

    /**
     * count the uninterrupted chain of superior ratings for an event.
     * Getting a rating less than 5 resets the chain and would reset the consecutive superiors to 0
     * @param ed EventDescription for which to find the current consecutive superior
     *           starts with the latest performance in that event (and go backwards)
     * @return number of current consecutive superiors
     */
    public int findCCS(EventDescription ed){
        int result=0;
        for(int i=performances.size(); i-- >0;){
            Performance p=performances.get(i);
            if(p.retrieveEvent().eventDescriptionID.equals(ed.ID)){
                if (p.rating<5) return result;
                result++;
            }
        }
        return result;
    }

    /**
     * count the uninterrupted chain of superior ratings for an event, *excluding the last performance*
     * Getting a rating less than 5 resets the chain and would reset the consecutive superiors to 0
     * @param ed EventDescription for which to find the current consecutive superior
     *           starts with the second to latest performance in that event (and go backwards)
     * @return number of previous consecutive superiors
     */
    public int findPCS(EventDescription ed){
        boolean one=false;
        int result=0;
        for(int i=performances.size(); i-- >0;){
            Performance p=performances.get(i);
            if(p.retrieveEvent().eventDescriptionID.equals(ed.ID)){
                if (one&p.rating<5) return result;
                result++;
                one=true;
            }
        }
        return result;
    }

    /**
     * find a performance from last year that matches a particular type of event
     * @param event the event to search
     * @return performance from last year
     */
    private Performance retrieveLastYearsPerformance(Event event) {
        SchoolYear lastYear=DBManager.getPreviousSchoolYear(event.retrieveYear());

        for (Performance p:performances){
            if (p.isInYear(lastYear)&&DBManager.Events.get(p.getEventID()).eventDescriptionID.equals(event.eventDescriptionID)){
                return p;
            }
        }
        return null;
    }

    /**
     * Find the total accumulated points for a type of event
     * @param ed the evend description describing the type of event
     * @return int total accumulated points (sum of all the ratings)
     */
    public int totalAccumulatedPoints(EventDescription ed) {
        int points=0;
        for (Performance p: performances) {
            if (p.retrieveEvent().eventDescriptionID.equals(ed.ID)){
                points+=p.rating;
            }
        }
        return points;
    }

    public List<String> getTeacherIDs() {
        return teacherIDs;
    }
    public void setTeacherIDs(List<String> teacherIDs) {
        this.teacherIDs = teacherIDs;
    }

    public List<String> getParentIDs() {
        return parentIDs;
    }
    public void setParentIDs(List<String> parentIDs) {
        this.parentIDs = parentIDs;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }
    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public List<Award> getAwards() {
        return awards;
    }
    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }

    public List<Performance> getPerformances() {
        return performances;
    }
    public void setPerformances(List<Performance> performances) { this.performances = performances; }
}
