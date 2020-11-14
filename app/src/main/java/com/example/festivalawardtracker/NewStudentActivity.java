package com.example.festivalawardtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Carlos W Mercado *
 */

public class NewStudentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextDatePicker;

    /**
     *
     * @param savedInstanceState Add.
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        /* ACTION BAR */
        toolbar = findViewById(R.id.toolbarNewStudent);
        toolbar.setTitle("Add student");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST GENDER */
        String[] GENDER = new String[] {"Male", "Female"};
        ArrayAdapter<String> adapterGender =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_gender,
                        GENDER);
        AutoCompleteTextView editTextFilledExposedDropdownGender =
                this.findViewById(R.id.autoCompleteTextViewStudentDropdownGender);
        editTextFilledExposedDropdownGender.setAdapter(adapterGender);

        /* DROPDOWN LIST INSTRUMENTS */
        String[] INSTRUMENTS = new String[] {"Piano", "Violin", "Viola", "Cello"};
        ArrayAdapter<String> adapterInstruments =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_instruments,
                        INSTRUMENTS);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownInstruments);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterInstruments);

        /* DATE PICKER */
        // Material Date Picker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Student Birthday");
        final MaterialDatePicker materialDatePicker = builder.build();

        // Setting Listener for Material Date Picker
        editTextDatePicker = findViewById(R.id.editTextStudentBirthdate);
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

        /* NEW ACTIVITY: Student Contact */
        MaterialButton btnAddContact = findViewById(R.id.btnStudentAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(NewStudentActivity.this, NewStudentActivityContact.class);
                startActivity(activityIntent);
            }
        });

        /* NEW ACTIVITY: Student Parent */
        MaterialButton btnAddParent = findViewById(R.id.btnStudentAddParent);
        btnAddParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(NewStudentActivity.this, NewStudentActivityParent.class);
                startActivity(activityIntent);
            }
        });

    }
}