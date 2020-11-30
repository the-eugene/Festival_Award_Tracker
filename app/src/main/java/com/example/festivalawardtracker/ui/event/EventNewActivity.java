package com.example.festivalawardtracker.ui.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.festivalawardtracker.Answer;
import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.Gender;
import com.example.festivalawardtracker.MainActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.SchoolYear;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.festivalawardtracker.R.id.autoCompleteTextViewDropdownSchoolYear;
import static com.example.festivalawardtracker.R.id.editText_endingDate;
import static com.example.festivalawardtracker.R.id.editText_startingDate;

/**
 * Instantiates a new Event class instance and pushes it to the database.
 * @author Carlos
 * @see EventActivity which calls this activity.
 */
public class EventNewActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewClickInterface {

    private static final String TAG = "EVENT_NEW_ACTIVITY";
    private static final String EVENT_DESCRIPTION_ID = "EVENT_DESCRIPTION_ID";

    EventNewRecyclerAdapter eventNewRecyclerAdapter;
    RecyclerView recyclerView;
    List<String> studentNames, birthday, age;

    /**
     *
     * @author Carlos
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_new_activity);

        final AutoCompleteTextView schoolYearInput = findViewById(autoCompleteTextViewDropdownSchoolYear);
        final TextInputEditText startingDateInput = findViewById(editText_startingDate);
        final TextInputEditText endingDateInput = findViewById(editText_endingDate);

        studentNames = new ArrayList<>();
        birthday = new ArrayList<>();
        age = new ArrayList<>();

        /* RECYCLER ADAPTER */
        recyclerView = findViewById(R.id.recyclerView_selectStudents);
        eventNewRecyclerAdapter = new EventNewRecyclerAdapter(studentNames,birthday,age,this);
        recyclerView.setAdapter(eventNewRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        /* GETTING INTENT */
        final String _event_description_ID = Utilities.retrieveExtra(this, EVENT_DESCRIPTION_ID);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_newEvent);
        toolbar.setTitle("Add event");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST SCHOOL YEAR */
        String[] schoolYearList = schoolYearList(); // Method of the current class
        // Drop-down list adapter
        ArrayAdapter<String> adapterScholarYearList =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        schoolYearList);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownSchoolYear);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterScholarYearList);

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

        /* SAVE EVENT BUTTON */
        Button eventSaveButton = findViewById(R.id.btnSaveEvent);
        eventSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // New event
                Event newEvent = new Event();
                newEvent.setSchoolYearID(schoolYearInput.getText().toString());
                newEvent.setStartLocalDate(Utilities.stringMaterialToLocalDate(Objects.requireNonNull(startingDateInput.getText()).toString()));
                newEvent.setEndLocalDate(Utilities.stringMaterialToLocalDate(Objects.requireNonNull(endingDateInput.getText()).toString()));

                // TODO: This ID must be fixed from the EventActivity. Carlos
                newEvent.setEventDescriptionID(_event_description_ID);

                // Time to push the new event
                DBManager.Events.put(newEvent);

                Toast toast = Toast.makeText(v.getContext(), "New event saved", Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        studentNames.add("Billy");
        studentNames.add("Billy2");
        studentNames.add("Billy3");
        studentNames.add("Billy4");
        studentNames.add("Billy5");
        studentNames.add("Billy6");
        studentNames.add("Billy");
        studentNames.add("Billy2");
        studentNames.add("Billy3");
        studentNames.add("Billy4");
        studentNames.add("Billy5");
        studentNames.add("Billy6");

        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");
        birthday.add("12/5/2000");

        age.add("1");
        age.add("2");
        age.add("3");
        age.add("4");
        age.add("5");
        age.add("6");
        age.add("1");
        age.add("2");
        age.add("3");
        age.add("4");
        age.add("5");
        age.add("6");
    } // End OnCreate

    /**
     * Retrieves a String array with the desired n * 2 amount of school years.
     * If chosen n = 1, it should retrieve current scholar year and next.
     * 0 is nor a valid value.
     * @author Carlos
     * @return schoolYearList A list of scholar years from n years ago, until n years into the future.
     */
    private String[] schoolYearList() { ;
        int n = 5; // Only change this variable if MORE or LESS school years are needed.
        int m = 2 * n;
        String[] schoolYearList = new String[m];
        int nYearsAgo = LocalDate.now().getYear() - n;

        for (int i = 0; i < m; i++) {
            String formerYear = Integer.toString(nYearsAgo + i);
            String latterYear = Integer.toString(nYearsAgo + i + 1);
            String schoolPeriod = formerYear + "-" + latterYear;
            schoolYearList[i] = schoolPeriod;
        }

        return schoolYearList;
    } // End schoolYearList

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View v) {

    }
}