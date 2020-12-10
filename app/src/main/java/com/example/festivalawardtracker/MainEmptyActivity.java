package com.example.festivalawardtracker;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * This class is the first thing that is brought up when the app is first opened
 * it will check to see if the user is currently logged in or not.
 * If they are logged in it will check to see if they are a student or teacher than
 * send them to the main activity with their ID and type.
 * If they are not logged in it will send them to the login/sign up activity.
 * @author Cayla, Carlos, Jimmy, & Eugene
 */

public class MainEmptyActivity extends Activity {

    //For debugging purposes
    private final String TAG = "MainEmptyActivity";
    //For storing the current logged in users email
    String email;
    //For initializing an Intent
    Intent activityIntent;


    /**
     * Responsible for checking if a user is signed in. If they are not send them to login/sign up.
     * If they are signed in  download the database, and retrieve their Id. After the database is
     * downloaded send them to the main activity.
     * @author Cayla, Carlos, Jimmy, & Eugene
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * For downloading the database, as well as retrieving IDs and sending user to
         * the correct UI
         * @author Cayla, Carlos, Jimmy, & Eugene
         */
        class queryThread implements Runnable {
            final Activity activity;

            //retrieves activity that called this class
            queryThread(Activity activity) {
                this.activity = activity;
            }

            @Override
            public void run() {
                //Download all teachers and students
                Log.d(this.getClass().getName(), "Loading Teacher and Student Database...");
                DBManager.Teachers.loadAll();
                DBManager.Students.loadAll();
                Student student = DBManager.getStudentByEmail(email);
                Teacher teacher = DBManager.getTeacherByEmail(email);
                //If a student is found
                if (student != null){
                    Log.d(TAG,"Found Student, Student: "+ student.getFirstName());
                    //Load the rest of the data
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    //Creates an intent with the type 2 for student and the student ID
                    Log.d(this.getClass().getName(), "...Finished");
                    activityIntent = new Intent(MainEmptyActivity.this, MainActivity.class);
                    activityIntent.putExtra("StudentID",student.ID);
                    activityIntent.putExtra("Type", 2);
                    startActivity(activityIntent);
                }
                //If a teacher is found
                else if (teacher != null){
                    Log.d(TAG,"Found Teacher, Teacher: " + teacher.getFirstName());
                    //Load the rest of the data
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    //Creates an intent with the type 1 for teacher and the teacher ID
                    Log.d(this.getClass().getName(), "...Finished");
                    activityIntent = new Intent(MainEmptyActivity.this, MainActivity.class);
                    activityIntent.putExtra("TeacherID",teacher.ID);
                    activityIntent.putExtra("Type",1);
                    startActivity(activityIntent);

                }
                /*If they are not a student or a teacher they shouldn't have been able to login so upon reopening
                app it should direct them to either a student or a teacher if it reaches this pont something went wrong
                during the login process or trying to find them in the data base.
                */
                else{
                    Log.d(TAG, "something went wrong further up the line");

                }
            }
        }

        // retrieve who the current user is
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //If there is a current user retrieve email and start a query
        if (user != null) {
            email = user.getEmail();
            Log.d(TAG, "Current Users email: "+ email);
            new Thread(new queryThread(MainEmptyActivity.this)).start();
        //If there isn't a current user send them to the login/Sign up activity
        } else {
            activityIntent = new Intent(this, SignInUpActivity.class);
            startActivity(activityIntent);
        }
        finish();

    }
}