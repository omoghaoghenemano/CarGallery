package com.example.cargallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
private Button signup;
private EditText email, password;
private TextView register;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = findViewById(R.id.btnLogin);
        email = findViewById(R.id.emails);
        password = findViewById(R.id.passwords);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        progressBar = findViewById(R.id.progressbar);
        register = findViewById(R.id.signups);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email, Password;
                Email = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                if(Email.isEmpty()){
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                   email.setError("Email is invalid");
                    email.requestFocus();
                    return;
                }
                if(Password.isEmpty()){
                   password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if(Password.length()<6){
                    password.setError("Min password is 6 characters");
                    password.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();

                            startActivity(new Intent(Login.this,Mainpage.class));

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(view, "Incorrect password or username", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }



                });

            }
        });
    }
}