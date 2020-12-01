package com.example.festivalawardtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

public class MainEmptyActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Intent activityIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Task<GetTokenResult> b = User.getIdToken();
//        Log.d("Token", String.valueOf(b));
        // go straight to main if a token is stored
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, SignInUpActivity.class);
        }
        startActivity(activityIntent);
        finish();
    }
}