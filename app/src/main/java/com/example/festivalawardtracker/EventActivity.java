package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class EventActivity extends AppCompatActivity {

    private Toolbar toolbarEvent;
    private TextInputEditText editTextDatePickerStart;
    private TextInputEditText editTextDatePickerEnd;

    /**
     *
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        /* ACTION BAR */
        toolbarEvent = findViewById(R.id.toolbarNewEvent);
        toolbarEvent.setTitle("Add event");
        setSupportActionBar(toolbarEvent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DATE PICKER START */
        // Material Date Picker
        MaterialDatePicker.Builder builderStart = MaterialDatePicker.Builder.datePicker();
        builderStart.setTitleText("Event Starting Date");
        final MaterialDatePicker materialDatePickerStart = builderStart.build();

        // Setting Listener for Material Date Picker
        editTextDatePickerStart = findViewById(R.id.editTextNewEventStartingDate);
        editTextDatePickerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePickerStart.show(getSupportFragmentManager(), "DATE_PICKER_START");
            }
        });

        // Retrieving date
        materialDatePickerStart.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // HEADS UP! The date can be retrieved either as plaintext or as object
                editTextDatePickerStart.setText(materialDatePickerStart.getHeaderText());

            }
        });

        /* DATE PICKER END */
        // Material Date Picker
        MaterialDatePicker.Builder builderEnd = MaterialDatePicker.Builder.datePicker();
        builderEnd.setTitleText("Student Birthday");
        final MaterialDatePicker materialDatePickerEnd = builderEnd.build();

        // Setting Listener for Material Date Picker
        editTextDatePickerEnd = findViewById(R.id.editTextNewEventEndingDate);
        editTextDatePickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePickerEnd.show(getSupportFragmentManager(), "DATE_PICKER_END");
            }
        });

        // Retrieving date
        materialDatePickerEnd.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // HEADS UP! The date can be retrieved either as plaintext or as object
                editTextDatePickerEnd.setText(materialDatePickerEnd.getHeaderText());

            }
        });

        /* NEW ACTIVITY: Event Contact */
        MaterialButton btnAddContact = findViewById(R.id.btnEventAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(EventActivity.this, ContactActivity.class);
                startActivity(activityIntent);
            }
        });
    }
}