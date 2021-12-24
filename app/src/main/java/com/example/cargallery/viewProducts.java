package com.example.cargallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class viewProducts extends AppCompatActivity {
    private ImageView carimages;
    private ImageView back;
    private Button addtocart;
    private TextView quantity;
    private TextView productnames, productmodels, productprice, increase, decrease;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;
 static Integer values = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        String productname = getIntent().getExtras().getString("productname");
        String productmodel = getIntent().getExtras().getString("productmodel");
        String productprices = getIntent().getExtras().getString("productprice");
        String carimage = getIntent().getExtras().getString("carimage");

        quantity = findViewById(R.id.quantitys);
        productnames = findViewById(R.id.productname);
        productmodels = findViewById(R.id.productmodels);
        productprice = findViewById(R.id.prices);
        carimages = findViewById(R.id.viewimage);
        addtocart = findViewById(R.id.btnAddtocart);
        increase = findViewById(R.id.increases);
        decrease = findViewById(R.id.decreases);
        back = findViewById(R.id.backarrow);
        productnames.setText(productname);
        productmodels.setText(productmodel);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Products");
        productprice.setText(productprices);
        Glide.with(getApplicationContext()).load(carimage).into(carimages);



        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            setIncrease();

            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            setDecrease();





            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mainpage.class);
                startActivity(intent);
            }
        });
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user == null) {
                    //
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);

                } else {
                    final HashMap<String, Object> carmap = new HashMap<>();
                    carmap.put("productname", productname);
                    carmap.put("productmodel", productmodel);
                    carmap.put("productprice", productprices);
                    carmap.put("carimage", carimage);
                    carmap.put("quantity",quantity.getText());


                    reference.child("shoppingcart").child(user.getUid()).child("Products").push().setValue(carmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "product added to cart", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(viewProducts.this);
                                        builder1.setMessage("Do you want to proceed to cart ");
                                        builder1.setCancelable(true);

                                        builder1.setPositiveButton(
                                                "Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(getApplicationContext(), Cartdata.class);
                                                        startActivity(intent);

                                                        dialog.cancel();
                                                    }
                                                });

                                        builder1.setNegativeButton(
                                                "No",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();


                                    } else {
                                        Toast.makeText(getApplicationContext(), "task is  unsuccesfful", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
            }
        });

    }
    public void setIncrease(){
        values++;

        quantity.setText(values.toString());
    }
    public void setDecrease(){
        values--;

        quantity.setText(values.toString());
    }
}