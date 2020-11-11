package com.example.festivalawardtracker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonUnitTest {
    static Person person=new Person();
    static{
        person.setName("Eugene", "Alexander", "Williams");
        person.gender= Person.Gender.MALE;
        person.birthday=LocalDate.of(1977,9,24);
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
    public void get_age(){
        Person P = new Person();
        P.birthday = LocalDate.of(2010,11,3);
        assertEquals(P.getAge(),10);
        assertEquals(P.getAge(LocalDate.of(2020,11,2)),9);
        assertEquals(P.getAge(LocalDate.of(2020,11,4)),10);
    }
    @Test
    public void name(){
        Person p = new Person();
        p.setName("Timmy","Earl","Smith");
        assertEquals("Timmy", p.firstName);
        assertEquals("Earl", p.middleName);
        assertEquals("Smith", p.lastName);
        assertEquals("Timmy E. Smith", p.getFullName());
    }
    @Test
    public void gender(){
        Person p = new Person();
        p.gender= Person.Gender.FEMALE;
        assertEquals(p.getGenderString(),"Female");
        p.gender= Person.Gender.MALE;
        assertEquals(p.getGenderString(),"Male");
    }
    @Test
    public void contact(){
        Person p = new Person();
        p.setContact("Natasha's Cafe",
                "859-259-2754",
                "natasha@beetnik.com",
                "112 Esplanade",
                "Lexington",
                "KY",
                "40508"
        );
        assertEquals("KY",p.contact.state);

        p.setContact("Natasha's Cafe",
                "859-259-2754",
                "natasha@beetnik.com",
                "112 Esplanade",
                "Lexington",
                "Kentucky",
                "40508"
        );
        assertEquals("KY",p.contact.state);
        assertEquals("Kentucky",p.contact.getStateName());
        assertEquals("Natasha's Cafe\n112 Esplanade\nLexington, KY  40508",p.contact.getAddress());
    }
    @Test
    public void save(){
        person.save();
        assertNotNull(person.ID);
    }

}
