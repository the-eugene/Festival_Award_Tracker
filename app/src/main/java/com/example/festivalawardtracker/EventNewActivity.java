package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.festivalawardtracker.ui.student.RecyclerViewClickInterface;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class EventNewActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewClickInterface {

    private Toolbar toolbarEvent;
    private TextInputEditText editTextDatePickerStart;
    private TextInputEditText editTextDatePickerEnd;
    EventNewRecyclerAdapter eventNewRecyclerAdapter;
    RecyclerView recyclerView;
    List<String> studentNames, birthday, age;

    /**
     *
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_new_activity);

        studentNames = new ArrayList<>();
        birthday = new ArrayList<>();
        age = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_selectSudents);

        eventNewRecyclerAdapter = new EventNewRecyclerAdapter(studentNames,birthday,age,this);
        recyclerView.setAdapter(eventNewRecyclerAdapter);
        recyclerView.setMotionEventSplittingEnabled(false);

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

        /* ACTION BAR */
        toolbarEvent = findViewById(R.id.toolbar_newEvent);
        toolbarEvent.setTitle("Add event");
        toolbarEvent.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarEvent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DATE PICKER START */
        // Material Date Picker
        MaterialDatePicker.Builder builderStart = MaterialDatePicker.Builder.datePicker();
        builderStart.setTitleText("Event Starting Date");
        final MaterialDatePicker materialDatePickerStart = builderStart.build();

        // Setting Listener for Material Date Picker
        editTextDatePickerStart = findViewById(R.id.editText_startingDate);
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
        editTextDatePickerEnd = findViewById(R.id.editText_endingDate);
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

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View v) {

    }
}