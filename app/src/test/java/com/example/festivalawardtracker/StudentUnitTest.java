package com.example.festivalawardtracker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StudentUnitTest {
    @Test
    public void instrument(){
        Student s = new Student();
        s.addInstrument(Instrument.piano);
        assertEquals(Instrument.piano,s.instruments.get(0));
    }
}
