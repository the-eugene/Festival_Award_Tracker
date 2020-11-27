package com.example.festivalawardtracker.ui.studentUser;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.festivalawardtracker.R;

// TODO This is here as the first step for implementing a student user interface for the app. Carlos

public class StudentUserFragment extends Fragment {

    private StudentUserFragmentViewModel mViewModel;

    public static StudentUserFragment newInstance() {
        return new StudentUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_student_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StudentUserFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

}