package com.example.festivalawardtracker;
import android.widget.EditText;

public class UserDatabase {
    String UName;
    public UserDatabase(String userName) {
        UName = userName;
    }
    public String getUName() {
        return UName;
    }
}