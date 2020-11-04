package com.example.festivalawardtracker;

public class Event implements DatabaseAware{
    String name;
    String description;
    Instrument instrument;
    int festivalID;

    @Override
    public boolean Save() {
        return false;
    }

    @Override
    public boolean Load() {
        return false;
    }
}
