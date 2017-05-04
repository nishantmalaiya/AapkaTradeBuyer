package com.example.pat.aapkatrade.shopdetail.shop_all_product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.cart.CartHolder;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.shopdetail.productdetail.ProductDetailActivity;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PPC16 on 4/21/2017.
 */

public class ShopAllProductAdapter extends RecyclerView.Adapter<ShopAllProductHolder> {

    final LayoutInflater inflater;
    List<ShopAllProductData> itemList;
    Context context;
    ShopAllProductHolder viewHolder,homeHolder;
    TextView textViewQuantity;
    LinearLayout linearLayoutQuantity;
    DroppyMenuPopup droppyMenu;


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
    public void onBindViewHolder(final ShopAllProductHolder holder, final int position)
    {

        homeHolder = holder;

        holder.tvProductName.setText(itemList.get(position).productName);
        holder.tvProductPrice.setText(itemList.get(position).productPrice);

        Picasso.with(context).load(itemList.get(position).productImage)
                .placeholder(R.drawable.default_noimage)
                .error(R.drawable.default_noimage)
                .into(holder.productImage);


        linearLayoutQuantity=homeHolder.dropdown_ll;

        textViewQuantity=homeHolder.tv_qty;

       // textViewQuantity.setText(itemList.get(position).quantity.toString());

        //linearLayoutQuantity.setOnClickListener(this);

        final DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(context, linearLayoutQuantity);
        droppyBuilder.addMenuItem(new DroppyMenuItem("1"))
                .addMenuItem(new DroppyMenuItem("2"))
                .addMenuItem(new DroppyMenuItem("3"))
                .addMenuItem(new DroppyMenuItem("4"))
                .addMenuItem(new DroppyMenuItem("5"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("More"));



        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                switch (id) {
                    case 0:
                       /* itemList.get(position).setQuantity("1");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());*/
                        notifyDataSetChanged();
                        break;
                    case 1:
                       /* itemList.get(position).setQuantity("2");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());*/
                        notifyDataSetChanged();
                        break;
                    case 2:
                       /* itemList.get(position).setQuantity("3");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());*/
                        notifyDataSetChanged();

                        break;
                    case 3:
                        /*itemList.get(position).setQuantity("4");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());*/
                        notifyDataSetChanged();
                        break;
                    case 4:
                       /* itemList.get(position).setQuantity("5");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());*/
                        notifyDataSetChanged();
                        break;
                    case 5:
                       // showPopup("Quantity",position);
                        break;

                }
            }
        });

        droppyMenu = droppyBuilder.build();


        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*holder.addToCartButton.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.addToCartButton.setBackground(ContextCompat.getDrawable(context, R.drawable.add_to_cart_selected));
                */

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