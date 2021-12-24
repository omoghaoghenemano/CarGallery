package com.example.cargallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class Mainpage extends AppCompatActivity {
    private ImageView carimage;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private ImageView bottonlogin, cart;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        carimage = findViewById(R.id.carimage);
        List<Integer> list = Arrays.asList(R.id.carimage);
        recyclerView = findViewById(R.id.recycleview);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser == null) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), Cartdata.class);
                    startActivity(intent);

                }

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference("Products");
        bottonlogin = findViewById(R.id.btnLogin);
        bottonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateuseronlinestatus();
            }
        });
    }

    public void validateuseronlinestatus() {
        if (firebaseUser == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            viewuserid h = new viewuserid();
            h.show(getSupportFragmentManager(), "loginuser");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Carname>().setQuery(reference, Carname.class).build();
        FirebaseRecyclerAdapter<Carname, CarHolder> adapter = new FirebaseRecyclerAdapter<Carname, CarHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CarHolder holder, int position, @NonNull Carname model) {
                String name = getRef(position).getKey();
                reference.child(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild("productname")) {

                            String Productname = snapshot.child("productname").getValue().toString();
                            String Productmodel = snapshot.child("productmodel").getValue().toString();
                            String Productprice = snapshot.child("productprice").getValue().toString();
                            String Carimages = snapshot.child("imagename").getValue().toString();

                            holder.productmodel.setText(Productmodel);
                            holder.Productnames.setText(Productname);
                            holder.price.setText(Productprice);

                            Glide.with(getApplicationContext()).load(Carimages).into(holder.carimages);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), viewProducts.class);
                                    intent.putExtra("productname", Productname);
                                    intent.putExtra("productmodel", Productmodel);
                                    intent.putExtra("productprice", Productprice);
                                    intent.putExtra("carimage", Carimages);
                                    startActivity(intent);

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @NonNull
            @Override
            public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false
                );
                CarHolder routeHolder = new CarHolder(views);
                return routeHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public static class CarHolder extends ViewHolder {
        TextView Productnames;
        TextView productmodel;
        ImageView carimages;
        TextView price;


        public CarHolder(@NonNull View itemView) {
            super(itemView);
            Productnames = itemView.findViewById(R.id.carproductname);
            productmodel = itemView.findViewById(R.id.carmodel);
            carimages = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.carprice);

        }

    }
}