package com.example.festivalawardtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 123;
    private static final String TYPE = "Type";
    // FRAGMENT HOME RECYCLERVIEW variables
    private RecyclerView recyclerView;
    //    studentAdapter adapter;
    DatabaseReference database;
    //    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String StudentID;

    public String getStudentID() {
        return StudentID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

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


//        class queryThread implements Runnable {
//            final Activity activity;
//
//            queryThread(Activity activity) {
//                this.activity = activity;
//            }
//
//            @Override
//            public void run() {
//                Log.d(this.getClass().getName(), "Loading Teacher and Student Database...");
//                DBManager.Teachers.loadAll();
//                DBManager.Students.loadAll();
//                Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
//                DBManager.Festivals.loadAll();
//                DBManager.EventDescriptions.loadAll();
//                DBManager.Events.loadAll();
//                DBManager.SchoolYears.loadAll();
//                DBManager.currentYear=DBManager.findCurrentYear();
//                Log.d(this.getClass().getName(), "...Finished");
//            }
//        }
//        new Thread(new queryThread(this)).start();

        Intent intent = getIntent();
        int userMode = intent.getIntExtra("Type",1);
        Log.d(TAG, "My number is:" + userMode);

        super.onCreate(savedInstanceState);

        switch(userMode) {
            // https://brightinventions.pl/blog/handling-different-user-types-in-android-application
            case 1:
                setContentView(R.layout.main_activity);

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
                setContentView(R.layout.main_student_user_activity);
                StudentID = intent.getStringExtra("StudentID");
                Log.d(TAG,"StudentID:"+ StudentID);

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
     * It handles the creation of options menu from the action bar.
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
        final MenuItem howToItem = menu.findItem(R.id.action_how_to);
        final MenuItem aboutItem = menu.findItem(R.id.action_about);

        howToItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final MaterialAlertDialogBuilder builderHowTo = new MaterialAlertDialogBuilder(MainActivity.this);
                builderHowTo.setTitle("INSTRUCTIONS");
                builderHowTo.setMessage(
                        "1) You can chose from three different lists in the left hand side menu: Student List, Festival List, and Rate Students (list)." +
                                "\n\n2) If you want to add any new item, click on the right lower hand side floating button with a plus sign." +
                                "\n\n3) If you want to edit information about the students, festivals or events, long press for around 2 seconds in the chosen item until the edition screen opens.");

                builderHowTo.setPositiveButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderHowTo.show();
                return false;
            }
        }); // End howToItem

        aboutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final MaterialAlertDialogBuilder builderAbout = new MaterialAlertDialogBuilder(MainActivity.this);
                builderAbout.setTitle("ABOUT THIS APP");
                builderAbout.setIcon(R.mipmap.ic_launcher);
                builderAbout.setMessage(
                        "College Project for CS246" +
                                "\nBrigham Young University, Idaho" +
                                "\nFall 2020" +
                                "\n\nCS246 - Software Design and Development" +
                                "\n\nTeacher: Dario Suarez Gonzalez" +
                                "\nStudents: Eugene Williams, Jimmy England, Cayla Tribett, Carlos W. Mercado" +
                                "\n\nHope never faileth.");

                builderAbout.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(getBaseContext(), "Thanks for passing by ;)", Toast.LENGTH_SHORT);
                        toast.show();
                        dialog.dismiss();
                    }
                });
                builderAbout.show();
                return false;
            }
        }); // End aboutItem
        return true;
    } // End onCreateOptionsMenu



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