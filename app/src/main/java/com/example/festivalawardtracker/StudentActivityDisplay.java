package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class StudentActivityDisplay extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextDatePicker;

    /**
     *
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_student);

//        /* ACTION BAR */
//        toolbar = findViewById(R.id.toolbarStudentDisplay);
//        toolbar.setTitle("Student Data");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}