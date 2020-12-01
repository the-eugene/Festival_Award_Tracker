package com.example.festivalawardtracker.ui.festival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.festivalawardtracker.Answer;
import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.R;
import com.example.festivalawardtracker.ui.Utilities;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

/**
 * Create new festival activity that adds festivals to the database
 * @author carloswashingtonmercado@gmail.com and Jimmy
 */
public class FestivalActivity extends AppCompatActivity {

    private static final String TAG = "FESTIVAL_NEW_ACTIVITY";
    private static final String FESTIVAL_ID = "FESTIVAL_ID";
    Festival festivalDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festival_new_activity);

        final EditText editFestivalName = findViewById(R.id.editText_festivalName);
        final AutoCompleteTextView editIsNFMC = findViewById(R.id.autoCompleteTextViewDropdownNewFestivalNFMC);

        /* Retrieving festivalID */
        final String _festivalID = Utilities.retrieveExtra(this, FESTIVAL_ID);
        Log.d(TAG, "(A) _festivalID is: " + _festivalID);

        /* ACTION BAR */
        final Toolbar toolbar = findViewById(R.id.toolbar_newFestival);
        toolbar.setTitle("Add Festival");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST is NFMC festival? Yes or No question */
        String[] YESORNO = Answer.Options();
        // Drop-down list adapter
        ArrayAdapter<String> adapterYesOrNO =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        YESORNO);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownNewFestivalNFMC);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterYesOrNO);

        /* Setting editable fields */
        if(_festivalID != null) {
            Log.d(TAG, "(B) _festivalID is: " + _festivalID);
            festivalDB = DBManager.Festivals.get(_festivalID);
            Log.d(TAG, "(C) festivalDB is: " + Objects.requireNonNull(festivalDB).toString());
            MaterialButton btnUpdate = findViewById(R.id.btnSaveFestival);

            btnUpdate.setText(R.string.update);
            toolbar.setTitle("Change Festival");

            editFestivalName.setText(Objects.requireNonNull(festivalDB).getName());
        }

        // onClick
        MaterialButton addFestival = findViewById(R.id.btnSaveFestival);
        addFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Festival newFestival = new Festival();

                if(_festivalID != null) {
                    newFestival = festivalDB;
                    newFestival.name = editFestivalName.getText().toString().trim();
                    newFestival.isNFMC = Utilities.yesOrNoToBoolean(editIsNFMC.getEditableText().toString());

                    Toast toast = Toast.makeText(view.getContext(), "Festival updated", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    newFestival.name = editFestivalName.getText().toString().trim();
                    newFestival.isNFMC = Utilities.yesOrNoToBoolean(editIsNFMC.getEditableText().toString());

                    Toast toast = Toast.makeText(view.getContext(), "New festival saved", Toast.LENGTH_LONG);
                    toast.show();
                }

                DBManager.Festivals.put(newFestival);
                finish();
            }
        }); // End OnClickListener
    } // End OnCreate
} // End class