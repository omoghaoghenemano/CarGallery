package com.example.cargallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Register extends AppCompatActivity {
private EditText emails;
private Button Next;
private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emails = findViewById(R.id.email);
        Next = findViewById(R.id.btnnext);
        back = findViewById(R.id.backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             validateEmailandmovetonext();

            }
        });

    }
    private void validateEmailandmovetonext(){
        String email = emails.getText().toString();
        if(email.isEmpty()){
            emails.setError("Email is required");
            emails.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emails.setError("Email is invalid");
           emails.requestFocus();
            return;
        }
        else{
            Intent intent = new Intent(getApplicationContext(), Registerpassword.class);
            intent.putExtra("myemail", email );
            startActivity(intent);

        }
    }
}