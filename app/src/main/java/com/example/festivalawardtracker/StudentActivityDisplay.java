package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class StudentActivityDisplay extends AppCompatActivity {

    public FloatingActionButton fabEditStudent;
    RecyclerView recyclerView;
    StudentDisplayRecyclerAdapter studentDisplayRecyclerAdapter;
    List<String> year,event,ccs,tp,awards,level;
    public static final String STUDENT_ID = "STUDENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_display_activity);

        year = new ArrayList<>();
        event = new ArrayList<>();
        ccs = new ArrayList<>();
        tp = new ArrayList<>();
        awards = new ArrayList<>();
        level = new ArrayList<>();

        recyclerView = findViewById(R.id.StudentDisplayRecyclerview);
        studentDisplayRecyclerAdapter = new StudentDisplayRecyclerAdapter(year,event,ccs,tp,awards,level);
        recyclerView.setAdapter(studentDisplayRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarStudentDisplay);
        toolbar.setTitle("Student Data");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        final String StudentID=getIntent().getExtras().getString("StudentID",null);
        Log.d("STUDENT_DISPLAY", "StudentID: " + StudentID);
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
                Intent activityIntent = new Intent( view.getContext(), StudentActivityEdit.class);
                activityIntent.putExtra(STUDENT_ID, StudentID);
                startActivity(activityIntent);
            }
        });

        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");
        year.add("2000");

        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");
        event.add("piano");


        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");
        ccs.add("4");

        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");
        tp.add("200");

        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");
        awards.add("Smile");

        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
        level.add("5");
    }
}

