package com.example.festivalawardtracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for festivals
 * Festivals are a group of Events which are related to one another (but not the same)
 * For example, a string festival may include a violin solo, violin duet, cello solo, etc.
 * Identical events (except dates and locations) are further grouped under EventDescriptions
 * @author Eugene
 * @see Event
 * @see EventDescription
 * @see DBAware
 */
public class Festival extends DBAware {
    public String name;
    public boolean isNFMC;
    List<String> eventDescriptionIDs = new ArrayList<>();

    public List<String> getEventDescriptions() {
        return eventDescriptionIDs;
    }

    public void setEventDescriptions(List<String> eventDescriptions) {
        this.eventDescriptionIDs = eventDescriptions;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean getNFMC() {
        return isNFMC;
    }
    public void setNFMC(boolean NFMC) {
        isNFMC = NFMC;
    }

}
