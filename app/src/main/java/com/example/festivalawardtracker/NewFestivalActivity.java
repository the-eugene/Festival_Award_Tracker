package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class NewFestivalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_festival);

        /* DROPDOWN LIST is NFMC festival? Yes or No question */
        String[] YESORNO = new String[] {"Yes", "No"};
        ArrayAdapter<String> adapterYesOrNO =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        YESORNO);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownInstruments);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterYesOrNO);

    } // End OnCreate
}