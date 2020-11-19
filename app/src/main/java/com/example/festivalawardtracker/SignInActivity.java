package com.example.festivalawardtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    EditText emailInput;
    EditText passwordInput;

    String email;
    String password;

    Button btnSignIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        email = sharedPref.getString("Email",null);
//        Log.d("email is ", email);
//        Log.d("passwordInput is ", passw);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.SignInEmailAddress);
        passwordInput = findViewById(R.id.SignInPassword);
        btnSignIn = findViewById(R.id.SignIn2);

        emailInput.setText(email);
        passwordInput.setText(password);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Email", email);
                // https://medium.com/@dynamicy/the-difference-between-commit-and-apply-3093da13b831
                editor.apply();

                password = passwordInput.getText().toString();

                if(email.isEmpty()) {
                    emailInput.setError("Please Enter your Email");
                    emailInput.requestFocus();
                }
                else if (password.isEmpty()){
                    passwordInput.setError("Please enter your Password");
                    passwordInput.requestFocus();
                }
                else if (!(email.isEmpty() && password.isEmpty())){
                    // TODO The entire set of conditions above could be deleted and the activity would still works. Carlos
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignInActivity", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                        Intent activityIntent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(activityIntent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignInActivity", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(SignInActivity.this, "Authentication failed",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                        // ...
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

}