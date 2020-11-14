package com.example.festivalawardtracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author Carlos W Mercado
 */
public class NewStudentActivityContact extends AppCompatActivity {

    private Toolbar toolbar;

    /**
     *
     * @param savedInstanceState Add.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student_contact);

        // ACTION BAR
        toolbar = findViewById(R.id.toolbarNewStudentContact);
        toolbar.setTitle("Add contact");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
