package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Performance {
    String eventID;
    String studentID;
    String level; //enum this?
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
}
