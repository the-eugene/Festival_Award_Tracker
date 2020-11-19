package com.example.festivalawardtracker;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DBManager {
    static FirebaseDatabase DB;
    static{
        DB = FirebaseDatabase.getInstance();
    }

//    static Map<Class<?>,Object> cache=new HashMap<>();
//    static Map<String,Class<?>> mapping=new HashMap<>();
//    static{
//        mapping.put("Teacher",Teacher.class);
//        mapping.put("Student",Student.class);
//        mapping.put("Person",Person.class);
//        mapping.put("Event",Event.class);
//        mapping.put("EventDescription",EventDescription.class);
//        mapping.put("Festival",Festival.class);
//        mapping.put("SchoolYear",SchoolYear.class);
//        for (Map.Entry<String,Class<?>> entry : mapping.entrySet()) {
//            cache.put(
//                entry.getValue(),
//                new DatabaseHashMap<String,Object>()
//            );
//        }
//    }

    static Map<String, Teacher> Teachers=new DatabaseHashMap<>(Teacher.class);
    static Map<String, Student> Students=new DatabaseHashMap<>(Student.class);
    static Map<String, Person> Parents=new DatabaseHashMap<>("Parents", Person.class);
    static Map<String, Event> Events=new DatabaseHashMap<>(Event.class);
    static Map<String, EventDescription> EventDescriptions=new DatabaseHashMap<>(EventDescription.class);
    static Map<String, Festival> Festivals=new DatabaseHashMap<>(Festival.class);
    static Map<String, SchoolYear> SchoolYears=new DatabaseHashMap<>(SchoolYear.class);

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
}

