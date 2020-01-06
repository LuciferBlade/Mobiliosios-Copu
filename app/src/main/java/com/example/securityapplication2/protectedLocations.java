package com.example.securityapplication2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class protectedLocations extends Fragment {
    FirebaseFirestore db;

    View view;
    RecyclerView rView;
    RecyclerView.Adapter adapter;
    List<String> name, uid, locUID, finalStatus;

    public protectedLocations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_protected_locations, container, false);              //!!!!

        db = FirebaseFirestore.getInstance();
        rView = view.findViewById(R.id.recView);

        rView.setHasFixedSize(true);

        uid = new ArrayList<>();
        locUID = new ArrayList<>();
        finalStatus = new ArrayList<>();

        final CollectionReference[] docRef = {db.collection("guardsLocation")};

        docRef[0].get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    uid.add(ds.getString("userUID"));
                    locUID.add(ds.getString("locationUID"));
                    finalStatus.add(ds.getString("status"));
                }
                //Toast.makeText(view.getContext(), uid.toString(), Toast.LENGTH_LONG).show();

                final List<String> finalName = new ArrayList<>();

                docRef[0] = db.collection("users");

                docRef[0].get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot qs = task.getResult();
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            for (int i = 0; i < finalStatus.size(); i++) {
                                if (uid.get(i).equals(ds.getString("uid"))) {
                                    finalName.add(ds.getString("name"));
                                }
                            }
                        }

                        final List<String> finalAddress = new ArrayList<>();
                        final List<String> finalLocName = new ArrayList<>();

                        docRef[0] = db.collection("Locations");

                        docRef[0].get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                QuerySnapshot qs = task.getResult();
                                for (DocumentSnapshot ds : qs.getDocuments()) {
                                    for (int i = 0; i < finalStatus.size(); i++) {
                                        if (locUID.get(i).equals(ds.getId())) {
                                            finalLocName.add(ds.getString("Name"));
                                            finalAddress.add(ds.getString("Adress"));
                                        }
                                    }
                                }

                                rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                                adapter = new AdapterProtectedLocations(view.getContext(), finalName,
                                        finalStatus, finalLocName, finalAddress);
                                rView.setAdapter(adapter);
                            }
                        });
                    }
                });
            }
        });


        return view;
    }
}
