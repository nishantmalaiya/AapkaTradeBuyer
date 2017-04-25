package com.example.pat.aapkatrade.productdetail.shop_all_product;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 4/21/2017.
 */

public class ShopAllProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_shop_service_list, parent, false);

        viewHolder = new ShopAllProductHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ShopAllProductHolder homeHolder = (ShopAllProductHolder) holder;

        Log.e("size----------", String.valueOf(itemList.size()));

        homeHolder.relativeViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        homeHolder.relativeViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        homeHolder.buttonAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeHolder.buttonAddtoCart.setTextColor(ContextCompat.getColor(context, R.color.white));
                homeHolder.buttonAddtoCart.setBackground(ContextCompat.getDrawable(context, R.drawable.add_to_cart_selected));
            }
        });

    }

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return 10;
        //return itemList.size();
    }


}