package com.example.festivalawardtracker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 *
 * @author Eugene
 * @see Event
 * @see Person
 * @see DBHashMap
 * @see Festival
 * @see SchoolYear
 */
public class DBAware {
    public String ID = null; //this holds UID for all derived classes

    /**
     * Save the object to FireBase, generate a new UID, or update an existing object
     * use this when objects are being updated and references to them are already in cache
     */
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

    /**
     * Load a single object from the database by its UID key
     * @param key the UID of the object being loaded
     * @param type the class of object being loaded
     * @param <T> the class of object being loaded
     * @return the object being loaded or null;
     */
    public static <T extends DBAware> T load(String key, final Class<T> type){
        return load(key, type.getSimpleName(),type);
    }

    /**
     * Load a single object from the database by its UID key from a specific location
     * @param key String key for the object
     * @param location firebase path to where the objects are stored
     * @param type the class of object being loaded
     * @param <T> the class of object being loaded
     * @return the object being loaded or null
     */
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

