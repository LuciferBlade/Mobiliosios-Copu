package com.example.securityapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginButton, registerButton;
    FirebaseAuth fAuth;
    ProgressBar pBar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        pBar = findViewById(R.id.progressBar);


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    emailField.setError("Email field is empty...");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordField.setError("Password field is empty...");
                    return;
                }
                if (password.length() < 6) {
                    passwordField.setError("Password must be at least 6 characters long!");
                    return;
                }

                pBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DocumentReference docRef2 = db.collection("logs").document();

                            Map<String, Object> log = new HashMap<>();

                            log.put("logDate", FieldValue.serverTimestamp());
                            log.put("userUID", fAuth.getCurrentUser().getUid());

                            docRef2.set(log);

                            Toast.makeText(LoginActivity.this, "You have logged in!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                            pBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }
}
