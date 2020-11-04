package com.example.festivalawardtracker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void person_get_ageWorks(){
        Person P = new Person();
        P.birthday = LocalDate.of(2010,11,3);
        assertEquals(P.getAge(),10);
        assertEquals(P.getAge(LocalDate.of(2020,11,2)),9);
        assertEquals(P.getAge(LocalDate.of(2020,11,4)),10);
    }
    @Test
    public void person_set_name(){
        Person p = new Person();
        p.setName("Timmy","E","hello");
        assertEquals("Timmy", p.firstName);
        assertEquals("E",p.middleName);
        assertEquals( "hello",p.lastName);
    }
    @Test
    public void student_add_instrument(){
        Student s = new Student();
        s.addInstrument(Instrument.piano);
//        assertThat(s.instruments).hasData(Instrument.piano);
        assertEquals(Instrument.piano,s.instruments.get(0));
    }


}