package com.example.festivalawardtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.festivalawardtracker.ui.studentUser.StudentUserFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignInUpActivity extends AppCompatActivity {

    private final String TAG = "SignInUpActivity";
    EditText emailInput;
    EditText passwordInput;

    String email;
    String password;

    Button btnSignIn, btnSignUp;
    FirebaseAuth mAuth;
    SharedPreferences sharedPref;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_up_activity);
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        email = sharedPref.getString("Email",null);
//        Log.d("email is ", email);
//        Log.d("passwordInput is ", passw);

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
                Log.d(TAG, "Email is: " + email);
                Student student = DBManager.getStudentByEmail(email);
                Teacher teacher = DBManager.getTeacherByEmail(email);
                if (student != null) {
                    Log.d(TAG, "Found Student");
                    Log.d(TAG, "Student:" + student.getFirstName());
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    Log.d(this.getClass().getName(), "...Finished");
                    Intent activityIntent = new Intent(SignInUpActivity.this, MainActivity.class);
                    activityIntent.putExtra("StudentID", student.ID);
                    activityIntent.putExtra("Type", 2);
                    startActivity(activityIntent);
                } else if (teacher != null) {
                    Log.d(TAG, "Found Teacher");
                    Log.d(TAG, "Teacher:" + teacher.getFirstName());
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    Log.d(this.getClass().getName(), "...Finished");
                    Intent activityIntent = new Intent(SignInUpActivity.this, MainActivity.class);
                    activityIntent.putExtra("Teacher", email);
                    activityIntent.putExtra("Type", 1);
                    startActivity(activityIntent);

                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthUI.getInstance()
                            .signOut(SignInUpActivity.this);
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User account deleted.");
                                    }
                                }
                            });
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(SignInUpActivity.this, "Email not found Please contact Teacher.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
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


        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.SignInEmailAddress);
        passwordInput = findViewById(R.id.SignInPassword);
        btnSignIn = findViewById(R.id.SignIn2);
        btnSignUp = findViewById(R.id.button2);

        emailInput.setText(email);
//        passwordInput.setText(password);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                boolean hasUppercase = !email.equals(email.toLowerCase());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Email", email);
                // https://medium.com/@dynamicy/the-difference-between-commit-and-apply-3093da13b831
                editor.apply();

                if(email.isEmpty()) {
                    emailInput.setError("Please Enter your Email");
                    emailInput.requestFocus();
                }
                else if (password.isEmpty()){
                    passwordInput.setError("Please enter your Password");
                    passwordInput.requestFocus();
                }
                else if (!(email.isEmpty() && password.isEmpty())){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG,email);
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignInActivity", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        new Thread(new queryThread(SignInUpActivity.this)).start();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignInActivity", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(SignInUpActivity.this, "Authentication failed",
                                                Toast.LENGTH_SHORT).show();
//                                        com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();
                boolean hasUppercase = !email.equals(email.toLowerCase());
                if (email.isEmpty()) {
                    emailInput.setError("Please Enter your Email");
                    emailInput.requestFocus();
                }
                else if (password.isEmpty()) {
                    passwordInput.setError("Please enter your Password");
                    passwordInput.requestFocus();
                }
                else if (hasUppercase){
                    Toast.makeText(SignInUpActivity.this, "Please enter email in lowercase.",
                            Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && password.isEmpty())) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignInUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign up success, update UI with the signed-in user's information
                                            Log.d("SignUpActivity", "createUserWithEmail:success");
                                            new Thread(new queryThread(SignInUpActivity.this)).start();

                                        } else {
                                            // If sign up fails, display a message to the user.
                                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignInUpActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                        // ...
                                    }
                                });
                }
                else {
                    Toast.makeText(SignInUpActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}