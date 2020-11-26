package com.example.festivalawardtracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class DisplayStudentActivity extends AppCompatActivity {

    private Toolbar toolbarStudentDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_display_activity);

        /* ACTION BAR */
        toolbarStudentDisplay = findViewById(R.id.toolbarStudentDisplay);
        setSupportActionBar(toolbarStudentDisplay);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        }
    }