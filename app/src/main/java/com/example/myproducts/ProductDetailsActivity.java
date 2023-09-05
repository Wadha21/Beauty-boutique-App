package com.example.myproducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Database.Database;
import com.example.myproducts.Model.Order;
import com.example.myproducts.Model.Product;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageButton bn_add, bn_minus;
    TextView tv_name, tv_discr, tv_price;
    ImageView iv_product;
    CollapsingToolbarLayout toolbarLayout;
    FloatingActionButton bn_store;
    String pro_id = "";
    FirebaseDatabase db;
    DatabaseReference reference;
    TextView tv_count;
    int count = 1;
    Product current_food;

    Button bn_cart;

    ActionBar actionBar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

       actionBar= getSupportActionBar();

        actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("Product Details");
        imageView=new ImageView(actionBar.getThemedContext());

        imageView.setImageResource(R.drawable.logo1);
        ActionBar.LayoutParams params =new ActionBar.LayoutParams(60,60
                , Gravity.RIGHT | Gravity.CENTER_VERTICAL
        );
        params.leftMargin= 20;
        imageView.setLayoutParams(params);
        actionBar.setCustomView(imageView);

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Products");

        bn_add = findViewById(R.id.pro_details_bn_add);
        bn_minus = findViewById(R.id.pro_details_bn_minus);
        bn_store = findViewById(R.id.pro_details_bn_store);
        tv_count = findViewById(R.id.pro_details_tv_count);
        tv_name = findViewById(R.id.pro_details_tv_food_name);
        tv_price = findViewById(R.id.pro_details_tv_food_price);
        tv_discr = findViewById(R.id.pro_details_tv_food_discreption);
        tv_count = findViewById(R.id.pro_details_tv_count);
        iv_product = findViewById(R.id.pro_details_iv_food);
        toolbarLayout = findViewById(R.id.pro_details_collapsing);
        bn_cart = findViewById(R.id.pro_details_bn_cart);


        bn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductDetailsActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

            pro_id = getIntent().getStringExtra("pro_id");
            if (pro_id != null) {
                if (Common.isConnectedToInterner(getBaseContext())) {
                    getDetailsProducts(pro_id);
                    bn_cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new Database(getBaseContext()).addCart(new Order(pro_id,
                                    current_food.getName(),
                                    tv_count.getText().toString(),
                                    current_food.getPrice())
                            );
                            Toast.makeText(getApplicationContext(), "Added Cart", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Check your connecing to internet !", Toast.LENGTH_SHORT).show();
                }
            }




        bn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tv_count.setText(count + "");
            }
        });

        bn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                tv_count.setText(count + "");
            }
        });
    }

    private void getDetailsProducts(String pro_id) {


        reference.child(pro_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                current_food = snapshot.getValue(Product.class);

                Picasso.with(getBaseContext()).load(current_food.getImage())
                        .into(iv_product);

                toolbarLayout.setTitle(current_food.getName());
                tv_price.setText(current_food.getPrice());
                tv_discr.setText(current_food.getDiscr());
                tv_name.setText(current_food.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}