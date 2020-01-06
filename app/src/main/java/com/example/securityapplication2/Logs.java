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
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Logs extends Fragment {
    FirebaseFirestore db;

    View view;
    RecyclerView rView;
    RecyclerView.Adapter adapter;
    List<String> name, uid;
    List<Date> logDate;

    public Logs() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_logs, container, false);

        db = FirebaseFirestore.getInstance();
        rView = view.findViewById(R.id.recView);

        rView.setHasFixedSize(true);

        name = new ArrayList<>();
        uid = new ArrayList<>();
        logDate = new ArrayList<>();

        final CollectionReference[] docRef = {db.collection("users")};

        docRef[0].get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    name.add(ds.getString("name"));
                    uid.add(ds.getString("uid"));
                }
                //Toast.makeText(view.getContext(), uid.toString(), Toast.LENGTH_LONG).show();

                final List<String> finalName = new ArrayList<>();
                final List<String> finalDate = new ArrayList<>();

                docRef[0] = db.collection("logs");

                docRef[0].orderBy("logDate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot qs = task.getResult();
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            for (int i = 0; i < name.size(); i++) {
                                if (uid.get(i).equals(ds.getString("userUID"))) {
                                    finalName.add(name.get(i));
                                    finalDate.add(ds.getDate("logDate").toString());
                                }
                            }
                        }
                        //Toast.makeText(view.getContext(), finalName.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(view.getContext(), finalDate.toString(), Toast.LENGTH_LONG).show();

                        rView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        adapter = new AdapterLogs(view.getContext(), finalName, finalDate);
                        rView.setAdapter(adapter);
                    }
                });
            }
        });


        return view;
    }
}
