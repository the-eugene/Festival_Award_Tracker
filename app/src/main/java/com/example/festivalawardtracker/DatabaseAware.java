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

    public static <T extends DatabaseAware> T load(String key, String location, final Class<T> type){
        DatabaseReference loadFrom=DBManager.currentDB.child(location).child(key);
        final TaskCompletionSource<T> task = new TaskCompletionSource<>();
        loadFrom.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        task.setResult(snapshot.getValue(type));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        task.setException(error.toException());
                    }
                }
        );
        Task<T> t = task.getTask();
        try {
            Tasks.await(t);
        } catch (ExecutionException | InterruptedException e) {
            t = Tasks.forException(e);
            return null;
        }
        if(t.isSuccessful()) {
            T obj=t.getResult();
            obj.ID=key;
            return obj;
        }
        return null;
    }
}

