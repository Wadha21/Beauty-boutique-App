package com.example.myproducts.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myproducts.Common.Common;
import com.example.myproducts.Interface.ItemClickListener;
import com.example.myproducts.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        , View.OnCreateContextMenuListener {

    TextView tv_cart_name, tv_cart_price, tv_cart_count;

    ItemClickListener itemClickListener;

    public void setTv_cart_name(TextView tv_cart_name) {
        this.tv_cart_name = tv_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);

        tv_cart_name = itemView.findViewById(R.id.cart_tv_item_name);
        tv_cart_price = itemView.findViewById(R.id.cart_tv_item_price);
        tv_cart_count = itemView.findViewById(R.id.cart_tv_item_count);

        itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select Action");
        contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
    }

}
