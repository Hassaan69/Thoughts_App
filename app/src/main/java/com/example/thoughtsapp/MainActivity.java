package com.example.thoughtsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ThoughtsAdapter.OnLikeImageClickListener {

    private String FUNNY = "funny";
    private String SERIOUS = "serious";
    private String CRAZY = "crazy";
    private String POPULAR = "popular";

    private String selectedCategory = FUNNY;
    private List<Thoughts> thoughtsList = new ArrayList<>();

    private ToggleButton mainFunnyButton;
    private ToggleButton mainSeriousButton;
    private ToggleButton mainCrazyButton;
    private ToggleButton mainPopularButton;
    private RecyclerView thoughtsRecyclerView;
    private ThoughtsAdapter thoughtsAdapter;
    private ThoughtsSharedViewModel thoughtsSharedViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing Toggle Buttons
        mainFunnyButton = findViewById(R.id.mainFunnyButton);
        mainCrazyButton = findViewById(R.id.mainCrazyButton);
        mainSeriousButton = findViewById(R.id.mainSeriousButton);
        mainPopularButton = findViewById(R.id.mainPopularButton);

        thoughtsSharedViewModel = new ViewModelProvider(this).get(ThoughtsSharedViewModel.class);
        thoughtsRecyclerView = findViewById(R.id.thoughtsRecyclerView);
        thoughtsAdapter = new ThoughtsAdapter(MainActivity.this ,this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddThoughtActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        ObserveChanges(selectedCategory);

    }

    void ObserveChanges(String prefvalue) {


        thoughtsSharedViewModel.init(prefvalue, MainActivity.this);
        Log.d("PREF VALUE " , prefvalue) ;
        thoughtsSharedViewModel.getThoughtsList().observe(this, new Observer<List<Thoughts>>() {
            @Override
            public void onChanged(List<Thoughts> list) {
                thoughtsAdapter.updateList(list);
                thoughtsRecyclerView.setAdapter(thoughtsAdapter);
            }
        });

    }

    public void mainFunnyClicked(View view) {
        if (selectedCategory.equals(FUNNY)) {
            mainFunnyButton.setChecked(true);
            return;
        }

        mainSeriousButton.setChecked(false);
        mainCrazyButton.setChecked(false);
        mainPopularButton.setChecked(false);
        selectedCategory = FUNNY;
        ObserveChanges(selectedCategory);
    }

    public void mainSeriousClicked(View view) {
        if (selectedCategory.equals(SERIOUS)) {
            mainSeriousButton.setChecked(true);
            return;
        }

        mainFunnyButton.setChecked(false);
        mainCrazyButton.setChecked(false);
        mainPopularButton.setChecked(false);
        selectedCategory = SERIOUS;
        ObserveChanges(selectedCategory);
    }

    public void mainCrazyClicked(View view) {
        if (selectedCategory.equals(CRAZY)) {
            mainCrazyButton.setChecked(true);
            return;
        }

        mainSeriousButton.setChecked(false);
        mainFunnyButton.setChecked(false);
        mainPopularButton.setChecked(false);
        selectedCategory = CRAZY;
        ObserveChanges(selectedCategory);
    }

    public void mainPopularClicked(View view) {
        if (selectedCategory.equals(POPULAR)) {
            mainPopularButton.setChecked(true);
            return;
        }

        mainSeriousButton.setChecked(false);
        mainFunnyButton.setChecked(false);
        mainCrazyButton.setChecked(false);
        selectedCategory = POPULAR;
        ObserveChanges(selectedCategory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ObserveChanges(selectedCategory);
    }


    @Override
    public void onImageClick(List<Thoughts> list , int position ) {
        Thoughts thought = list.get(position);
        FirebaseFirestore.getInstance().collection("thoughts")
                .document(thought.getDocumentId())
                .update("numLikes" , thought.getNumLikes()+1);
    }
}