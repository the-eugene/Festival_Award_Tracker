package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Model class for Events which are performances that recur year to year within a festival
 * Events consist of Event and an EventDescription classes
 * Event class are the actual times and places where students will perform during a festival
 * Events are paired with EventDescriptions - which represent data that stays the same from year to year
 * @see EventDescription
 * @see DBAware
 * @see Contact
 */
public class Event extends DBAware {
    public String schoolYearID;
    public LocalDate start;
    public LocalDate end;
    public Contact location;
    public String eventDescriptionID;
    public List<String> studentIDs = new ArrayList<>();

    /**
     * get the school year during which this event occurred
     * @return
     */
    public SchoolYear retrieveYear() {
        return DBManager.SchoolYears.get(schoolYearID);
    }

    public String getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(String schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    /**
     * Tests if the event is in a particular school year
     * @param year the SchoolYear object representing a school year
     * @return true if the event did occur in that year
     */
    public boolean isInYear(SchoolYear year) {
        return year != null && schoolYearID.equals(year.ID);
    }

    public String getStart() {
        return start.toString();
    }

    public void setStart(String start) {
        this.start = LocalDate.parse(start);
    }

    public void setStartLocalDate(LocalDate start) {
        this.start = start;
    }

    public String getEnd() {
        return end.toString();
    }

    public void setEnd(String end) {
        this.end = LocalDate.parse(end);
    }

    public void setEndLocalDate(LocalDate end) {
        this.end = end;
    }

    public Contact getLocation() {
        return location;
    }

    public void setLocation(Contact location) {
        this.location = location;
    }

    /**
     * @return EventDescription object for this event
     */
    public EventDescription retrieveDescription() {
        return DBManager.EventDescriptions.get(eventDescriptionID);
    }

    public String getEventDescriptionID() {
        return eventDescriptionID;
    }

    public void setEventDescriptionID(String eventDescriptionID) {
        this.eventDescriptionID = eventDescriptionID;
    }

    public List<String> getStudents() {
        return studentIDs;
    }

    public void setStudents(List<String> students) {
        this.studentIDs = students;
    }

    /**
     * Creates (if necessary) and assigns proper IDs to link events to their description and school year
     * @param eventDescription EventDescription representing these types of events
     * @param year the SchoolYear during which the event occurred
     */
    public void addLinks(EventDescription eventDescription, SchoolYear year) {
        if (eventDescription.ID == null) DBManager.EventDescriptions.put(null, eventDescription);
        if (year.ID == null) DBManager.SchoolYears.put(null, year);
        eventDescriptionID = eventDescription.ID;
        schoolYearID = year.ID;
        DBManager.Events.put(ID, this); //not save()
    }

    /**
     * class providing a comparator allowing years to be sorted by date (Descending)
     */
    public static class sortByYear implements Comparator<Event> {
        public int compare(Event a, Event b) {
            return -a.start.compareTo(b.start);
        }
    }
}

