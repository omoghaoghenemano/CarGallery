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

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
private Button upload,insertimage;
private EditText productname, productprice, productmodel;
private ImageView productimage;
    private int PICK_IMAGE_REQUEST = 1;
DatabaseReference refuser;
    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upload = findViewById(R.id.insertimage);
        insertimage = findViewById(R.id.button);
        productname = findViewById(R.id.productname);
        productprice = findViewById(R.id.productprice);
        productmodel = findViewById(R.id.productmodel);
        productimage = findViewById(R.id.myimage);
        refuser = FirebaseDatabase.getInstance().getReference("Products");

        storageReference = FirebaseStorage.getInstance().getReference();
insertimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        chooseImage();

    }
});
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();


                String Productname = productname.getText().toString().trim();
                String Productprice = productprice.getText().toString().trim();
                String Productmodel = productmodel.getText().toString().trim();
                Method ms = new Method(Productname, Productmodel, Productprice);
                refuser.push().setValue(ms);


                Toast.makeText(getApplicationContext(),"data inserted", Toast.LENGTH_SHORT).show();


            }
        });

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK &&
                data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                productimage.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if(filePath != null){


            StorageReference ref= storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(MainActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                   // String imageUrl = taskSnapshot.getDownloadUrl().toString();
                 //   Picasso.with(getBaseContext()).load(imageUrl).into(productimage);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
}}
