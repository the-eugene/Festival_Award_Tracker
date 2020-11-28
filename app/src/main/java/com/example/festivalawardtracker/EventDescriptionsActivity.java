package com.example.festivalawardtracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class EventDescriptionsActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    EventDescriptionsRecyclerAdapter eventDescriptionsRecyclerAdapter;
    RecyclerView recyclerView;
    List<String> Name, Instrument;
    FloatingActionButton newEventDescription;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_recyclerview_activity);

        Name = new ArrayList<>();
        Instrument = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_eventDescriptions);

        eventDescriptionsRecyclerAdapter = new EventDescriptionsRecyclerAdapter(Name, Instrument, this);
        recyclerView.setAdapter(eventDescriptionsRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Name.add("Name1");
        Name.add("Name2");
        Name.add("Name3");
        Name.add("Name4");
        Name.add("Name5");
        Name.add("Name6");
        Name.add("Name7");
        Name.add("Name8");
        Name.add("Name9");
        Name.add("Name10");
        Name.add("Name11");
        Name.add("Name12");
        Name.add("Name13");
        Name.add("Name14");
        Name.add("Name15");

        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");
        Instrument.add("Piano");

        newEventDescription = findViewById(R.id.goTo_EventDescriptionsNewActivity);
        newEventDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(EventDescriptionsActivity.this, EventDescriptionsNewActivity.class);
                startActivity(Intent);
            }
        });


        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_eventDescriptions);
        toolbar.setTitle("Festival");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onItemClick(int position) {
        Intent Intent = new Intent(EventDescriptionsActivity.this, EventActivity.class);
        startActivity(Intent);
    }

}
