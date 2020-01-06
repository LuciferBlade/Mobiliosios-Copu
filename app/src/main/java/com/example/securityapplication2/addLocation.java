package com.example.securityapplication2;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class addLocation extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore db;

    View view;
    EditText locName, locAddress;
    Button button;

    public addLocation(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_location, container, false);

        locName = view.findViewById(R.id.locNameField);
        locAddress = view.findViewById(R.id.locAddressField);

        button = view.findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                String name = locName.getText().toString();
                String address = locAddress.getText().toString();

                if (TextUtils.isEmpty(name)){
                    locName.setError("Location name field is empty...");
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    locAddress.setError("Location address field is empty...");
                    return;
                }

                fAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();


                DocumentReference docRef = db.collection("Locations")
                        .document();

                Map<String, Object> location = new HashMap<>();

                location.put("Name", name);
                location.put("Adress", address);

                docRef.set(location).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        NavHostFragment.findNavController(getParentFragment()).popBackStack();
                    }
                });
            }
        });

        button = view.findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                NavHostFragment.findNavController(getParentFragment()).popBackStack();
            }
        });

        return view;
    }
}
