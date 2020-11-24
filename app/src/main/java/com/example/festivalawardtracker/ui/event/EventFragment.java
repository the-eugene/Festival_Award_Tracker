package com.example.festivalawardtracker.ui.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.EventActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.StudentActivity;
import com.example.festivalawardtracker.StudentRatingsActivity;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class EventFragment extends Fragment implements View.OnClickListener, RecyclerViewClickInterface {

    RecyclerView recyclerView;
    FloatingActionButton fabNewEvent;
    EventRecyclerAdapter eventRecyclerAdapter;
    List<String> eventName, startDate, endDate, eventLocation, eventInstruments;
    Context thisContext;


    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.main_fragment_event, container, false);

        thisContext = container.getContext();
        Context context = root.getContext();

        eventName = new ArrayList<>();
        startDate = new ArrayList<>();
        endDate = new ArrayList<>();
        eventLocation = new ArrayList<>();
        eventInstruments = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recyclerView_event);
        eventRecyclerAdapter = new EventRecyclerAdapter(eventName,startDate,endDate,eventLocation,eventInstruments,this);
        recyclerView.setAdapter(eventRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        eventName.add("Event 1");
        eventName.add("Event 2");
        eventName.add("Event 3");
        eventName.add("Event 4");
        eventName.add("Event 5");
        eventName.add("Event 6");
        eventName.add("Event 7");
        eventName.add("Event 8");
        eventName.add("Event 9");
        eventName.add("Event 10");
        eventName.add("Event 11");
        eventName.add("Event 12");
        eventName.add("Event 13");
        eventName.add("Event 14");
        eventName.add("Event 15");

        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");
        startDate.add("Today");

        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");
        endDate.add("Tomorrow");

        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");
        eventLocation.add("Here");

        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");
        eventInstruments.add("Piano");

        // Setting up the FAB button for add event
        // https://stackoverflow.com/questions/11857022/fragment-implements-onclicklistener
        fabNewEvent = root.findViewById(R.id.fab_newEvent);
        fabNewEvent.setOnClickListener(this);



        return root; // Returning the view.
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent activityIntent = new Intent( v.getContext(), EventActivity.class);
        startActivity(activityIntent);
    }

    @Override
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(thisContext, StudentRatingsActivity.class);
        startActivity(activityIntent);
    }
}