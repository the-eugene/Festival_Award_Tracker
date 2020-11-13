package com.example.festivalawardtracker;

import java.util.ArrayList;
import java.util.List;

public class Festival extends DatabaseAware {
    String name;
    boolean isNFMC;
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

    public boolean isNFMC() {
        return isNFMC;
    }

    public void setNFMC(boolean NFMC) {
        isNFMC = NFMC;
    }

}
