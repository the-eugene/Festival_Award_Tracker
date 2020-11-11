package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Event extends DatabaseAware{
String schoolYearID;
LocalDate start;
LocalDate end;
Contact location;
String EventDescriptionID;

    public SchoolYear getYear() {
        return DBManager.SchoolYears.get(schoolYearID);
    }

    public EventDescription getDescription() {
        return DBManager.EventDescriptions.get(EventDescriptionID);
    }

    public boolean isInYear(SchoolYear year) {
        return year.ID.equals(schoolYearID);
    }
}
