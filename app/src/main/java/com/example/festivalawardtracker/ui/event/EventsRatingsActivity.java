package com.example.festivalawardtracker.ui.event;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Displays a screen for adding a rating to a given student.
 * @author Eugene, Jimmy, Cayla, Carlos
 * @see RateStudentsFragment
 */
public class EventsRatingsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventRatingsRecyclerAdapter eventRatingsRecyclerAdapter;
    private static final String TAG = "EVENT_RATINGS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_ratings_recyclerview_activity);
        Log.d(TAG, "OnCreate: " + this.getClass().getName());

        final String  event_ID=getIntent().hasExtra(Utilities.EVENT_ID)?
                getIntent().getExtras().getString(Utilities.EVENT_ID):
                getPreferences(Context.MODE_PRIVATE).getString(Utilities.EVENT_ID, null);

        Event e=DBManager.Events.get(event_ID);
        ((TextView)findViewById(R.id.textView_eventNameR)).setText(DBManager.EventDescriptions.get(e.getEventDescriptionID()).getName());
        ((TextView)findViewById(R.id.textView_startDate)).setText(e.getStart());
        ((TextView)findViewById(R.id.textView_endDate)).setText(e.getEnd());
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
        // Adding and setting button to action bar
        MaterialButton button = new MaterialButton(this);
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        toolbarLayoutParams.gravity = Gravity.END;
        button.setLayoutParams(toolbarLayoutParams);
        button.setText(R.string.save);
        button.setBackground(null);
        button.setTextColor(Color.WHITE);
        toolbar.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event=DBManager.Events.get(event_ID);

                for(int i=0;i<eventRatingsRecyclerAdapter.studentIDs.size();i++){
                    String sID=eventRatingsRecyclerAdapter.studentIDs.get(i);
                    String lvl= eventRatingsRecyclerAdapter.level.get(i).getText().toString();
                    String rating=eventRatingsRecyclerAdapter.rating.get(i).getText().toString();
                    if (lvl.length()>0&&rating.length()>0&&eventRatingsRecyclerAdapter.performances.get(i)==null) {
                        Log.d(this.getClass().getName(), String.format("Student: %s Level: %s, Rating: %s", sID, lvl, rating));
                        DBManager.Students.get(sID).addPerformance(event_ID, event.end, lvl, Integer.parseInt(rating));
                    }
                }

                Toast toast = Toast.makeText(v.getContext(), "New event and ratings saved", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });
    }
}
