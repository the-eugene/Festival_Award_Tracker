package com.example.festivalawardtracker;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Log.d("award","Adding Awards");
        Event event=performance.retrieveEvent();
        SchoolYear year=event.retrieveYear();
        EventDescription description=event.getDescription();
        Festival festival=description.retrieveFestival();
        if (festival.isNFMC){
            //deal with cups
            int TAP = totalAccumulatedPoints(description);
            int PAP = TAP-performance.rating;
            Log.d("award","Checking NFMC Cups");
            Log.d("award", "PAP:"+PAP+" TAP: "+TAP);
            int cupLevel=TAP/15;
            if(TAP/15 > PAP/15) {
                Log.d("award", "Getting a cup!");
                Log.d("award", "CupLevel: " + cupLevel);
                awards.add(new Award(Award.lookUpCupAwardType(cupLevel),
                        ID,
                        performance.date,
                        event.ID,
                        performances.indexOf(performance)
                        )
                );
            }
            //deal with Certificates
            Log.d("award","Checking NFMC Certificates");

            Award lastYearsAward = retrieveLastYearsAward(event);
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

    private Award retrieveLastYearsAward(Event event) {
        SchoolYear lastYear=DBManager.getPreviousSchoolYear(event.retrieveYear());
        for (Award award:awards){
            if (award.isInYear(lastYear)&&award.getEventID().equals(event.ID)){
                return award;
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
