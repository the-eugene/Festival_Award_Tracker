package com.example.festivalawardtracker.ui.event;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.R;

/**
 * Fragment containing the rating screen for students.
 * This is one of the 3 main fragments inside the main app activity.
 * @author Carlos, Jimmy, Eugene, Cayla
 * @see EventsRatingsActivity For updating student ratings.
 */
public class RateStudentsFragment extends Fragment {

    RecyclerView recyclerView;
    RateStudentsRecyclerAdapter rateStudentsRecyclerAdapter;
    Context thisContext;
    private static final String TAG = "RATE_STUDENTS_FRAGMENT";

    /**
     * Creates the current fragment inside the main activity.
     * @param container MainActivity
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateView: " + this.getClass().getName());

        // Set the rate students recycler view
        View root = inflater.inflate(R.layout.ratings_recyclerview_fragment_main, container, false);

        thisContext = container.getContext();
        Context context = root.getContext();

        recyclerView = root.findViewById(R.id.recyclerView_ratings_fragment);
        rateStudentsRecyclerAdapter = new RateStudentsRecyclerAdapter(DBManager.currentYear, getActivity());
        recyclerView.setAdapter(rateStudentsRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return root; // Returning the view.
    }
} // End fragment