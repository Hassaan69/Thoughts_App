package com.example.thoughtsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thoughtsapp.R;
import com.example.thoughtsapp.Model.Thoughts;
import com.example.thoughtsapp.Adapters.ThoughtsAdapter;
import com.example.thoughtsapp.ThoughtsSharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ThoughtsAdapter.OnItemClickListener {

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
    FloatingActionButton fab;
    private RecyclerView thoughtsRecyclerView;
    private ThoughtsAdapter thoughtsAdapter;
    private ThoughtsSharedViewModel thoughtsSharedViewModel;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing Toggle Buttons
        mainFunnyButton = findViewById(R.id.mainFunnyButton);
        mainCrazyButton = findViewById(R.id.mainCrazyButton);
        mainSeriousButton = findViewById(R.id.mainSeriousButton);
        mainPopularButton = findViewById(R.id.mainPopularButton);

        auth = FirebaseAuth.getInstance();

        thoughtsSharedViewModel = new ViewModelProvider(this).get(ThoughtsSharedViewModel.class);
        thoughtsRecyclerView = findViewById(R.id.thoughtsRecyclerView);
        thoughtsAdapter = new ThoughtsAdapter(MainActivity.this, this);


       fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddThoughtActivity.class));
            }
        });

        ObserveChanges(selectedCategory);

    }

    void ObserveChanges(String prefvalue) {

        thoughtsSharedViewModel.init(prefvalue, MainActivity.this);
        Log.d("PREF VALUE ", prefvalue);
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
        thoughtsSharedViewModel.RemoveListener();
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
        thoughtsSharedViewModel.RemoveListener();
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
        thoughtsSharedViewModel.RemoveListener();
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
        thoughtsSharedViewModel.RemoveListener();
        ObserveChanges(selectedCategory);
    }

    private void updateUI() {
        if (auth.getCurrentUser() == null) {
            mainPopularButton.setEnabled(false);
            mainCrazyButton.setEnabled(false);
            mainFunnyButton.setEnabled(false);
            mainSeriousButton.setEnabled(false);
            fab.setEnabled(false);
            thoughtsRecyclerView.setVisibility(View.INVISIBLE);

        } else {
            mainPopularButton.setEnabled(true);
            mainCrazyButton.setEnabled(true);
            mainFunnyButton.setEnabled(true);
            mainSeriousButton.setEnabled(true);
            fab.setEnabled(true);
            thoughtsRecyclerView.setVisibility(View.VISIBLE);
            ObserveChanges(selectedCategory);

        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        updateUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuitem = menu.getItem(0);
        if (auth.getCurrentUser() == null) {
            //Logged Out
            menuitem.setTitle("Login");
        } else {
            menuitem.setTitle("LogOut");


        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionLogin_Login) {
            if (auth.getCurrentUser() == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            } else {
                auth.signOut();
                updateUI();

            }
        }
        return false;
    }

    @Override
    public void onItemClick(List<Thoughts> list, int position) {
        Thoughts thought = list.get(position);
        Intent intent = new Intent(MainActivity.this , CommentsActivity.class);
        intent.putExtra("docId" , thought.getDocumentId()) ;
        startActivity(intent);
    }
}