package com.example.festivalawardtracker;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Static class holding objects necessary for database operations, methods for linking objects together, querying, etc.
 * @author Eugene
 * @see Person
 * @see Teacher
 * @see Student
 * @see Event
 * @see EventDescription
 * @see Festival
 * @see SchoolYear
 */
public class DBManager {
    public static FirebaseDatabase DB = FirebaseDatabase.getInstance(); //Database instance
    public static DatabaseReference currentDB; //Reference to root of data storage
    public static boolean isLoaded = false; //true when all preload operations are complete

    //HashMap objects holding String UID - Object key value pairs
    public static DBHashMap<Teacher> Teachers = new DBHashMap<>(Teacher.class);
    public static DBHashMap<Student> Students = new DBHashMap<>(Student.class);
    public static DBHashMap<Person> Parents = new DBHashMap<>(Person.class);
    public static DBHashMap<Event> Events = new DBHashMap<>(Event.class);
    public static DBHashMap<EventDescription> EventDescriptions = new DBHashMap<>(EventDescription.class);
    public static DBHashMap<Festival> Festivals = new DBHashMap<>(Festival.class);
    public static DBHashMap<SchoolYear> SchoolYears = new DBHashMap<>(SchoolYear.class);
    public static SchoolYear currentYear;

    static {
        setCurrentDB(""); //default root is root of FireBase
    }

    /**
     * Set rooot location for database operations
     *
     * @param location Set this to where in the DB all data should be stored, leave as "" for main database and "TEST" for a test location
     */
    public static void setCurrentDB(String location) {
        currentDB = location.isEmpty() ? DB.getReference() : DB.getReference(location);
    }

    /**
     * Finds the school year previous to year passed to the method
     *
     * @param year SchoolYear object for year in question
     * @return SchoolYear immediately before the year passed
     */
    public static SchoolYear getPreviousSchoolYear(SchoolYear year) {
        if (year.sequence > 0)
            return DBManager.getYearBySequence(year.sequence - 1);
        return null;
    }

    /**
     * Get a SchoolYear object based on the sequence assigned to school years
     *
     * @param seq integer sequence of the year
     * @return SchoolYear with that sequence or null if year was the first year.
     */
    private static SchoolYear getYearBySequence(int seq) {
        for (Map.Entry<String, SchoolYear> line : SchoolYears.entrySet()) {
            if (line.getValue().sequence == seq)
                return line.getValue();
        }
        //not in cache
        DataSnapshot ds = runQuery(currentDB.child("SchoolYear").orderByChild("sequence").equalTo(seq).limitToFirst(1));
        if (ds != null && ds.getChildrenCount() == 1) {
            ds = ds.getChildren().iterator().next();
            SchoolYear result = ds.getValue(SchoolYear.class);
            result.ID = ds.getKey();
            return result;
        }
        return null;
    }

    /**
     * Provides logic needed to link a festival and an event description belonging to that festival
     *
     * @param festival         Festival object to link to
     * @param eventDescription EventDescription object to link to
     */
    public static void linkFestivalEventDescription(Festival festival, EventDescription eventDescription) {
        //ensure festival is in database and cache, generate ID if necessary
        Festivals.put(festival.ID, festival);
        //link one direction
        eventDescription.festivalID = festival.ID;
        //ensure EventDescription is in database and cache, generate ID if necessary
        EventDescriptions.put(eventDescription.ID, eventDescription);
        //link other direction
        festival.eventDescriptionIDs.add(eventDescription.ID);
        festival.save(); //update in db
    }

    /**
     * Provides logic for linking an event to a school year and an event description
     *
     * @param event            Event being linked
     * @param eventDescription Event Description being linked
     * @param schoolYear       SchoolYear being linked
     */
    public static void linkEvent(Event event, EventDescription eventDescription, SchoolYear schoolYear) {
        event.addLinks(eventDescription, schoolYear);
        schoolYear.addEvent(event);
    }

    /**
     * Provides logic for linking student and teacher
     *
     * @param teacher Teacher object being linked
     * @param student Student object being linked
     */
    public static void linkTeacherStudent(Teacher teacher, Student student) {
        student.addTeacher(teacher); //ID will be generated if necessary
        teacher.addStudent(student); //ID will be generated if necessary
    }

    /**
     * Execute a query on the database.
     *
     * @param query Firebase Query to run
     * @return raw DataSnapshot result of the query.
     */
    public static DataSnapshot runQuery(Query query) {
        final TaskCompletionSource<DataSnapshot> task = new TaskCompletionSource<>();
        Log.e("DBManager.runQuery", "Called with " + query.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                task.setResult(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                task.setException(error.toException());
            }
        });
        Task<DataSnapshot> t = task.getTask();
        try {
            Tasks.await(t);
        } catch (ExecutionException | InterruptedException e) {
            t = Tasks.forException(e);
        }
        if (t.isSuccessful()) {
            return t.getResult();
        }
        return null;
    }

    /**
     * Finds a teacher with a specific email (if exists)
     *
     * @param email email string to find
     * @return teacher object if one is found, null otherwise.
     */
    public static Teacher getTeacherByEmail(String email) {
        for (Map.Entry<String, Teacher> e : Teachers.entrySet()) {
            if (e.getValue().getEmail().equals(email))
                return e.getValue();
        }
        DataSnapshot ds = runQuery(currentDB.child("Teacher").orderByChild("email").equalTo(email).limitToFirst(1));
        if (ds != null && ds.getChildrenCount() == 1) {
            ds = ds.getChildren().iterator().next();
            Teacher result = ds.getValue(Teacher.class);
            result.ID = ds.getKey();
            Teachers.put(result.ID, result);
            return result;
        }
        return null;
    }

    /**
     * Finds a student with a specific email (if exists)
     *
     * @param email email string to find
     * @return student object if one is found, null otherwise.
     */

    public static Student getStudentByEmail(String email) {

        return getOneByAttribute(Students, "email", email);
    }

    /**
     * Find the FIRST object in the database based on one of its attributes and the value searched for
     *
     * @param map       the DBHashMap of the object type to be searched (and result added to if found)
     * @param attribute name of the attribute to be searched, needs to be first child of object. For example, "gender"
     * @param value     Value being searched for, for example "MALE"
     * @param <T>       Class of object being searched for.
     * @return instance of the object if found, or null
     */
    public static <T extends DBAware> T getOneByAttribute(DBHashMap<T> map, String attribute, String value) {
        DataSnapshot ds = runQuery(currentDB.child(map.pathToData).orderByChild(attribute).equalTo(value).limitToFirst(1));
        if (ds != null && ds.getChildrenCount() == 1) {
            ds = ds.getChildren().iterator().next();
            T result = ds.getValue(map.type);
            result.ID = ds.getKey();
            map.put(result.ID, result);
            return result;
        }
        return null;
    }

    /**
     * finds the schoolyear object representing the current year, if it exists, or adds a new one if it does not exist yet
     *
     * @return SchoolYear object representing current school year
     */
    public static SchoolYear findCurrentYear() {
        int seq = 0;
        int start = 2010;
        for (SchoolYear sy : SchoolYears.values()) {
            seq = Math.max(seq, sy.sequence);
            if (!LocalDate.now().isBefore(sy.start) && !LocalDate.now().isAfter(sy.end)) {
                return sy;
            }
        }
        //Making it here means we ran out of years!
        seq++;
        SchoolYear cyear = null;
        while ((start + seq) <= LocalDate.now().getYear()) {
            SchoolYear year = new SchoolYear();
            year.setName((start + seq) + "-" + (start + seq + 1) + "");
            year.setStart(String.valueOf(LocalDate.of(start + seq, 7, 15)));
            year.setEnd(String.valueOf(LocalDate.of(start + seq + 1, 7, 14)));
            year.setSequence(seq);
            DBManager.SchoolYears.put(year);
            if (!LocalDate.now().isBefore(year.start) && !LocalDate.now().isAfter(year.end)) {
                cyear = year;
            }
            seq++;
        }
        return cyear;
    }

    /**
     * Creates a thread to preload all the data from firebase at once
     *
     * @param activity activity to run the thread on
     * @return returns a thread object that can be run to complete the preload
     */
    public static Thread preload(Activity activity) {
        class queryThread implements Runnable {
            final Activity activity;

            queryThread(Activity activity) {
                this.activity = activity;
            }

            @Override
            public void run() {
                Log.d(this.getClass().getName(), "Loading Teacher and Student Database...");
                DBManager.Teachers.loadAll();
                DBManager.Students.loadAll();
                Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                DBManager.Festivals.loadAll();
                DBManager.EventDescriptions.loadAll();
                DBManager.Events.loadAll();
                DBManager.SchoolYears.loadAll();
                DBManager.currentYear = DBManager.findCurrentYear();
                Log.d(this.getClass().getName(), "...Finished");
                DBManager.isLoaded = true;
            }
        }
        return new Thread(new queryThread(activity));
    }
}

