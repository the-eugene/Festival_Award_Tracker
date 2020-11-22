package com.example.festivalawardtracker;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ModelClassesInstrumentedTest {
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

        SchoolYear year=TestStaticObjects.setUpSchoolYear1();
        SchoolYear year2=TestStaticObjects.setUpSchoolYear2();
        DBManager.SchoolYears.put(year.ID,year);
        assertNotNull(year.ID);

        Event[] e1 = new Event[10];
        Event[] e2 = new Event[10];
        for (int i=0;i<10;i++) {
            e1[i] = TestStaticObjects.setUpEvent(year);
            DBManager.linkEvent(
                    e1[i],
                    ed[new Random().ints(0, 3).findFirst().getAsInt()]
                    ,year
            );
        }
        for (int i=0;i<10;i++) {
            e2[i] = TestStaticObjects.setUpEvent(year2);
            DBManager.linkEvent(
                    e2[i],
                    ed[new Random().ints(0, 3).findFirst().getAsInt()]
                    ,year2
            );
        }

        Student s=TestStaticObjects.setUpStudent();
        s.addParent(TestStaticObjects.setUpPerson());
        s.addParent(TestStaticObjects.setUpPerson2());
        Teacher t=TestStaticObjects.setUpTeacher();
        DBManager.linkTeacherStudent(t,s);

        //working to here!
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",4
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",5
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",4
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",5
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",4
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",5
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",4
        );
        s.addPerformance(e1[0].ID,
                TestStaticObjects.between(e1[0].start,e1[0].end),
                "PP",5
        );
        s.save();
    }
}