package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchoolYear extends DatabaseAware{
    int sequence;
    String name;
    LocalDate start;
    LocalDate end;
    List<String> eventIDs=new ArrayList<>();
    boolean containsDate(LocalDate d){return d.compareTo(start)*d.compareTo(end)<=0;}

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getEventIDs() {
        return eventIDs;
    }

    public void setEventIDs(ArrayList<String> eventIDs) {
        this.eventIDs = eventIDs;
    }

    public void addEvent(Event event) {
        if (event.ID==null) DBManager.Events.put(null,event);
        eventIDs.add(event.ID);
        DBManager.SchoolYears.put(ID,this);
    }
}
