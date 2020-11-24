package com.example.festivalawardtracker;

import android.util.Log;

import androidx.annotation.NonNull;

import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class ModelClassesInstrumentedTest {

    @Test
    public void testYearTraverse(){
        DBManager.setCurrentDB("TEST"); //switch to test DB
        DBManager.currentDB.setValue(null); //Clear test DB
        SchoolYear[] years=new SchoolYear[10];
        for (int i=0;i<10;i++){
            years[i]=TestStaticObjects.setUpSchoolYear(i);
            DBManager.SchoolYears.put(null,years[i]);
        }

        Festival f=TestStaticObjects.setUpFestival1();
        EventDescription ed =TestStaticObjects.setUpEventDescription1();
        DBManager.linkFestivalEventDescription(f,ed);
        Event e=TestStaticObjects.setUpEvent(years[5]);
        DBManager.linkEvent(e,ed,years[5]);

        SchoolYear lastYear=DBManager.getPreviousSchoolYear(years[6]);
        assertNotNull(lastYear);
        assertEquals(5,lastYear.sequence);
        assertFalse(e.isInYear(years[6]));
        assertTrue(e.isInYear(years[5]));
        assertFalse(e.isInYear(years[4]));

        DBManager.SchoolYears.clear();
        assertEquals(0,DBManager.SchoolYears.size());
        DBManager.SchoolYears.loadAll();
        assertNotEquals(0,DBManager.SchoolYears.size());
        assertEquals(lastYear.ID,DBManager.getPreviousSchoolYear(years[6]).ID);

        DBManager.SchoolYears.clear();
        //load it from database!
        assertEquals(lastYear.ID,DBManager.getPreviousSchoolYear(years[6]).ID);

    }

    // This code works, but cannot get it to work with generics.
//    @Test
//    public void testDBHashLoading(){
//        DatabaseHashMap<SchoolYear> test =new DatabaseHashMap<>(SchoolYear.class);
//
//        final TaskCompletionSource<HashMap<String,SchoolYear>> task = new TaskCompletionSource<>();
//        DBManager.currentDB.child(test.pathToData).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot ds) {
//                GenericTypeIndicator<HashMap<String,SchoolYear>> t=new GenericTypeIndicator<HashMap<String, SchoolYear>>() {};
//                task.setResult(ds.getValue(t));
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                task.setException(error.toException());
//            }
//        });
//        Task<HashMap<String,SchoolYear>> t = task.getTask();
//        try {
//            Tasks.await(t);
//        } catch (ExecutionException | InterruptedException e) {
//            t = Tasks.forException(e);
//            return;
//        }
//        if(t.isSuccessful()) {
//            if (t.getResult()!=null) test.putAll(t.getResult());
//        }
//
//        assertNotEquals(0,test.size());
//        assertEquals(10,test.size());
//    }



    @Test
    public void instantiate_objects_db(){
        DBManager.setCurrentDB("TEST"); //switch to test DB
        DBManager.currentDB.setValue(null); //Clear test DB

        Festival f=TestStaticObjects.setUpFestival1();
        Festival f2=TestStaticObjects.setUpFestival2();
        EventDescription[] ed ={
                TestStaticObjects.setUpEventDescription1(),
                TestStaticObjects.setUpEventDescription2(),
                TestStaticObjects.setUpEventDescription3(),
                TestStaticObjects.setUpEventDescription4()};

        DBManager.linkFestivalEventDescription(f,ed[0]);
        DBManager.linkFestivalEventDescription(f,ed[1]);
        DBManager.linkFestivalEventDescription(f2,ed[2]);
        DBManager.linkFestivalEventDescription(f2,ed[3]);

        assertNotNull(f.ID);
        assertNotNull(ed[3].ID);

        assertEquals(2,f.eventDescriptionIDs.size());
        assertEquals(2,f2.eventDescriptionIDs.size());
        assertEquals(f2.ID,ed[3].festivalID);

        SchoolYear[] years=new SchoolYear[10];
        for (int i=0;i<10;i++){
            years[i]=TestStaticObjects.setUpSchoolYear(i);
            DBManager.SchoolYears.put(null,years[i]);
        }

        Event[][] events = new Event[4][10];
        for (int i=0;i<4;i++) {
            for(int j=0;j<10;j++){
                events[i][j]=TestStaticObjects.setUpEvent(years[j]);
                DBManager.linkEvent(
                        events[i][j],
                        ed[i],
                        years[j]
                );
            }
        }

        Student s=TestStaticObjects.setUpStudent();
        s.addParent(TestStaticObjects.setUpPerson());
        s.addParent(TestStaticObjects.setUpPerson2());
        Teacher t=TestStaticObjects.setUpTeacher();
        DBManager.linkTeacherStudent(t,s);

        Random random = new Random();
        for (int i=0;i<4;i++) {
            Log.d("test_setup", String.format("Event: %s",ed[i].name) );
            for(int j=0;j<10;j++){
                s.addPerformance(events[i][j].ID,
                        TestStaticObjects.between(events[i][j].start,events[i][j].end),
                        "PP",random.nextInt(2)+4
                );
            }
        }
        s.save();
    }

}
