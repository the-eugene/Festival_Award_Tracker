package com.example.festivalawardtracker;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

class DBHashMap<V extends DBAware> extends HashMap<String,V> {
    final Class<V> type;
    String pathToData;

    DBHashMap(Class<V> type){
        super();
        this.type=type;
        pathToData=type.getSimpleName();
    }

    DBHashMap(String pathToData, Class<V> type){
        super();
        this.type=type;
        this.pathToData=pathToData;
    }

    @Override
    public V get(Object key) {
        if (containsKey(key)){ return super.get(key);
        } else {
            V val= DBAware.load((String) key,pathToData,type);
            if (val!=null){super.put((String) key, val);}
            return val;
        }
    }

    @Nullable
    @Override
    public V put(String key, V value) {
        value.save();
        if (key==null) key=value.ID;
        return super.put(key, value);
    }

    public void loadAll(){
        clear();
        putAll(getMapByQuery(DBManager.currentDB.child(pathToData)));
    }

    public Map<String,V> getMapByQuery(Query query){
        DBHashMap<V> result=new DBHashMap<V>(type);
        DataSnapshot ds=DBManager.runQuery(query);
        for (DataSnapshot row:ds.getChildren()){
            V item = row.getValue(type);
            item.ID=row.getKey();
            result.put(item.ID,item);
        }
        return result;
    }
}