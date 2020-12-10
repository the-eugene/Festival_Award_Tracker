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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * This class is used for Sign-in or Sign-up student and teachers. If signing in
 * it will authenticate them, download student and teacher database, check to see
 * if they are a student or teacher than send them to the main activity with their
 * ID and type. If signing up it will create a new login, download student and teacher
 * database, check to see if they are a student or teacher if they are send them to
 * the main activity with their ID and type, if not it will log them out, delete
 * credentials and prompt them to see their teacher.
 *
 * @author Cayla, Carlos, Jimmy, & Eugene
 * @see com.example.festivalawardtracker.ui.student.StudentFragment Fragment displayed immediately after this activity closes.
 * @see StudentUserFragment Same as StudentFragment, but for any student user.
 * @see MainActivity
 */
public class SignInUpActivity extends AppCompatActivity {

    //Used for debugging
    private final String TAG = "SIGNIN_SIGNUP_ACTIVITY";
    //Used to retrieve email and password textfield
    EditText emailInput, passwordInput;
    //Used to store email and password
    String email, password;
    //Used to retrieve sign-in and sign-up buttons
    Button btnSignIn, btnSignUp;
    //Initialize firebase authentication
    FirebaseAuth mAuth;
    //Initialize shared preferences
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_up_activity);
        Log.d(TAG, "OnCreate: " + this.getClass().getName());


        //Initialize shared preferences
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        //set the email to previously saved email
        email = sharedPref.getString("Email",null);
//        Log.d("email is ", email);

        /**
         * For downloading the database, as well as retrieving IDs and sending user to
         * the correct UI. Also for logging them out and deleting credentials if they
         * are not in data base.
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
                Log.d(TAG, "Email is: " + email);
                Student student = DBManager.getStudentByEmail(email);
                Teacher teacher = DBManager.getTeacherByEmail(email);
                //If a student is found
                if (student != null) {
                    Log.d(TAG, "Found Student");
                    Log.d(TAG, "Student:" + student.getFirstName());
                    //Load the rest of the data
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    Log.d(this.getClass().getName(), "...Finished");
                    //Creates an intent with the type 2 for student and the student ID
                    Intent activityIntent = new Intent(SignInUpActivity.this, MainActivity.class);
                    activityIntent.putExtra("StudentID", student.ID);
                    activityIntent.putExtra("Type", 2);
                    startActivity(activityIntent);
                }
                //If a teacher is found get their Id and send them to the main activity, as well as download the rest of the database.
                else if (teacher != null) {
                    Log.d(TAG, "Found Teacher");
                    Log.d(TAG, "Teacher:" + teacher.getFirstName());
                    //Load the rest of the data
                    Log.d(this.getClass().getName(), "Loading Festival and Event Database...");
                    DBManager.Festivals.loadAll();
                    DBManager.EventDescriptions.loadAll();
                    DBManager.Events.loadAll();
                    DBManager.SchoolYears.loadAll();
                    DBManager.currentYear = DBManager.findCurrentYear();
                    Log.d(this.getClass().getName(), "...Finished");
                    //Creates an intent with the type 1 for teacher and the teacher ID
                    Intent activityIntent = new Intent(SignInUpActivity.this, MainActivity.class);
                    activityIntent.putExtra("TeacherID", teacher.ID);
                    activityIntent.putExtra("Type", 1);
                    startActivity(activityIntent);

                } else {
                    //Finds current user
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //Signs Out user
                    AuthUI.getInstance()
                            .signOut(SignInUpActivity.this);
                    //deletes user
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User account deleted.");
                                    }
                                }
                            });
                    //prompts user to see teacher as email isn't in database
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(SignInUpActivity.this, "Email not found Please contact Teacher.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }

            }
        }

        //Initialize firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        //Retrieve email and password textfields
        emailInput = findViewById(R.id.SignInEmailAddress);
        passwordInput = findViewById(R.id.SignInPassword);
        //Retrieve sign-in and sign-up buttons
        btnSignIn = findViewById(R.id.SignIn2);
        btnSignUp = findViewById(R.id.button2);

        //sets textview to the email stored in shared preferences
        emailInput.setText(email);

        //Sign-In
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            /**
             * For checking required fields for sign-in. If successful calls queryThread class.
             * If it fails displays correct error message to the user.
             * @author Cayla, Carlos, Jimmy, & Eugene
             * @param v
             */

            @Override
            public void onClick(View v) {

                //retrieves email and password entered by the user
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                //checks to see if email has any uppercase letters
                boolean hasUppercase = !email.equals(email.toLowerCase());

                //stores email entered into shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Email", email);
                // https://medium.com/@dynamicy/the-difference-between-commit-and-apply-3093da13b831
                editor.apply();

                //checks to see if they left the email textview blank
                if(email.isEmpty()) {
                    emailInput.setError("Please Enter your Email");
                    emailInput.requestFocus();
                }
                //checks to see if they left the password textview blank
                else if (password.isEmpty()){
                    passwordInput.setError("Please enter your Password");
                    passwordInput.requestFocus();
                }
                //prompts them to retype with lowercase if email contains uppercase
                else if (hasUppercase){
                    emailInput.setError("Please enter email in lowercase.");
                    emailInput.requestFocus();
                }
                //If all the above have been successful
                else if (!(email.isEmpty() && password.isEmpty())){
                    //attempts to authenticate them calling firebase auth
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInUpActivity.this, new OnCompleteListener<AuthResult>() {

                                /**
                                 * If sign-in is successful starts queryThread. If sign-in fails
                                 * than displays correct error message.
                                 * @author Cayla, Carlos, Jimmy, & Eugene
                                 * @param task
                                 */
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG,email);
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignInActivity", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //Initializes queryThread
                                        new Thread(new queryThread(SignInUpActivity.this)).start();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignInActivity", "signInWithEmail:failure", task.getException());

                                        // Retrieves error code
                                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                        switch (errorCode) {

                                            //Typed in the wrong password
                                            case "ERROR_WRONG_PASSWORD":
//                                                Toast.makeText(SignInUpActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                                passwordInput.setError("password is incorrect ");
                                                passwordInput.requestFocus();
                                                passwordInput.setText("");
                                                break;

                                            //User is not in the database
                                            case "ERROR_USER_NOT_FOUND":
                                                Toast.makeText(SignInUpActivity.this, "User not found.", Toast.LENGTH_LONG).show();
                                                break;
                                        }
//                                        Toast.makeText(SignInUpActivity.this, "Authentication failed",
//                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            /**
             * For checking required fields for sign-up. If successful calls query class.
             * If it fails displays correct error message to the user.
             * @author Cayla, Carlos, Jimmy, & Eugene
             * @param v
             */

            @Override
            public void onClick(View v) {
                //retrieves email and password entered by the user
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                //checks to see if email has any uppercase letters
                boolean hasUppercase = !email.equals(email.toLowerCase());

                //checks to see if they left the email textview blank
                if (email.isEmpty()) {
                    emailInput.setError("Please Enter your Email");
                    emailInput.requestFocus();
                }
                //checks to see if they left the password textview blank
                else if (password.isEmpty()) {
                    passwordInput.setError("Please enter your Password");
                    passwordInput.requestFocus();
                }
                //prompts them to retype with lowercase if email contains uppercase
                else if (hasUppercase){
                    emailInput.setError("Please enter email in lowercase.");
                    emailInput.requestFocus();
                }
                //If all the above have been successful
                else if (!(email.isEmpty() && password.isEmpty())) {
                        //attempts to authenticate them calling firebase auth
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignInUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    /**
                                     * If sign-up is successful starts queryThread. If sign-up fails
                                     * than displays correct error message.
                                     * @author Cayla, Carlos, Jimmy, & Eugene
                                     * @param task
                                     */
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign up success, update UI with the signed-in user's information
                                            Log.d("SignUpActivity", "createUserWithEmail:success");
                                            //Initializes queryThread
                                            new Thread(new queryThread(SignInUpActivity.this)).start();

                                        } else {
                                            // If sign up fails, display a message to the user.
                                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.getException());
                                            // Retrieves error code
                                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                            switch (errorCode) {

                                                //Typed email incorrectly
                                                case "ERROR_INVALID_EMAIL":
//                                                    Toast.makeText(SignInUpActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                                    emailInput.setError("The email address is badly formatted.");
                                                    emailInput.requestFocus();
                                                    break;

                                                //Password must me at least 6 characters
                                                case "ERROR_WEAK_PASSWORD":
//                                                    Toast.makeText(SignInUpActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                                    passwordInput.setError("Password must be at least 6 characters");
                                                    passwordInput.requestFocus();
                                                    break;

                                                //The email is already signed up
                                                case "ERROR_EMAIL_ALREADY_IN_USE":
//                                                    Toast.makeText(SignInUpActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                                    emailInput.setError("The email address is already in use by another account.");
                                                    emailInput.requestFocus();
                                                    break;

                                            }
                                            Toast.makeText(SignInUpActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
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