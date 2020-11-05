package com.example.festivalawardtracker;

public class Teacher implements DatabaseAware {
    int[] studentIDs;

    @Override
    public boolean Save() {
        return false;
    }

    @Override
    public boolean Load() {
        return false;
    }
}
