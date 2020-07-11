package com.example.thoughtsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    private TextView loginEmailText;
    private TextView loginPasswordText;
    private FirebaseAuth auth;
    Button loginClicked;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailText = findViewById(R.id.loginEmailText);
        loginPasswordText = findViewById(R.id.loginPasswordText);
        loginClicked = findViewById(R.id.loginLoginButton);
        loginClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLoginClicked(v);
            }
        });

        auth = FirebaseAuth.getInstance();
    }

    public void loginLoginClicked(View view) {

        String email = loginEmailText.getText().toString();
        String password = loginPasswordText.getText().toString();
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
            Toast.makeText(this, "Please  Enter Email", Toast.LENGTH_SHORT).show();

        }
        if (!email.equals("") && (password.length() >= 6)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }


    }

    public void loginCreateClicked(View view) {
        startActivity(new Intent(LoginActivity.this, CreateUserActivity.class));
        finish();
    }
}