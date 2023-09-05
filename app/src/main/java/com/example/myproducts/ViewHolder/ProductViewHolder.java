package com.example.myproducts.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myproducts.Interface.ItemClickListener;
import com.example.myproducts.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

public TextView tv_namefood;
public ImageView iv_food,iv_favorit;
private ItemClickListener itemClickListener;


public ProductViewHolder(View itemView){
        super(itemView);

        tv_namefood= (TextView) itemView.findViewById(R.id.tv_name_product);
        iv_food= (ImageView) itemView.findViewById(R.id.iv_product);

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
