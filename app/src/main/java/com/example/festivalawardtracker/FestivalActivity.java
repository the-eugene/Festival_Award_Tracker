package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * @author carloswashingtonmercado@gmail.com
 */
public class FestivalActivity extends AppCompatActivity {

    private Toolbar toolbarFestival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_festival);

        /* ACTION BAR */
        toolbarFestival = findViewById(R.id.toolbarNewEvent);
        toolbarFestival.setTitle("Add Festival");
        setSupportActionBar(toolbarFestival);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST is NFMC festival? Yes or No question */
        String[] YESORNO = new String[] {"Yes", "No"};
        // ADAPTER
        ArrayAdapter<String> adapterYesOrNO =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        YESORNO);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownNewFestivalNFMC);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterYesOrNO);

    } // End OnCreate
}