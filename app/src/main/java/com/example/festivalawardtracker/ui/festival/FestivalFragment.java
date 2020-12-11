package com.example.festivalawardtracker.ui.festival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

/**
 * Displays a list of festivals. Each of which provides access to its own list of events.
 * It's possible to add new festivals on this screen.
 * Long press on the festival name enables edition.
 * Addition or edition of festivals updates the list and its information in the remote database.
 * @author Carlos, Cayla, Jimmy, Eugene
 * @see FestivalActivity Used for addition and edition of festivals.
 * @see com.example.festivalawardtracker.ui.eventDescription.EventDescriptionsActivity Displays name and brief description of the events for the selected festivals.
 */
public class FestivalFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton fabNewFestival;
    FestivalRecyclerAdapter festivalRecyclerAdapter;
    Context thisContext;
    Context context;
    private static final String TAG = "FESTIVAL_FRAGMENT";


    /**
     * create the view and loads the data
     * @param inflater inflates the view
     * @param container the view group
     * @param savedInstanceState the bundle of information
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.festival_recyclerview_fragment_main, container, false);
        Log.d(TAG, "OnCreateView: " + this.getClass().getName());

        thisContext = container.getContext();
        context = root.getContext();

        recyclerView = root.findViewById(R.id.recyclerView_festivals);
        festivalRecyclerAdapter = new FestivalRecyclerAdapter(DBManager.Festivals);
        recyclerView.setAdapter(festivalRecyclerAdapter);
        Log.d(this.getClass().getName(),"onCreateView");

        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // FAB button
        fabNewFestival = root.findViewById(R.id.newFestivalActivity);
        fabNewFestival.setOnClickListener(this);

        return root;
    } // End onCreateView

    /**
     * updates the data when page reloads
     * @author Eugene
     */
    public void onResume() {
        festivalRecyclerAdapter.updateFestivalList();
        festivalRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }

    /**
     * Creates a new festival
     * @param v the view
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent( v.getContext(), FestivalActivity.class);
        startActivity(intent);
    } // End onClick()
} // End class