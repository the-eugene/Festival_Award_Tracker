package com.example.festivalawardtracker;

public class DatabaseAware {
    String ID;

    public boolean save(){
        return DBManager.saveData(this);
    }
}
