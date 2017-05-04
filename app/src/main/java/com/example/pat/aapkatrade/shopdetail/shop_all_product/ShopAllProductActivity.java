package com.example.pat.aapkatrade.shopdetail.shop_all_product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class ShopAllProductActivity extends AppCompatActivity {

    private ArrayList<ShopAllProductData> shopAllProductDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ShopAllProductAdapter shopAllProductAdapter;
    private LinearLayout linearLayout;
    private Context context;
    private ProgressBarHandler progressBarHandler;
    private String shopId;
    private int page = 1;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_all_product);
        context = ShopAllProductActivity.this;
        if (getIntent() != null) {
            shopId = getIntent().getStringExtra("shopId");
        }
        setuptoolbar();
        initView();
        getAllShopProducts("0");
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemCount = linearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount > 0) {
                    if ((totalItemCount - 1) == lastVisibleItemCount) {
                        page = page + 1;
                        getAllShopProducts(String.valueOf(page));
                    }
                }
            }
        });
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        progressBarHandler = new ProgressBarHandler(context);
        linearLayout = (LinearLayout) findViewById(R.id.layout_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.order_list);
    }

    private void setuptoolbar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void getAllShopProducts(String pageNumber) {
        progressBarHandler.show();


        Ion.with(ShopAllProductActivity.this)
                .load(getResources().getString(R.string.webservice_base_url) + "/view_all_products/" + shopId)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("page", pageNumber)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result != null) {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------" + result.toString());
                            JsonArray jsonProductList = result.getAsJsonArray("all_products");
                            if (jsonProductList != null && jsonProductList.size() > 0) {
                                for (int i = 0; i < jsonProductList.size(); i++) {
                                    JsonObject jsonproduct = (JsonObject) jsonProductList.get(i);
                                    String productId = jsonproduct.get("id").getAsString();
                                    String productName = jsonproduct.get("name").getAsString();
                                    String productShortDescription = jsonproduct.get("short_des").getAsString();
                                    String price = jsonproduct.get("price").getAsString();
                                    String productImage = jsonproduct.get("image_url").getAsString();
                                    shopAllProductDatas.add(new ShopAllProductData(productId, productName, productShortDescription, price, productImage));
                                }
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                shopAllProductAdapter = new ShopAllProductAdapter(context, shopAllProductDatas);
                                mRecyclerView.setAdapter(shopAllProductAdapter);
                            }
                        } else {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------NULL RESULT FOUND");
                        }

                    }
                });

    }
}