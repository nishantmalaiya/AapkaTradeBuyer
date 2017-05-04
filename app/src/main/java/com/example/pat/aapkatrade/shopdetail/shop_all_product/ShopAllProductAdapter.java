package com.example.pat.aapkatrade.shopdetail.shop_all_product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.shopdetail.productdetail.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PPC16 on 4/21/2017.
 */

public class ShopAllProductAdapter extends RecyclerView.Adapter<ShopAllProductHolder> {

    final LayoutInflater inflater;
    List<ShopAllProductData> itemList;
    Context context;
    ShopAllProductHolder viewHolder;


    public ShopAllProductAdapter(Context context, List<ShopAllProductData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ShopAllProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopAllProductHolder(inflater.inflate(R.layout.row_shop_service_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ShopAllProductHolder holder, final int position) {
        holder.tvProductName.setText(itemList.get(position).productName);
        holder.tvProductPrice.setText(itemList.get(position).productPrice);
        Picasso.with(context).load(itemList.get(position).productImage)
                .placeholder(R.drawable.default_noimage)
                .error(R.drawable.default_noimage)
                .into(holder.productImage);

        holder.relativeViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        holder.relativeViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addToCartButton.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.addToCartButton.setBackground(ContextCompat.getDrawable(context, R.drawable.add_to_cart_selected));
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", itemList.get(position).productId);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


}