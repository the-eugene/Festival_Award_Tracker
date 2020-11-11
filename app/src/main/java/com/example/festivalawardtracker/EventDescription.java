package com.example.festivalawardtracker;

public class EventDescription {
    String name;
    String description;
    Instrument instrument;
    String festivalID;

    public Festival getFestival() {
        return DBManager.Festivals.get(festivalID);
    }
}
