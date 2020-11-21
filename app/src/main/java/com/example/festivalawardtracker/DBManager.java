package com.example.festivalawardtracker;

import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DBManager {

    public static FirebaseDatabase DB = FirebaseDatabase.getInstance();
    public static DatabaseReference currentDB;
    static {
        setCurrentDB("");
    }

    static Map<String, Teacher> Teachers=new DatabaseHashMap<>(Teacher.class);
    static Map<String, Student> Students=new DatabaseHashMap<>(Student.class);
    static Map<String, Person> Parents=new DatabaseHashMap<>("Parents", Person.class);
    static Map<String, Event> Events=new DatabaseHashMap<>(Event.class);
    static Map<String, EventDescription> EventDescriptions=new DatabaseHashMap<>(EventDescription.class);
    static Map<String, Festival> Festivals=new DatabaseHashMap<>(Festival.class);
    static Map<String, SchoolYear> SchoolYears=new DatabaseHashMap<>(SchoolYear.class);

    public static void setCurrentDB(String location){
        currentDB=location.isEmpty()?DB.getReference():DB.getReference(location);
    }

    public static <T extends DatabaseAware> boolean saveData(T obj){
        String key=obj.ID;
        //TODO: implement saving single item by key
        return false;
    }

    public static SchoolYear getPreviousSchoolYear(SchoolYear year) {
        if (year.sequence>0)
            return DBManager.getYearBySequence(year.sequence-1);
        return null;
    }

    private static SchoolYear getYearBySequence(int seq) {
        for (Map.Entry<String,SchoolYear> line:SchoolYears.entrySet()) {
            if (line.getValue().sequence==seq)
                return line.getValue();
        }
        //not in cache
        return null; //TODO: Search DB by sequence
    }

    public static void linkFestivalEventDescription(Festival festival, EventDescription eventDescription) {
        //ensure festival is in database and cache, generate ID if necessary
        Festivals.put(festival.ID,festival);
        //link one direction
        eventDescription.festivalID=festival.ID;
        //ensure EventDescription is in database and cache, generate ID if necessary
        EventDescriptions.put(eventDescription.ID,eventDescription);
        //link other direction
        festival.eventDescriptionIDs.add(eventDescription.ID);
        festival.save(); //update in db
    }

    public static void linkEvent(Event event, EventDescription eventDescription, SchoolYear schoolYear) {
        //ensure EventDescription is in database and cache, generate ID if necessary
        EventDescriptions.put(eventDescription.ID,eventDescription);
        //ensure SchoolYear is in database and cache, generate ID if necessary
        SchoolYears.put(schoolYear.ID,schoolYear);
        //link to EventDescription
        event.eventDescriptionID=eventDescription.ID;
        event.schoolYearID=schoolYear.ID;
        //ensure event is in database and cache, generate ID if necessary
        Events.put(event.ID,event);
        //link SchoolYear other direction
        schoolYear.eventIDs.add(eventDescription.ID);
        schoolYear.save(); //update in db
    }

}

