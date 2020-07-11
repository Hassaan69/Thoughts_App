package com.example.thoughtsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddThoughtActivity extends AppCompatActivity {
    private String FUNNY = "funny";
    private String SERIOUS = "serious";
    private String CRAZY = "crazy";
    private String selectedCategory = FUNNY;

    private ToggleButton addFunnyButton;
    private ToggleButton addSeriousButton;
    private ToggleButton addCrazyButton;

    private Button addPostButton;

    private TextView addUserNameText;
    private TextView addThoughtText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thought);

        addFunnyButton = findViewById(R.id.addFunnyButton);
        addSeriousButton = findViewById(R.id.addSeriousButton);
        addCrazyButton = findViewById(R.id.addCrazyButton);

        addPostButton = findViewById(R.id.addPostButton);

        addUserNameText = findViewById(R.id.addUserNameText);
        addThoughtText = findViewById(R.id.addThoughtText);


        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostClicked();
            }
        });

    }

    void addPostClicked() {

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("category", selectedCategory);
        data.put("numComments", 0);
        data.put("numLikes", 0);
        data.put("thoughtText", addThoughtText.getText().toString());
        data.put("timestamp", FieldValue.serverTimestamp());
        data.put("username", addUserNameText.getText().toString());

        FirebaseFirestore.getInstance().collection("thoughts").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddThoughtActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addFunnyClicked(View view) {

        if (selectedCategory.equals(FUNNY)) {
            addFunnyButton.setChecked(true);
            return;
        }

        addSeriousButton.setChecked(false);
        addCrazyButton.setChecked(false);
        selectedCategory = FUNNY;

    }

    public void addSeriousClicked(View view) {
        if (selectedCategory == SERIOUS) {
            addSeriousButton.setChecked(true);
            return;
        }

        addFunnyButton.setChecked(false);
        addCrazyButton.setChecked(false);
        selectedCategory = SERIOUS;

    }

    public void addCrazyClicked(View view) {
        if (selectedCategory == CRAZY) {
            addCrazyButton.setChecked(true);
            return;
        }

        addSeriousButton.setChecked(false);
        addFunnyButton.setChecked(false);
        selectedCategory = CRAZY;
    }
}