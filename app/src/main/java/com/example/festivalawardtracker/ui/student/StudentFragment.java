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
import com.example.festivalawardtracker.MainActivity;
import com.example.festivalawardtracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * This class is a fragment for displaying all the current students the teacher has.
 * Gives options of adding a new student and displaying a summery of a student.
 * Updates list when students are added.
 * @auther Cayla, Carlos, Jimmy, & Eugene
 */
public class StudentFragment extends Fragment implements View.OnClickListener {

    StudentRecyclerAdapter studentRecyclerAdapter;
    FloatingActionButton fabNewStudent;
    RecyclerView recyclerView;
    Context context;
    String TeacherID;

    /**
     * Contains a recyclerview to display all current students a teacher has.
     * Also gives options of adding a new student and displaying a summery of a student.
     * @auther Cayla, Carlos, Jimmy, & Eugene
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
        //retrieve the layout we want to use for this fragment
        View root = inflater.inflate(R.layout.students_recyclerview_fragment_main, container, false);

        //find the context of the current fragment
        context = root.getContext();

        //Retrieves from the main activity the ID of the teacher that logged in.
        MainActivity activity = (MainActivity) getActivity();
        TeacherID = activity.getTeacherID();

        //Retrieves and sets the recyclerview for UI (Displays events the student attended).
        recyclerView = root.findViewById(R.id.recyclerView_student);
        studentRecyclerAdapter = new StudentRecyclerAdapter(DBManager.Students);
        recyclerView.setAdapter(studentRecyclerAdapter);

        //This is working but there has to be a better way...
        //Updates student list in the background.
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

        //Prevent double clicks on the recyclerview
        recyclerView.setMotionEventSplittingEnabled(false);

        //Sets the lines you see between each item in the recyclerView.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Setting up the FAB button for add student
        fabNewStudent = root.findViewById(R.id.fab_newStudent);
        fabNewStudent.setOnClickListener(this);

        return root; // Returning the view.
    }

    /**
     * Sends user to the StudentNewActivity to create a new student
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent activityIntent = new Intent( v.getContext(), StudentNewActivity.class);
        startActivity(activityIntent);
    }

    /**
     * Updates list of students when user navigates back.
     * @author Cayla, Carlos, Jimmy, & Eugene
     */
    public void onResume() {
        studentRecyclerAdapter.updateStudentList();
        studentRecyclerAdapter.notifyDataSetChanged();
        super.onResume();
    }
} // End fragment