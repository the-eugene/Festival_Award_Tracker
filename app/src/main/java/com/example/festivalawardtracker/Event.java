package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Event implements DatabaseAware{
int schoolYearID;
LocalDate start;
LocalDate end;
Contact location;

    @Override
    public boolean Save() {
        return false;
    }

    @Override
    public boolean Load() {
        return false;
    }
}
