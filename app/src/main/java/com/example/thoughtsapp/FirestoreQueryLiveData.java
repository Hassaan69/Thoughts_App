package com.example.thoughtsapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreQueryLiveData extends LiveData<Task<DocumentSnapshot>> {
    private static final String Log_tag = "FirebaseQueryLiveData";
    private final CollectionReference collectionReference;
    private final OnCompleteListener listner = new MyValueEventListner();
    public FirestoreQueryLiveData(CollectionReference ref){
        this.collectionReference = ref;
    }
    @Override
    protected void onActive(){
        Log.d(Log_tag, "onActive");
        collectionReference.get().addOnCompleteListener(listner);
    }
    @Override
    protected void onInactive(){
        Log.d(Log_tag, "onInactive");
        collectionReference.get().addOnCompleteListener(listner);
    }
    private class MyValueEventListner implements OnCompleteListener<DocumentSnapshot> {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            setValue(task);
        }
    }
}