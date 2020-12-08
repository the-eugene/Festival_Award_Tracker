package com.example.festivalawardtracker.ui.studentUser;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.MainActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.student.StudentEditActivity;
import com.example.festivalawardtracker.ui.student.StudentSummaryRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

// TODO This is here as the first step for implementing a student user interface for the app. Carlos

public class StudentUserFragment extends Fragment {

    private StudentUserFragmentViewModel mViewModel;
    private final String TAG = "StudentUserFragment";
    public static StudentUserFragment newInstance() {
        return new StudentUserFragment();
    }
    public static final String STUDENT_ID = "StudentID";
    String StudentID;
    RecyclerView recyclerView;
    StudentSummaryRecyclerAdapter studentSummaryRecyclerAdapter;
    Context thisContext;
    FloatingActionButton fabEditStudent;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.main_fragment_student_user, container, false);
        Context context = root.getContext();
        thisContext = container.getContext();

        MainActivity activity = (MainActivity) getActivity();
        StudentID = activity.getStudentID();

        Log.d(TAG,"Student ID is: "+ StudentID);
        Student s= DBManager.Students.get(StudentID);

        recyclerView = root.findViewById(R.id.StudentDisplayRecyclerview);
        studentSummaryRecyclerAdapter = new StudentSummaryRecyclerAdapter(s);
        recyclerView.setAdapter(studentSummaryRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


//        TextView name=findViewById(R.id.textViewStudentFullName);
//        name.setText(s.getFullName());

        TextView birthday=root.findViewById(R.id.textViewStudentBirthday);
        birthday.setText(s.getBirthday());

        TextView phone=root.findViewById(R.id.textViewStudentPhone);
        phone.setText(s.contact.getPhone());

        TextView email=root.findViewById(R.id.textViewStudentEmail);
        email.setText(s.getEmail());

        TextView address=root.findViewById(R.id.textViewStudentAddress);
        address.setText(s.contact.fullAddress());

        TextView instruments=root.findViewById(R.id.textViewStudentInstrument);
        instruments.setText(s.instrumentList());

        fabEditStudent = root.findViewById(R.id.fab_editStudent33);
        fabEditStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent( view.getContext(), StudentEditActivity.class);
                activityIntent.putExtra(STUDENT_ID, StudentID);
                startActivity(activityIntent);
            }
        });
        return root;
    }


//    protected void onPause() {
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(STUDENT_ID,StudentID);
//        editor.apply();
//        super.onPause();
//    }
}