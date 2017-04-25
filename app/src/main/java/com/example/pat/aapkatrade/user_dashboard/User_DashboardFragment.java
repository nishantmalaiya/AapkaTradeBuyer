package com.example.pat.aapkatrade.user_dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;

import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.my_profile.MyProfileActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_DashboardFragment extends Fragment {


    RecyclerView dashboardlist;
    DashboardAdapter dashboardAdapter;
    ArrayList<DashboardData> dashboardDatas = new ArrayList<>();
    AppSharedPreference app_sharedpreference;
    TextView tvMobile, tvEmail, textViewName, tvUserType;
    ImageView btnEdit;
    ProgressBarHandler progressBarHandler;
    RecyclerView.LayoutManager layoutManager;
    CircleImageView imageviewpp;
    String user_image;
    public User_DashboardFragment() {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_dashboard, container, false);
        app_sharedpreference = new AppSharedPreference(getActivity());
        progressBarHandler = new ProgressBarHandler(getActivity());
        tvUserType = (TextView) v.findViewById(R.id.tvUserType);
        setup_layout(v);
        dashboardlist = (RecyclerView) v.findViewById(R.id.dashboardlist);
        layoutManager = new LinearLayoutManager(getActivity());
        setup_data();
        dashboardlist.setNestedScrollingEnabled(false);
        return v;
    }

    private void setup_layout(View v) {
        imageviewpp=(CircleImageView)v.findViewById(R.id.imageviewpp) ;

        user_image = app_sharedpreference.getsharedpref("profile_pic","demo");
        Log.e("user_image",user_image);
//        Picasso.with(getActivity()).load(user_image)
//                .error(R.drawable.ic_profile_user)
//                .into(imageviewpp);
        textViewName = (TextView) v.findViewById(R.id.textViewName);
        tvMobile = (TextView) v.findViewById(R.id.tvMobile);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        btnEdit = (ImageView) v.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(i);
            }
        });

        if (app_sharedpreference.getsharedpref("username", "notlogin") != null) {
            String Username = app_sharedpreference.getsharedpref("name", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");
            if (Username.equals("notlogin")) {
                textViewName.setText("");
                tvEmail.setText("");
            } else {
                textViewName.setText(Username);
                tvEmail.setText(Emailid);
            }
        }




    }

    private void setup_data() {
        dashboardDatas.clear();
        try {

            if (app_sharedpreference.shared_pref != null) {

                String userid = app_sharedpreference.getsharedpref("userid", "0");

                String user_detail_webserviceurl = (getResources().getString(R.string.webservice_base_url)) + "/userdata";

                userdata_webservice(user_detail_webserviceurl, "2", userid);
            } else {
                Log.e("null_sharedPreferences", "sharedPreferences");
            }

        } catch (Exception e) {
        }




    }

    public void userdata_webservice(String url, final String user_type, String user_id) {
        Log.e("url", url);
        Log.e("user_type", user_type);
        Log.e("user_id", user_id);
        progressBarHandler.show();
        Ion.with(getActivity())
                .load(url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type",user_type)
                .setBodyParameter("id", user_id)
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            Log.e("result_myProfile", "result_myProfile is null ");
                            progressBarHandler.hide();
                        } else {

                            progressBarHandler.hide();
                            Log.e("result_myProfile", "result_myProfile is not null ");
                            String order_quantity = result.get("order").getAsString();
                                    app_sharedpreference.setsharedpref("order_quantity", order_quantity);
                                    dashboardDatas.add(new DashboardData("", "My Profile", R.drawable.ic_myprofile, R.drawable.circle_teal, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Change Password", R.drawable.ic_chngpswd, R.drawable.circle_purple, false, ""));
                                    dashboardDatas.add(new DashboardData("", "Order", R.drawable.ic_lstprdct, R.drawable.circle_sienna, true, order_quantity));
                                    dashboardDatas.add(new DashboardData("", "Cancel Order", R.drawable.ic_lstprdct, R.drawable.circle_cherry_red, true, ""));

                                    tvUserType.setText("Welcome Buyer");
                                    dashboardlist.setLayoutManager(layoutManager);
                                    dashboardAdapter = new DashboardAdapter(getContext(), dashboardDatas);
                                    dashboardlist.setAdapter(dashboardAdapter);
                            }
                        }



                });
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if (app_sharedpreference.getsharedpref("username", "notlogin") != null)
        {
            String Username = app_sharedpreference.getsharedpref("name", "not");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "not");
            if (Username.equals("notlogin"))
            {
                textViewName.setText("");
                tvEmail.setText("");
            }
            else
            {
                textViewName.setText(Username);
                tvEmail.setText(Emailid);
            }
        }


    }
}