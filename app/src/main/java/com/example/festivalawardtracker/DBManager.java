package com.example.festivalawardtracker;

import java.util.HashMap;
import java.util.Map;

public class DBManager {
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

    static Map<String, Teacher> Teachers=new DatabaseHashMap<>();
    static Map<String, Student> Students=new DatabaseHashMap<>();
    static Map<String, Person> Parents=new DatabaseHashMap<>();
    static Map<String, Event> Events=new DatabaseHashMap<>();
    static Map<String, EventDescription> EventDescriptions=new DatabaseHashMap<>();
    static Map<String, Festival> Festivals=new DatabaseHashMap<>();
    static Map<String, SchoolYear> SchoolYears=new DatabaseHashMap<>();

    public static <T> T getData(String key){
        return null; //TODO: implement getting single item by key
    }
    public static <T extends DatabaseAware> boolean saveData(T obj){
        String key=obj.ID;
        //TODO: implement saving single item by key
        return false;
    }

    public static SchoolYear getPreviousSchoolYear(SchoolYear year) {
//        for(Map.Entry<String, SchoolYear> y:SchoolYears.entrySet()){
//            y.getValue().
//        }
// TODO: Finish prev SchoolYear
        return null;
    }
}

