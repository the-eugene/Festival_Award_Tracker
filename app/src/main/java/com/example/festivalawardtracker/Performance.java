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

    public Event retrieveEvent() {
        return DBManager.Events.get(eventID);
    }

    public boolean isInYear(SchoolYear year) {
        return retrieveEvent().isInYear(year);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }
}
