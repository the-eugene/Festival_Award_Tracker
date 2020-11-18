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
        event.setSchoolYearID("2019");
        event.setStart(String.valueOf(LocalDate.of(2019,4,3)));
        event.setEnd(String.valueOf(LocalDate.of(2019,4,3)));
        event.setLocation(setUpContact1());
        event.setEventDescriptionID("NFMC Strings 001");
        return event;
    }

    static Event setUpEvent2(){
        Event event2=new Event();
        event2.setSchoolYearID("2019");
        event2.setStart(String.valueOf(LocalDate.of(2019,2,3)));
        event2.setEnd(String.valueOf(LocalDate.of(2019,2,3)));
        event2.setLocation(setUpContact1());
        event2.setEventDescriptionID("NFMC Piano 001");
        return event2;
    }

    static SchoolYear setUpSchoolYear1(){
        SchoolYear year = new SchoolYear();
        year.setName("Year One");
        year.setStart(String.valueOf(LocalDate.of(2018,8,15)));
        year.setEnd(String.valueOf(LocalDate.of(2019,5,15)));
        year.setSequence(2019);
        return year;
    }

    static EventDescription setUpEventDescriptions1(){
        EventDescription eventDescription = new EventDescription();
        eventDescription.setName("NFMC Strings");
        eventDescription.setDescription("Strings Event for 2019");
        eventDescription.setInstrument(Instrument.violin);
        eventDescription.setFestivalID("NFMC Strings 001");
        return eventDescription;
    }

    static EventDescription setUpEventDescriptions2(){
        EventDescription eventDescription2 = new EventDescription();
        eventDescription2.setName("NFMC Piano");
        eventDescription2.setDescription("Piano Event for 2019");
        eventDescription2.setInstrument(Instrument.piano);
        eventDescription2.setFestivalID("NFMC Piano 001");
        return eventDescription2;
    }

    static Festival setUpFestival1(){
        Festival festival1 = new Festival();
        return festival1;
    }
}
