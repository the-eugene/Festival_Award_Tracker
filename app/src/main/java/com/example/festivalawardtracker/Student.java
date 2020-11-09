package com.example.festivalawardtracker;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    ArrayList<String> teacherIDs;
    ArrayList<String> parentIDs;
    List<Instrument> instruments;
    List<Award> awards;
    List<Performance> performances;

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
        Event event=DBManager.Events.get(performance.eventID);
        SchoolYear year=DBManager.SchoolYears.get(event.schoolYearID);
        EventDescription description=DBManager.EventDescriptions.get(event.EventDescriptionID);
        Festival festival=DBManager.Festivals.get(description.festivalID);
        if (festival.isNFMC){
                //TODO NFMC Award Logic
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
}
