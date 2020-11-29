package com.example.festivalawardtracker.ui.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.festivalawardtracker.Event;
import com.example.festivalawardtracker.MainActivity;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.festivalawardtracker.R.id.autoCompleteTextViewDropdownSchoolYear;
import static com.example.festivalawardtracker.R.id.editText_endingDate;
import static com.example.festivalawardtracker.R.id.editText_startingDate;
import static com.example.festivalawardtracker.R.id.start;

/**
 * @author Carlos
 */
public class EventNewActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewClickInterface {

    private static final String TAG = "EVENT_NEW_ACTIVITY";
    private TextInputEditText editTextDatePickerStart;
    private TextInputEditText editTextDatePickerEnd;

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

        recyclerView = findViewById(R.id.recyclerView_selectStudents);

        eventNewRecyclerAdapter = new EventNewRecyclerAdapter(studentNames,birthday,age,this);
        recyclerView.setAdapter(eventNewRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_newEvent);
        toolbar.setTitle("Add event");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        }); // End of Starting Date Picker

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
        }); // End if Ending Date Picker

        /* SAVE EVENT BUTTON */
        Button eventSaveButton = findViewById(R.id.btnSaveEvent);
        eventSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event newEvent = new Event();
//                String schoolYearID;          GET FROM DROPDOWN LIST FOR SCHOOL YEARS
//                LocalDate start;              USE
//                LocalDate end;                USE
//                Contact location;             DON'T USE IT
//                String eventDescriptionID;    GET FROM DB

                // TODO I'm still working on this class. Carlos
                newEvent.setSchoolYearID(schoolYearInput.getText().toString());
                newEvent.setStartLocalDate(Utilities.stringToLocalDate(Objects.requireNonNull(startingDateInput.getText()).toString()));
                newEvent.setEndLocalDate(Utilities.stringToLocalDate(Objects.requireNonNull(endingDateInput.getText()).toString()));

                Log.d(TAG, "onClick: " + newEvent.getSchoolYearID());
                Log.d(TAG, "onClick: " + newEvent.getStart());
                Log.d(TAG, "onClick: " + newEvent.getEnd());

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

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View v) {

    }
}