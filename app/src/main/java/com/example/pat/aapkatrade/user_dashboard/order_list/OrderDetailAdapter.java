package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.user_dashboard.order_list.order_details.OrderDetailsActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;

    private Context context;
    OrderDetailHolder viewHolder;
    AppSharedPreference appSharedPreference;
    String userId;
    private List<OrderDetailData> orderListDatas;

    public OrderDetailAdapter(Context context, List<OrderDetailData> itemList) {
        this.orderListDatas = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        appSharedPreference = new AppSharedPreference(context);

        userId = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString(), "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_order_detail, parent, false);
        viewHolder = new OrderDetailHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        OrderDetailHolder homeHolder = (OrderDetailHolder) holder;
        homeHolder.tvDateTime.setText("Time:"+orderListDatas.get(position).DateTime);
        homeHolder.tvPrize.setText(orderListDatas.get(position).Prize);

        homeHolder.tvDiscount.setText(orderListDatas.get(position).discount+"OFF");
        homeHolder.tvProductQty.setText("Qty:"+orderListDatas.get(position).ProductQty);
        homeHolder.tvProductName.setText("Name:"+orderListDatas.get(position).ProductName);

        Picasso.with(context).load(orderListDatas.get(position).ProductImage)

                .error(R.drawable.ic_profile_user)
                .into(((OrderDetailHolder) holder).imgProductImage);


    }


    private void showMessage(String s) {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return orderListDatas.size();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}

