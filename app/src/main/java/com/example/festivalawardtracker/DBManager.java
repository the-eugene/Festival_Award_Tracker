package com.example.festivalawardtracker;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DBManager {

    public static FirebaseDatabase DB = FirebaseDatabase.getInstance();
    public static DatabaseReference currentDB;
    static {
        setCurrentDB("");
    }

    static DatabaseHashMap<Teacher> Teachers=new DatabaseHashMap<>(Teacher.class);
    static DatabaseHashMap<Student> Students=new DatabaseHashMap<>(Student.class);
    static DatabaseHashMap<Person> Parents=new DatabaseHashMap<>(Person.class);
    static DatabaseHashMap<Event> Events=new DatabaseHashMap<>(Event.class);
    static DatabaseHashMap<EventDescription> EventDescriptions=new DatabaseHashMap<>(EventDescription.class);
    static DatabaseHashMap<Festival> Festivals=new DatabaseHashMap<>(Festival.class);
    static DatabaseHashMap<SchoolYear> SchoolYears=new DatabaseHashMap<>(SchoolYear.class);

    public static void setCurrentDB(String location){
        currentDB=location.isEmpty()?DB.getReference():DB.getReference(location);
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
        DataSnapshot ds=runQuery(currentDB.child("SchoolYear").orderByChild("sequence").equalTo(seq).limitToFirst(1));
        if(ds!=null && ds.getChildrenCount()==1){
            ds=ds.getChildren().iterator().next();
            SchoolYear result=ds.getValue(SchoolYear.class);
            result.ID= ds.getKey();
            return result;
        }
        return null;
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
        event.addLinks(eventDescription,schoolYear);
        schoolYear.addEvent(event);
    }

    public static void linkTeacherStudent(Teacher teacher, Student student) {
        student.addTeacher(teacher); //ID will be generated if necessary
        teacher.addStudent(student); //ID will be generated if necessary
    }

    public static DataSnapshot runQuery(Query query){
        final TaskCompletionSource<DataSnapshot> task = new TaskCompletionSource<>();
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
        if(t.isSuccessful()) {
            return t.getResult();
        }
        return null;
    }

    public static Teacher getTeacherByEmail(String email){
        for (Map.Entry<String,Teacher> e:Teachers.entrySet()){
            if (e.getValue().getEmail().equals(email))
                return e.getValue();
        }
        DataSnapshot ds=runQuery(currentDB.child("Teacher").orderByChild("email").equalTo(email).limitToFirst(1));
        if(ds!=null && ds.getChildrenCount()==1){
            ds=ds.getChildren().iterator().next();
            Teacher result=ds.getValue(Teacher.class);
            result.ID= ds.getKey();
            Teachers.put(result.ID,result);
            return result;
        }
        return null;
    }

    public static Student getStudentByEmail(String email){
        for (Map.Entry<String,Student> e:Students.entrySet()){
            if (e.getValue().getEmail().equals(email))
                return e.getValue();
        }
    return getOneByAttribute(Students,"email",email);
    }

    public static <T extends DatabaseAware> T getOneByAttribute(DatabaseHashMap<T> map,String attribute, String value){
        DataSnapshot ds=runQuery(currentDB.child(map.pathToData).orderByChild(attribute).equalTo(value).limitToFirst(1));
        if(ds!=null && ds.getChildrenCount()==1){
            ds=ds.getChildren().iterator().next();
            T result=ds.getValue(map.type);
            result.ID= ds.getKey();
            map.put(result.ID,result);
            return result;
        }
        return null;
    }


}

