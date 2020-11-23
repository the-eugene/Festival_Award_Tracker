package com.example.festivalawardtracker;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    List<String> teacherIDs = new ArrayList<>();
    List<String> parentIDs = new ArrayList<>();
    List<Instrument> instruments = new ArrayList<>();
    List<Award> awards = new ArrayList<>();
    List<Performance> performances = new ArrayList<>();

    Student() {}

    // When and how is parent ID set? Carlos
    public void addParent(@NotNull Person parent){
        if(parent.ID == null)
            DBManager.Parents.put(null,parent);
        parentIDs.add(parent.ID);
    }

    public int countParents(){return parentIDs.size();}

    // Shouldn't teachers be person object type? Carlos
    public void addTeacher(Teacher teacher){
        if(teacher.ID == null)
            DBManager.Parents.put(null,teacher);
        teacherIDs.add(teacher.ID);
        DBManager.Students.put(ID,this); //instead of save
    }

    public void addInstrument(Instrument i) {
        instruments.add(i);
    }

    //TODO ? getAwardSummary(){}
    public void addPerformance(String eventID, LocalDate date, String level, int rating){
        Performance p=new Performance(eventID,date,level, rating);
        performances.add(p);
        addAward(p);
        DBManager.Students.put(ID,this); //instead of save
    }

    public void addAward(@NotNull Performance performance) {
        Event event=performance.retrieveEvent();
        SchoolYear year=event.retrieveYear();
        EventDescription description=event.getDescription();
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

    private Performance retrieveLastYearsPerformance(Event event) {
        SchoolYear lastYear=DBManager.getPreviousSchoolYear(event.retrieveYear());

        for (Performance p:performances){
            if (p.isInYear(lastYear)&&DBManager.Events.get(p.getEventID()).eventDescriptionID.equals(event.eventDescriptionID)){
                return p;
            }
        }
        return null;
    }

    private int totalAccumulatedPoints(EventDescription ed) {
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
