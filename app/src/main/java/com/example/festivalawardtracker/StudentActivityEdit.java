package com.example.festivalawardtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder;

/**
 *
 * @author Carlos
 * @see StudentActivity
 * @see StudentActivityDisplay
 */
public class StudentActivityEdit extends AppCompatActivity {
    private static final String TAG = "STUDENT_EDIT";
    String[] INSTRUMENTS = Instrument.Options();
    CheckBox[] checkboxes = new CheckBox[INSTRUMENTS.length];
    String _studentID;

    /**
     * Sets all the layout components to their required values, where necessary.
     * @param savedInstanceState Add.
     * @see StudentActivityDisplay Where this activity is started.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_new_activity); // This layout is also used by StudentActivity.java

        Intent intent = getIntent();
        _studentID = intent.getStringExtra(StudentActivityDisplay.STUDENT_ID);
//
        Student studentDB = DBManager.Students.get(_studentID);
        if (studentDB == null) Log.wtf(this.getClass().getSimpleName(),"NO ID PASSED");

        // Student fields can go below here if needed
        // Instrument checkboxes
        Object[] instrumentsDB = studentDB.getInstruments().toArray();
        Log.d(TAG, "Instruments from DB: " + Arrays.toString(instrumentsDB)); // It's working

        // Person fields
        final TextInputEditText firstNameInput = findViewById(R.id.editTextPersonName);
        firstNameInput.setText(studentDB.getFirstName());

        final TextInputEditText middleNameInput = findViewById(R.id.editTextPersonMiddleName);
        middleNameInput.setText(studentDB.getMiddleName());

        final TextInputEditText lastNameInput = findViewById(R.id.editTextPersonLastName);
        lastNameInput.setText(studentDB.getLastName());

        final TextView genderInput  = (TextView) findViewById(R.id.dropdownGender);
        genderInput.setText(studentDB.getGenderString());

        final TextInputEditText birthdayInput = findViewById(R.id.editTextPersonBirthdate);
        birthdayInput.setText(formatLocalDate(studentDB.getBirthday()));

        // Contact fields
        final TextInputEditText emailInput = findViewById(R.id.editTextEmail);
        emailInput.setText(studentDB.getEmail());

        final TextInputEditText phoneInput = findViewById(R.id.editTextPhoneNumber);
        phoneInput.setText(studentDB.contact.getPhone());

        final TextInputEditText streetInput = findViewById(R.id.editTextStreet);
        streetInput.setText(studentDB.contact.getStreet());

        final TextInputEditText cityInput = findViewById(R.id.editTextCity);
        cityInput.setText(studentDB.contact.getCity());

        final TextInputEditText stateInput = findViewById(R.id.editTextState);
        stateInput.setText(studentDB.contact.getState());

        final TextInputEditText zipInput = findViewById(R.id.editTextZip);
        zipInput.setText(studentDB.contact.getZip());

        /* ACTION BAR */
        Toolbar toolbarStudent = findViewById(R.id.toolbarNewStudent);
        toolbarStudent.setTitle("Change student data");
        toolbarStudent.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarStudent);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /* BIRTHDAY DATE PICKER */
        // Material Date Picker
        Builder<Long> builder = Builder.datePicker();
        builder.setTitleText("Student Birthday");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        // Setting Listener for Material Date Picker
        birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER_BIRTHDAY");
            }
        });
        // Retrieving date
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // HEADS UP! The date can be retrieved either as plaintext or as object
                birthdayInput.setText(materialDatePicker.getHeaderText());
            }
        }); /* End Birthday Date Picker */

        /* DROPDOWN LIST GENDER */
        String[] GENDER = Gender.Options();
        ArrayAdapter<String> adapterGender =
                new ArrayAdapter<>(
                        getBaseContext(),
                        R.layout.dropdown_layout,
                        GENDER);
        AutoCompleteTextView editTextFilledExposedDropdownGender = this.findViewById(R.id.dropdownGender);
        editTextFilledExposedDropdownGender.setAdapter(adapterGender);

        /* INSTRUMENT CHECKBOXES */
        // TODO Possible programmatical solution for current checkbox layout ugly arrangement:
        //  https://spin.atomicobject.com/2019/04/08/constraintlayout-chaining-views-programmatically/
        //  Carlos
        LinearLayout insLayout = findViewById(R.id.instruments_programmatical_layout);
        for (int i = 0; i < INSTRUMENTS.length; i++){
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(INSTRUMENTS[i]);
            checkBox.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
            checkBox.setChecked(studentDB.instruments.contains(Instrument.values()[i]));
            insLayout.addView(checkBox);
            checkboxes[i] = checkBox;
        }

        /* NEW ACTIVITY: Student Parent */
        MaterialButton btnAddParent = findViewById(R.id.btnStudentAddParent);
        btnAddParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(StudentActivityEdit.this, ParentActivity.class);
                startActivity(activityIntent);
            }
        });

        /* SAVE STUDENT BUTTON */
        Button studentSaveButton = (Button) findViewById(R.id.btnSaveStudent);
        studentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Student newStudent = new Student();
            Contact newContact = new Contact();

            /* Retrieve Student.java fields input */
            newStudent.instruments.clear(); //necessary if editing student.
            for (int i = 0; i < INSTRUMENTS.length; i++){
                if(checkboxes[i].isChecked()) {
                    newStudent.addInstrument(Instrument.values()[i]);
                    Log.d("FOR", "Instruments: " + newStudent.instruments.toString());
                }
            }

            /* Person.java */
            newStudent.firstName = firstNameInput.getText().toString();
            newStudent.middleName = middleNameInput.getText().toString();
            newStudent.lastName = lastNameInput.getText().toString();
            newStudent.gender = Person.Gender.valueOf(genderInput.getText().toString().toUpperCase());
            newStudent.birthday = stringToLocalDate(birthdayInput.getText().toString());

            /* Contact.java */
            newContact.phone = phoneInput.getText().toString();
            newContact.email = emailInput.getText().toString();
            newContact.street = streetInput.getText().toString();
            newContact.city = cityInput.getText().toString();
            newContact.state = stateInput.getText().toString();
            newContact.zip = zipInput.getText().toString();
            newStudent.setContact(newContact);

            // This DB reference is here just for testing purposes
//                mFirebaseDatabaseReference.child(MESSAGES_CHILD).push().setValue(newStudent);

            // Pushing to the DBHashMap
            DBManager.Students.put(newStudent);

            Toast toast = Toast.makeText(view.getContext(), "Changes saved", Toast.LENGTH_SHORT);
            toast.show();

            finish();
            }
        });
    }

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
     * It parses a string date in the given format into a LocalDate data type.
     * Date picker returning string format requires to use this method.
     * @param date Birthday, or any other date from the UI.
     */
    public LocalDate stringToLocalDate(String date) {
        /*
         * MaterialDatePicker: https://developer.android.com/reference/com/google/android/material/datepicker/MaterialDatePicker.Builder?authuser=1
         * Date parsing issue: https://docs.oracle.com/javase/tutorial/datetime/iso/format.html
         */
        DateTimeFormatter pattern;
        LocalDate localDate = null;

        try {
            try {
                pattern = DateTimeFormatter.ofPattern("MMM dd, yyyy");
                localDate = LocalDate.parse(date, pattern);
            } catch (DateTimeParseException e1) {
                Log.e("DATE_TIME_PARSING", "Failed at first case.");
            }

            try {
                pattern = DateTimeFormatter.ofPattern("MMM d, yyyy");
                localDate = LocalDate.parse(date, pattern);
            } catch (DateTimeParseException e2) {
                Log.e("DATE_TIME_PARSING", "Failed at second case.");
            }

        } catch (DateTimeParseException e2) {
            Log.wtf("DATE_TIME_PARSING", "Something terrible has just happened.");
        }
        return localDate;
    } // End of stringToLocalDate(...)
} // End of StudentActivity class