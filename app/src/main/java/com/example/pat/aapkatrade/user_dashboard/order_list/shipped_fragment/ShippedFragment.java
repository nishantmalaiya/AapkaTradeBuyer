package com.example.pat.aapkatrade.user_dashboard.order_list.shipped_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListAdapter;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;


public class ShippedFragment extends Fragment
{

    private ArrayList<OrderListData> orderListDatas = new ArrayList<>();
    private RecyclerView order_list;
    private OrderListAdapter orderListAdapter;
    private ProgressBarHandler progress_handler;
    private LinearLayout layout_container;
    private AppSharedPreference appSharedPreference;
    private String user_id;
    private String user_type;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipped, container, false);

        progress_handler = new ProgressBarHandler(getActivity());

        appSharedPreference = new AppSharedPreference(getActivity());

        user_id = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString(),"");
        user_type = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_TYPE.toString(), "1");

        setup_layout(view);

       // get_web_data();

        return view;

    }


    private void setup_layout(View view)
    {


        layout_container = (LinearLayout) view.findViewById(R.id.layout_container);

        order_list = (RecyclerView) view.findViewById(R.id.order_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        order_list.setLayoutManager(mLayoutManager);


    }


    private void get_web_data()
    {
        orderListDatas.clear();
        progress_handler.show();
        Log.e("hi1234", user_id+"##cancel##"+AndroidUtils.getUserType(user_type)+"@@@@"+user_type);

        Ion.with(getActivity())
                .load(getResources().getString(R.string.webservice_base_url)+"/seller_order_list")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("buyer_id",user_id)
                .setBodyParameter("type", "2")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {
                        if (result == null) {
                            progress_handler.hide();
                            layout_container.setVisibility(View.INVISIBLE);
                        } else {


                            if (result.get("error").getAsString().contains("false")) {


                                JsonObject jsonObject = result.getAsJsonObject();


                                JsonObject jsonObject1 = jsonObject.getAsJsonObject("result");

                                JsonArray list = jsonObject1.getAsJsonArray("list");


                                for (int i = 0; i < list.size(); i++) {
                                    JsonObject jsonObject2 = (JsonObject) list.get(i);
                                    String product_name = jsonObject2.get("product_name").getAsString();

                                    String product_price = jsonObject2.get("product_price").getAsString();

                                    String product_qty = jsonObject2.get("product_qty").getAsString();
                                    String image_url = jsonObject2.get("image_url").getAsString();

  String orderid = jsonObject2.get("ORDER_ID").getAsString();


                                    String created_at = jsonObject2.get("created_at").getAsString();


                                    orderListDatas.add(new OrderListData(orderid,product_name, product_price, product_qty, created_at, image_url));


                                }


                                orderListAdapter = new OrderListAdapter(getActivity(), orderListDatas);
                                order_list.setAdapter(orderListAdapter);
                                orderListAdapter.notifyDataSetChanged();
                                progress_handler.hide();
                            }
                        }
                    }



                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Ion.getDefault(getActivity()).cancelAll(getActivity());
        progress_handler.hide();
    }
}
