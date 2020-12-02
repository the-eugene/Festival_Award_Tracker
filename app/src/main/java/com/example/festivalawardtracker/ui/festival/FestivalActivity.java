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
import com.example.festivalawardtracker.ui.eventDescription.EventDescriptionsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 * Create new festival activity that adds festivals to the database
 * @author Carlos
 * @author Jimmy
 * @see FestivalFragment Festivas are CREATED for the first time here.
 * @see EventDescriptionsActivity Festivals are EDITED from there.
 * @see Festival The java class that is handled here from the app to the database
 */
public class FestivalActivity extends AppCompatActivity {

    private static final String TAG = "FESTIVAL_NEW_ACTIVITY";
    private static final String FESTIVAL_ID = "FESTIVAL_ID";
    Festival festivalDB = null;

    /**
     * Creation/edition of new/existing festivals is made here.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festival_new_activity);

        /* Retrieve main input fields from the Android activity */
        final EditText editFestivalName = findViewById(R.id.editText_festivalName);
        final MaterialCheckBox isNFMC= findViewById(R.id.checkBoxNFMC);

        /* Retrieving festivalID */
        final String _festivalID = Utilities.retrieveExtra(this, FESTIVAL_ID);
        Log.d(TAG, "(A) _festivalID is: " + _festivalID);

        /* ACTION BAR */
        final Toolbar toolbar = findViewById(R.id.toolbar_newFestival);
        toolbar.setTitle("Add Festival");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        // TODO The activity MUST NOT accept empty or erroneous input values.

        // Not null if something is sent from EventDescriptionActivity
        if(_festivalID != null) {
            festivalDB = DBManager.Festivals.get(_festivalID); // Retrieving festival information from database

            MaterialButton btnUpdate = findViewById(R.id.btnSaveFestival);
            btnUpdate.setText(R.string.update);
            toolbar.setTitle("Edit "+festivalDB.name);
            editFestivalName.setText(Objects.requireNonNull(festivalDB).getName());
            isNFMC.setChecked(festivalDB.getNFMC());
        }

        /* onClickListener: Save or Update button */
        // Here the new/edited festival is sent to the database
        MaterialButton addFestival = findViewById(R.id.btnSaveFestival);
        addFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editFestivalName.getText().length()==0){
                    editFestivalName.setError("Can't Be Blank!");
                    return;
                }
                Festival newFestival = new Festival();
                if(_festivalID != null) {
                    newFestival = festivalDB;
                    newFestival.name = editFestivalName.getText().toString().trim();
                    newFestival.isNFMC = isNFMC.isChecked();

                    Toast toast = Toast.makeText(view.getContext(), "Festival updated", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    newFestival.name = editFestivalName.getText().toString().trim();
                    newFestival.isNFMC = isNFMC.isChecked();

                    Toast toast = Toast.makeText(view.getContext(), "New festival saved", Toast.LENGTH_LONG);
                    toast.show();
                }

                DBManager.Festivals.put(newFestival);
                finish();
            }
        }); // End OnClickListener
    } // End OnCreate
} // End class