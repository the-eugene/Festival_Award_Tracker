package com.example.festivalawardtracker.ui.event;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.event.EventRatingsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsRatingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventRatingsRecyclerAdapter eventRatingsRecyclerAdapter;
    List<String> name,age,birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_ratings_recyclerview_activity);

        name = new ArrayList<>();
        age = new ArrayList<>();
        birthday = new ArrayList<>();


        recyclerView =findViewById(R.id.recyclerView_studentRatings);
        eventRatingsRecyclerAdapter = new EventRatingsRecyclerAdapter(name,age,birthday);
        recyclerView.setAdapter(eventRatingsRecyclerAdapter);

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
        Toolbar toolbar = findViewById(R.id.toolbar_studentRatings);
        toolbar.setTitle("Student Ratings");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}