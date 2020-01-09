package com.example.securityapplication2.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.securityapplication2.ViewModels.Activity.LoginViewModel;
import com.example.securityapplication2.ViewModels.Activity.MainViewModel;
import com.example.securityapplication2.ViewModels.Activity.RegisterViewModel;
import com.example.securityapplication2.ViewModels.Fragment.AllGuardsViewModel;
import com.example.securityapplication2.ViewModels.Fragment.AllLocationsViewModel;
import com.example.securityapplication2.ViewModels.Fragment.EditProfileViewModel;
import com.example.securityapplication2.ViewModels.Fragment.LogsViewModel;
import com.example.securityapplication2.ViewModels.Fragment.ProfileViewModel;
import com.example.securityapplication2.ViewModels.Fragment.ProtectedLocationsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class Firebase {

    private FirebaseAuth fAuth;

    private FirebaseFirestore db;
    private DocumentReference docRef, docRef2;
    private CollectionReference colRef, colRef2;

    private String[] data;

    public Firebase() {
        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return fAuth.getCurrentUser();
    }

    public void loginAction(String email, String password, final LoginViewModel lvm) {
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addLogData();
                    lvm.notifySuccess("You have logged in!");
                } else {
                    lvm.notifyFail("Error: " + task.getException());
                }
            }
        });
    }

    public void registerAction(final String email, String password, final RegisterViewModel rvm) {
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    rvm.notifySuccess("User has been created!");
                    addUserData(email);
                    addLogData();
                } else {
                    rvm.notifyFail("Error: " + task.getException());
                }
            }
        });
    }

    public void changeNameAction(final String name, final EditProfileViewModel epvm) {
        addUserData2(name);
        epvm.notifyMove();
    }

    public void logoutAction(MainViewModel mvm) {
        FirebaseAuth.getInstance().signOut();
        mvm.logoutFeedback();
    }

    private void setCollectionReference(String collectionName) {
        colRef = db.collection(collectionName);
    }

    private void setCollectionReference2(String collectionName) {
        colRef2 = db.collection(collectionName);
    }

    private void setDocumentReference(String collectionName) {
        docRef = db.collection(collectionName).document();
    }

    private void setDocumentReference2(String collectionName) {
        docRef2 = db.collection(collectionName).document();
    }

    private void setDocumentsReference(String collectionName, String documentUID) {
        docRef = db.collection(collectionName).document(documentUID);
    }

    private void addLogData() {
        setCollectionReference("logs");

        Map<String, Object> log = new HashMap<>();
        log.put("logDate", FieldValue.serverTimestamp());
        log.put("userUID", Objects.requireNonNull(fAuth.getCurrentUser()).getUid());

        docRef.set(log);
    }

    public void addUserData(String email) {
        setDocumentsReference("users", getCurrentUser().getUid());

        Map<String, Object> user = new HashMap<>();


        user.put("email", email);
        user.put("name", "");
        user.put("uid", getCurrentUser().getUid());

        docRef.set(user);
    }

    public void addUserData2(String name) {
        setDocumentsReference("users", getCurrentUser().getUid());

        Map<String, Object> user = new HashMap<>();

        user.put("email", getCurrentUser().getEmail());
        user.put("name", name);
        user.put("uid", getCurrentUser().getUid());

        docRef.set(user);
    }

    public void addLocationData(String name, String address) {
        setDocumentReference("Locations");

        Map<String, Object> location = new HashMap<>();

        location.put("Name", name);
        location.put("Adress", address);

        docRef.set(location);
    }

    public void getUserData(String uid, final ProfileViewModel pvm) {
        setDocumentsReference("users", uid);

        data = new String[2];
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                data[0] = Objects.requireNonNull(ds).getString("name");
                data[1] = ds.getString("email");
                pvm.returnUserData(data);
            }
        });
    }


    public void getAndListenGuards(String collectionName, final String[] fields,
                                   final AllGuardsViewModel agvm) {
        setCollectionReference(collectionName);
        final ArrayList<String[]>[] documentData = new ArrayList[]{new ArrayList<>()};

        /*colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    documentData[0].add(new String[fields.length]);

                    String[] temp = new String[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        temp[i] = ds.getString(fields[i]);
                    }

                    documentData[0].set(documentData[0].size() - 1, temp);
                }
            }
        });*/

        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    agvm.setMessage("Error: " + e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    documentData[0] = new ArrayList<>();
                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                        documentData[0].add(new String[fields.length]);

                        String[] temp = new String[fields.length];
                        for (int i = 0; i < fields.length; i++) {
                            temp[i] = ds.getString(fields[i]);
                        }

                        documentData[0].set(documentData[0].size() - 1, temp);
                    }
                    agvm.returnData(documentData[0]);
                }
            }
        });

        //agvm.returnData(documentData[0]);
    }

    public void getAndListenLocations(String collectionName, final String[] fields,
                                      final AllLocationsViewModel alvm) {
        setCollectionReference(collectionName);
        final ArrayList<String[]>[] documentData = new ArrayList[]{new ArrayList<>()};

        /*colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    documentData[0].add(new String[fields.length]);

                    String[] temp = new String[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        temp[i] = ds.getString(fields[i]);
                    }

                    documentData[0].set(documentData[0].size() - 1, temp);
                }
            }
        });*/

        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    alvm.setMessage("Error: " + e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    documentData[0] = new ArrayList<>();
                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                        documentData[0].add(new String[fields.length]);

                        String[] temp = new String[fields.length];
                        for (int i = 0; i < fields.length; i++) {
                            temp[i] = ds.getString(fields[i]);
                        }

                        documentData[0].set(documentData[0].size() - 1, temp);
                    }
                    alvm.returnData(documentData[0]);
                }
            }
        });

        //alvm.returnData(documentData[0]);
    }

    public void getAndListenLogs(final String[] collectionName, final String[] fields,
                                 final int[] fieldSize, final LogsViewModel lvm) {
        setCollectionReference(collectionName[0]);
        final ArrayList<String[]>[] documentData = new ArrayList[]{new ArrayList<>()};

        /*colRef.orderBy("logDate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    documentData[0].add(new String[fields.length]);

                    String[] temp = new String[fields.length];
                    for (int i = 0; i < fieldSize[0]; i++) {
                        temp[i] = ds.getString(fields[i]);
                    }

                    documentData[0].set(documentData[0].size() - 1, temp);
                }

                setCollectionReference2(collectionName[1]);
                colRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot qs = task.getResult();
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            for (int i = 0; i < documentData.length; i++) {
                                if (documentData[0].get(i)[1].equals(ds.getString(fields[3]))) {
                                    documentData[0].get(i)[2] = ds.getString(fields[2]);
                                    documentData[0].get(i)[3] = ds.getString(fields[3]);

                                }
                            }
                        }

                    }
                });
            }
        });*/

        colRef.orderBy("logDate").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    lvm.setMessage("Error: " + e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    documentData[0] = new ArrayList<>();
                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                        documentData[0].add(new String[fields.length]);

                        String[] temp = new String[fields.length];
                        temp[0] = Objects.requireNonNull(ds.getDate(fields[0])).toString();
                        temp[1] = ds.getString(fields[1]);

                        documentData[0].set(documentData[0].size() - 1, temp);
                    }

                    setCollectionReference(collectionName[1]);
                    colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot qs = task.getResult();
                            for (DocumentSnapshot ds : Objects.requireNonNull(qs).getDocuments()) {
                                for (int i = 0; i < documentData[0].size(); i++) {
                                    if (documentData[0].get(i)[1].equals(ds.getString(fields[3]))) {
                                        documentData[0].get(i)[2] = ds.getString(fields[2]);
                                        documentData[0].get(i)[3] = ds.getString(fields[3]);

                                    }
                                }
                            }
                            lvm.returnData(documentData[0]);
                        }
                    });
                }
            }
        });

        //lvm.returnData(documentData[0]);
    }

    public void getAndListenProtectedLoc(final String[] collectionName, final String[] fields,
                                         final int[] fieldSize, final ProtectedLocationsViewModel plvm) {
        setCollectionReference(collectionName[0]);
        final ArrayList<String[]>[] documentData = new ArrayList[]{new ArrayList<>()};

        /*colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot qs = task.getResult();
                for (DocumentSnapshot ds : qs.getDocuments()) {
                    documentData[0].add(new String[fields.length]);

                    String[] temp = new String[fields.length];
                    for (int i = 0; i < fieldSize[0]; i++) {
                        temp[i] = ds.getString(fields[i]);
                    }
                    documentData[0].set(documentData[0].size() - 1, temp);
                }

                setCollectionReference(collectionName[1]);
                colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot qs = task.getResult();
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            for (int i = 0; i < documentData.length; i++) {
                                if (documentData[0].get(i)[2].equals(ds.getString(fields[4]))) {
                                    documentData[0].get(i)[3] = ds.getString(fields[3]);
                                    documentData[0].get(i)[4] = ds.getString(fields[4]);

                                }
                            }
                        }
                    }
                });

                setCollectionReference2(collectionName[2]);
                colRef2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot qs = task.getResult();
                        for (DocumentSnapshot ds : qs.getDocuments()) {
                            for (int i = 0; i < documentData.length; i++) {
                                if (documentData[0].get(i)[0].equals(ds.getId())) {
                                    documentData[0].get(i)[5] = ds.getString(fields[5]);
                                    documentData[0].get(i)[6] = ds.getString(fields[6]);
                                }
                            }
                        }
                    }
                });
            }
        });*/

        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    plvm.setMessage("Error: " + e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    documentData[0] = new ArrayList<>();
                    for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                        documentData[0].add(new String[fields.length]);

                        String[] temp = new String[fields.length];

                        temp[0] = ds.getString(fields[0]);
                        temp[1] = ds.getString(fields[1]);
                        temp[2] = ds.getString(fields[2]);

                        documentData[0].set(documentData[0].size() - 1, temp);
                    }

                    setCollectionReference(collectionName[1]);
                    colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot qs = task.getResult();
                            for (DocumentSnapshot ds : Objects.requireNonNull(qs).getDocuments()) {
                                for (int i = 0; i < documentData[0].size(); i++) {
                                    if (documentData[0].get(i)[2].equals(ds.getString(fields[4]))) {
                                        documentData[0].get(i)[3] = ds.getString(fields[3]);
                                        documentData[0].get(i)[4] = ds.getString(fields[4]);

                                    }
                                }
                            }
                            setCollectionReference(collectionName[2]);
                            colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    QuerySnapshot qs = task.getResult();
                                    for (DocumentSnapshot ds : Objects.requireNonNull(qs).getDocuments()) {
                                        for (int i = 0; i < documentData[0].size(); i++) {
                                            if (documentData[0].get(i)[0].equals(ds.getId())) {
                                                documentData[0].get(i)[5] = ds.getString(fields[5]);
                                                documentData[0].get(i)[6] = ds.getString(fields[6]);
                                            }
                                        }
                                    }
                                    plvm.returnData(documentData[0]);
                                    Log.d("TAG1", documentData[0].get(0)[0] + "|" + documentData[0].get(0)[2] + "|" + documentData[0].get(0)[3] + "|" + documentData[0].get(0)[4] + "|" + documentData[0].get(0)[6]);
                                    Log.d("TAG2", documentData[0].get(1)[0] + "|" + documentData[0].get(1)[2] + "|" + documentData[0].get(1)[3] + "|" + documentData[0].get(1)[4] + "|" + documentData[0].get(1)[6]);
                                }
                            });
                        }
                    });
                }
            }
        });

        //plvm.returnData(documentData[0]);
    }
}