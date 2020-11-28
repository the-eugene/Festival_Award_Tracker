package com.example.festivalawardtracker.ui.festival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.EventDescriptionsActivity;
import com.example.festivalawardtracker.FestivalNewActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class FestivalFragment extends Fragment implements View.OnClickListener, RecyclerViewClickInterface {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fabNewFestival;
    FestivalRecyclerAdapter festivalRecyclerAdapter;
    List<String> festivalNames;
    Context thisContext;
    Context context;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.festival_recyclerview_fragment_main, container, false);

        thisContext = container.getContext();
        context = root.getContext();

        festivalNames = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recyclerView_festivals);
        festivalRecyclerAdapter = new FestivalRecyclerAdapter(DBManager.Festivals,this);
        recyclerView.setAdapter(festivalRecyclerAdapter);

        class queryThread implements Runnable{
            final Activity activity;
            queryThread(Activity activity){
                this.activity=activity;
            }
            @Override
            public void run(){
                DBManager.Festivals.loadAll();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        festivalRecyclerAdapter.updateFestivalList();
                        festivalRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        }

        new Thread(new queryThread(getActivity())).start();

        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Setting up the FAB button for add festival
        // https://stackoverflow.com/questions/11857022/fragment-implements-onclicklistener
        fabNewFestival = root.findViewById(R.id.goTo_FestivalNewActivity);
        fabNewFestival.setOnClickListener(this);

        return root;
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent activityIntent = new Intent( v.getContext(), FestivalNewActivity.class);
        startActivity(activityIntent);
    }

    @Override
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(context, EventDescriptionsActivity.class);
        startActivity(activityIntent);

//        NavHostFragment.findNavController(this).navigate(R.id.action_festival_to_event);
    }
}