package com.example.festivalawardtracker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersonInstrumentedTest {
        static Person person=new Person();
        static{
            person.setName("Eugene", "Alexander", "Williams");
            person.setGender(Person.Gender.MALE);
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
    public void save(){
        person.save();
        assertNotNull(person.ID);
        Person p2=Person.load(person.ID, Person.class);
        assertEquals(person.lastName,p2.lastName);
        assertEquals(person.contact.email,p2.contact.email);
    }
}
