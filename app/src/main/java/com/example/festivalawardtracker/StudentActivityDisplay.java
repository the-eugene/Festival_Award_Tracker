package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class StudentActivityDisplay extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_display_activity);

        /* ACTION BAR */
        toolbar = findViewById(R.id.toolbarStudentDisplay);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String StudentID=getIntent().getExtras().getString("StudentID",null);
        Student s=DBManager.Students.get(StudentID);
        if (s==null) Log.wtf(this.getClass().getSimpleName(),"NO ID PASSED");

        TextView name=findViewById(R.id.textViewStudentFullName);
        name.setText(s.getFullName());

        TextView birthday=findViewById(R.id.textViewStudentBirthday);
        birthday.setText(s.getBirthday());

        TextView phone=findViewById(R.id.textViewStudentPhone);
        phone.setText(s.contact.getPhone());

        TextView email=findViewById(R.id.textViewStudentEmail);
        email.setText(s.getEmail());

        TextView address=findViewById(R.id.textViewStudentAddress);
        address.setText(s.contact.fullAddress());

        TextView instruments=findViewById(R.id.textViewStudentInstrument);
        instruments.setText(s.instrumentList());
    }
}