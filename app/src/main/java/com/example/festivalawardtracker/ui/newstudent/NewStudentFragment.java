package com.example.festivalawardtracker.ui.newstudent;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.festivalawardtracker.R;
import com.google.android.material.textfield.TextInputLayout;

public class NewStudentFragment extends Fragment {

    NewStudentViewModel mViewModel;
    TextInputLayout textInputLayoutGender;
    AutoCompleteTextView autoCompleteTextViewGender;


    public static NewStudentFragment newInstance() {
        return new NewStudentFragment();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* TODO: Implement somehow the drowp-down list. CARLOS
        https://material.io/develop/android/components/menu
        https://youtu.be/d6lDVQ8aBRc
        */

        textInputLayoutGender = getActivity().findViewById(R.id.textInputLayoutStudentGender);
//        autoCompleteTextViewGender = getActivity().findViewById(R.id.dropdown_gender);

        String[] itemsGender = new String[] {
                "Male",
                "Female"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(),
                R.layout.dropdown_layout,
                itemsGender
        );

        Log.d("Element CWM:", String.valueOf(textInputLayoutGender));
        Log.d("Element CWM:", String.valueOf(autoCompleteTextViewGender));

//        autoCompleteTextViewGender.setAdapter(adapter);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_new_student, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewStudentViewModel.class);
        // TODO: Use the ViewModel
    }

}