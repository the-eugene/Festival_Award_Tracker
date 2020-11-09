package com.example.festivalawardtracker;

import java.util.HashMap;

class DatabaseHashMap<K,V> extends HashMap<K,V> {
    @Override
    public V get(Object key) {
        if (containsKey(key)){ return super.get(key);
        } else {
            V val=getData((String) key);
            put((K) key, val);
            return val;
        }
    }
    private <T> T getData(String key){
        T data=(T) DBManager.getData(key);
        assert data!=null;
        return data;
    }
}