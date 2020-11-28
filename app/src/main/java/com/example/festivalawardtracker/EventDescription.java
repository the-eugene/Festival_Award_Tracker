package com.example.festivalawardtracker;

public class EventDescription extends DBAware {
    String name;
    String description;
    Instrument instrument;
    String festivalID;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Instrument getInstrument() {
        return instrument;
    }
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getFestivalID() {
        return festivalID;
    }
    public void setFestivalID(String festivalID) { this.festivalID = festivalID; }
    public Festival retrieveFestival() { return DBManager.Festivals.get(festivalID); }
}