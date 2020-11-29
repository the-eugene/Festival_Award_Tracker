package com.example.festivalawardtracker.ui.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    Context thisContext;
    Context context;
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
        Log.d(this.getClass().getName(),"onCreateView");

        /* RECYCLER */
        recyclerView = findViewById(R.id.recyclerView_eventsActivity);
        eventActivityRecyclerAdapter= new EventActivityRecyclerAdapter(DBManager.Events);
        recyclerView.setAdapter(eventActivityRecyclerAdapter);


        class queryThread implements Runnable{
            final Activity activity;
            queryThread(Activity activity){
                this.activity = activity;
            }
            @Override
            public void run(){
                DBManager.Events.loadAll();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eventActivityRecyclerAdapter.updateEventList();
                        eventActivityRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
//        new Thread(new queryThread()).start();

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

    }
}
