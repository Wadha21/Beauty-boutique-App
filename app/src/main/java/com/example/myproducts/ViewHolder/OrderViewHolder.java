package com.example.myproducts.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproducts.Interface.ItemClickListener;
import com.example.myproducts.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_order_id,tv_order_status,tv_order_username,tv_order_address;
    ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_order_id=itemView.findViewById(R.id.order_status_tv_order_id);
        tv_order_status=itemView.findViewById(R.id.order_status_tv_order_status);
        tv_order_username =itemView.findViewById(R.id.order_status_tv_username);
        tv_order_address=itemView.findViewById(R.id.order_status_tv_address);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View view) {

    }
}
