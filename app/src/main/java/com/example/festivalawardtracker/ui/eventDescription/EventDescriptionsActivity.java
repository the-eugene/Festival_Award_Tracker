package com.example.festivalawardtracker.ui.eventDescription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.event.EventActivity;
import com.example.festivalawardtracker.ui.festival.FestivalActivity;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 *
 * @author
 */
public class EventDescriptionsActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private static final String TAG = "EVENT_DESCRIPTIONS_ACTIVITY";

    EventDescriptionsRecyclerAdapter eventDescriptionsRecyclerAdapter;
    RecyclerView recyclerView;

    private static final String FESTIVAL_ID = "FESTIVAL_ID";
    public String _festivalID;;

    FloatingActionButton btnNewEventDescription;
    FloatingActionButton btnEditFestival;
    Context context;
    /**
     *
     * @author
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_recyclerview_activity);
        Log.d(TAG,"onCreate" + this.getClass().getName());

        /* Retrieving festival ID */
        _festivalID = Utilities.retrieveExtra(this, FESTIVAL_ID);

        /* RECYCLER VIEW and ADAPTER*/
        recyclerView = findViewById(R.id.recyclerView_eventDescriptions);
        eventDescriptionsRecyclerAdapter = new EventDescriptionsRecyclerAdapter(_festivalID, this);
        recyclerView.setAdapter(eventDescriptionsRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* BUTTON NEW EVENT DESCRIPTION */
        btnNewEventDescription = findViewById(R.id.btnNewEventDescription);
        btnNewEventDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDescriptionsActivity.this, EventDescriptionsNewActivity.class);
                intent.putExtra(FESTIVAL_ID, _festivalID);
                startActivity(intent);
            }
        });

        /* BUTTON EDIT FESTIVAL */
        btnEditFestival = findViewById(R.id.btnEditFestival);
        btnEditFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventDescriptionsActivity.this, FestivalActivity.class);
                intent.putExtra(FESTIVAL_ID, _festivalID);
                startActivity(intent);
            }
        });

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_eventDescriptions);
        toolbar.setTitle("Event Descriptions");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    } // End OnCreate

    /**
     *
     * @author
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(EventDescriptionsActivity.this, EventActivity.class);
        intent.putExtra(FESTIVAL_ID, _festivalID);
        startActivity(intent);
    }

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
        editor.putString(FESTIVAL_ID, _festivalID);
        editor.apply();
        super.onPause();
    }
} // End class
