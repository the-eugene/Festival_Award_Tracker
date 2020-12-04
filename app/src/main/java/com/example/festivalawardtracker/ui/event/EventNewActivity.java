package com.example.festivalawardtracker.ui.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.EventDescription;
import com.example.festivalawardtracker.MainActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.SchoolYear;
import com.example.festivalawardtracker.ui.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.festivalawardtracker.R.id.autoCompleteTextViewDropdownSchoolYear;
import static com.example.festivalawardtracker.R.id.editText_endingDate;
import static com.example.festivalawardtracker.R.id.editText_startingDate;

/**
 * Instantiates a new Event class instance and pushes it to the database.
 * @author Carlos
 * @see EventActivity which calls this activity.
 */
public class EventNewActivity extends AppCompatActivity {

    private static final String TAG = "EVENT_NEW_ACTIVITY";
    private static final String EVENT_ID = "EVENT_ID";
    private static final String EVENT_DESCRIPTION_ID = "EVENT_DESCRIPTION_ID";

    EventNewRecyclerAdapter eventNewRecyclerAdapter;
    RecyclerView recyclerView;
    String event_description_ID, event_ID;
    Event event;
    EventDescription eventDescription;
    boolean isNew = false;

    /**
     *
     * @author Carlos
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(this.getClass().getName(), "Starting OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_new_activity);

        /* GETTING INTENT */
        event_description_ID = Utilities.retrieveExtra(this, EVENT_DESCRIPTION_ID);
        eventDescription=DBManager.EventDescriptions.get(event_description_ID);

        event_ID = getIntent().hasExtra(EVENT_ID)?
                getIntent().getExtras().getString(EVENT_ID):
                getPreferences(Context.MODE_PRIVATE).getString(EVENT_ID, null);

        if (!event_ID.equals("new"))
            event = DBManager.Events.get(event_ID);
        else {
            event = new Event();
            isNew = true;
            event.start = LocalDate.now();
            event.end = LocalDate.now();
            event.eventDescriptionID = event_description_ID;
        }

        final AutoCompleteTextView schoolYearInput = findViewById(autoCompleteTextViewDropdownSchoolYear);
        final TextInputEditText startingDateInput = findViewById(editText_startingDate);
        final TextInputEditText endingDateInput = findViewById(editText_endingDate);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_newEvent);
        // Adding and setting save/update button to action bar
        MaterialButton button = new MaterialButton(this);
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        toolbarLayoutParams.gravity = Gravity.END;
        button.setLayoutParams(toolbarLayoutParams);
        button.setText(R.string.update);
        button.setBackground(null);
        button.setTextColor(Color.WHITE);
        toolbar.addView(button);
        toolbar.setTitle("Add Event");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST SCHOOL YEAR */
        String schoolYearDefault=isNew?DBManager.currentYear.getName():DBManager.SchoolYears.get(event.schoolYearID).getName();

        final Map<String, String> schoolYearOptionsMap=new HashMap<>();
        for (Map.Entry<String, SchoolYear> row : DBManager.SchoolYears.entrySet()) {
            schoolYearOptionsMap.put(row.getValue().getName(), row.getKey());
        }

        String[] schoolYearList = isNew?
                schoolYearOptionsMap.keySet().toArray(new String[schoolYearOptionsMap.size()]):
                new String[]{schoolYearDefault};

        Arrays.sort(schoolYearList);
            // Drop-down list adapter
        AutoCompleteTextView schoolYearDropDown =findViewById(R.id.autoCompleteTextViewDropdownSchoolYear);
        ArrayAdapter<String> adapterScholarYearList =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        schoolYearList);
        schoolYearDropDown.setAdapter(adapterScholarYearList);

        Log.d(this.getClass().getName(),"Setting school year to: "+schoolYearDefault);
        schoolYearDropDown.setText(schoolYearDefault,false);

        /* STARTING DATE PICKER */
        MaterialDatePicker.Builder<Long> builderStartingDate = MaterialDatePicker.Builder.datePicker();
        builderStartingDate.setTitleText("Event Starting Date");
        final MaterialDatePicker<Long> materialStartDatePicker = builderStartingDate.build();
        startingDateInput.setOnClickListener(new View.OnClickListener() {  // Setting Listener
            @Override
            public void onClick(View v) {
                materialStartDatePicker.show(getSupportFragmentManager(), "DATE_PICKER_STARTING_DATE");
            }
        });
        materialStartDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {  // Retrieving date
            @Override
            public void onPositiveButtonClick(Object selection) {
                startingDateInput.setText(materialStartDatePicker.getHeaderText());
            }
        }); // End Starting Date Picker
        startingDateInput.setText(formatLocalDate(event.getStart()));

        /* ENDING DATE PICKER */
        MaterialDatePicker.Builder<Long> builderEndingDate = MaterialDatePicker.Builder.datePicker();
        builderEndingDate.setTitleText("Event Ending Date");
        final MaterialDatePicker<Long> materialEndDatePicker = builderEndingDate.build();
        endingDateInput.setOnClickListener(new View.OnClickListener() { // Setting Listener
            @Override
            public void onClick(View v) {
                materialEndDatePicker.show(getSupportFragmentManager(), "DATE_PICKER_ENDING_DATE");
            }
        });
        materialEndDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() { // Retrieving date
            @Override
            public void onPositiveButtonClick(Object selection) {
                endingDateInput.setText(materialEndDatePicker.getHeaderText());
            }
        }); // End Ending Date Picker
        endingDateInput.setText(formatLocalDate(event.getEnd()));

        /* SAVE EVENT BUTTON */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNew)
                    DBManager.linkEvent(
                            event,
                            eventDescription,
                            DBManager.SchoolYears.get(
                                    schoolYearOptionsMap.get( //gets year id from user friendly options
                                            schoolYearInput.getText().toString() //get user selection
                                    )));

                event.setStartLocalDate(Utilities.stringMaterialToLocalDate(Objects.requireNonNull(startingDateInput.getText()).toString()));
                event.setEndLocalDate(Utilities.stringMaterialToLocalDate(Objects.requireNonNull(endingDateInput.getText()).toString()));
                DBManager.Events.put(event);

                Toast toast = Toast.makeText(v.getContext(), "New event saved", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });

        /* RECYCLER ADAPTER */
        recyclerView = findViewById(R.id.recyclerView_selectStudents);
        eventNewRecyclerAdapter = new EventNewRecyclerAdapter(event,this);
        recyclerView.setAdapter(eventNewRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    } // End OnCreate

    /**
     * It changes a given string date from on date format into another (string).
     * @author Carlos
     * @param dateStringIn string date provided by the database. Its format is provided by LocalDate.toString()
     * @return string date in the format "MMM d, yyyy", which is the format used by materialDatePicker.
     */
    private String formatLocalDate(String dateStringIn) {
        String dateStringOut = "Hello";
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, yyyy");

        try {
            Date dateCarrier = inputFormat.parse(dateStringIn);
            assert dateCarrier != null;
            dateStringOut = outputFormat.format(dateCarrier);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStringOut;
    } // End of formatLocalDate(...)

    /**
     *
     * @author
     */
    @Override
    protected void onResume() {
        super.onResume();
        eventNewRecyclerAdapter.update();
    }

    /**
     *
     * @author
     */
    protected void onPause() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(EVENT_DESCRIPTION_ID, event_description_ID);
        editor.putString(EVENT_ID, event_ID);
        editor.apply();
        super.onPause();
    }
}