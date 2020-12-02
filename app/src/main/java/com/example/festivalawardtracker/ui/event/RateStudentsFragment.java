package com.example.festivalawardtracker.ui.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;

import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class RateStudentsFragment extends Fragment implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    RateStudentsRecyclerAdapter rateStudentsRecyclerAdapter;
    List<String> eventName, startDate, endDate, eventInstruments;
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

        View root = inflater.inflate(R.layout.events_recyclerview_fragment_main, container, false);

        thisContext = container.getContext();
        Context context = root.getContext();

        recyclerView = root.findViewById(R.id.recyclerView_eventsFragment);
        rateStudentsRecyclerAdapter = new RateStudentsRecyclerAdapter(DBManager.Events, DBManager.EventDescriptions,this);
        recyclerView.setAdapter(rateStudentsRecyclerAdapter);

        class queryThread implements Runnable{
            final Activity activity;
            queryThread(Activity activity){
                this.activity=activity;
            }
            @Override
            public void run(){
                DBManager.Events.loadAll();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rateStudentsRecyclerAdapter.updateEventsList();
                        rateStudentsRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        if (DBManager.Events.size()==0)
            new Thread(new queryThread(getActivity())).start();


        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return root; // Returning the view.
    }

//    @Override
//    public void onClick(View v) {
//        Intent activityIntent = new Intent( v.getContext(), EventNewActivity.class);
//        startActivity(activityIntent);
//    }

    @Override
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(thisContext, EventsRatingsActivity.class);
        startActivity(activityIntent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) menuItem.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onResume() {
        rateStudentsRecyclerAdapter.updateEventsList();
        rateStudentsRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }
}