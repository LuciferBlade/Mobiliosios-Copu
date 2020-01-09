package com.example.securityapplication2.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Activity.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    EditText emailField, passwordField;
    Button registerButton, backButton;

    RegisterViewModel rvm;

    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rvm = new RegisterViewModel(this);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);

        pBar = findViewById(R.id.progressBar);

        if (rvm.checkOnline()) {
            rvm.startNewActivity(this, new MainActivity());
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvm.startNewActivity(RegisterActivity.this, new LoginActivity());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvm.setEmail(emailField.getText().toString());
                rvm.setPassword(passwordField.getText().toString());

                if (rvm.checkEmailAndPassword(emailField, passwordField)){
                    pBar.setVisibility(View.VISIBLE);
                    rvm.registerAction();
                }
            }
        });
    }

    // Feedback methods
    public void registerSuccess(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
        rvm.startNewActivity(this, new MainActivity());
    }

    public void registerFail(String message){
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
        pBar.setVisibility(View.INVISIBLE);
    }
}
