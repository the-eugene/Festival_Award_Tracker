package com.example.festivalawardtracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentRatingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    StudentRatingsRecyclerAdapter studentRatingsRecyclerAdapter;
    List<String> name,age,birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_ratings_recyclerview_activity);

        name = new ArrayList<>();
        age = new ArrayList<>();
        birthday = new ArrayList<>();


        recyclerView =findViewById(R.id.recyclerView_student_ratings);
        studentRatingsRecyclerAdapter = new StudentRatingsRecyclerAdapter(name,age,birthday);
        recyclerView.setAdapter(studentRatingsRecyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        name.add("Billy");
        name.add("Billy2");
        name.add("Billy3");
        name.add("Billy4");
        name.add("Billy5");
        name.add("Billy6");
        name.add("Billy");
        name.add("Billy2");
        name.add("Billy3");
        name.add("Billy4");
        name.add("Billy5");
        name.add("Billy6");

        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");

        age.add("1");
        age.add("2");
        age.add("3");
        age.add("4");
        age.add("5");
        age.add("6");
        age.add("1");
        age.add("2");
        age.add("3");
        age.add("4");
        age.add("5");
        age.add("6");

        /* ACTION BAR */
        toolbar = findViewById(R.id.toolbarStudentRatings);
        toolbar.setTitle("Student Ratings");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
