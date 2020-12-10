package com.example.festivalawardtracker.ui.eventDescription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.festival.FestivalActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * It displays the short description of the events of the selected festival on the festival list.
 * Allows the edition of the festival name.
 * Allows the addition of new events (and their description).
 * Long press over existing events events in the list allow their individual edition.
 * @author Carlos, Jimmy, Cayla, Eugene
 * @see com.example.festivalawardtracker.ui.festival.FestivalFragment Previous screen.
 * @see EventDescriptionsNewActivity Addition of new events and their description from floating button.
 * @see FestivalActivity For the edition of the current festival information.
 * @see com.example.festivalawardtracker.ui.event.EventActivity Performances corresponding to any selected event from the list.
 */
public class EventDescriptionsActivity extends AppCompatActivity {

    private static final String TAG = "EVENT_DESCRIPTIONS_ACTIVITY";

    EventDescriptionsRecyclerAdapter eventDescriptionsRecyclerAdapter;
    RecyclerView recyclerView;

    private static final String FESTIVAL_ID = "FESTIVAL_ID";
    public String festival_ID;;

    FloatingActionButton btnNewEventDescription;
    /**
     * Contains the recycler view with the list of events (and their descriptions) contained
     * in the selected festival (previous screen).
     * @author Carlos, Jimmy, Cayla
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_recyclerview_activity);
        Log.d(TAG, "OnCreate: " + this.getClass().getName());

        /* Retrieving festival ID */
        festival_ID = Utilities.retrieveExtra(this, FESTIVAL_ID);
        Festival festival= DBManager.Festivals.get(festival_ID);

        /* RECYCLER VIEW and ADAPTER*/
        recyclerView = findViewById(R.id.recyclerView_eventDescriptions);
        eventDescriptionsRecyclerAdapter = new EventDescriptionsRecyclerAdapter(festival_ID, this);
        recyclerView.setAdapter(eventDescriptionsRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_eventDescriptions);
        toolbar.setTitle(festival.name+" Events");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* BUTTON EDIT FESTIVAL */
        // Adding edit icon button to action bar
        MaterialButton button = new MaterialButton(this);
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        toolbarLayoutParams.gravity = Gravity.END;
        button.setLayoutParams(toolbarLayoutParams);
        button.setIconResource(R.drawable.ic_baseline_edit_24);
        button.setBackground(null);
        button.setTooltipText("Edit current festival information here");
        toolbar.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDescriptionsActivity.this, FestivalActivity.class);
                intent.putExtra(FESTIVAL_ID, festival_ID);
                startActivity(intent);
            }
        });

        /* BUTTON NEW EVENT  */
        btnNewEventDescription = findViewById(R.id.btnNewEventDescription);
        btnNewEventDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDescriptionsActivity.this, EventDescriptionsNewActivity.class);
                intent.putExtra(FESTIVAL_ID, festival_ID);
                intent.putExtra(Utilities.EVENT_DESCRIPTION_ID, "new");
                startActivity(intent);
            }
        });
    } // End OnCreate


    /**
     *
     * @author
     */
    @Override
    protected void onResume() {
        super.onResume();
        eventDescriptionsRecyclerAdapter.update();
    }

    /**
     *
     * @author
     */
    protected void onPause() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(FESTIVAL_ID, festival_ID);
        editor.apply();
        super.onPause();
    }
} // End class
