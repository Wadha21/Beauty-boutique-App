package com.example.myproducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Database.Database;
import com.example.myproducts.Model.Order;
import com.example.myproducts.Model.Request;
import com.example.myproducts.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {


    RecyclerView rv_cart;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase db;
    DatabaseReference reference;

    TextView tv_total_price;
    Button bn_place;

    List<Order> cartList = new ArrayList<>();
    CartAdapter adapter;

    ActionBar actionBar;


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("My Cart");
        imageView=new ImageView(actionBar.getThemedContext());

        imageView.setImageResource(R.drawable.logo1);
        ActionBar.LayoutParams params =new ActionBar.LayoutParams(60,60
                , Gravity.RIGHT | Gravity.CENTER_VERTICAL
        );
        params.leftMargin= 20;
        imageView.setLayoutParams(params);
        actionBar.setCustomView(imageView);




        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Requests");

        bn_place = findViewById(R.id.cart_bn_place);
        tv_total_price = findViewById(R.id.cart_tv_total_price);
        rv_cart = findViewById(R.id.cart_rv_cart);

        rv_cart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_cart.setLayoutManager(layoutManager);


        bn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartList.size() > 0) {
                    showDialog();
                }
                else{
                    Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadFoodList();

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Enter Your Address");
        final EditText et_address = new EditText(CartActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        et_address.setLayoutParams(params);
        builder.setView(et_address);
        builder.setIcon(R.drawable.ic_cart);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Common.currentUser.getUsername(),
                        Common.currentUser.getName(),
                        et_address.getText().toString(),
                        tv_total_price.getText().toString()
                        , cartList
                );

                reference.child(String.valueOf(System.currentTimeMillis())).
                        setValue(request);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this, "Placed Order", Toast.LENGTH_LONG).show();
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();


    }

    private void loadFoodList() {

        cartList = new Database(this).getCarts();
        adapter = new CartAdapter(cartList, this);
        rv_cart.setAdapter(adapter);

        int total = 0;

        for (Order order : cartList) {
            total +=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        tv_total_price.setText(" $ "+total);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if(item.getTitle().equals(Common.DELETE)){
            deleteCart(item.getOrder());
        }
        return true;
    }

    private void deleteCart(int order) {
        cartList.remove(order);
        new Database(this).cleanCart();
        for(Order item: cartList){
            new Database(this).addCart(item);
        }
        loadFoodList();
    }


}