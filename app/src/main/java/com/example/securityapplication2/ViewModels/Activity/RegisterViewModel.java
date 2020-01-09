package com.example.securityapplication2.ViewModels.Activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.Views.Activities.RegisterActivity;

public class RegisterViewModel extends ViewModel {
    Firebase firebase;

    //kinda dumb...
    RegisterActivity activity;

    private String email, password;

    public RegisterViewModel(Activity activity) {
        firebase = new Firebase();
        this.activity = (RegisterActivity) activity;
    }

    public boolean checkOnline() {
        return firebase.getCurrentUser() != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void startNewActivity(final Activity currentActivity, final Activity nextActivity) {
        currentActivity.startActivity(new Intent(currentActivity.getApplicationContext(), nextActivity.getClass()));
        currentActivity.finish();

    }

    public boolean checkEmailAndPassword(EditText emailField, EditText passwordField) {
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Email field is empty...");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Password field is empty...");
            return false;
        }
        if (password.length() < 6) {
            passwordField.setError("Password must be at least 6 characters long!");
            return false;
        }
        return true;
    }

    public void registerAction() {
        firebase.registerAction(email, password, this);
    }

    public void notifySuccess(String message) {
        activity.registerSuccess(message);
    }

    public void notifyFail(String message) {
        activity.registerFail(message);
    }
}
