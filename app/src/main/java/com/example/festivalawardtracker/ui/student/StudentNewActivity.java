package com.example.festivalawardtracker.ui.student;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.festivalawardtracker.Contact;
import com.example.festivalawardtracker.DBHashMap;
import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Gender;
import com.example.festivalawardtracker.Instrument;
import com.example.festivalawardtracker.Person;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.Student;
import com.example.festivalawardtracker.ui.Utilities;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.textfield.TextInputEditText;

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
public class StudentNewActivity extends AppCompatActivity {

    //  This DB reference is here just for testing purposes
//    private final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//    public static final String MESSAGES_CHILD = "zzz_student_test";

    final String[] INSTRUMENTS = Instrument.Options();
    final CheckBox[] checkboxes = new CheckBox[INSTRUMENTS.length];

    /**
     * Sets all the layout components to their required values, where necessary.
     * @param savedInstanceState Add.
     * @see StudentFragment Where this activity is started.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);

        // Student fields can go below here if needed
        // Instrument

        // Person fields
        final TextInputEditText firstNameInput = findViewById(R.id.editTextPersonName);

        final TextInputEditText middleNameInput = findViewById(R.id.editTextPersonMiddleName);
        final TextInputEditText lastNameInput = findViewById(R.id.editTextPersonLastName);
        final TextView genderInput  = findViewById(R.id.dropdownGender);
        final TextInputEditText birthdayInput = findViewById(R.id.editTextPersonBirthdate);

        // Contact fields
        final TextInputEditText emailInput = findViewById(R.id.editTextEmail);
        final TextInputEditText phoneInput = findViewById(R.id.editTextPhoneNumber);
        final TextInputEditText streetInput = findViewById(R.id.editTextStreet);
        final TextInputEditText cityInput = findViewById(R.id.editTextCity);
        final TextInputEditText stateInput = findViewById(R.id.editTextState);
        final TextInputEditText zipInput = findViewById(R.id.editTextZip);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbarNewStudent);
        toolbar.setTitle("Add student");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /* BIRTHDAY DATE PICKER */
        Builder<Long> builder = Builder.datePicker();
        builder.setTitleText("Student Birthday");

        //constraint on calender, Max birthday is today.
        CalendarConstraints.Builder constraintsBuilderRange = new CalendarConstraints.Builder();
        long max = System.currentTimeMillis();
        CalendarConstraints.DateValidator dateValidatorMax = DateValidatorPointBackward.before(max - 86400000);
        constraintsBuilderRange.setValidator(dateValidatorMax);
        builder.setCalendarConstraints(constraintsBuilderRange.build());

        // Build calendar
        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Setting Listener
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER_BIRTHDAY");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) { // Retrieving date
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
        FlowLayout insLayout = findViewById(R.id.instruments_programmatical_layout);
        for (int i = 0; i < INSTRUMENTS.length; i++){
            CheckBox checkBox= new CheckBox(this);
            checkBox.setText(INSTRUMENTS[i]);
            checkBox.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
            insLayout.addView(checkBox);
            checkboxes[i] = checkBox;
        }

        /* SAVE STUDENT BUTTON */
        // Adding button to action bar
        MaterialButton button = new MaterialButton(this);
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        toolbarLayoutParams.gravity = Gravity.END;
        button.setLayoutParams(toolbarLayoutParams);
        button.setText(R.string.save);
        button.setBackground(null);
        button.setTextColor(Color.WHITE);
        toolbar.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
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
                newStudent.birthday = Utilities.stringMaterialToLocalDate(birthdayInput.getText().toString());

                /* Contact.java */
                newContact.phone = phoneInput.getText().toString();
                newContact.email = emailInput.getText().toString().toLowerCase();
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

                finish();
            }
        });
    }
} // End of class