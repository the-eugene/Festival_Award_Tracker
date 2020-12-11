package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Model class that represents an individual student performing at an event
 * it also includes the score they received from the judges
 * @author Eugene
 */
public class Performance {
    String eventID;
    String level;
    int rating;
    LocalDate date;

    /**
     * No argument constructor for firebase
     */
    public Performance() {}

    /**
     * Constructor
     * @param eventID The string ID of the event where the student performed
     * @param date The date of the performance
     * @param level The difficulty level of the student's performance
     * @param rating Judges score
     */
    public Performance(String eventID, LocalDate date, String level, int rating) {
        this.eventID=eventID;
        this.date=date;
        this.level=level;
        this.rating=rating;
    }

    /**
     * @return the Event where the student performed
     */
    public Event retrieveEvent() {
        return DBManager.Events.get(eventID);
    }

    /**
     * Checks if the performance occured during a particular school year
     * @param year The SchoolYear object representing the school year
     * @return true if the performance happened during that school year
     */
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

    /**
     * Class to provide sorting capabilities to Performances - this one sorts by year and within the year by event name alphabetically
     */
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
