package com.example.festivalawardtracker;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    List<String> teacherIDs= new ArrayList<>();
    List<String> parentIDs=new ArrayList<>();
    List<Instrument> instruments=new ArrayList<>();
    List<Award> awards=new ArrayList<>();
    List<Performance> performances=new ArrayList<>();

    Student(){}

    public void addParent(@NotNull Person p){
        parentIDs.add(p.ID);
    }
    public int countParents(){return parentIDs.size();}

    public void addTeacher(String teacherID){
        teacherIDs.add(teacherID);
    }
    public void addInstrument(Instrument i) {
        instruments.add(i);
    }
    //TODO ? getAwardSummary(){}
    public void addPerformance(String eventID, LocalDate date, String level, int rating){
        Performance p=new Performance(ID, eventID,date,level, rating);
        performances.add(p);
        addAward(p);
        save();
    }

    public void addAward(@NotNull Performance performance) {
        Event event=performance.retrieveEvent();
        SchoolYear year=event.retrieveYear();
        EventDescription description=event.getDescription();
        Festival festival=description.retrieveFestival();
        if (festival.isNFMC){
            int totalPoints= totalAccumulatedPoints(event);
            Award lastYearsAward= retrieveLastYearsAward(event);
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

    private int totalAccumulatedPoints(Event event) {
        int points=0;
        for (Performance p: performances) {
            if (p.retrieveEvent().ID.equals(event.ID)){
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

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
}
