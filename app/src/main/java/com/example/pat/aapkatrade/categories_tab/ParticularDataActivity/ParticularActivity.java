package com.example.pat.aapkatrade.categories_tab.ParticularDataActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoriesListAdapter;
import com.example.pat.aapkatrade.categories_tab.CategoriesListData;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.location.AddressEnum;
import com.example.pat.aapkatrade.location.GeoCoderAddress;
import com.example.pat.aapkatrade.location.Mylocation;
import com.example.pat.aapkatrade.search.Search;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class ParticularActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CategoriesListAdapter categoriesListAdapter;
    private ArrayList<CategoriesListData> shopListDatas = new ArrayList<>();
    private ProgressBarHandler progress_handler;
    private FrameLayout layout_container;
    private String url;
    private Mylocation mylocation;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = ParticularActivity.this;

        Intent intent = getIntent();

        url = intent.getStringExtra("url");

        System.out.println("url---------------" + url);
        setContentView(R.layout.activity_categories_list);

        setUpToolBar();

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);

        progress_handler = new ProgressBarHandler(this);

        layout_container = (FrameLayout) view.findViewById(R.id.layout_container);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        view.findViewById(R.id.home_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean permission_status = CheckPermission.checkPermissions(ParticularActivity.this);


                if (permission_status)

                {
                    mylocation = new Mylocation(ParticularActivity.this);
                    LocationManager_check locationManagerCheck = new LocationManager_check(
                            ParticularActivity.this);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable()) {


                        double latitude = mylocation.getLatitude();
                        double longitude = mylocation.getLongitude();
                        GeoCoderAddress geoCoderAddress_statename = new GeoCoderAddress(ParticularActivity.this, latitude, longitude);
                        String state_name = geoCoderAddress_statename.get_state_name().get(AddressEnum.STATE);
                        if (state_name != null) {
                            Intent goto_search = new Intent(ParticularActivity.this, Search.class);
                            goto_search.putExtra("latitude", latitude);
                            goto_search.putExtra("longitude", longitude);
                            goto_search.putExtra("state_name", state_name);
                            startActivity(goto_search);
                            finish();
                        } else {
                            Log.e("statenotfound", "" + "statenotfound");
                        }
                    } else {
                        locationManagerCheck.createLocationServiceError(ParticularActivity.this);
                    }

                }


            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setHasFixedSize(true);

        StikkyHeaderBuilder.stickTo(mRecyclerView)
                .setHeader(R.id.header_simple, view)
                .minHeightHeaderDim(R.dimen.min_header_height)
                .build();

        get_web_data();

    }

    private void get_web_data() {
        shopListDatas.clear();
        progress_handler.show();

        Ion.with(ParticularActivity.this)
                .load(url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result == null) {
                            progress_handler.hide();
                            layout_container.setVisibility(View.INVISIBLE);
                        } else {
                            JsonObject jsonObject = result.getAsJsonObject();

                            String message = jsonObject.get("message").toString().substring(0, jsonObject.get("message").toString().length());

                            String message_data = message.replace("\"", "");

                            System.out.println("message_data==================" + message_data);

                            if (message_data.equals("No record found")) {

                                progress_handler.hide();
                                layout_container.setVisibility(View.INVISIBLE);

                            } else {
                                JsonArray jsonArray = jsonObject.getAsJsonArray("result");

                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);

                                    String shopId = jsonObject2.get("id").getAsString();

                                    String shopName = jsonObject2.get("prodname").getAsString();

                                    //  String shopPrice = jsonObject2.get("price").getAsString();

                                    //String product_cross_price = jsonObject2.get("cross_price").getAsString();

                                    String shopLocation = jsonObject2.get("city_name").getAsString() + "," + jsonObject2.get("state_name").getAsString() + "," +
                                            jsonObject2.get("country_name").getAsString();
                                    String shopImage = jsonObject2.get("image_url").getAsString();

                                    shopListDatas.add(new CategoriesListData(shopId, shopName, shopImage, shopLocation));

                                }
                                categoriesListAdapter = new CategoriesListAdapter(ParticularActivity.this, shopListDatas);
                                mRecyclerView.setAdapter(categoriesListAdapter);

                            }
                            progress_handler.hide();
                        }

                    }


                });
    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}