package com.example.festivalawardtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainEmptyActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

//        Task<GetTokenResult> b = User.getIdToken();

//        Log.d("Token", String.valueOf(b));
        // go straight to main if a token is stored
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            activityIntent = new Intent(this, MainActivity.class);
            //TODO: check email and pull data from firebase

        } else {
            activityIntent = new Intent(this, LogInOptionsActivity.class);
        }

        startActivity(activityIntent);
        finish();
        }
    }