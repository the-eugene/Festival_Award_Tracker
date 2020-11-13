package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Event extends DatabaseAware{
String schoolYearID;
LocalDate start;
LocalDate end;
Contact location;
String eventDescriptionID;

    public SchoolYear retrieveYear() {
        return DBManager.SchoolYears.get(schoolYearID);
    }

    public EventDescription getDescription() {
        return DBManager.EventDescriptions.get(eventDescriptionID);
    }

    public boolean isInYear(SchoolYear year) {
        return year.ID.equals(schoolYearID);
    }

    public String getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(String schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    public String getStart() {
        return start.toString();
    }

    public void setStart(String start) {
        this.start = LocalDate.parse(start);
    }

    public String getEnd() {
        return end.toString();
    }

    public void setEnd(String end) {
        this.end = LocalDate.parse(end);
    }

    public Contact getLocation() {
        return location;
    }

    public void setLocation(Contact location) {
        this.location = location;
    }

    public String getEventDescriptionID() {
        return eventDescriptionID;
    }

    public void setEventDescriptionID(String eventDescriptionID) {
        this.eventDescriptionID = eventDescriptionID;
    }
}
