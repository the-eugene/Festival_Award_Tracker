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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignUp;
    FirebaseAuth mAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.SignUpEmailAddress);
        password = findViewById(R.id.SignUpPassword);
        btnSignUp = findViewById(R.id.SignUp2);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String passwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please Enter your Email");
                    emailId.requestFocus();
                }
                else if (passwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (!(email.isEmpty() && passwd.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignUpActivity", "createUserWithEmail:success");
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        // Get a reference to the database service
                                        database = FirebaseDatabase.getInstance().getReference("user");
                                        EditText userName = (EditText) findViewById(R.id.SignUpEmailAddress);
                                        String uName = userName.getText().toString().trim();
                                        UserDatabase User = new UserDatabase(uName);
                                        String id = database.push().getKey();
                                        database.child(id).setValue(User);
                                        Intent activityIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(activityIntent);
                                        finish();

                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignUpActivity", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}