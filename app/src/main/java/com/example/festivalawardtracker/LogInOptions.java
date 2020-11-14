package com.example.festivalawardtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInOptions extends AppCompatActivity {

    Button btnSignUp;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);
        btnSignUp = findViewById(R.id.signUp);
        btnSignIn = findViewById(R.id.signIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View V){
               Intent activityIntent = new Intent(LogInOptions.this, SignUp.class);
               startActivity(activityIntent);
               finish();
           }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                Intent activityIntent = new Intent(LogInOptions.this, SignIn.class);
                startActivity(activityIntent);
                finish();
            }
        });
    }
}