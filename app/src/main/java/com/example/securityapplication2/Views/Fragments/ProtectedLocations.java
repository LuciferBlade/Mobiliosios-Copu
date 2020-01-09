package com.example.securityapplication2.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityapplication2.Adapters.AdapterProtectedLocations;
import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Fragment.ProtectedLocationsViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProtectedLocations extends Fragment {

    private View view;
    private RecyclerView rView;

    private ProtectedLocationsViewModel plvm;

    public ProtectedLocations() {
        plvm = new ProtectedLocationsViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_protected_locations, container, false);

        rView = view.findViewById(R.id.recView);
        rView.setHasFixedSize(true);

        plvm.getProtectedLocData();

        return view;
    }

    public void changeFields(List<String> name, List<String> status, List<String> locName, List<String> locAddress) {

        rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.Adapter adapter = new AdapterProtectedLocations(view.getContext(), name, status, locName, locAddress);
        rView.setAdapter(adapter);
    }

    public void setToast(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
