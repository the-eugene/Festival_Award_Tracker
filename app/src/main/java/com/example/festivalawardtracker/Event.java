package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Event extends DBAware {
    String schoolYearID;
    LocalDate start;
    LocalDate end;
    Contact location;
    String eventDescriptionID;

    public SchoolYear retrieveYear() {
        return DBManager.SchoolYears.get(schoolYearID);
    }
    public String getSchoolYearID() {
        return schoolYearID;
    }
    public void setSchoolYearID(String schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    public boolean isInYear(SchoolYear year) {
        return year!=null&&schoolYearID.equals(year.ID);
    }

    public String getStart() {
        return start.toString();
    }
    public void setStart(String start) { this.start = LocalDate.parse(start); }
    public void setStartLocalDate(LocalDate start) { this.start = start; }

    public String getEnd() {
        return end.toString();
    }
    public void setEnd(String end) {
        this.end = LocalDate.parse(end);
    }
    public void setEndLocalDate(LocalDate end) { this.end = end; }

    public Contact getLocation() {
        return location;
    }
    public void setLocation(Contact location) {
        this.location = location;
    }

    public EventDescription getDescription() { return DBManager.EventDescriptions.get(eventDescriptionID); }
    public String getEventDescriptionID() {
        return eventDescriptionID;
    }
    public void setEventDescriptionID(String eventDescriptionID) {
        this.eventDescriptionID = eventDescriptionID;
    }

    public void addLinks(EventDescription eventDescription,SchoolYear year){
        if (eventDescription.ID==null) DBManager.EventDescriptions.put(null,eventDescription);
        if (year.ID==null) DBManager.SchoolYears.put(null,year);
        eventDescriptionID=eventDescription.ID;
        schoolYearID=year.ID;
        DBManager.Events.put(ID,this); //not save()
    }
}

