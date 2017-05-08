package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
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

        setuptoolbar();

        progressBarHandler = new ProgressBarHandler(context);

        initView();

        setup_layout();


    }

    private void setuptoolbar()
    {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        AndroidUtils.setImageColor(homeIcon, context, R.color.white);

        homeIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }





    }


    private void initView()
    {
        locationImageView = (ImageView) findViewById(R.id.locationImageView);

        AndroidUtils.setImageColor(locationImageView, context, R.color.green);

        /* checkDeliveryButton = (TextView) findViewById(R.id.checkDeliveryButton);

        buttonContainer = (RelativeLayout) findViewById(R.id.buttonContainer);
       AndroidUtils.setBackgroundSolid(buttonContainer, context, R.color.orange, 25, GradientDrawable.RECTANGLE);
      */


    }

    private void setup_layout()
    {

        mycartRecyclerView = (RecyclerView) findViewById(R.id.order_list);

        getAllShopProducts("");


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

    private void getAllShopProducts(String pageNumber)
    {

        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        progressBarHandler.show();

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
                        if (result != null)
                        {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------" + result.toString());

                            JsonObject jsonObject = result.getAsJsonObject("result");

                            JsonArray jsonProductList = jsonObject.getAsJsonArray("items");
                            if (jsonProductList != null && jsonProductList.size() > 0)
                            {
                                for (int i = 0; i < jsonProductList.size(); i++)
                                {
                                    JsonObject jsonproduct = (JsonObject) jsonProductList.get(i);
                                    String Id = jsonproduct.get("id").getAsString();
                                    String productName = jsonproduct.get("name").getAsString();
                                    String productqty = jsonproduct.get("quantity").getAsString();
                                    String price = jsonproduct.get("price").getAsString();

                                    System.out.println("price--------------------"+price);

                                    String productImage = jsonproduct.get("image_url").getAsString();
                                    String product_id = jsonproduct.get("product_id").getAsString();
                                    cartDataArrayList.add(new CartData(Id, productName, productqty, price, productImage,product_id));
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
