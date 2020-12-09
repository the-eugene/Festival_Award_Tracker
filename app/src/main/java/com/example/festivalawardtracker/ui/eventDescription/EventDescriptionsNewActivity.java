package com.example.festivalawardtracker.ui.eventDescription;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.Instrument;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 *
 * @author Carlos
 */
public class EventDescriptionsNewActivity extends AppCompatActivity {

    private static final String TAG = "EVENT_DESCRIPTION";
    AutoCompleteTextView autoCompleteTextView;

    Festival festival;
    EventDescription eventDescription;

    boolean isNew;

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
        final TextInputEditText inputEventName = findViewById(R.id.editText_eventName);
        final TextInputEditText inputEventDescription = findViewById(R.id.editText_eventDescription);

        /* Retrieving festival ID */
        String eventDescriptionID = Utilities.retrieveExtra(this, Utilities.EVENT_DESCRIPTION_ID);
        isNew=eventDescriptionID.equals("new");
        if(isNew){
            eventDescription = new EventDescription();
            String festivalID = Utilities.retrieveExtra(this, "FESTIVAL_ID");
            festival = DBManager.Festivals.get(festivalID);
        } else {
            eventDescription = DBManager.EventDescriptions.get(eventDescriptionID);
            festival=eventDescription.retrieveFestival();
        }

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_newEventDescription);
        MaterialButton button = new MaterialButton(this);
        if (isNew) {
            toolbar.setTitle("Add event to " + festival.name);
            button.setText(R.string.save);
        } else {
            toolbar.setTitle("Event from " + festival.name);
            button.setText(R.string.update);
        }
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST INSTRUMENTS */
        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewDropdownInstruments);
        String[] options = Instrument.Options();
        int dropDownLayout = R.layout.instrument_dropdown_menu_popup_item;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, dropDownLayout, options);
        autoCompleteTextView.setAdapter(arrayAdapter);

        if (!isNew) {
            autoCompleteTextView.setText(eventDescription.instrument.ToCapitalizedString());
            inputEventDescription.setText(eventDescription.getDescription());
            inputEventName.setText(eventDescription.getName());
        }

        /* SAVE NEW EVENT with DESCRIPTION */
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        toolbarLayoutParams.gravity = Gravity.END;
        button.setLayoutParams(toolbarLayoutParams);
        button.setBackground(null);
        button.setTextColor(Color.WHITE);
        toolbar.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEventName.getText().length()==0){
                    inputEventName.setError("Name cannot be left blank");
                    return;
                }
                else
                    inputEventName.setError(null);

                if (autoCompleteTextView.getText().length()==0){
                    autoCompleteTextView.setError("Instrument cannot be left blank");
                    return;
                }
                else
                    autoCompleteTextView.setError(null);

                eventDescription.setName(inputEventName.getText().toString());
                eventDescription.setDescription(inputEventDescription.getText().toString());
                eventDescription.setInstrument(Instrument.valueOf(autoCompleteTextView.getText().toString().toLowerCase()));

                if (isNew)
                    DBManager.linkFestivalEventDescription(festival,eventDescription); //linking also saves event
                else
                    eventDescription.save();

                Toast toast = Toast.makeText(v.getContext(), "New event saved", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            } // End saveButton
        }); // End setOnClickListener
    } // End onCreate

    // code that sends app back to last page when you hit the x in the tool bar
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up

        return false;
    }
} // End class
