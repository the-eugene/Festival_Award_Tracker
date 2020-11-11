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

    public void addParent(@NotNull Person p){
        parentIDs.add(p.ID);
        //TODO: save();
    }
    public int countParents(){return parentIDs.size();}

    public void addTeacher(String teacherID){
        teacherIDs.add(teacherID);
        //TODO save();
    }
    public void addInstrument(Instrument i) {
        instruments.add(i);
        //TODO save();
    }
    //TODO ? getAwardSummary(){}
    public void addPerformance(String eventID, LocalDate date, String level, int rating){
        Performance p=new Performance(ID, eventID,date,level, rating);
        performances.add(p);
        addAward(p);
        save();
    }

    public void addAward(@NotNull Performance performance) {
        Event event=performance.getEvent();
        SchoolYear year=event.getYear();
        EventDescription description=event.getDescription();
        Festival festival=description.getFestival();
        if (festival.isNFMC){
            int totalPoints= getTotalAccumulatedPoints(event);
            Award lastYearsAward=getLastYearsAward(event);
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

    private Award getLastYearsAward(Event event) {
        SchoolYear lastYear=DBManager.getPreviousSchoolYear(event.getYear());
        for (Award award:awards){
            if (award.isInYear(lastYear)){
                return award;
            }
        }
        return null;
    }

    private int getTotalAccumulatedPoints(Event event) {
        int points=0;
        for (Performance p: performances) {
            if (p.getEvent().ID.equals(event.ID)){
                points+=p.rating;
            }
        }
        return points;
    }
}
