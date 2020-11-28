package com.example.festivalawardtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Carlos
 */
public class EventDescriptionsNewActivity extends AppCompatActivity {

    private static final String TAG = "EVENT_DESCRIPTION";
    AutoCompleteTextView autoCompleteTextView;
    String _festivalID = "festivalID_test";

    // TODO Cayla, please, add in @see which classes are important for this class
    /**
     *
     * @author Carlos
     * @param savedInstanceState
     * @see
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_descriptions_new_activity);

        // TODO possible solution for festivalID retrieval
//        Intent intent = getIntent();
//        _festivalID = intent.getExtras().getString("festivalID",null);

        final TextInputEditText inputEventName = findViewById(R.id.editText_eventName);
        final TextInputEditText inputEventDescription = findViewById(R.id.editText_eventDescription);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_newEventDescription);
        toolbar.setTitle("Add new event");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST INSTRUMENTS */
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewDropdownInstruments);
        String[] options = Instrument.Options();
        int dropDownLayout = R.layout.instrument_dropdown_menu_popup_item;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, dropDownLayout, options);
        autoCompleteTextView.setAdapter(arrayAdapter);

        /* SAVE NEW EVENT with DESCRIPTION */
        Button saveButton = findViewById(R.id.btnSaveEvent);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDescription newEventDescription = new EventDescription();

                newEventDescription.setFestivalID(_festivalID);
                newEventDescription.setName(inputEventName.getText().toString());
                newEventDescription.setDescription(inputEventDescription.getText().toString());
                newEventDescription.setInstrument(Instrument.valueOf(autoCompleteTextView.getText().toString().toLowerCase()));

                DBManager.EventDescriptions.put(newEventDescription);

                Toast toast = Toast.makeText(v.getContext(), "New event saved", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            } // End saveButton
        }); // End setOnClickListener
    } // End onCreate
} // End class
