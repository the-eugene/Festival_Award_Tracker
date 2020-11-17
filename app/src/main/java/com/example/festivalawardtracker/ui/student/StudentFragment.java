package com.example.festivalawardtracker.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.StudentActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class StudentFragment extends Fragment implements View.OnClickListener {

    private StudentViewModel studentViewModel;
    FloatingActionButton fabNewStudent;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root Returning view to the fragment
     */
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        View root = inflater.inflate(R.layout.main_fragment_student, container, false);

        // Setting up the FAB button for add student
        // https://stackoverflow.com/questions/11857022/fragment-implements-onclicklistener
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
}