package com.example.festivalawardtracker;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int RC_SIGN_IN = 123;

    // FRAGMENT HOME RECYCLERVIEW variables
    private RecyclerView recyclerView;
    //    studentAdapter adapter;
    DatabaseReference database;
    //    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int userMode = 1;

        super.onCreate(savedInstanceState);

        switch(userMode) {
            // https://brightinventions.pl/blog/handling-different-user-types-in-android-application
            case 1:
                setContentView(R.layout.activity_main);

                Toolbar mToolbar = findViewById(R.id.main_toolbar);
                setSupportActionBar(mToolbar);
                mToolbarMenuAction(mToolbar);

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);

                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.nav_festival, R.id.nav_event)
                        .setDrawerLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);

                // TODO: FRAGMENT HOME RECYCLERVIEW
                database = FirebaseDatabase.getInstance().getReference();
                recyclerView = findViewById(R.id.recyclerView_student);

                Log.d("MAIN_ACTIVITY", "Switch: 1 case");
                break;
            case 2:
//                setContentView(R.layout.activity_main_student_user);

//                Toolbar mToolbar = findViewById(R.id.main_toolbar);
//                setSupportActionBar(mToolbar);
//                mToolbarMenuAction(mToolbar);
//
//                DrawerLayout drawer = findViewById(R.id.drawer_layout);
//                NavigationView navigationView = findViewById(R.id.nav_view);
//
//                mAppBarConfiguration = new AppBarConfiguration.Builder(
//                        R.id.nav_home, R.id.nav_festival, R.id.nav_event)
//                        .setDrawerLayout(drawer)
//                        .build();
//                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//                NavigationUI.setupWithNavController(navigationView, navController);

                Log.d("MAIN_ACTIVITY", "Switch: 2 case");
                break;
            default:
                Log.d("MAIN_ACTIVITY", "Switch: Default case");
                break;
        }
    }

    private void mToolbarMenuAction(Toolbar mToolbar) {
        // TODO Maybe with a OnClickListener, for the items in the inflated menu
    }

    /**
     * @author carloswashingtonmercado@gmail.com
     * @param menu
     * @return
     * @link <a>https://stackoverflow.com/a/37562572/7389293</a> Not used
     * @link <a>https://developer.android.com/guide/topics/search/search-dialog.html#UsingSearchWidget</a>
     * This is the solution I used here.
     * @link <a>https://developer.android.com/guide/topics/ui/layout/recyclerview?authuser=1</a>
     * Not used
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_activity2, menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Iconify the widget by default

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void createSignInIntent(View view) {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    public void questionIsItClickable(View view) {
        Toast.makeText(this, "It is toasting, yeah.", Toast.LENGTH_SHORT).show();
    }

// TODO: Works Here but I think it should have its own class?

    public void saveStudent(View view) {
        Toast.makeText(this, "Student Saved", Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance().getReference("student");
        EditText editFirstName = (EditText) findViewById(R.id.editTextStudentName);
//        EditText editLastName = (EditText) findViewById(R.id.editTextStudentLastName);
//        EditText editBirthDate = (EditText) findViewById(R.id.editTextBirthDate);
//        Spinner editGender = (Spinner) findViewById(R.id.spinnerGender);
//        EditText editPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
//        Spinner editInstruments = (Spinner) findViewById(R.id.spinnerInstruments);
//        EditText editTeacher = (EditText) findViewById(R.id.editTextTeachersName);

        String FirstName = editFirstName.getText().toString().trim();
//        String lastName = editLastName.getText().toString().trim();
//        String birthDate = editBirthDate.getText().toString().trim();
//        String gender = editGender.getSelectedItem().toString();
//        String phoneNumber = editPhoneNumber.getText().toString().trim();
//        String instrument = editInstruments.getSelectedItem().toString();
//        String teacher = editTeacher.getText().toString().trim();

//        studentDatabase student = new studentDatabase(FirstName, lastName, birthDate, gender, phoneNumber, instrument, teacher);
//        studentDatabase student = new studentDatabase(FirstName);
        String id = database.push().getKey();

//        database.child(id).setValue(student);


    }

    /**
     * @author Carlos
     * @param item
     * Functionality for the log out button.
     */
    public void loggingOut(MenuItem item) {
        // Accesing Items in the Menu Drawer, using OnClick attribute in the drawer menu xml file
        // https://developer.android.com/guide/topics/resources/menu-resource
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent activityIntent = new Intent(MainActivity.this, LogInOptionsActivity.class);
                        startActivity(activityIntent);
                        finish();
                    }
                });
    }
}