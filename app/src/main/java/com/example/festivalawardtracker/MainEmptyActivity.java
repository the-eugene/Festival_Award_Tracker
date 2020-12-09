package com.example.festivalawardtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.festivalawardtracker.ui.studentUser.StudentUserFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

public class MainEmptyActivity extends Activity {

    private final String TAG = "MainEmptyActivity";
    String email;
    FirebaseAuth mAuth;
    Intent activityIntent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Task<GetTokenResult> b = User.getIdToken();
//        Log.d("Token", String.valueOf(b));

        class queryThread implements Runnable {
            final Activity activity;

            queryThread(Activity activity) {
                this.activity = activity;
            }

            @Override
            public void run() {
                Log.d(this.getClass().getName(), "Loading Teacher and Student Database...");
                DBManager.Teachers.loadAll();
                DBManager.Students.loadAll();
                Student student = DBManager.getStudentByEmail(email);
//                Log.d(TAG,"Student:"+ student.getFirstName());
                Teacher teacher = DBManager.getTeacherByEmail(email);
//                Log.d(TAG,"Teacher:" + teacher.getFirstName());
                if (student != null){
                    Log.d(TAG,"Found Student");
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    Log.d(this.getClass().getName(), "...Finished");
                    Intent activityIntent = new Intent(MainEmptyActivity.this, MainActivity.class);
                    activityIntent.putExtra("StudentID",student.ID);
                    activityIntent.putExtra("Type", 2);
                    startActivity(activityIntent);
                }else if (teacher != null){
                    Log.d(TAG,"Found Teacher");
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    Log.d(this.getClass().getName(), "...Finished");
                    Intent activityIntent = new Intent(MainEmptyActivity.this, MainActivity.class);
                    activityIntent.putExtra("Teacher",teacher.ID);
                    activityIntent.putExtra("Type",1);
                    startActivity(activityIntent);

                }else{
                    Log.d(TAG, "something went wrong further up the line");
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    AuthUI.getInstance()
//                            .signOut(MainEmptyActivity.this);
//                    user.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Log.d(TAG, "User account deleted.");
//                                    }
//                                }
//                            });

                }


//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
//                        DBManager.Festivals.loadAll();
//                        DBManager.EventDescriptions.loadAll();
//                        DBManager.Events.loadAll();
//                        DBManager.SchoolYears.loadAll();
//                        DBManager.currentYear = DBManager.findCurrentYear();
//                        Log.d(this.getClass().getName(), "...Finished");
//                    }
//                });


            }
        }

        // go straight to main if a token is stored
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        AuthUI.getInstance()
//                .signOut(MainEmptyActivity.this);
        if (user != null) {
            email = user.getEmail();
            Log.d(TAG, "Current Users email: "+ email);
            new Thread(new queryThread(MainEmptyActivity.this)).start();
//            activityIntent = new Intent(this, SignInUpActivity.class);
//            startActivity(activityIntent);
        } else {
            activityIntent = new Intent(this, SignInUpActivity.class);
            startActivity(activityIntent);
        }
        finish();

    }
}