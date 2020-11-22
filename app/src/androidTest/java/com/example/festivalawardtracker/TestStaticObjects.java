package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


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

    static Contact setUpContact2(){
        return new Contact(    "",
                "(123) 456-7890",
                "info@spacetime.com",
                "1151 Manchester St",
                "Baton Rouge",
                "LA",
                "70726");
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

    static Festival setUpFestival1(){
        Festival festival1 = new Festival();
        festival1.isNFMC=true;
        festival1.name="String Festival";
        return festival1;
    }

    static Festival setUpFestival2(){
        Festival festival = new Festival();
        festival.isNFMC=true;
        festival.name="Piano Festival";
        return festival;
    }

    static EventDescription setUpEventDescription1(){
        EventDescription eventDescription = new EventDescription();
        eventDescription.setName("Violin Solo");
        eventDescription.setDescription("Students play their violins all alone while the world judges");
        eventDescription.setInstrument(Instrument.violin);
        return eventDescription;
    }

    static EventDescription setUpEventDescription2(){
        EventDescription eventDescription = new EventDescription();
        eventDescription.setName("Violin Duet");
        eventDescription.setDescription("Playing violins together, preferably in sync");
        eventDescription.setInstrument(Instrument.violin);
        return eventDescription;
    }

    static EventDescription setUpEventDescription3(){
        EventDescription eventDescription = new EventDescription();
        eventDescription.setName("Piano Solo");
        eventDescription.setDescription("A solo piano event");
        eventDescription.setInstrument(Instrument.piano);
        return eventDescription;
    }

    static EventDescription setUpEventDescription4(){
        EventDescription eventDescription = new EventDescription();
        eventDescription.setName("Piano Hymn");
        eventDescription.setDescription("Piano hymn event");
        eventDescription.setInstrument(Instrument.piano);
        return eventDescription;
    }

    static SchoolYear setUpSchoolYear1(){
        SchoolYear year = new SchoolYear();
        year.setName("2018-2019 School Year");
        year.setStart(String.valueOf(LocalDate.of(2018,8,15)));
        year.setEnd(String.valueOf(LocalDate.of(2019,5,15)));
        year.setSequence(0);
        return year;
    }

    static SchoolYear setUpSchoolYear2(){
        SchoolYear year = new SchoolYear();
        year.setName("2019-2020 School Year");
        year.setStart(String.valueOf(LocalDate.of(2019,8,15)));
        year.setEnd(String.valueOf(LocalDate.of(2020,5,15)));
        year.setSequence(1);
        return year;
    }

    static Event setUpEvent(SchoolYear year){
        Event event=new Event();
        event.start=between(year.start,year.end);
        event.end=between(event.start,event.start.plusDays(7));
        event.setLocation(setUpContact1());
        return event;
    }

    static Student setUpStudent() {
        Student student = new Student();
        student.setName("Alice", "Becky", "Callaway");
        student.setGender(Student.Gender.FEMALE);
        student.birthday = LocalDate.of(2007, 12, 20);
        student.setContact(setUpContact2());
        student.addInstrument(Instrument.violin);
        student.addInstrument(Instrument.cello);
        return student;
    }

    static Teacher setUpTeacher(){
        Teacher t=new Teacher();
        t.setName("Elaine", "Nina", "Wachter");
        t.setBirthday("1971-04-16");
        t.setContact("Wachter Music",
                "920-857-6214",
                "nina@wachtermusic.com",
                "11020 Buddy Ellis Rd.",
                "Denham Springs",
                "LA",
                "70726"
        );
        t.setGender(Person.Gender.FEMALE);
        return t;
    }

    public static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

}

