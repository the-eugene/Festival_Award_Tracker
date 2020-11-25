package com.example.festivalawardtracker.ui.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.StudentActivity;
import com.example.festivalawardtracker.StudentActivityDisplay;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class StudentFragment extends Fragment implements View.OnClickListener, RecyclerViewClickInterface {

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

        View root = inflater.inflate(R.layout.main_fragment_student, container, false);

        thisContext = container.getContext();
        Context context = root.getContext();

        studentNames = new ArrayList<>();
        birthday = new ArrayList<>();
        age = new ArrayList<>();
        gender = new ArrayList<>();
        awardInfo = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recyclerView_student);

        Log.d("StudentFragment", String.valueOf(studentNames));
        studentRecyclerAdapter = new StudentRecyclerAdapter(studentNames,birthday,age,gender,awardInfo,this);
        recyclerView.setAdapter(studentRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        studentNames.add("Billy");
        studentNames.add("Billy2");
        studentNames.add("Billy3");
        studentNames.add("Billy4");
        studentNames.add("Billy5");
        studentNames.add("Billy6");
        studentNames.add("Billy");
        studentNames.add("Billy2");
        studentNames.add("Billy3");
        studentNames.add("Billy4");
        studentNames.add("Billy5");
        studentNames.add("Billy6");

        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");

        age.add("1");
        age.add("2");
        age.add("3");
        age.add("4");
        age.add("5");
        age.add("6");
        age.add("1");
        age.add("2");
        age.add("3");
        age.add("4");
        age.add("5");
        age.add("6");

        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");
        gender.add("M");

        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");
        awardInfo.add("Good Job");


        // Setting up the FAB button for add student
        fabNewStudent = root.findViewById(R.id.fab_newStudent);
        fabNewStudent.setOnClickListener(this);

        return root; // Returning the view.
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent activityIntent = new Intent( v.getContext(), StudentActivity.class);
        startActivity(activityIntent);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent (thisContext, StudentActivityDisplay.class);
        startActivity(intent);
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
                studentRecyclerAdapter.getFilter().filter(newText);
                return false;

            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }
}