package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
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
import com.example.pat.aapkatrade.user_dashboard.address.viewpager.CartCheckoutActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderActivity;
import com.example.pat.aapkatrade.user_dashboard.order_list.OrderListData;

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



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        context = MyCartActivity.this;

        setuptoolbar();

         initView();


        setup_layout();


    }

    private void setuptoolbar() {
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

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mycartRecyclerView.setLayoutManager(mLayoutManager);


        cartDataArrayList.add(new CartData("1","Nokia Mobile","1","500"));
        cartDataArrayList.add(new CartData("1","Nokia Mobile","1","600"));
        cartDataArrayList.add(new CartData("1","Nokia Mobile","1","700"));
        cartDataArrayList.add(new CartData("1","Nokia Mobile","1","800"));
        cartDataArrayList.add(new CartData("1","Nokia Mobile","1","900"));
        cartDataArrayList.add(new CartData("1","Nokia Mobile","1","1000"));



        cartAdapter = new CartAdapter(MyCartActivity.this, cartDataArrayList);

        mycartRecyclerView.setAdapter(cartAdapter);
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
