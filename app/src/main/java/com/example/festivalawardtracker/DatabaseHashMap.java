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
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
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

    //    public V getData(String key){
//            final TaskCompletionSource<V> task = new TaskCompletionSource<>();
//            mapRoot.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            task.setResult(snapshot.getValue(type));
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            task.setException(error.toException());
//                        }
//                    }
//            );
//            Task<V> t = task.getTask();
//            try {
//                Tasks.await(t);
//            } catch (ExecutionException | InterruptedException e) {
//                t = Tasks.forException(e);
//                return null;
//            }
//            if(t.isSuccessful()) {
//                V obj=t.getResult();
//                obj.ID=key;
//                return obj;
//            }
//            return null;
//        }
}