package com.example.securityapplication2.ViewModels.Activity;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.R;
import com.example.securityapplication2.Views.Activities.MainActivity;

public class MainViewModel extends ViewModel {

    Firebase firebase;

    //kinda dumb...
    MainActivity activity;

    public MainViewModel(Activity activity) {
        firebase = new Firebase();
        this.activity = (MainActivity) activity;
    }

    public void startNewActivity(final Activity currentActivity, final Activity nextActivity) {
        currentActivity.startActivity(new Intent(currentActivity.getApplicationContext(), nextActivity.getClass()));
        currentActivity.finish();
    }

    public void logoutAction() {
        firebase.logoutAction(this);
    }

    public void logoutFeedback(){
        activity.notifyOut();
    }

    public int backButtonAction(int temp, NavController navController, MainActivity mainActivity) {
        if (temp > 0) {
            Navigation.findNavController(mainActivity.findViewById(R.id.fragment)).popBackStack();
            temp--;
        } else {
            mainActivity.finish();
            System.exit(0);
        }
        return temp;
    }

    public int menuSwitching(int menuItem, int temp, MainActivity mainActivity,  NavController navController){
        switch (menuItem) {
            case R.id.profile:
                temp++;
                navController.navigate(R.id.to_profile);
                break;

            case R.id.allLocations:
                temp++;
                navController.navigate(R.id.to_allLocations);
                break;

            case R.id.protectedLocations:
                temp++;
                navController.navigate(R.id.to_protectedLocations);
                break;

            case R.id.allGuards:
                temp++;
                navController.navigate(R.id.to_allGuards);
                break;

            case R.id.Logs:
                temp++;
                navController.navigate(R.id.to_logs);
                break;

            case R.id.Randamonium:
                temp++;
                navController.navigate(R.id.to_randamonium);
                break;

            case R.id.logout:
                mainActivity.logout();
                break;

            case R.id.exit:
                mainActivity.finish();
                System.exit(0);
                break;

            default:
                break;
        }
        return temp;
    }
}
