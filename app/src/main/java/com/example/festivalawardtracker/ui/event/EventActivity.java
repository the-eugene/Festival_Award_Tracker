package com.example.festivalawardtracker.ui.event;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class EventActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private static final String TAG = "EVENT_ACTIVITY";
    EventActivityRecyclerAdapter eventActivityRecyclerAdapter;
    RecyclerView recyclerView;
    List<String> eventName, startDate, endDate, eventInstruments;
    FloatingActionButton newEvent;
    TextView event,eventDescription;

    /**
     *
     * @author
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_recyclerview_activity);

        eventName = new ArrayList<>();
        startDate = new ArrayList<>();
        endDate = new ArrayList<>();
        eventInstruments = new ArrayList<>();

        event = findViewById(R.id.textView_eventName);
        eventDescription = findViewById(R.id.textView_eventDescription);

        /* RECYCLER */
        recyclerView = findViewById(R.id.recyclerView_eventsActivity);

        eventActivityRecyclerAdapter= new EventActivityRecyclerAdapter(eventName,startDate,endDate,eventInstruments, this);
        recyclerView.setAdapter(eventActivityRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");
        eventName.add("Person");

        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");
        startDate.add("November 5");

        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");
        endDate.add("November 6");

        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");
        eventInstruments.add("piano");

        /* BUTTON NEW EVENT ACTIVITY */
        newEvent = findViewById(R.id.btnNewEventActivity);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(EventActivity.this, EventNewActivity.class);
                startActivity(Intent);
            }
        });

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarEventDescriptions);
        toolbar.setTitle("Event");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     *
     * @author
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(EventActivity.this, EventsRatingsActivity.class);
        startActivity(activityIntent);

    }
}
