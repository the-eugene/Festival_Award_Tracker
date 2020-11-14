package com.example.festivalawardtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Carlos W Mercado
 */
public class ParentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editTextDatePicker;

    /**
     *
     * @param savedInstanceState Add.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student_parent);

        // ACTION BAR
        toolbar = findViewById(R.id.toolbarNewStudentParent);
        toolbar.setTitle("Add parent");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST GENDER */
        String[] GENDER = new String[] {"Male", "Female"};
        ArrayAdapter<String> adapterGender =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        GENDER);
        AutoCompleteTextView editTextFilledExposedDropdownGender =
                this.findViewById(R.id.autoCompleteTextViewParentDropdownGender);
        editTextFilledExposedDropdownGender.setAdapter(adapterGender);

        /* DATE PICKER */
        // Material Date Picker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Parent Birthday");
        final MaterialDatePicker materialDatePicker = builder.build();

        // Setting Listener for Material Date Picker
        editTextDatePicker = findViewById(R.id.editTextParentBirthdate);
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

        /* NEW ACTIVITY: Parent Contact */
        MaterialButton btnAddContact = findViewById(R.id.btnParentAddContact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityIntent = new Intent(ParentActivity.this, ContactActivity.class);
                startActivity(activityIntent);
            }
        });

    }
}
