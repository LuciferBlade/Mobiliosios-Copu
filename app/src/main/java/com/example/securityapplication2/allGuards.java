package com.example.securityapplication2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class allGuards extends Fragment {

    FirebaseFirestore db;

    View view;
    RecyclerView rView;
    RecyclerView.Adapter adapter;
    List<String> name, email;

    public allGuards() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_guards, container, false);

        db = FirebaseFirestore.getInstance();
        rView = view.findViewById(R.id.recView);

        rView.setHasFixedSize(true);

        name = new ArrayList<>();
        email = new ArrayList<>();

        CollectionReference docRef = db.collection("users");

        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    name.add(ds.getString("name"));
                    email.add(ds.getString("email"));
                }
                //Toast.makeText(view.getContext(), name.toString(), Toast.LENGTH_LONG).show();

                rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                adapter = new Adapter(view.getContext(), name, email);
                rView.setAdapter(adapter);

            }
        });
        return view;
    }
}
