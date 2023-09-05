package com.example.myproducts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Interface.ItemClickListener;
import com.example.myproducts.Model.Product;
import com.example.myproducts.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductsActivity extends AppCompatActivity {



    FirebaseDatabase db;
    DatabaseReference foodlist;
    RecyclerView rv_food;
    RecyclerView.LayoutManager layoutManager;
    String categid ="";
    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;
    ActionBar actionBar;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | actionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setTitle("Products");
        imageView=new ImageView(actionBar.getThemedContext());

        imageView.setImageResource(R.drawable.logo1);
        ActionBar.LayoutParams params =new ActionBar.LayoutParams(60,60
                , Gravity.RIGHT | Gravity.CENTER_VERTICAL
        );
        params.leftMargin= 20;
        imageView.setLayoutParams(params);
        actionBar.setCustomView(imageView);


        db = FirebaseDatabase.getInstance();
        foodlist = db.getReference("Products");


        rv_food = (RecyclerView) findViewById(R.id.rv_product);
        rv_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_food.setLayoutManager(layoutManager);


        if (getIntent() != null) {
            categid = getIntent().getStringExtra("categid");
        }
        if (categid != null) {
            if(Common.isConnectedToInterner(getBaseContext())){
                loadListProducts(categid);
            }
            else{
                Toast.makeText(ProductsActivity.this, "Check your connecing to internet !", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void loadListProducts(String categid) {

        adapter = new
                FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                        Product.class,
                        R.layout.item_product,
                        ProductViewHolder.class,
                        foodlist.orderByChild("menu_id").equalTo(categid)) {
                    @Override
                    protected void populateViewHolder(ProductViewHolder productViewHolder, Product product, int i) {

                        productViewHolder.tv_namefood.setText(product.getName());

                        Picasso.with(getBaseContext()).load(product.getImage())
                                .into(productViewHolder.iv_food);




                        productViewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int pos, boolean isLongClick) {
                                Intent intent=new Intent(ProductsActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("pro_id",adapter.getRef(pos).getKey());
                                startActivity(intent);
                            }
                        });
                    }
                };
        rv_food.setAdapter(adapter);

    }

}