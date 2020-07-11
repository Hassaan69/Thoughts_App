package com.example.thoughtsapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ThoughtsSharedViewModel extends ViewModel {
    private MutableLiveData<List<Thoughts>> mThoughtsList;
    private ThoughtsRepository thoughtsRepository;


    public void init(String category , Activity activity) {
        Log.d("CATEGORY", category);
        thoughtsRepository = ThoughtsRepository.getInstance();
        thoughtsRepository.ListenThoughts(category , activity);
        mThoughtsList = thoughtsRepository.data;
    }

    public LiveData<List<Thoughts>> getThoughtsList() {
        return mThoughtsList;
    }

}
