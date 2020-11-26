package com.example.festivalawardtracker;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static com.google.android.material.datepicker.MaterialDatePicker.Builder;

/**
 * Pre-loads the necessary information to the drop-down list and date-picker components.
 * Retrieves the information input from the activity to the student class.
 * Parses that information where necessary.
 * Puts the class to the DBHashMAp class.
 * @author Carlos
 * @see DBHashMap
 */
public class StudentActivity extends AppCompatActivity {

    //  This DB reference is here just for testing purposes
//    private final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//    public static final String MESSAGES_CHILD = "zzz_student_test";

    //Map<String, Student> localDbHashMap = new DBHashMap<Student>(Student.class); //unnecessary, Eugene
    String[] INSTRUMENTS = Instrument.Options();
    CheckBox[] checks = new CheckBox[INSTRUMENTS.length];

    /**
     * Sets all the layout components to their required values, where necessary.
     * @param savedInstanceState Add.
     * @see StudentFragment Where this activity is started.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_new_activity);

        // Student fields



        // Person fields
        final TextInputEditText firstNameInput = findViewById(R.id.editTextPersonName);
        final TextInputEditText middleNameInput = findViewById(R.id.editTextPersonMiddleName);
        final TextInputEditText lastNameInput = findViewById(R.id.editTextPersonLastName);
        final TextView editTextGender  = (TextView) findViewById(R.id.dropdownGender);
        final TextInputEditText editTextDatePicker;

        // Contact fields
        final TextInputEditText emailInput = findViewById(R.id.editTextEmail);
        final TextInputEditText phoneNumberInput = findViewById(R.id.editTextPhoneNumber);
        final TextInputEditText streetInput = findViewById(R.id.editTextStreet);
        final TextInputEditText cityInput = findViewById(R.id.editTextCity);
        final TextInputEditText stateInput = findViewById(R.id.editTextState);
        final TextInputEditText zipInput = findViewById(R.id.editTextZip);

        /* ACTION BAR */
        Toolbar toolbarStudent = findViewById(R.id.toolbarNewStudent);
        toolbarStudent.setTitle("Add student");
        toolbarStudent.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarStudent);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /* BIRTHDAY DATE PICKER */
        // Material Date Picker
        Builder<Long> builder = Builder.datePicker();
        builder.setTitleText("Student Birthday");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        // Setting Listener for Material Date Picker
        editTextDatePicker = findViewById(R.id.editTextPersonBirthdate);
        editTextDatePicker.setOnClickListener(new View.OnClickListener() {
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
                editTextDatePicker.setText(materialDatePicker.getHeaderText());
            }
        });
        /* End Birthday Date Picker */

        /* DROPDOWN LIST GENDER */
        String[] GENDER = Gender.Options();
        ArrayAdapter<String> adapterGender =
                new ArrayAdapter<>(
                        getBaseContext(),
                        R.layout.dropdown_layout,
                        GENDER);
        AutoCompleteTextView editTextFilledExposedDropdownGender = this.findViewById(R.id.dropdownGender);
        editTextFilledExposedDropdownGender.setAdapter(adapterGender);

        /* NEW ACTIVITY: Student Parent */
        MaterialButton btnAddParent = findViewById(R.id.btnStudentAddParent);
        btnAddParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(StudentActivity.this, ParentActivity.class);
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
                for (int i=0;i<INSTRUMENTS.length;i++){
                    if(checks[i].isChecked()) {
                        newStudent.addInstrument(Instrument.values()[i]);
                    }
                }

                /* Person.java */
                newStudent.firstName = firstNameInput.getText().toString();
                newStudent.middleName = middleNameInput.getText().toString();
                newStudent.lastName = lastNameInput.getText().toString();
                newStudent.gender = Person.Gender.valueOf(editTextGender.getText().toString().toUpperCase());
                newStudent.birthday = stringToLocalDate(editTextDatePicker.getText().toString());

                /* Contact.java */
                newContact.phone = phoneNumberInput.getText().toString();
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

                Toast toast = Toast.makeText(view.getContext(), "New student saved", Toast.LENGTH_SHORT);
                toast.show();

                /* Clearing input fields */
                firstNameInput.setText("");
                middleNameInput.setText("");
                lastNameInput.setText("");
                emailInput.setText("");
                phoneNumberInput.setText("");
                editTextDatePicker.setText("");
                editTextGender.setText("");
                // instrument.setText("");
                streetInput.setText("");
                cityInput.setText("");
                stateInput.setText("");
                zipInput.setText("");
            }
        });
    }

    /**
     * It parses a string date in the given format into a LocalDate data type.
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
    } // End of stringToLocalDate()
} // End of StudentActivity class