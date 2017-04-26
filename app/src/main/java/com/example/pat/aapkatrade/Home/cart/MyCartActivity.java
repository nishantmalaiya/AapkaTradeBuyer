package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.user_dashboard.address.viewpager.CartCheckoutActivity;


public class MyCartActivity extends AppCompatActivity {
    private Context context;
    private ImageView locationImageView;
    private TextView checkDeliveryButton;
    RelativeLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        context = MyCartActivity.this;

        initView();

    }

    private void initView() {


        locationImageView = (ImageView) findViewById(R.id.locationImageView);
        AndroidUtils.setImageColor(locationImageView, context, R.color.green);

        checkDeliveryButton = (TextView) findViewById(R.id.checkDeliveryButton);

        buttonContainer = (RelativeLayout) findViewById(R.id.buttonContainer);
        AndroidUtils.setBackgroundSolid(buttonContainer, context, R.color.orange, 25, GradientDrawable.RECTANGLE);

    }


}
