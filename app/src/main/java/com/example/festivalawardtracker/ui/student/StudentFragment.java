package com.example.festivalawardtracker.ui.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class StudentFragment extends Fragment implements View.OnClickListener {

    StudentRecyclerAdapter studentRecyclerAdapter;
    FloatingActionButton fabNewStudent;
    RecyclerView recyclerView;
    List<String> studentNames, birthday, age, gender, awardInfo;
    Context thisContext;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root Returning view to the fragment
     * @link <a>https://stackoverflow.com/questions/11857022/fragment-implements-onclicklistener</a>
     * Implementing listeners for buttons on fragments seems to be something not that straight
     * forward to do.
     */
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        Log.d(this.getClass().getName(),"onCreateView");
        View root = inflater.inflate(R.layout.students_recyclerview_fragment_main, container, false);

        thisContext = container.getContext();
        Context context = root.getContext();

        recyclerView = root.findViewById(R.id.recyclerView_student);
        studentRecyclerAdapter = new StudentRecyclerAdapter(DBManager.Students);
        recyclerView.setAdapter(studentRecyclerAdapter);
        //This is working but there has to be a better way...
        class pauseForLoad implements Runnable{
            final Activity activity;
            pauseForLoad (Activity activity){this.activity=activity;}
            @Override
            public void run(){
                while (!DBManager.isLoaded) SystemClock.sleep(100);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        studentRecyclerAdapter.updateStudentList();
                        studentRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        if (!DBManager.isLoaded) new Thread(new pauseForLoad(getActivity())).start(); //only do this if nothing is preloaded


        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Setting up the FAB button for add student
        fabNewStudent = root.findViewById(R.id.fab_newStudent);
        fabNewStudent.setOnClickListener(this);

        return root; // Returning the view.
    }

    /**
     * @author
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent activityIntent = new Intent( v.getContext(), StudentNewActivity.class);
        startActivity(activityIntent);
    }

    /**
     * @author Eugene
     */
    public void onResume() {
        studentRecyclerAdapter.updateStudentList();
        studentRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }
} // End fragment