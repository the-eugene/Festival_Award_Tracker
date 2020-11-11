package com.example.festivalawardtracker;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

class DatabaseHashMap<K,V> extends HashMap<K,V> {
    DatabaseReference mapRoot;
    DatabaseHashMap(String pathToData){
        super();
        mapRoot=DBManager.DB.getReference(pathToData);
    }
    @Override
    public V get(Object key) {
        if (containsKey(key)){ return super.get(key);
        } else {
            V val=getData((String) key);
            if (val!=null){put((K) key, val);}
            return val;
        }
    }
    private V getData(String key){
        final TaskCompletionSource<V> task = new TaskCompletionSource<>();
        mapRoot.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<V> v=new GenericTypeIndicator<V>() {};
                        task.setResult(snapshot.getValue(v));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        task.setException(error.toException());
                    }
                }
        );
        Task<V> t = task.getTask();
        try {
            Tasks.await(t);
        } catch (ExecutionException | InterruptedException e) {
            t = Tasks.forException(e);
            return null;
       }
        if(t.isSuccessful()) {
            return t.getResult();
        }
        return null;
    }
}