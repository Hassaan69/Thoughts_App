package com.example.thoughtsapp.Activities;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thoughtsapp.Adapters.CommentsAdapter;
import com.example.thoughtsapp.Model.Comments;
import com.example.thoughtsapp.R;
import com.example.thoughtsapp.ThoughtsSharedViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CommentsActivity extends AppCompatActivity {
    private static String doucmentId;
    private TextView addCommentText;
    private List<Comments> commentsList = new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private ThoughtsSharedViewModel thoughtsSharedViewModel;
    private RecyclerView commentsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        doucmentId = getIntent().getStringExtra("docId");
        addCommentText = findViewById(R.id.enterCommentText);
        commentsRV = findViewById(R.id.commentsRv);

        thoughtsSharedViewModel = new ViewModelProvider(this).get(ThoughtsSharedViewModel.class);
        commentsAdapter = new CommentsAdapter(CommentsActivity.this);
        commentsRV.setAdapter(commentsAdapter);
        thoughtsSharedViewModel.getComments(doucmentId);
        thoughtsSharedViewModel.getmCommentList().observe(this, new Observer<List<Comments>>() {
            @Override
            public void onChanged(List<Comments> comments) {
                commentsAdapter.updateList(comments);
            }
        });
    }

    public void addCommentClicked(View view) {
        final String commentTxt = addCommentText.getText().toString();
        final DocumentReference thoughtref = FirebaseFirestore.getInstance().collection("thoughts").document(doucmentId);
        FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot thought = transaction.get(thoughtref);
                Long numComments = thought.getLong("numComments") + 1;
                transaction.update(thoughtref, "numComments", numComments);
                DocumentReference newCommentRef = FirebaseFirestore.getInstance().collection("thoughts").document(doucmentId)
                        .collection("comments").document();

                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("commentTxt", commentTxt);
                data.put("timestamp", FieldValue.serverTimestamp());
                data.put("username", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName().toString());
                transaction.set(newCommentRef,data);


                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {

                addCommentText.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CommentsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}