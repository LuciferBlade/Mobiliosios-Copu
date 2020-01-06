package com.example.securityapplication2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth fAuth;

    View view;
    TextView name, email;//, uid;
    Button button;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.nameField);
        email = view.findViewById(R.id.emailField);
        //uid = view.findViewById(R.id.idField);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        DocumentReference docRef = db.collection("users")
                .document(fAuth.getCurrentUser().getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                name.setText(ds.getString("name"));
                email.setText(ds.getString("email"));
                //uid.setText(ds.getString("uid"));
            }
        });

        button = view.findViewById(R.id.editProfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().;
                NavHostFragment.findNavController(getFragmentManager().getPrimaryNavigationFragment()).navigate(R.id.to_editProfile);
            }
        });

        return view;
    }

}
