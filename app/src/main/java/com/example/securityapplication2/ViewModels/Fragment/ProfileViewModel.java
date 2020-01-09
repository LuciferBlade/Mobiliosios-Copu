package com.example.securityapplication2.ViewModels.Fragment;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.R;
import com.example.securityapplication2.Views.Fragments.Profile;

public class ProfileViewModel extends ViewModel {
    Firebase firebase;
    Profile profile;

    public ProfileViewModel(Profile profile){
        firebase = new Firebase();
        this.profile = profile;
    }

    public void requestUserData(){
        firebase.getUserData(firebase.getCurrentUser().getUid(), this);
    }

    public void returnUserData(String[] data){
        profile.setFields(data);
    }

    public void navigateFragment(NavController navController) {
        navController.navigate(R.id.to_editProfile);
    }
}
