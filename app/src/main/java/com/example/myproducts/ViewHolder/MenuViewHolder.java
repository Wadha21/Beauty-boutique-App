package com.example.myproducts.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myproducts.Interface.ItemClickListener;
import com.example.myproducts.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_namemenu;
    public ImageView iv_menu;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView){
        super(itemView);

        tv_namemenu= (TextView) itemView.findViewById(R.id.tv_namemenu);
        iv_menu= (ImageView) itemView.findViewById(R.id.iv_menu);

        itemView.setOnClickListener(this);

    }



    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view , getAdapterPosition() , false);
    }
}
