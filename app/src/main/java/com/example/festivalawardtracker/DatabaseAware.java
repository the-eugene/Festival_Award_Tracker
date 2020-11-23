package com.example.festivalawardtracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutionException;

public class DatabaseAware {
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

    public static <T extends DatabaseAware> T load(String key, final Class<T> type){
        return load(key, type.getSimpleName(),type);
    }

    public static <T extends DatabaseAware> T load(String key, String location, Class<T> type){
        DatabaseReference loadFrom=DBManager.currentDB.child(location).child(key);
        DataSnapshot ds=DBManager.runQuery(loadFrom);
        T obj=null;
        if (ds!=null) {
            obj = ds.getValue(type);
            obj.ID = key;
        }
        return obj;
    }
}

