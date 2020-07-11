package com.example.thoughtsapp;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thoughtsapp.Model.Comments;
import com.example.thoughtsapp.Model.Thoughts;

import java.util.List;

public class ThoughtsSharedViewModel extends ViewModel {
    private MutableLiveData<List<Thoughts>> mThoughtsList;
    private ThoughtsRepository thoughtsRepository;
    private MutableLiveData<List<Comments>> mCommentList;


    public void init(String category, Activity activity) {
        Log.d("CATEGORY", category);
        thoughtsRepository = ThoughtsRepository.getInstance();
        thoughtsRepository.ListenThoughts(category, activity);
        mThoughtsList = thoughtsRepository.data;
    }

    public void RemoveListener() {
        thoughtsRepository.listenerRegistration.remove();

    }

    public void getComments(String docId) {
        thoughtsRepository = ThoughtsRepository.getInstance();
        thoughtsRepository.addComment(docId);
        mCommentList = thoughtsRepository.commentData;
    }

    public MutableLiveData<List<Comments>> getmCommentList() {
        return mCommentList;
    }

    public MutableLiveData<List<Thoughts>> getThoughtsList() {
        return mThoughtsList;
    }

}
