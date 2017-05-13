package com.example.pat.aapkatrade.user_dashboard.address.viewpager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.cart.CartData;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppConfig;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class CartCheckoutActivity extends AppCompatActivity
{

    RelativeLayout relativePayment;
    String fname,lname,mobile,state_id,address;
    AppSharedPreference app_sharedpreference;
    ArrayList<CartData>  cartDataArrayList = new ArrayList<>();
    private Context context;
    private ImageView locationImageView;
    public static TextView tvContinue,tvPriceItemsHeading,tvPriceItems,tvLastPayableAmount,tvAmountPayable;
    RelativeLayout buttonContainer;
    RecyclerView mycartRecyclerView;
    CartCheckOutAdapter cartAdapter;
    private ProgressBarHandler progressBarHandler;
    public static CardView cardviewProductDeatails;
    public static LinearLayout linearAddressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart_checkout);

        context = CartCheckoutActivity.this;

        setuptoolbar();

        progressBarHandler = new ProgressBarHandler(context);

        app_sharedpreference = new AppSharedPreference(getApplicationContext());
        Bundle bundle = getIntent().getExtras();

        fname = bundle.getString("fname");

        lname = bundle.getString("lname");

        mobile= bundle.getString("mobile");

        state_id = bundle.getString("state_id");

        address = bundle.getString("address");

        initView();

        setup_layout();


    }


    private void initView()
    {

        linearAddressLayout = (LinearLayout) findViewById(R.id.linearAddressLayout);

        cardviewProductDeatails = (CardView) findViewById(R.id.cardviewProductDeatails);

        locationImageView = (ImageView) findViewById(R.id.locationImageView);

        tvLastPayableAmount = (TextView) findViewById(R.id.tvLastPayableAmount);

        tvPriceItemsHeading = (TextView) findViewById(R.id.tvPriceItemsHeading);

        tvAmountPayable = (TextView) findViewById(R.id.tvAmountPayable);

        tvPriceItems = (TextView) findViewById(R.id.tvPriceItems);

        tvPriceItems.setText(getApplicationContext().getResources().getText(R.string.Rs)+"4251");

        tvAmountPayable.setText(getApplicationContext().getResources().getText(R.string.Rs)+"4251");


        AndroidUtils.setImageColor(locationImageView, context, R.color.green);



    }



    private void setup_layout()
    {

        relativePayment = (RelativeLayout) findViewById(R.id.relativePayment);

        mycartRecyclerView = (RecyclerView) findViewById(R.id.order_list);

        getAllShopProducts("");


        relativePayment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               String userid = app_sharedpreference.getSharedPref("userid", "");
                callwebservice__save_order(userid);

            }
        });



    }


    private void setuptoolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
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
        switch (item.getItemId())
        {
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

        Ion.with(CartCheckoutActivity.this)
                .load(getResources().getString(R.string.webservice_base_url) + "/list_cart")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("device_id", AppConfig.getCurrentDeviceId(context))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result != null)
                        {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------" + result.toString());

                            JsonObject jsonObject = result.getAsJsonObject("result");

                            String cart_count = jsonObject.get("total_qty").getAsString();
                            String total_amount =  jsonObject.get("total_amount").getAsString();

                            tvPriceItemsHeading.setText("Price("+cart_count+"items)");
                            tvPriceItems.setText(getApplicationContext().getResources().getText(R.string.Rs)+total_amount);
                            tvAmountPayable.setText(getApplicationContext().getResources().getText(R.string.Rs)+total_amount);
                           // tvLastPayableAmount.setText(getApplicationContext().getResources().getText(R.string.Rs)+total_amount);


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
                                    String subtotal_price = jsonproduct.get("sub_total").getAsString();
                                    System.out.println("price--------------------"+price);

                                    String productImage = jsonproduct.get("image_url").getAsString();
                                    String product_id = jsonproduct.get("product_id").getAsString();
                                    cartDataArrayList.add(new CartData(Id, productName, productqty, price, productImage,product_id,subtotal_price));
                                }
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mycartRecyclerView.setLayoutManager(mLayoutManager);
                                cartAdapter = new CartCheckOutAdapter(context, cartDataArrayList);
                                mycartRecyclerView.setAdapter(cartAdapter);

                                linearAddressLayout.setVisibility(View.VISIBLE);
                                cardviewProductDeatails.setVisibility(View.VISIBLE);




                            }
                        }
                        else
                        {
                            AndroidUtils.showErrorLog(context, "-jsonObject------------NULL RESULT FOUND");
                        }

                    }
                });
    }


    private void callwebservice__save_order(String buyer_id)
    {

        progressBarHandler.show();

        String login_url = context.getResources().getString(R.string.webservice_base_url) + "/save_order";
        Ion.with(context)
                .load(login_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("buyer_id", buyer_id)
                .setBodyParameter("device_id", AppConfig.getCurrentDeviceId(context))

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        AndroidUtils.showErrorLog(context,result,"dghdfghsaf dawbnedvhaewnbedvsab dsadduyf");
                        // System.out.println("result-----------"+result.toString());
                        progressBarHandler.hide();

                    }
                });
    }

}
