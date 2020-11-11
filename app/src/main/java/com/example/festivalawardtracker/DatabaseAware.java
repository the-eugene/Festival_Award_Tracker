package com.example.festivalawardtracker;

import com.google.firebase.database.DatabaseReference;

public class DatabaseAware {
    String ID=null;
    public void save(){
        DatabaseReference saveTo;
        if (ID==null){
            saveTo=DBManager.DB.getReference().child(this.getClass().toString()).push();
            ID=saveTo.getKey();
        } else {
            saveTo=DBManager.DB.getReference().child(this.getClass().toString()).child(ID);
        }
        saveTo.setValue(this);
    }
}
