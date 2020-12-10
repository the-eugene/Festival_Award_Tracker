package com.example.festivalawardtracker;

/**
 * Model Class representing metadata common to recurring events
 * Events consist of Event and an EventDescription classes
 * Event class are the actual times and places where students will perform during a festival
 * Events are paired with EventDescriptions - which represent data that stays the same from year to year
 * EventDescriptions are a convenient way to group events - such as all piano solos.
 * @see Event
 */
public class EventDescription extends DBAware {
    public String name;
    public Instrument instrument;
    String description;
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

    public void setFestivalID(String festivalID) {
        this.festivalID = festivalID;
    }

    public Festival retrieveFestival() {
        return DBManager.Festivals.get(festivalID);
    }
}