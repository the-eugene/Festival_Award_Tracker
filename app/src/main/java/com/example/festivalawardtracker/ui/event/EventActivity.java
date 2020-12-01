package com.example.festivalawardtracker.ui.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
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
    private static final String EVENT_DESCRIPTION_ID = "EVENT_DESCRIPTION_ID";
    public String event_description_id;
    EventDescription eventDescription;
    EventActivityRecyclerAdapter eventActivityRecyclerAdapter;
    RecyclerView recyclerView;
    FloatingActionButton newEvent;
    Context thisContext;
    Context context;
    TextView event_description_description, event_description_name;

    /**
     *
     * @author
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_recyclerview_activity);
        Log.d(TAG,"onCreate" + this.getClass().getName());

        /* Receiving ID */
        event_description_id = Utilities.retrieveExtra(this, EVENT_DESCRIPTION_ID);
        eventDescription=DBManager.EventDescriptions.get(event_description_id);

        event_description_name = findViewById(R.id.textView_eventName);
        event_description_name.setText(eventDescription.getName());
        event_description_description = findViewById(R.id.textView_eventDescription);
        event_description_description.setText(eventDescription.getDescription());

        /* RECYCLER */
        recyclerView = findViewById(R.id.recyclerView_eventsActivity);
        eventActivityRecyclerAdapter= new EventActivityRecyclerAdapter(eventDescription, this);
        recyclerView.setAdapter(eventActivityRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* BUTTON NEW EVENT ACTIVITY */
        newEvent = findViewById(R.id.btnNewEventActivity);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, EventNewActivity.class);
                Log.d(TAG, " NEW Event For: " + event_description_id);
                intent.putExtra(EVENT_DESCRIPTION_ID, event_description_id);
                startActivity(intent);
            }
        });

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarEventDescriptions);
        toolbar.setTitle("Performances for "+eventDescription.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } // End onCreate

//    private Activity getActivity() {
//    }

    /**
     *
     * @author
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(EventActivity.this, EventsRatingsActivity.class);
        startActivity(activityIntent);
    } // End onItemClick

    /**
     *
     * @author
     */
    @Override
    protected void onResume() {
        super.onResume();
        eventActivityRecyclerAdapter.update();
    }

    /**
     *
     * @author
     */
    protected void onPause() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EVENT_DESCRIPTION_ID, event_description_id);
        editor.apply();
        super.onPause();
    }
} // End class
