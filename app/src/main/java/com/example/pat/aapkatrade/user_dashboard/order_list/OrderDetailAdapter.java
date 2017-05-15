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
    private List<OrderListData> itemList;
    private Context context;
    OrderListHolder viewHolder;
    AppSharedPreference appSharedPreference;
    String userId;
    private List<OrderListData> orderListDatas;

    public OrderDetailAdapter(Context context, List<OrderListData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        appSharedPreference = new AppSharedPreference(context);

        userId = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString(), "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_order_detail, parent, false);
        viewHolder = new OrderListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        OrderListHolder homeHolder = (OrderListHolder) holder;


        homeHolder.productName.setText(itemList.get(position).productName);

        homeHolder.tvOrderDate.setText(itemList.get(position).order_date);
        homeHolder.tvOrderPrize.setText(itemList.get(position).product_price);
        Log.e("itemList_image",itemList.get(position).product_image);
        Picasso.with(context).load(itemList.get(position).product_image)

                .error(R.drawable.ic_profile_user)
                .into(((OrderListHolder) holder).productImage);


        homeHolder.img_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
call_order_detail_webservice(itemList.get(position).OrderId);
            }
        });




    }

    private void call_order_detail_webservice(String orderId) {

        Ion.with(context)
                .load(context.getResources().getString(R.string.webservice_base_url) + "/buyer_order_details")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", userId)
                .setBodyParameter("ORDER_ID", orderId)

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {


                        } else {


                            if (result.get("error").getAsString().contains("false")) {


                                AndroidUtils.showErrorLog(context,"orderDetailData",result.toString());


                                Intent i =new Intent(context, OrderDetailsActivity.class);


                                context.startActivity(i);


//                                JsonObject jsonObject = result.getAsJsonObject();
//
//
//                                JsonObject jsonObject1 = jsonObject.getAsJsonObject("result");
//
//                                JsonArray list = jsonObject1.getAsJsonArray("list");
//
//
//
//                                for (int i = 0; i < list.size(); i++) {
//                                    JsonObject jsonObject2 = (JsonObject) list.get(i);
//                                    String product_name = jsonObject2.get("product_name").getAsString();
//
//                                    String orderid = jsonObject2.get("ORDER_ID").getAsString();
//
//                                    String product_price = jsonObject2.get("product_price").getAsString();
//
//                                    String product_qty = jsonObject2.get("product_qty").getAsString();
//                                    String image_url = jsonObject2.get("image_url").getAsString();
//
////
//                                    Log.e("image_url_orderList", image_url);
//
//                                    String created_at = jsonObject2.get("created_at").getAsString();
//
//
//                                    //orderListDatas.add(new OrderListData(orderid,product_name, product_price, product_qty, created_at, image_url));
//
//
//                                }


//                                orderListAdapter = new OrderListAdapter(getActivity(), orderListDatas);
//                                order_list.setAdapter(orderListAdapter);
//                                orderListAdapter.notifyDataSetChanged();
//                                progress_handler.hide();
                            }
                        }
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

