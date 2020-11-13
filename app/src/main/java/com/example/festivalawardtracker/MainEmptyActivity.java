package com.example.festivalawardtracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.GetTokenResult;

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

        } else {
            activityIntent = new Intent(this, Options.class);
        }

        startActivity(activityIntent);
        finish();
        }
    }