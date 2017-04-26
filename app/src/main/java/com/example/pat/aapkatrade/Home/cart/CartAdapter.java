package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.productdetail.shop_all_product.ShopAllProductData;
import com.example.pat.aapkatrade.productdetail.shop_all_product.ShopAllProductHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PPC16 on 4/25/2017.
 */


public class CartAdapter extends RecyclerView.Adapter<ShopAllProductHolder> {
    private ArrayList<CartData> cartDataArrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;


    public CartAdapter(Context context, ArrayList<CartData> cartDataArrayList) {
        this.cartDataArrayList = cartDataArrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ShopAllProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ShopAllProductHolder holder, int position) {
//        View view = inflater.inflate(R.layout.row_shop_service_list, holder, false);
    }

//    @Override
    /*
    public ShopAllProductHolder onBindViewHolder(ShopAllProductHolder holder, int position) {
        View view = inflater.inflate(R.layout.row_shop_service_list, holder, false);
        return new ShopAllProductHolder(view);
    }
*/

    private void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return cartDataArrayList.size();
    }


}