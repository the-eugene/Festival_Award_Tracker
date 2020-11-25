package com.example.festivalawardtracker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class DBAware {
    String ID = null;
    public void setID(String ID) { this.ID = ID; }

    public void save(){
        DatabaseReference saveTo;
        if (ID == null) {
            saveTo = DBManager.currentDB.child(this.getClass().getSimpleName()).push();
            ID = saveTo.getKey();
        } else {
            saveTo=DBManager.currentDB.child(this.getClass().getSimpleName()).child(ID);
        }
        saveTo.setValue(this);
    }

    public static <T extends DBAware> T load(String key, final Class<T> type){
        return load(key, type.getSimpleName(),type);
    }

    public static <T extends DBAware> T load(String key, String location, Class<T> type){
        DatabaseReference loadFrom = DBManager.currentDB.child(location).child(key);
        DataSnapshot ds = DBManager.runQuery(loadFrom);
        T obj = null;
        if (ds != null) {
            obj = ds.getValue(type);
            obj.ID = key;
        }
        return obj;
    }
}

