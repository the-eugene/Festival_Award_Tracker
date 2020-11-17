package com.example.festivalawardtracker.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.festivalawardtracker.EventActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.StudentActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class EventFragment extends Fragment implements View.OnClickListener {

    private EventViewModel eventViewModel;
    FloatingActionButton fabNewEvent;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        View root = inflater.inflate(R.layout.main_fragment_event, container, false);

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
}