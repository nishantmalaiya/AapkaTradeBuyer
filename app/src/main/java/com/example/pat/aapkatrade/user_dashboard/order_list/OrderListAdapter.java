package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.google.gson.JsonArray;
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

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private List<OrderListData> itemList;
    private Context context;
    OrderListHolder viewHolder;
    AppSharedPreference appSharedPreference;
    String userId;
    private List<OrderListData> orderListDatas;

    public OrderListAdapter(Context context, List<OrderListData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        appSharedPreference = new AppSharedPreference(context);

        userId = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString(), "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_order_list, parent, false);
        viewHolder = new OrderListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        OrderListHolder homeHolder = (OrderListHolder) holder;


        homeHolder.productName.setText(itemList.get(position).product_name);

        homeHolder.tvOrderDate.setText(itemList.get(position).order_date);
        homeHolder.tvOrderPrize.setText(itemList.get(position).product_price);

        Picasso.with(context).load(itemList.get(position).image_url)

                .error(R.drawable.ic_profile_user)
                .into(((OrderListHolder) holder).productImage);


        homeHolder.img_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(context, OrderDetailsActivity.class);
i.putExtra("OrderId",itemList.get(position).order_id);
                i.putExtra("userId",userId);

                context.startActivity(i);



            }
        });




    }



    private void showMessage(String s) {

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
    }


}

