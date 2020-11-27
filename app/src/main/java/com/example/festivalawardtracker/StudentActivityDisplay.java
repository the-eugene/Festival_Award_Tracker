package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class StudentActivityDisplay extends AppCompatActivity {

    public FloatingActionButton fabEditStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_display_activity);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarStudentDisplay);
        toolbar.setTitle("Student Data");
        toolbar.setTitleTextColor(Color.WHITE);
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

        fabEditStudent = findViewById(R.id.fab_editStudent33);
        fabEditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent( view.getContext(), StudentActivity.class);
                startActivity(activityIntent);
            }
        });

    }
}

