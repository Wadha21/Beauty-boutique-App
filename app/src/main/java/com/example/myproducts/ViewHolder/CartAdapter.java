package com.example.myproducts.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myproducts.Model.Order;
import com.example.myproducts.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    List<Order> orderList;
    Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder( CartViewHolder holder, int position) {
        String quantity=orderList.get(position).getQuantity();
        holder.tv_cart_count.setText(quantity);
        holder.tv_cart_name.setText(orderList.get(position).getProduct_name());
        int price = Integer.parseInt(orderList.get(position).getPrice())
                *Integer.parseInt(orderList.get(position).getQuantity());
        holder.tv_cart_price.setText(" $ "+price);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
