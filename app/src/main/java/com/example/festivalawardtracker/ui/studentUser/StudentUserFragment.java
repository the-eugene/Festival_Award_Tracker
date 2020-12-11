package com.example.festivalawardtracker.ui.studentUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.MainActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.example.festivalawardtracker.ui.student.StudentEditActivity;
import com.example.festivalawardtracker.ui.student.StudentSummaryRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This class is a fragment for displaying the Student UI.
 * @author Cayla, Carlos, Jimmy, & Eugene
 */

public class StudentUserFragment extends Fragment {

    //Used for debugging
    private final String TAG = "STUDENT_USER_FRAGMENT";
    //Used to pass the student Id to the edit student info activity
    public static final String STUDENT_ID = "StudentID";
    //Used to store the logged in students ID
    String StudentID;
    //Used to initialize recyclerview
    RecyclerView recyclerView;
    StudentSummaryRecyclerAdapter studentSummaryRecyclerAdapter;
    //Used to get the fragments context for DividerItemDecoration
    Context context;
    //Used to initialize floating action button
    FloatingActionButton fabEditStudent;

    /**
     * Used to set fragments view as well as place student specific information in the
     * textViews and recyclerViews.  Also adds function to the floating action button
     * to edit student information.
     *
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @param inflater inflates the view
     * @param container the view group
     * @param savedInstanceState the bundle of information
     * @return root this is the view of the fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //retrieve the layout we want to use for this fragment
        View root = inflater.inflate(R.layout.main_fragment_student_user, container, false);
        Log.d(TAG, "OnCreateView: " + this.getClass().getName());


        //find the context of the current fragment
        context = root.getContext();


        //Retrieves from the main activity the ID of the student that logged in.
        MainActivity activity = (MainActivity) getActivity();
        StudentID = activity.getStudentID();

        //Retrieves student information from the database based off of student ID.
        Log.d(TAG,"Student ID is: "+ StudentID);
        Student s= DBManager.Students.get(StudentID);

        //Retrieves and sets the recyclerview for UI (Displays events the student attended).
        recyclerView = root.findViewById(R.id.StudentDisplayRecyclerview);
        studentSummaryRecyclerAdapter = new StudentSummaryRecyclerAdapter(s);
        recyclerView.setAdapter(studentSummaryRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        //Sets the lines you see between each item in the recyclerView.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Sets the birthday on the UI for the student logged in.
        TextView birthday=root.findViewById(R.id.textViewStudentBirthday);
        birthday.setText(s.getBirthday());

        //Sets the phone number on the UI for the student logged in.
        TextView phone=root.findViewById(R.id.textViewStudentPhone);
        phone.setText(s.contact.getPhone());

        //Sets the email on the UI for the student logged in.
        TextView email=root.findViewById(R.id.textViewStudentEmail);
        email.setText(s.getEmail());

        //Sets the address on the UI for the student logged in.
        TextView address=root.findViewById(R.id.textViewStudentAddress);
        address.setText(s.contact.fullAddress());

        //Sets the instruments on the UI for the student logged in.
        TextView instruments=root.findViewById(R.id.textViewStudentInstrument);
        instruments.setText(s.instrumentList());

        //retrieves the floating action button and when clicked sends the student to a page to edit their information.
        fabEditStudent = root.findViewById(R.id.fab_editStudent);
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
}