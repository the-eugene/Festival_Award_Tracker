package com.example.festivalawardtracker.ui.festival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.festivalawardtracker.Answer;
import com.example.festivalawardtracker.DBManager;
import com.example.festivalawardtracker.Festival;
import com.example.festivalawardtracker.R;
import com.google.android.material.button.MaterialButton;

/**
 * Create new festival activity that adds festivals to the database
 * @author carloswashingtonmercado@gmail.com and Jimmy
 */
public class FestivalNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.festival_new_activity);

        final EditText editFestivalName = (EditText)findViewById(R.id.editText_festivalName);
        final AutoCompleteTextView editIsNFMC = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextViewDropdownNewFestivalNFMC);

        /* ACTION BAR */
        Toolbar toolbar = findViewById(R.id.toolbar_newFestival);
        toolbar.setTitle("Add Festival");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
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

                finish();
            }
        }); // End OnClickListener

    } // End OnCreate
}