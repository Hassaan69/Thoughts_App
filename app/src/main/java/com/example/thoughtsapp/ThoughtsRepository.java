package com.example.thoughtsapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ThoughtsRepository {

    private static ThoughtsRepository instance;
    public MutableLiveData<List<Thoughts>> data = new MutableLiveData<>();
    private List<Thoughts> thoughtsList = new ArrayList<>();
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("thoughts");

    public static ThoughtsRepository getInstance() {
        if (instance == null) {
            instance = new ThoughtsRepository();
        }
        return instance;
    }

    //This will hear any updates from firestore
    public void ListenThoughts(String category , Activity activity) {
        Log.d("CATEGORY", category);
        if (category.equals("popular")) {
                     collectionReference
                    .orderBy("numLikes", Query.Direction.DESCENDING)
                    .addSnapshotListener(activity , new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            //if there is an exception  we want to skip
                            if (e != null) {
                                Log.d("Listener", "Failed", e);
                                return;
                            }
                            //if we are here we have data
                            if (queryDocumentSnapshots != null) {
                                parseData(queryDocumentSnapshots);
                            }
                        }
                    });


        } else {
                     collectionReference
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .whereEqualTo("category", category)
                    .addSnapshotListener(activity , new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            //if there is an exception  we want to skip
                            if (e != null) {
                                Log.d("Listener", "Failed", e);
                                return;
                            }
                            //if we are here we have data
                            if (queryDocumentSnapshots != null) {
                                parseData(queryDocumentSnapshots);
                            }


                        }
                    });

        }
    }


    private void parseData(QuerySnapshot snapshots) {
        thoughtsList.clear();
        for (DocumentSnapshot document : snapshots.getDocuments()) {
            String Category = Objects.requireNonNull(document.get("category")).toString();
            Integer numComments = Integer.valueOf(Objects.requireNonNull(document.get("numComments")).toString());
            Integer numLikes = Integer.valueOf(Objects.requireNonNull(document.get("numLikes")).toString());
            String thoughtTxt = Objects.requireNonNull(document.get("thoughtText")).toString();
            Date Timestamp = document.getDate("timestamp");
            String Username = Objects.requireNonNull(document.get("username")).toString();
            String documentID = document.getId();
            Thoughts thought = new Thoughts(Username, Timestamp, thoughtTxt, numLikes, numComments, Category, documentID);
            thoughtsList.add(thought);
        }
        data.postValue(thoughtsList);
    }

}
