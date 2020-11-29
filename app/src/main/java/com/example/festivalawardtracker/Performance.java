package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.util.Comparator;

public class Performance {
    String eventID;
    String level;   //enum this?
                    // If there's a predetermined scale for levels, like intermediate, advance, etc, I think it should be enumed. CARLOS
    int rating;
    LocalDate date;

    public Performance() {}

    public Performance(String eventID, LocalDate date, String level, int rating) {
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

    public static class sortByYear implements Comparator<Performance>
    {
        public int compare(Performance a, Performance b)
        {
            int byYear = a.retrieveEvent().retrieveYear().sequence - b.retrieveEvent().retrieveYear().sequence;
            int byAlphabet = DBManager.EventDescriptions.get(a.retrieveEvent().eventDescriptionID).name.compareTo(DBManager.EventDescriptions.get(b.retrieveEvent().eventDescriptionID).name);
            return byYear==0?byAlphabet:-byYear;
        }
    }
}
