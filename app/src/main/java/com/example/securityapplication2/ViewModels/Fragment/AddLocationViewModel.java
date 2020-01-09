package com.example.securityapplication2.ViewModels.Fragment;

import android.text.TextUtils;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.Views.Fragments.AddLocation;

public class AddLocationViewModel extends ViewModel {
    private Firebase firebase;
    private AddLocation addLocation;

    private String name, address;

    public AddLocationViewModel(AddLocation addLocation) {
        firebase = new Firebase();
        this.addLocation = addLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean checkNameAndAddress(EditText nameField, EditText addressField) {
        if (TextUtils.isEmpty(name)) {
            nameField.setError("Email field is empty...");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            addressField.setError("Password field is empty...");
            return false;
        }
        return true;
    }

    public void addLocation(){
        firebase.addLocationData(name, address);
    }

    public void navigateFragment(NavController navController) {
        navController.popBackStack();
    }
}
