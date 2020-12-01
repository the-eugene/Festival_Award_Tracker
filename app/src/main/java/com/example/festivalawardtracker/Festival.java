package com.example.festivalawardtracker;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eugene
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
