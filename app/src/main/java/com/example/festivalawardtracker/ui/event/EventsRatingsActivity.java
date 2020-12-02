package com.example.festivalawardtracker.ui.event;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.event.EventRatingsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsRatingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventRatingsRecyclerAdapter eventRatingsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_ratings_recyclerview_activity);

        final String  event_ID=getIntent().hasExtra(Utilities.EVENT_ID)?
                getIntent().getExtras().getString(Utilities.EVENT_ID):
                getPreferences(Context.MODE_PRIVATE).getString(Utilities.EVENT_ID, null);

        recyclerView =findViewById(R.id.recyclerView_studentRatings);
        eventRatingsRecyclerAdapter = new EventRatingsRecyclerAdapter(event_ID,this);
        recyclerView.setAdapter(eventRatingsRecyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_studentRatings);
        toolbar.setTitle("Student Ratings");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //SAVE BUTTON
        Button eventSaveButton = findViewById(R.id.btnSaveRatings);
        eventSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event=DBManager.Events.get(event_ID);

                for(int i=0;i<eventRatingsRecyclerAdapter.studentIDs.size();i++){
                    String sID=eventRatingsRecyclerAdapter.studentIDs.get(i);
                    String lvl= eventRatingsRecyclerAdapter.level.get(i).getText().toString();
                    String rating=eventRatingsRecyclerAdapter.rating.get(i).getText().toString();
                    if (lvl.length()>0&&rating.length()>0) {
                        Log.d(this.getClass().getName(), String.format("Student: %s Level: %s, Rating: %s", sID, lvl, rating));
                        //DBManager.Students.get(sID).addPerformance(event_ID, event.end, lvl, Integer.parseInt(rating));
                    }
                }

                Toast toast = Toast.makeText(v.getContext(), "New event saved", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });
    }
}
