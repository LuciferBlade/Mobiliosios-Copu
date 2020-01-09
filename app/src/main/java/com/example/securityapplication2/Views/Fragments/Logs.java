package com.example.securityapplication2.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityapplication2.Adapters.AdapterLogs;
import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Fragment.LogsViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Logs extends Fragment {

    private View view;
    private RecyclerView rView;

    private LogsViewModel lvm;

    public Logs() {
        lvm = new LogsViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_logs, container, false);

        rView = view.findViewById(R.id.recView);
        rView.setHasFixedSize(true);

        lvm.getLogsData();

        return view;
    }

    public void changeFields(List<String> name, List<String> email){

        rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.Adapter adapter = new AdapterLogs(view.getContext(), name, email);
        rView.setAdapter(adapter);
    }

    public void setToast(String message){
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
