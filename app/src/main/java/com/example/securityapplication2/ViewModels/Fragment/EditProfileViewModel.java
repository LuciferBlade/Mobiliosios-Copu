package com.example.securityapplication2.ViewModels.Fragment;

import android.text.TextUtils;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.Views.Fragments.EditProfile;

public class EditProfileViewModel extends ViewModel {
    private Firebase firebase;
    private EditProfile editProfile;

    private String name;

    public EditProfileViewModel(EditProfile editProfile) {
        firebase = new Firebase();
        this.editProfile = editProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean checkName(EditText nameField) {
        if (TextUtils.isEmpty(name)) {
            nameField.setError("Name field is empty...");
            return false;
        }
        return true;
    }

    public void setNewName() {
        firebase.changeNameAction(name, this);
    }

    public void navigateFragment(NavController navController) {
        navController.popBackStack();
    }

    public void notifyMove() {
        editProfile.navigate();
    }
}
