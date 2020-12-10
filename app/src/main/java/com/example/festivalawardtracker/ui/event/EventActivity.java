package com.example.festivalawardtracker.ui.event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.eventDescription.EventDescriptionsNewActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Pulls event Data from firebase realtime Database
 * Shows event details in a recycle view
 * clicking any event will go to event descriptions page
 * Clicking the add button will bring up the add event page.
 * @author Cayla, Carlos, Jimmy, & Eugene
 */
public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EVENT_ACTIVITY";
    public String event_description_id;
    EventDescription eventDescriptionDB;
    EventActivityRecyclerAdapter eventActivityRecyclerAdapter;
    RecyclerView recyclerView;
    FloatingActionButton newEvent;
    TextView event_description_description, event_description_name;

    /**
     * Creates and sets the layout
     * loads the info into the recycle view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_recyclerview_activity);
        Log.d(this.getClass().getName(), "Starting OnCreate");

        /* Receiving ID */
        event_description_id = Utilities.retrieveExtra(this, Utilities.EVENT_DESCRIPTION_ID);
        eventDescriptionDB = DBManager.EventDescriptions.get(event_description_id);
        event_description_name = findViewById(R.id.textView_eventName);
        event_description_name.setText(eventDescriptionDB.getName());
        event_description_description = findViewById(R.id.textView_eventDescription);
        event_description_description.setText(eventDescriptionDB.getDescription());

        /* RECYCLER */
        recyclerView = findViewById(R.id.recyclerView_eventsActivity);
        eventActivityRecyclerAdapter= new EventActivityRecyclerAdapter(eventDescriptionDB, this);
        recyclerView.setAdapter(eventActivityRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarEventDescriptions);
        toolbar.setTitle(eventDescriptionDB.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* BUTTON EDIT EVENT */
        // Adding edit icon button to action bar
        MaterialButton button = new MaterialButton(this);
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        toolbarLayoutParams.gravity = Gravity.END;
        button.setLayoutParams(toolbarLayoutParams);
        button.setIconResource(R.drawable.ic_baseline_edit_24);
        button.setBackground(null);
        button.setTooltipText("Edit current event information here");
        toolbar.addView(button);

        /**
         * add click functionality to recycle view that saves info and sends it to next page
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, EventDescriptionsNewActivity.class);
                intent.putExtra(Utilities.EVENT_DESCRIPTION_ID, event_description_id);
                intent.putExtra(Utilities.EVENT_ID, "new");
                startActivity(intent);
            }
        });

        /**
         * Floating action button that brings up the new event page
         */
        /* FLOATING ACTION BUTTON NEW PERFORMANCE ACTIVITY */
        newEvent = findViewById(R.id.btnNewEventActivity);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, EventNewActivity.class);
                Log.d(TAG, " NEW Event For: " + event_description_id);
                intent.putExtra(Utilities.EVENT_DESCRIPTION_ID, event_description_id);
                intent.putExtra(Utilities.EVENT_ID, "new");
                startActivity(intent);
            }
        });
    } // End onCreate


    @Override
    protected void onResume() {
        super.onResume();
        eventActivityRecyclerAdapter.update();
    }


    protected void onPause() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Utilities.EVENT_DESCRIPTION_ID, event_description_id);
        editor.apply();
        super.onPause();
    }
} // End class
