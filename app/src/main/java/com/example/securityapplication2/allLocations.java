package com.example.securityapplication2;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class allLocations extends Fragment {

    FirebaseFirestore db;
    Button button;

    View view;
    RecyclerView rView;
    RecyclerView.Adapter adapter;
    List<String> name, adress;

    public allLocations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_locations, container, false);

        db = FirebaseFirestore.getInstance();
        rView = view.findViewById(R.id.recView);

        rView.setHasFixedSize(true);

        name = new ArrayList<>();
        adress = new ArrayList<>();

        CollectionReference docRef = db.collection("Locations");

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    name.add(ds.getString("Name"));
                    adress.add(ds.getString("Adress"));
                }
                //Toast.makeText(view.getContext(), name.toString(), Toast.LENGTH_LONG).show();

                rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                adapter = new AdapterLocation(view.getContext(), name, adress);
                rView.setAdapter(adapter);

            }
        });

        button = view.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(getFragmentManager().getPrimaryNavigationFragment()).navigate(R.id.to_addLocation);
            }
        });

        return view;
    }

}
