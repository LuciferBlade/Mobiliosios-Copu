package com.example.securityapplication2.ViewModels.Fragment;

import androidx.lifecycle.ViewModel;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.Views.Fragments.AllGuards;

import java.util.ArrayList;
import java.util.List;

public class AllGuardsViewModel extends ViewModel {
    Firebase firebase;
    public AllGuards guards;

    public AllGuardsViewModel(AllGuards guards){
        firebase = new Firebase();
        this.guards = guards;
    }

    public void getGuardsData(){
        firebase.getAndListenGuards("users", new String[]{"name", "email"}, this);
    }

    public void returnData(ArrayList<String[]> values){
        List<String> name = new ArrayList<>();
        List<String> email = new ArrayList<>();

        for (int i = 0; i < values.size(); i++){
            name.add(values.get(i)[0]);
            email.add(values.get(i)[1]);
        }

        guards.changeFields(name, email);
    }

    public void setMessage(String message){
        guards.setToast(message);
    }

}
