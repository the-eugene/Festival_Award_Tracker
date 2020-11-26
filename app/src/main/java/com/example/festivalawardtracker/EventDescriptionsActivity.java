package com.example.festivalawardtracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
    Context context;

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






        /* ACTION BAR */
        toolbarEvent = findViewById(R.id.toolbarEventDescriptions);
        toolbarEvent.setTitle("");
        setSupportActionBar(toolbarEvent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onItemClick(int position) {
        Navigation.findNavController(this,R.id.recyclerView_event_descriptions).navigate(R.id.action_festival_to_event);

    }
}