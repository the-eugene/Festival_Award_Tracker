package com.example.festivalawardtracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    FloatingActionButton newEventDescription;
    Context context;
    String fID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(this.getClass().getName(),"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_recyclerview_activity);

        fID=getIntent().getExtras().getString("festivalID",null);
        if (fID == null) Log.wtf(this.getClass().getSimpleName(),"NO ID PASSED");

        recyclerView = findViewById(R.id.recyclerView_eventDescriptions);

        eventDescriptionsRecyclerAdapter = new EventDescriptionsRecyclerAdapter(fID, this);
        recyclerView.setAdapter(eventDescriptionsRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

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
