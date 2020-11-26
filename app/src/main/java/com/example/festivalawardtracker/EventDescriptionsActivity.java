package com.example.festivalawardtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.ui.event.EventFragment;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class EventDescriptionsActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    private Toolbar toolbarEvent;
    EventDescriptionsRecyclerAdapter eventDescriptionsRecyclerAdapter;
    RecyclerView recyclerView;
    List<String> Name, Instrument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_recyclerview_activity);

        Name = new ArrayList<>();
        Instrument = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_event_descriptions);

        eventDescriptionsRecyclerAdapter = new EventDescriptionsRecyclerAdapter(Name, Instrument, this);
        recyclerView.setAdapter(eventDescriptionsRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* ACTION BAR */
        toolbarEvent = findViewById(R.id.toolbarNewEvent);
        toolbarEvent.setTitle("");
        setSupportActionBar(toolbarEvent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(this, EventFragment.class);
        startActivity(activityIntent);

    }
}
