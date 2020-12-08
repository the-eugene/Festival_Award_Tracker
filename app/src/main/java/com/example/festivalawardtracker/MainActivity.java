package com.example.festivalawardtracker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;


import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

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

        Thread preload=DBManager.preload(this);
        preload.start();


        //TODO: code used to add the four of us as teachers, Delete once testing is done
//        Teacher newTeacher = new Teacher();
//        Contact newContact = new Contact();
//        newTeacher.firstName = "Carlos";
//        newTeacher.middleName = "C";
//        newTeacher.lastName = "Mercado";
//        newTeacher.gender = Person.Gender.MALE;
//        newTeacher.birthday = LocalDate.of(1991,03,23);
//        newContact.email = "superegotist@gmail.com";
//        newTeacher.setContact(newContact);
//        DBManager.Teachers.put(newTeacher);

        int userMode = 1; // yserMode = 2 displays Student Mode

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
                        R.id.nav_home, R.id.nav_festival, R.id.nav_ratings)
                        .setDrawerLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);


                Log.d("MAIN_ACTIVITY", "Switch: 1 case");
                break;
            case 2:
                setContentView(R.layout.activity_main_student_user);

                Toolbar mToolbarStudentUser = findViewById(R.id.main_toolbar_student_user);
                setSupportActionBar(mToolbarStudentUser);
                mToolbarMenuAction(mToolbarStudentUser);

                DrawerLayout drawerStudentUser = findViewById(R.id.drawer_layout_student_user);
                NavigationView navigationViewStudentUser = findViewById(R.id.nav_view_student_user);
//
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home_student_user)
                        .setDrawerLayout(drawerStudentUser)
                        .build();
                NavController navControllerStudentUser = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navControllerStudentUser, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationViewStudentUser, navControllerStudentUser);

                Log.d("MAIN_ACTIVITY", "Switch: 2 case");
                break;
            default:
                Log.d("MAIN_ACTIVITY", "Switch: Default case");
                break;
        }
    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     * @author Carlos
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void mToolbarMenuAction(Toolbar mToolbar) {
        // TODO Maybe with a OnClickListener, for the items in the inflated menu
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
                        Intent activityIntent = new Intent(MainActivity.this, SignInUpActivity.class);
                        startActivity(activityIntent);
                        finish();
                    }
                });
    }
}