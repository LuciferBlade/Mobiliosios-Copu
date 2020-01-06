package com.example.securityapplication2;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class editProfile extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore db;

    View view;
    EditText editText;
    Button button;

    public editProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editText = view.findViewById(R.id.newNameField);

        button = view.findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                String name = editText.getText().toString();

                if (TextUtils.isEmpty(name)){
                    editText.setError("Name field is empty...");
                    return;
                }

                fAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();

                Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

                DocumentReference docRef = db.collection("users")
                        .document(fAuth.getCurrentUser().getUid());

                Map<String, Object> user = new HashMap<>();

                user.put("email", fAuth.getCurrentUser().getEmail());
                user.put("name", name);
                user.put("uid", fAuth.getCurrentUser().getUid());

                docRef.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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
