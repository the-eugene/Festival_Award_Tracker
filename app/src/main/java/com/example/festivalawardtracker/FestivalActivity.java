package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

/**
 * Create new festival activity that adds festivals to the database
 * @author carloswashingtonmercado@gmail.com and Jimmy
 */
public class FestivalActivity extends AppCompatActivity {

    private Toolbar toolbarFestival;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_festival);
        final EditText editFestivalName = (EditText)findViewById(R.id.editTextNewFestivalName);
        final AutoCompleteTextView editIsNFMC = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewDropdownNewFestivalNFMC);

                /* ACTION BAR */
        toolbarFestival = findViewById(R.id.toolbarNewFestival);
        toolbarFestival.setTitle("Add Festival");
        toolbarFestival.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbarFestival);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* DROPDOWN LIST is NFMC festival? Yes or No question */
        String[] YESORNO = Answer.Options();
        // ADAPTER
        ArrayAdapter<String> adapterYesOrNO =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_layout,
                        YESORNO);
        AutoCompleteTextView editTextFilledExposedDropdownInstruments =
                this.findViewById(R.id.autoCompleteTextViewDropdownNewFestivalNFMC);
        editTextFilledExposedDropdownInstruments.setAdapter(adapterYesOrNO);

        //firebase set up
        MaterialButton addFestival = findViewById(R.id.btnSaveFestival);
        addFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Festival f = new Festival();
                boolean check;
                String YesNo;
                YesNo = editIsNFMC.getEditableText().toString();

                //convert isNFMC to boolean answers
                if (YesNo.equalsIgnoreCase("yes")){
                    check = true;
                }
                else{
                    check = false;
                }

                //attach to hashmap and push to database
                f.name = editFestivalName.getText().toString().trim();
                f.isNFMC = check;
                DBManager.Festivals.put(f);
                Toast toast = Toast.makeText(v.getContext(), "New festival saved", Toast.LENGTH_LONG);
                toast.show();

                //clear input boxes
                editFestivalName.setText("");
                editIsNFMC.setText("");
            }
        });

    } // End OnCreate
}