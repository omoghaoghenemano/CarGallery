package com.example.cargallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Cartdata extends AppCompatActivity {
    private Button checkouts;
    private ImageView imageview;
    RecyclerView recyclerView;
    DatabaseReference cartreference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView productname, productmodel, productprice,gettotal;
    static Integer values = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartdata);
        checkouts = findViewById(R.id.chekout);
        imageview = findViewById(R.id.backarrow);
        productmodel = findViewById(R.id.nameofmodel);
        productname = findViewById(R.id.nameofproduct);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        recyclerView = findViewById(R.id.myproducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String id = user.getUid().toString();
        cartreference = FirebaseDatabase.getInstance().getReference("Products").child("shoppingcart").child(user.getUid()).child("Products");
        gettotal = findViewById(R.id.gettotal);
        countnumberitem();
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mainpage.class);
                startActivity(intent);
            }
        });
        checkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customtoast("Order succesful");

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Cartdetails>().setQuery(cartreference.orderByChild("productname"), Cartdetails.class).build();
        FirebaseRecyclerAdapter<Cartdetails, Cartdata.CarHolder> adapter = new FirebaseRecyclerAdapter<Cartdetails, Cartdata.CarHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull Cartdata.CarHolder holder, int position, @NonNull Cartdetails model) {
                String name = getRef(position).getKey();



                cartreference.child(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild("productname")) {

                            Cartdetails det = snapshot.getValue(Cartdetails.class);

                            String Productname = snapshot.child("productname").getValue().toString();
                            String Productmodel = snapshot.child("productmodel").getValue().toString();
                            String Productprice = snapshot.child("productprice").getValue().toString();
                            String Carimages = snapshot.child("carimage").getValue().toString();
                            Integer Quantity = Integer.parseInt(snapshot.child("quantity").getValue().toString());


                            holder.Productnames.setText(det.productname);
                            Integer value = Integer.parseInt(det.quantity.toString()) * Integer.parseInt(det.productprice);




                            holder.productmodela.setText(det.productmodel);
                            holder.quantity.setText(det.quantity);
                            holder.price.setText(det.productprice);
                            holder.increase.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    values++;
                                    holder.quantity.setText(values.toString());

                                }
                            });
                            holder.delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Cartdata.this);
                                    builder1.setMessage("Are you sure you want to delete ");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    cartreference.child(name).removeValue();
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
                                }
                            });
                            holder.decrease.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    values--;
                                    holder.quantity.setText(values.toString());
                                }
                            });
                            Glide.with(getApplicationContext()).load(det.carimage).into(holder.carimages);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


            @NonNull
            @Override
            public Cartdata.CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false
                );
                Cartdata.CarHolder routeHolder = new Cartdata.CarHolder(views);
                return routeHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }


    public static class CarHolder extends RecyclerView.ViewHolder {
        TextView Productnames;
        TextView productmodela;
        ImageView carimages;
        TextView price;
        TextView quantity, increase, decrease,delete;


        public CarHolder(@NonNull View itemView) {
            super(itemView);
            Productnames = itemView.findViewById(R.id.nameofproduct);
            productmodela = itemView.findViewById(R.id.nameofmodel);
            carimages = itemView.findViewById(R.id.imgs);
            price = itemView.findViewById(R.id.cartnameofprice);
            quantity = itemView.findViewById(R.id.cartquantitys);
            increase = itemView.findViewById(R.id.increased);
            decrease = itemView.findViewById(R.id.decreased);
            delete = itemView.findViewById(R.id.deleted);


        }


    }
    public  void customtoast(String s){

        LayoutInflater inflater = getLayoutInflater();
        View layouts = inflater.inflate(R.layout.customtoast, findViewById(R.id.layout), false);
        TextView textView = layouts.findViewById(R.id.custommes);
        textView.setText(s);
        Toast toast = new Toast(getApplicationContext());

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layouts);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);


        toast.show();

    }
    public void countnumberitem(){
        cartreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            String v = String.valueOf(snapshot.getChildrenCount());
             System.out.println(v);
             gettotal.setText(v);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}