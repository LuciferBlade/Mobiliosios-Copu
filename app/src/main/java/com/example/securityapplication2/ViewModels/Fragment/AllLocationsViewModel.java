package com.example.securityapplication2.ViewModels.Fragment;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.R;
import com.example.securityapplication2.Views.Fragments.AllLocations;

import java.util.ArrayList;
import java.util.List;

public class AllLocationsViewModel extends ViewModel {

    private Firebase firebase;
    private AllLocations locations;

    public AllLocationsViewModel(AllLocations locations){
        firebase = new Firebase();
        this.locations = locations;
    }

    public void getLocationsData(){
        firebase.getAndListenLocations("Locations", new String[]{"Name", "Adress"}, this);
    }

    public void returnData(ArrayList<String[]> values){
        List<String> name = new ArrayList<>();
        List<String> email = new ArrayList<>();

        for (int i = 0; i < values.size(); i++){
            name.add(values.get(i)[0]);
            email.add(values.get(i)[1]);
        }

        locations.changeFields(name, email);
    }

    public void setMessage(String message){
        locations.setToast(message);
    }

    public void navigateFragment(NavController navController) {
        navController.navigate(R.id.to_addLocation);
    }
}
