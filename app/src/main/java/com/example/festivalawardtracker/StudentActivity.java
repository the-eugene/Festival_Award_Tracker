package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Objects;

import static com.google.android.material.datepicker.MaterialDatePicker.*;

/**
 *
 * @author carloswashingtonmercado@gmail.com
 */
public class StudentActivity extends AppCompatActivity {

    private TextInputEditText editTextDatePicker;
    private final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    public static final String MESSAGES_CHILD = "student"; // this is where it goes

    /**
     *
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        /* Layout information */
        final TextInputEditText firstNameInput = findViewById(R.id.editTextPersonName);
        final TextInputEditText middleNameInput = findViewById(R.id.editTextPersonMiddleName);
        TextInputEditText lastNameInput;
        TextInputEditText emailInput;
        TextInputEditText phoneNumberInput;
        TextInputEditText birthdateInput;
        EditText editTextGender  = (EditText) findViewById(R.id.autoCompleteTextViewPersonDropdownGender);
        String gender;
        EditText editTextInstrument  = (EditText) findViewById(R.id.autoCompleteTextViewDropdownInstruments);
        String instrument;
        TextInputEditText StreetInput;
        TextInputEditText CityInput;
        TextInputEditText StateInput;
        TextInputEditText ZipInput;

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
        AutoCompleteTextView editTextFilledExposedDropdownGender = this.findViewById(R.id.autoCompleteTextViewPersonDropdownGender);
        editTextFilledExposedDropdownGender.setAdapter(adapterGender);

        /* DROPDOWN LIST INSTRUMENTS */
        String[] INSTRUMENTS = Instrument.Options();
        ArrayAdapter<String> adapterInstruments =
                new ArrayAdapter<>(
                        getBaseContext(),
                        R.layout.dropdown_layout,
                        INSTRUMENTS);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownInstruments);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterInstruments);



        /* NEW ACTIVITY: Student Parent */
        MaterialButton btnAddParent = findViewById(R.id.btnStudentAddParent);
        btnAddParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(StudentActivity.this, ParentActivity.class);
                startActivity(activityIntent);
            }
        });

        final Contact contact = new Contact();
        contact.business = "Natasha's Cafe";
        contact.phone = "859-259-2754";
        contact.email = "natasha@beetnik.com";
        contact.street = "112 Esplanade";
        contact.city = "Lexington";
        contact.state = "KY";
        contact. zip = "40508";

        /* SAVE STUDENT BUTTON */
        Button studentSaveButton = (Button) findViewById(R.id.btnSaveStudent);
        studentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student newStudent = new Student(
                    firstNameInput.getText().toString(),
                    middleNameInput.getText().toString()
                );
                newStudent.setGender(Student.Gender.FEMALE);
                newStudent.birthday = LocalDate.of(2007, 12, 20);
                newStudent.setContact(contact);
                newStudent.addInstrument(Instrument.violin);

                mFirebaseDatabaseReference.child(MESSAGES_CHILD).push().setValue(newStudent);
                middleNameInput.setText("1");
            }
        });

    }
}