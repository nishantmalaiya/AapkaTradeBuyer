package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.shopdetail.shop_all_product.ShopAllProductActivity;
import com.example.pat.aapkatrade.shopdetail.shop_all_product.ShopAllProductAdapter;
import com.example.pat.aapkatrade.shopdetail.shop_all_product.ShopAllProductData;
import com.example.pat.aapkatrade.user_dashboard.address.viewpager.CartCheckoutActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class MyCartActivity extends AppCompatActivity
{

    ArrayList<CartData>  cartDataArrayList = new ArrayList<>();
    private Context context;
    private ImageView locationImageView;
    private TextView checkDeliveryButton;
    RelativeLayout buttonContainer;
    RecyclerView mycartRecyclerView;
    CartAdapter cartAdapter;
    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_cart);

        context = MyCartActivity.this;

        progressBarHandler = new ProgressBarHandler(context);

        setuptoolbar();

         initView();

        setup_layout();

    }

    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

    }

    private void initView()
    {
        locationImageView = (ImageView) findViewById(R.id.locationImageView);

        AndroidUtils.setImageColor(locationImageView, context, R.color.green);

        checkDeliveryButton = (TextView) findViewById(R.id.checkDeliveryButton);

        buttonContainer = (RelativeLayout) findViewById(R.id.buttonContainer);

        AndroidUtils.setBackgroundSolid(buttonContainer, context, R.color.orange, 25, GradientDrawable.RECTANGLE);

    }

    private void setup_layout()
    {

        mycartRecyclerView = (RecyclerView) findViewById(R.id.mycartRecyclerView);

        getAllShopProducts("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }



    private void getAllShopProducts(String pageNumber)
    {

        progressBarHandler.show();
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        Ion.with(MyCartActivity.this)
                .load(getResources().getString(R.string.webservice_base_url) + "/list_cart")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("device_id", android_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result != null) {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------" + result.toString());

                            JsonObject jsonObject = result.getAsJsonObject("result");

                            JsonArray jsonProductList = jsonObject.getAsJsonArray("items");
                            if (jsonProductList != null && jsonProductList.size() > 0)
                            {
                                for (int i = 0; i < jsonProductList.size(); i++)
                                {
                                    JsonObject jsonproduct = (JsonObject) jsonProductList.get(i);
                                    String productId = jsonproduct.get("id").getAsString();
                                    String productName = jsonproduct.get("name").getAsString();
                                    String productQty = jsonproduct.get("quantity").getAsString();
                                    String price = jsonproduct.get("price").getAsString();
                                    String productImage = jsonproduct.get("image_url").getAsString();
                                    String product_id = jsonproduct.get("product_id").getAsString();
                                    cartDataArrayList.add(new CartData(productId, productName, productQty, price, productImage,product_id));
                                }
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mycartRecyclerView.setLayoutManager(mLayoutManager);
                                cartAdapter = new CartAdapter(context, cartDataArrayList);
                                mycartRecyclerView.setAdapter(cartAdapter);
                            }
                        }
                        else
                        {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------NULL RESULT FOUND");
                        }

                    }
                });

    }



}
