package com.example.festivalawardtracker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventDescriptionsNewActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    private Toolbar toolbarEventDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_new_activity);

        /* ACTION BAR */
        toolbarEventDescriptions = findViewById(R.id.toolbar_newEventDescription);
        toolbarEventDescriptions.setTitle("Add event description");
        setSupportActionBar(toolbarEventDescriptions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* DROPDOWN LIST INSTRUMENTS */
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewDropdownInstruments);

        String[] options = Instrument.Options();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout._instrument_dropdown_menu_popup_item,options);
        autoCompleteTextView.setAdapter(arrayAdapter);



    }

}
