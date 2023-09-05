package com.example.myproducts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Model.Request;
import com.example.myproducts.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatuesActivity extends AppCompatActivity {


    FirebaseDatabase db;
    DatabaseReference reference;
    RecyclerView rv_orders;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;


    ActionBar actionBar;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_statues);


        actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("My Order");
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

        rv_orders = findViewById(R.id.rv_orders);

        rv_orders.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_orders.setLayoutManager(layoutManager);


        loadOrders(Common.currentUser.getUsername());

    }

    private void loadOrders(String username) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                reference.orderByChild("username").equalTo(username)) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, Request request, int i) {

                orderViewHolder.tv_order_id.setText(adapter.getRef(i).getKey() + "  # ");
                orderViewHolder.tv_order_status.setText(Common.convertCodeToStatus(request.getStatus()));
                orderViewHolder.tv_order_address.setText(request.getAddress());
                orderViewHolder.tv_order_username.setText(request.getUsername());

            }
        };
        adapter.notifyDataSetChanged();
        rv_orders.setAdapter(adapter);
    }

}