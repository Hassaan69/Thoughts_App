package com.example.thoughtsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtsapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateUserActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    TextView createEmailText;
    TextView createPasswordText;
    TextView createUsernameText;
    Button createButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user2);
        auth = FirebaseAuth.getInstance();
        createEmailText = findViewById(R.id.createEmailText);
        createPasswordText = findViewById(R.id.createPasswordText);
        createUsernameText = findViewById(R.id.createUsernameText);
        createButton = findViewById(R.id.createCreateButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCreateClicked(v);
            }
        });

    }

    public void createCreateClicked(View view) {
        String email = createEmailText.getText().toString();
        String password = createPasswordText.getText().toString();
        final String username = createUsernameText.getText().toString();
        if (password.length() < 6) {
            Toast.makeText(this, "Password Minimun 6 character", Toast.LENGTH_SHORT).show();
        }
        if (password.equals("")) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Wrong Email", Toast.LENGTH_SHORT).show();

        }
        if (email.equals("")) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

        }
        if (username.equals("")) {
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();

        }
        if (!email.equals("") && (password.length() >= 6)
                &&!username.equals("")
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //UserCreated
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            authResult.getUser().updateProfile(userProfileChangeRequest)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("Error", e.getMessage());
                                            Toast.makeText(CreateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            HashMap<String, Object> data = new HashMap<String, Object>();
                            data.put("userName", username);
                            data.put("dateCreated", FieldValue.serverTimestamp());
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(authResult.getUser().getUid())
                                    .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   startActivity(new Intent(CreateUserActivity.this,MainActivity.class));
                                   finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Error", e.getMessage());
                                    Toast.makeText(CreateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(CreateUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public void createCancelClick(View view) {
        finish();
    }
}