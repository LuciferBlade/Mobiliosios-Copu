package com.example.securityapplication2.Views.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Fragment.AddLocationViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLocation extends Fragment {
    private InputMethodManager imm;

    private EditText locName, locAddress;

    private AddLocationViewModel addlvm;

    public AddLocation() {
        addlvm = new AddLocationViewModel(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_location, container, false);

        imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Activity.INPUT_METHOD_SERVICE);

        locName = view.findViewById(R.id.locNameField);
        locAddress = view.findViewById(R.id.locAddressField);

        Button button = view.findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
                addlvm.setName(locName.getText().toString());
                addlvm.setAddress(locAddress.getText().toString());

                if (addlvm.checkNameAndAddress(locName, locAddress)) {
                    addlvm.addLocation();
                    addlvm.navigateFragment(NavHostFragment.findNavController(Objects.requireNonNull(getParentFragment())));
                }
            }
        });

        button = view.findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
                addlvm.navigateFragment(NavHostFragment.findNavController(Objects.requireNonNull(getParentFragment())));
            }
        });

        return view;
    }
}
