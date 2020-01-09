package com.example.securityapplication2.Views.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.securityapplication2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class addGuardLocation extends Fragment {


    public addGuardLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_guard_location, container, false);
    }

}
