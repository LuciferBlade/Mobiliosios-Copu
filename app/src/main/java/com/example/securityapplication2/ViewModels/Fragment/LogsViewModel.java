package com.example.securityapplication2.ViewModels.Fragment;

import androidx.lifecycle.ViewModel;

import com.example.securityapplication2.Models.Firebase;
import com.example.securityapplication2.Views.Fragments.Logs;

import java.util.ArrayList;
import java.util.List;

public class LogsViewModel extends ViewModel {
    Firebase firebase;
    Logs logs;

    public LogsViewModel(Logs logs){
        firebase = new Firebase();
        this.logs = logs;
    }

    public void getLogsData(){
        firebase.getAndListenLogs(new String[]{"logs", "users"},
                new String[]{"logDate","userUID","name", "uid"}, new int[]{2,2}, this);
    }

    public void returnData(ArrayList<String[]> values){
        List<String> name = new ArrayList<>();
        List<String> date = new ArrayList<>();

        for (int i = 0; i < values.size(); i++){
            name.add(values.get(i)[2]);
            date.add(values.get(i)[0]);
        }

        logs.changeFields(name, date);
    }

    public void setMessage(String message){
        logs.setToast(message);
    }

}
