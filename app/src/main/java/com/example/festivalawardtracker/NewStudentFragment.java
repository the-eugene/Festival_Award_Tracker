package com.example.festivalawardtracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewStudentFragment extends Fragment {
    String FirstName;
    DatabaseReference database;

    public NewStudentFragment() {
        //Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.fragment_new_student,
//                container, false);
//        Button button = (Button) view.findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(this, "Student Saved", Toast.LENGTH_SHORT).show();
//
//                database = FirebaseDatabase.getInstance().getReference("student");
//                EditText editFirstName = (EditText) view.findViewById(R.id.editTextStudentName);
//                EditText editLastName = (EditText) view.findViewById(R.id.editTextStudentLastName);
//                EditText editBirthDate = (EditText) view.findViewById(R.id.editTextBirthDate);
//                Spinner editGender = (Spinner) view.findViewById(R.id.spinnerGender);
//                EditText editPhoneNumber = (EditText) view.findViewById(R.id.editTextPhoneNumber);
//                Spinner editInstruments = (Spinner) view.findViewById(R.id.spinnerInstruments);
//                EditText editTeacher = (EditText) view.findViewById(R.id.editTextTeachersName);
//
//                FirstName = editFirstName.getText().toString().trim();
//                String lastName = editLastName.getText().toString().trim();
//                String birthDate = editBirthDate.getText().toString().trim();
//                String gender = editGender.toString().trim();
//                String phoneNumber = editPhoneNumber.getText().toString().trim();
//                String instrument = editInstruments.toString().trim();
//                String teacher = editTeacher.getText().toString().trim();
//
//                studentDatabase student = new studentDatabase(FirstName, lastName, birthDate, gender, phoneNumber, instrument, teacher);
//                String id = database.push().getKey();
//
//                database.child(id).setValue(student);
//            }
//        });
//        return view;
//    }





}
