package com.example.festivalawardtracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Central class of the application.
 * It handles the creation of the first view of the app, either for student or teacher user.
 * @author Jimmy, Cayla, Eugene, Carlos
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final String TAG = "MAIN_ACTIVITY";
    private static final int RC_SIGN_IN = 123;
    final int CREATE_FILE = 1;
    //String used to store user type number
    private static final String TYPE = "Type";
    //Used to store studentID
    String StudentID;
    //Used to store teacherID
    String TeacherID;

    /**
     * Returns the student ID so the fragment calling it can know what information to display.
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @return StudentID the Id of the student logged in.
     */
    public String getStudentID() {
        return StudentID;
    }

    /**
     * Returns the student ID so the fragment calling it can know what information to display.
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @return TeacherID the Id of the teacher logged in.
     */
    public String getTeacherID(){return TeacherID;}

    /**
     * Depending on who logs in sets the correct fragment for the user to see.
     * Case 1 is for teachers and case 2 is for students.
     * Also retrieves IDs needed for the fragments to display the correct information for a specific teacher or student.
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Retrieves the number from login for the user. 1 for Teacher, or 2 for student.
        Intent intent = getIntent();
        int userMode = intent.getIntExtra(TYPE,1);
        Log.d(TAG, "My number is (onCreate):" + userMode);

        super.onCreate(savedInstanceState);

        switch(userMode) {
            // https://brightinventions.pl/blog/handling-different-user-types-in-android-application
            case 1:
                //Sets the view for a teacher
                setContentView(R.layout.main_activity);

                //Retrieves the Teacher ID from login
                TeacherID = intent.getStringExtra("TeacherID");
                Log.d(TAG,"TeacherID:"+ TeacherID);

                //Retrieves and sets the toolbar or top bar in the app for the teacher.
                Toolbar mToolbar = findViewById(R.id.main_toolbar);
                setSupportActionBar(mToolbar);
                mToolbarMenuAction(mToolbar);

                //Retrieves and sets the drawer for the teacher view
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home, R.id.nav_festival, R.id.nav_ratings, R.id.nav_export)
                        .setOpenableLayout(drawer)
                        .build();
                NavigationView navigationView = findViewById(R.id.nav_view);
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
                Log.d(TAG, "Switch: 1 case (onCreate)");
                break;


            case 2:
                //Sets the view for a student
                setContentView(R.layout.main_student_user_activity);

                //Retrieves the Teacher ID from login
                StudentID = intent.getStringExtra("StudentID");
                Log.d(TAG,"StudentID:"+ StudentID);

                //Retrieves and sets the toolbar or top bar in the app for the student.
                Toolbar mToolbarStudentUser = findViewById(R.id.main_toolbar_student_user);
                setSupportActionBar(mToolbarStudentUser);
                mToolbarMenuAction(mToolbarStudentUser);

                //Retrieves and sets the drawer for the student view
                DrawerLayout drawerStudentUser = findViewById(R.id.drawer_layout_student_user);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home_student_user)
                        .setOpenableLayout(drawerStudentUser)
                        .build();
                NavigationView navigationViewStudentUser = findViewById(R.id.nav_view_student_user);
                NavController navControllerStudentUser = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navControllerStudentUser, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationViewStudentUser, navControllerStudentUser);

                Log.d(TAG, "Switch: 2 case (onCreate)");
                break;
            default:
                Log.d(TAG, "Switch: Default case (onCreate)");
                break;
        }
    } // End onCreate

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     * @author Cayla, Carlos, Jimmy, & Eugene
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
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @param menu
     * @return Boolean value
     * @link <a>https://stackoverflow.com/a/37562572/7389293</a> Not used
     * @link <a>https://developer.android.com/guide/topics/search/search-dialog.html#UsingSearchWidget</a>
     * This is the solution I used here.
     * @link <a>https://developer.android.com/guide/topics/ui/layout/recyclerview?authuser=1</a>
     * Not used
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem howToItem = menu.findItem(R.id.action_how_to);
        final MenuItem aboutItem = menu.findItem(R.id.action_about);
        //Retrieves the number from login for the user. 1 for Teacher, or 2 for student.
        Intent intent = getIntent();
        int userMode = intent.getIntExtra(TYPE,1);
        Log.d(TAG, "My number is (onCreateOptionsMenu):" + userMode);

        switch(userMode) {
            case 1:
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
                }); // End howToItem teacher

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
                }); // End aboutItem teacher

                Log.d(TAG, "Switch: 1 case (onCreateOptionsMenu)");
                return true;

            case 2:
                howToItem.setVisible(false);

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
                }); // End aboutItem student
                Log.d(TAG, "Switch: 2 case (onCreateOptionsMenu)");
                return true;
            default:
                Log.d(TAG, "Switch: Default case (onCreateOptionsMenu)");
                return true;
        }
    } // End onCreateOptionsMenu

    /**
     * Functionality for the log out button.
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @param item
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

    /**
     * @author Eugene
     * @param item
     */
    public void exportFile(MenuItem item){
        createFile(Uri.EMPTY);
    }

    /**
     * @author Eugene
     * @param pickerInitialUri
     */
    private void createFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, "students.csv");
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        startActivityForResult(intent, CREATE_FILE);
    }

    /**
     * @author Eugene
     * @param requestCode
     * @param resultCode
     * @param resultData
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == CREATE_FILE
                && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.d(this.getClass().getName(),uri.toString());
                // Perform operations on the document using its URI.
                saveFile(uri);
            }
        }
        super.onActivityResult(requestCode,resultCode, resultData);
    }


    /**
     * @author Eugene
     * @param uri
     */
    private void saveFile(Uri uri) {
        final String[] fields={"Event","Code","Club","Teacher","Last Name","First Name","Middle Name","Birthdate","PCS","PPs","Class","Rtg"};
        Map<String, String> row=new HashMap<>();
        String[] row_string=new String[fields.length];
        try {
            ParcelFileDescriptor pfd = getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());

            //write out headers
            fileOutputStream.write((String.join(",",fields)+"\n").getBytes());
            Log.d(this.getClass().getName(),"Processing Export");
            row.put("Code",""); //there are no codes
            for(String eID:DBManager.currentYear.eventIDs){
                Event e=DBManager.Events.get(eID);
                EventDescription ed= DBManager.EventDescriptions.get(e.eventDescriptionID);
                row.put("Event", ed.name);
                Log.d("Saving Event", ed.name);
                for(String sID:e.studentIDs){
                    Student s=DBManager.Students.get(sID);
                    //Teacher t=DBManager.Teachers.get(s.teacherIDs.get(0)); //TODO: V2, handle multiple teachers
                    Performance p=null;
                    for(Performance performance:s.performances)
                        if(performance.eventID.equals(eID))
                            p=performance;
                    if (p!=null) { //student actually participated
                        row.put("Club", "Wachter Music"); //TODO: V2, add club to Teacher
                        row.put("Teacher", "Nina Wachter"); //row.put("Teacher", t.getFullName());
                        row.put("Last Name", s.getLastName());
                        row.put("First Name", s.getFirstName());
                        row.put("Middle Name", s.getMiddleName());
                        row.put("Birthdate", s.getBirthday());
                        row.put("PCS", Integer.toString(s.findPCS(ed)));
                        row.put("PPs", Integer.toString(s.totalAccumulatedPoints(ed)-p.rating));
                        row.put("Class",p.level);
                        row.put("Rtg",Integer.toString(p.rating));
                        for(int i=0;i<fields.length;i++)
                            row_string[i]=row.get(fields[i]);
                        fileOutputStream.write((String.join(",",row_string)+"\n").getBytes());
                        Log.d("FileExport Row",String.join(",",row_string)+"\n");
                    }
                }
            }
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            Log.wtf(this.getClass().getName(), "File not found in saveFile()");
            e.printStackTrace();
        } catch (IOException e) {
            Log.wtf(this.getClass().getName(), "Failed Write in saveFile()");
            e.printStackTrace();
        }
    }
}