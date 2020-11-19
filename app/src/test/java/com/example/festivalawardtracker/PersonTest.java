package com.example.festivalawardtracker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Eugene
 */
public class PersonTest {

    static Person person = new Person();
    static {
        person.setName("Eugene", "Alexander", "Williams");
        person.setGender(Person.Gender.MALE);
        person.setBirthday("Aug 24, 1977");
        person.setContact("Natasha's Cafe",
                "859-259-2754",
                "natasha@beetnik.com",
                "112 Esplanade",
                "Lexington",
                "KY",
                "40508"
        );
    }

    @Test
    public void testName(){
        Person p = new Person();
        p.setName("Timmy","Earl","Smith");
        assertEquals("Timmy", p.getFirstName());
        assertEquals("Earl", p.getMiddleName());
        assertEquals("Smith", p.getLastName());
        assertEquals("Timmy E. Smith", p.getFullName());
    }

    @Test
    public void testGender(){
        Person p = new Person();
        p.setGender(Person.Gender.FEMALE);
        assertEquals(p.getGenderString(),"Female");
        p.setGender(Person.Gender.MALE);
        assertEquals(p.getGenderString(),"Male");
    }

    @Test
    public void testAge() {
        Person p = new Person();
        p.setBirthday("Nov 3, 2010");
        assertEquals(p.getAge(),10);
        assertEquals(p.getAge(LocalDate.of(2020,11,2)),9);
        assertEquals(p.getAge(LocalDate.of(2020,11,4)),10);
    }

    @Test
    public void testBirthday() {
        Person p = new Person();
        p.setBirthday("Nov 23, 1990");
        assertEquals(p.getBirthday(), "1990-11-23");
    }

    @Test
    public void testContact(){
        Person p = new Person();
        p.setContact("Natasha's Cafe",
                "859-259-2754",
                "natasha@beetnik.com",
                "112 Esplanade",
                "Lexington",
                "Kentucky",
                "40508"
        );
        assertEquals("KY", p.getContact().state);
        assertEquals("Kentucky", p.getContact().stateName());
        assertEquals("Natasha's Cafe\n112 Esplanade\nLexington, KY  40508", p.getContact().fullAddress());
    }

    @Test
    public void testSave(){
        person.save();
        assertNotNull(person.ID);
    }

    @Test public void testLocation() { assertEquals( "Lexington, KY", person.shortLocation()); }
}
