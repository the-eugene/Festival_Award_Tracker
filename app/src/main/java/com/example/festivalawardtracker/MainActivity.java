package com.example.festivalawardtracker;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int RC_SIGN_IN = 123;
    final int CREATE_FILE = 1;
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
                        R.id.nav_home, R.id.nav_festival, R.id.nav_ratings, R.id.nav_export)
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

    public void exportFile(MenuItem item){
        createFile(Uri.EMPTY);

    }

    private void createFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_TITLE, "students.csv");
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        startActivityForResult(intent, CREATE_FILE);
    }

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