package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Performance {
    String eventID;
    String studentID;
    String level;   //enum this?
                    // If there's a predetermined scale for levels, like intermediate, advance, etc, I think it should be enumed. CARLOS
    int rating;
    LocalDate date;

    public Performance() {}

    public Performance(String studentID, String eventID, LocalDate date, String level, int rating) {
        this.studentID=studentID;
        this.eventID=eventID;
        this.date=date;
        this.level=level;
        this.rating=rating;
    }

    public Event getEvent() {
        return DBManager.Events.get(eventID);
    }

    public boolean isInYear(SchoolYear year) {
        return getEvent().isInYear(year);
    }
}
