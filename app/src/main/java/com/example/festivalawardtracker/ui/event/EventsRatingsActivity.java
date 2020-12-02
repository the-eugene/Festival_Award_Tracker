package com.example.festivalawardtracker.ui.event;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.event.EventRatingsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsRatingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventRatingsRecyclerAdapter eventRatingsRecyclerAdapter;
    List<String> name,age,birthday;
    ArrayList<EventStudentRatingEditTextBoxes> level;
    ArrayList<EventStudentRatingEditTextBoxes> rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_ratings_recyclerview_activity);

        name = new ArrayList<>();
        age = new ArrayList<>();
        birthday = new ArrayList<>();


        recyclerView =findViewById(R.id.recyclerView_studentRatings);
        eventRatingsRecyclerAdapter = new EventRatingsRecyclerAdapter(name,age,birthday,level,rating);
        recyclerView.setAdapter(eventRatingsRecyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        name.add("Billy");
        name.add("Billy2");
        name.add("Billy3");
        name.add("Billy4");
        name.add("Billy5");
        name.add("Billy6");
        name.add("Billy7");
        name.add("Billy8");
        name.add("Billy9");
        name.add("Billy10");
        name.add("Billy11");
        name.add("Billy12");

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
        age.add("7");
        age.add("8");
        age.add("9");
        age.add("10");
        age.add("11");
        age.add("12");

        level = populateLevel();
        rating = populateRating();


        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_studentRatings);
        toolbar.setTitle("Student Ratings");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private ArrayList<EventStudentRatingEditTextBoxes> populateLevel() {

        ArrayList<EventStudentRatingEditTextBoxes> levelList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            EventStudentRatingEditTextBoxes editLevel = new EventStudentRatingEditTextBoxes();
            editLevel.setEditLevelValue(String.valueOf(i));
            levelList.add(editLevel);
        }
        return levelList;
    }

    private ArrayList<EventStudentRatingEditTextBoxes> populateRating() {

        ArrayList<EventStudentRatingEditTextBoxes> ratingList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            EventStudentRatingEditTextBoxes editRating = new EventStudentRatingEditTextBoxes();
            editRating.setEditLevelValue(String.valueOf(i));
            ratingList.add(editRating);
        }
        return ratingList;
    }

}
