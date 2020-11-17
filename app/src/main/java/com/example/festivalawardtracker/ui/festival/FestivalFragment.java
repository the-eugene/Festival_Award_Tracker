package com.example.festivalawardtracker.ui.festival;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.festivalawardtracker.EventActivity;
import com.example.festivalawardtracker.FestivalActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.StudentActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class FestivalFragment extends Fragment implements View.OnClickListener {

    private FestivalViewModel festivalViewModel;
    FloatingActionButton fabNewFestival;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        festivalViewModel = new ViewModelProvider(this).get(FestivalViewModel.class);
        View root = inflater.inflate(R.layout.main_fragment_festival, container, false);

        // Setting up the FAB button for add festival
        fabNewFestival = root.findViewById(R.id.fab_newFestival);
        fabNewFestival.setOnClickListener(this);

        return root;
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent activityIntent = new Intent( v.getContext(), FestivalActivity.class);
        startActivity(activityIntent);
    }
}