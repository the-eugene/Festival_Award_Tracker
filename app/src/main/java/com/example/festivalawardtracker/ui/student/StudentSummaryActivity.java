package com.example.festivalawardtracker.ui.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StudentSummaryActivity extends AppCompatActivity {

    public FloatingActionButton fabEditStudent;
    RecyclerView recyclerView;
    StudentSummaryRecyclerAdapter studentSummaryRecyclerAdapter;
    public static final String STUDENT_ID = "StudentID";
    String StudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_display_activity);

        if (getIntent().hasExtra(STUDENT_ID))
            StudentID = getIntent().getExtras().getString(STUDENT_ID);
        else
            StudentID= getPreferences(Context.MODE_PRIVATE).getString(STUDENT_ID,null);
        if(StudentID==null) Log.wtf(this.getClass().getSimpleName(),"NO ID PASSED");
        Student s= DBManager.Students.get(StudentID);

        recyclerView = findViewById(R.id.StudentDisplayRecyclerview);
        studentSummaryRecyclerAdapter = new StudentSummaryRecyclerAdapter(s,this);
        recyclerView.setAdapter(studentSummaryRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarStudentDisplay);
        toolbar.setTitle("Student Data");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


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
                Intent activityIntent = new Intent( view.getContext(), StudentEditActivity.class);
                activityIntent.putExtra(STUDENT_ID, StudentID);
                startActivity(activityIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //run queries here if necessary...
    }

    protected void onPause() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(STUDENT_ID,StudentID);
        editor.apply();
        super.onPause();
    }
}