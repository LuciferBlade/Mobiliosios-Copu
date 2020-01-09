package com.example.securityapplication2.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityapplication2.Adapters.AdapterLocation;
import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Fragment.AllLocationsViewModel;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllLocations extends Fragment {

    private View view;
    private RecyclerView rView;

    private AllLocationsViewModel locations;

    public AllLocations() {
        locations = new AllLocationsViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all_locations, container, false);

        rView = view.findViewById(R.id.recView);
        rView.setHasFixedSize(true);

        locations.getLocationsData();

        Button button = view.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                locations.navigateFragment(NavHostFragment.findNavController(Objects.requireNonNull(getFragmentManager().getPrimaryNavigationFragment())));
            }
        });

        return view;
    }

    public void changeFields(List<String> name, List<String> adress) {

        rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.Adapter adapter = new AdapterLocation(view.getContext(), name, adress);
        rView.setAdapter(adapter);
    }

    public void setToast(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
