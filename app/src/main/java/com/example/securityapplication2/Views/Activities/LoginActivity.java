package com.example.securityapplication2.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Activity.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginButton, registerButton;

    LoginViewModel lvm;

    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lvm = new LoginViewModel(this);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        pBar = findViewById(R.id.progressBar);

        if (lvm.checkOnline()) {
            lvm.startNewActivity(this, new MainActivity());
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvm.startNewActivity(LoginActivity.this, new RegisterActivity());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvm.setEmail(emailField.getText().toString());
                lvm.setPassword(passwordField.getText().toString());

                if (lvm.checkEmailAndPassword(emailField, passwordField)){
                    pBar.setVisibility(View.VISIBLE);
                    lvm.loginAction();
                }
            }
        });
    }

    // Feedback methods
    public void loginSuccess(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        lvm.startNewActivity(this, new MainActivity());
    }

    public void loginFail(String message){
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        pBar.setVisibility(View.INVISIBLE);
    }
}
