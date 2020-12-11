package com.example.festivalawardtracker;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene
 * A HashMap that can save and load objects from firebase
 * @param <V> The type parameter of objects this Map will hold
 */
public class DBHashMap<V extends DBAware> extends HashMap<String, V> {
    final Class<V> type; //holds type information for objects to be instantiated
    String pathToData; //holds the FireBase path where data for this HashMap will be stored in DB

    /**
     * Constructor for the class, Assume that path to data just needs to be the name of the class stored
     * @param type type of object that will be stored in this HashMap
     */
    DBHashMap(Class<V> type){
        super();
        this.type = type;
        pathToData = type.getSimpleName();
    }

    /**
     * Constructor for the class, with option for custom location for data.
     * @deprecated NOT YET TESTED
     * @param pathToData FireBase path where data for this HashMap will be stored in DB
     * @param type type of object stored in the HashMap
     */
    DBHashMap(String pathToData, Class<V> type){
        super();
        this.type = type;
        this.pathToData = pathToData;
    }

    /**
     * Standard Map method, but tries to pull object from firebase if it does not exist in the Map
     * @param key the string UID of the object to be retrieved
     * @return the object mapped by key, or null if none exists
     */
    @Override
    public V get(Object key) {
        if (containsKey(key)) { return super.get(key);
        } else {
            V val = DBAware.load((String) key, pathToData, type);
            if (val != null){super.put((String) key, val);}
            return val;
        }
    }

    /**
     * Standard Map method, but also saves object to FireBase. For new objects, a new key will be generated and assigned by FireBase.
     * @param key the string UID of the object, or null if it is a new object
     * @param value the object to be stored
     * @return the value that was previously mapped to the key
     */
    @Nullable
    @Override
    public V put(String key, V value) {
        value.save();
        if (key == null) key = value.ID;
        return super.put(key, value);
    }

    /**
     * NON STANDARD method, for convenience. equivalent to put(obj.ID,obj)
     * @see public V put(String key, V value)
     * @param value the object to be stored
     * @return the value that was previously mapped to the value.ID
     */
    public V put(V value) {
        value.save();
        String key=value.ID;
        return super.put(key, value);
    }

    /**
     * Load all objects of this Map's mapped class from the database into this HashMap
     */
    public void loadAll(){
        clear();
        putAll(getMapByQuery(DBManager.currentDB.child(pathToData)));
    }

    /**
     * Generates a NEW DBHashMap based on results of a query
     * @param query FireBase Query. Results of query must be a list of objects of same type
     * @return new DBHashMap with objects (or empty if nothing was found)
     */
    public Map<String,V> getMapByQuery(Query query){
        DBHashMap<V> result = new DBHashMap<V>(type);
        DataSnapshot ds = DBManager.runQuery(query);
        for (DataSnapshot row:ds.getChildren()){
            V item = row.getValue(type);
            item.ID = row.getKey();
            result.put(item.ID,item);
        }
        return result;
    }
}