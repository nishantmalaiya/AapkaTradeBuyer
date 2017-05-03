package com.example.pat.aapkatrade.add_to_cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;

import java.util.ArrayList;

/**
 * Created by PPC16 on 4/22/2017.
 */

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartHolder> {
    private Context context;
    private ArrayList<AddToCartData> addToCartDataArrayList = new ArrayList<>();
    private LayoutInflater inflater;

    public AddToCartAdapter(Context context, ArrayList<AddToCartData> addToCartDataArrayList){
        this.context = context;
        this.addToCartDataArrayList = addToCartDataArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public AddToCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddToCartHolder(inflater.inflate(R.layout.row_shop_service_list, parent, false));
    }

    @Override
    public void onBindViewHolder(AddToCartHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return addToCartDataArrayList.size();
    }
}
