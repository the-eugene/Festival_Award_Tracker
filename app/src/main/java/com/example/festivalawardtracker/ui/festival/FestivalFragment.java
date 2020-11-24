package com.example.festivalawardtracker.ui.festival;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalawardtracker.EventActivity;
import com.example.festivalawardtracker.FestivalActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.StudentActivity;
import com.example.festivalawardtracker.ui.event.EventFragment;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class FestivalFragment extends Fragment implements View.OnClickListener, RecyclerViewClickInterface {

    RecyclerView recyclerView;
    FloatingActionButton fabNewFestival;
    FestivalRecyclerAdapter festivalRecyclerAdapter;
    List<String> festivalNames;
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

        View root = inflater.inflate(R.layout.main_fragment_festival, container, false);

        thisContext = container.getContext();
        Context context = root.getContext();

        festivalNames = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerView_festival);
        festivalRecyclerAdapter = new FestivalRecyclerAdapter(festivalNames,this);
        recyclerView.setAdapter(festivalRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        festivalNames.add("festival1");
        festivalNames.add("festival2");
        festivalNames.add("festival3");
        festivalNames.add("festival4");
        festivalNames.add("festival5");
        festivalNames.add("festival6");
        festivalNames.add("festival7");
        festivalNames.add("festival8");
        festivalNames.add("festival9");
        festivalNames.add("festival10");
        festivalNames.add("festival11");
        festivalNames.add("festival12");
        festivalNames.add("festival13");
        festivalNames.add("festival14");
        festivalNames.add("festival15");

        // Setting up the FAB button for add festival
        // https://stackoverflow.com/questions/11857022/fragment-implements-onclicklistener
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

    @Override
    public void onItemClick(int position) {


    }
}