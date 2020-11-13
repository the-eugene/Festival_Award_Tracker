package com.example.festivalawardtracker;

import java.time.LocalDate;

public class TestStaticObjects {
    static Contact setUpContact1(){
        return new Contact("Natasha's Cafe",
                "859-259-2754",
                "natasha@beetnik.com",
                "112 Esplanade",
                "Lexington",
                "KY",
                "40508");
    }
    static Person setUpPerson(){
        Person person = new Person();
        person.setName("Eugene", "Alexander", "Williams");
        person.setGender(Person.Gender.MALE);
        person.birthday= LocalDate.of(1977,9,24);
        person.setContact(setUpContact1());
        return person;
    }
    static Person setUpPerson2(){
        Person person = new Person();
        person.setName("Ronald", "H", "McDonald");
        person.setGender(Person.Gender.MALE);
        person.birthday= LocalDate.of(1950,3,4);
        person.setContact(setUpContact1());
        return person;
    }

    static Student setUpStudent() {
        Student student = new Student();
        student.setName("Alice", "Becky", "Callaway");
        student.setGender(Student.Gender.FEMALE);
        student.birthday = LocalDate.of(2007, 12, 20);
        student.setContact("",
                "(123) 456-7890",
                "info@spacetime.com",
                "1151 Manchester St",
                "Baton Rouge",
                "LA",
                "70726"
        );
        student.addInstrument(Instrument.violin);
        student.addInstrument(Instrument.cello);
        student.addPerformance(setUpEvent1().ID,
                LocalDate.of(2019,4,3),
                "PP",
                5);
        return student;
    }

    static Event setUpEvent1(){
        Event event=new Event();
        return event;
    }
}
