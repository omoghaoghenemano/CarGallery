package com.example.cargallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registerpassword extends AppCompatActivity {
    private EditText userpass;
    private Button signingup;
    private FirebaseAuth mAuth;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpassword);
        userpass = findViewById(R.id.mypassword);
        signingup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        back = findViewById(R.id.backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
        signingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatepasswordandsignup();
            }
        });


    }

    public void validatepasswordandsignup() {
        String Email = getIntent().getExtras().getString("myemail");
        String Password = userpass.getText().toString();
        if (Password.isEmpty()) {
            userpass.setError("Password is required");
            userpass.requestFocus();


        }
        if (Password.length() < 6) {
            userpass.setError("Min password is 6 characters");
            userpass.requestFocus();

        }
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(Email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "User is registered", Toast.LENGTH_LONG).show();

                                        finish();
                                        startActivity(new Intent(Registerpassword.this, Mainpage.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Registeration Unsucessful", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Registeration Unsucessful", Toast.LENGTH_LONG).show();


                        }

                    }
                });


    }

}