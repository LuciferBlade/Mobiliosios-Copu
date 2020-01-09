package com.example.securityapplication2.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.securityapplication2.R;
import com.example.securityapplication2.ViewModels.Fragment.ProfileViewModel;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private ProfileViewModel pvm;

    private TextView name, email;//, uid;

    public Profile() {
        pvm = new ProfileViewModel(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.nameField);
        email = view.findViewById(R.id.emailField);

        pvm.requestUserData();

        Button button = view.findViewById(R.id.editProfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                pvm.navigateFragment(NavHostFragment.findNavController(Objects.requireNonNull(getFragmentManager().getPrimaryNavigationFragment())));
            }
        });

        return view;
    }

    public void setFields(String[] data){
        name.setText(data[0]);
        email.setText(data[1]);
    }

}
