package com.example.securityapplication2.ViewModels.Fragment;

import androidx.lifecycle.ViewModel;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.Views.Fragments.ProtectedLocations;

import java.util.ArrayList;
import java.util.List;

public class ProtectedLocationsViewModel extends ViewModel {
    Firebase firebase;
    ProtectedLocations protectedLocations;

    public ProtectedLocationsViewModel(ProtectedLocations protectedLocations){
        firebase = new Firebase();
        this.protectedLocations = protectedLocations;
    }

    public void getProtectedLocData(){
        firebase.getAndListenProtectedLoc(new String[]{"guardsLocation","users", "Locations"},
                new String[]{"locationUID", "status", "userUID",//0,1,2
                        "name", "uid",                          //3,4
                        "Name", "Adress"},                      //5,6
                new int[]{3,2,2}, this);
    }

    public void returnData(ArrayList<String[]> values){
        List<String> name = new ArrayList<>();
        List<String> status = new ArrayList<>();
        List<String> locationName = new ArrayList<>();
        List<String> locationAddress = new ArrayList<>();

        for (int i = 0; i < values.size(); i++){
            name.add(values.get(i)[3]);
            status.add(values.get(i)[1]);
            locationName.add(values.get(i)[5]);
            locationAddress.add(values.get(i)[6]);
        }

        protectedLocations.changeFields(name, status, locationName, locationAddress);
    }

    public void setMessage(String message){
        protectedLocations.setToast(message);
    }
}
