package com.example.festivalawardtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignIn extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.SignInEmailAddress);
        password = findViewById(R.id.SignInPassword);
        btnSignIn = findViewById(R.id.SignIn2);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String passwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please Enter your Email");
                    emailId.requestFocus();
                }
                else if (passwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (!(email.isEmpty() && passwd.isEmpty())){
                    mAuth.signInWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignIn", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                        Intent activityIntent = new Intent(SignIn.this, MainActivity.class);
                                        startActivity(activityIntent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignIn", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(SignIn.this, "Authentication failed.",
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