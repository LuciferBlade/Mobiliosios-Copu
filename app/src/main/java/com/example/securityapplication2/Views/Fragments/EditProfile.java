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
import com.example.securityapplication2.ViewModels.Fragment.EditProfileViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfile extends Fragment {
    private InputMethodManager imm;

    private EditText nameField;

    private EditProfileViewModel epvm;

    public EditProfile() {
        epvm = new EditProfileViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Activity.INPUT_METHOD_SERVICE);
        nameField = view.findViewById(R.id.newNameField);

        Button button = view.findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                epvm.setName(nameField.getText().toString());

                if (epvm.checkName(nameField)) {
                    epvm.setNewName();
                }
            }
        });

        button = view.findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                epvm.navigateFragment(NavHostFragment.findNavController(Objects.requireNonNull(getParentFragment())));
            }
        });

        return view;
    }

    public void navigate() {
        epvm.navigateFragment(NavHostFragment.findNavController(Objects.requireNonNull(getParentFragment())));
    }

}
