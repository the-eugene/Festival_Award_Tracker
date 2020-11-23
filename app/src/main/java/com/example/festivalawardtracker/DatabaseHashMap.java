package com.example.festivalawardtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

class DatabaseHashMap<V extends DatabaseAware> extends HashMap<String,V> {
    final Class<V> type;
    String pathToData;

    DatabaseHashMap( Class<V> type){
        super();
        this.type=type;
        pathToData=type.getSimpleName();
    }

    DatabaseHashMap(String pathToData, Class<V> type){
        super();
        this.type=type;
        this.pathToData=pathToData;
    }

    @Override
    public V get(Object key) {
        if (containsKey(key)){ return super.get(key);
        } else {
            V val=DatabaseAware.load((String) key,pathToData,type);
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
        DatabaseHashMap <V> result=new DatabaseHashMap<V>(type);
        DataSnapshot ds=DBManager.runQuery(query);
        for (DataSnapshot row:ds.getChildren()){
            V item = row.getValue(type);
            item.ID=row.getKey();
            result.put(item.ID,item);
        }
        return result;
    }
}