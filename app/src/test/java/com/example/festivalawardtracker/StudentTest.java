package com.example.festivalawardtracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentTest {

    Student student = new Student();
    Person parent = new Person();
    String teacherID = "teacher ID number";

    @Test
    public void testParents() {
        student.addParent(parent);
        assertEquals(1, student.countParents());
    }

    @Test
    public void testInstrument() {
        student.addInstrument(Instrument.piano);
        assertEquals(Instrument.piano, student.instruments.get(0));
    }

    @Test
    public void testTeacher() {
        student.addTeacher(teacherID);
        assertEquals(1, student.teacherIDs.size());
    }


}
