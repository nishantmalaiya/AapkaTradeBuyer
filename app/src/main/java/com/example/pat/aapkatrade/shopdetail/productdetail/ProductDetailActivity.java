package com.example.pat.aapkatrade.shopdetail.productdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.shopdetail.ShopDetailActivity;
import com.example.pat.aapkatrade.shopdetail.ShopViewPagerAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ProductDetailActivity extends AppCompatActivity {
    private Context context;
    private ProgressBarHandler progressBarHandler;
    ViewPager viewPager;
    private String productId = "0", quantity = "1";
    private TextView tvProductName, tvProductPrice, tvDiscountValue, tvUnitValue, tvAmountPaidValue, tvDescriptionValue;
    private LinearLayout viewPagerIndicator;
    private int dotsCount;
    private CircleIndicator circleIndicator;
    private ImageView[] dots;
    private Timer banner_timer = new Timer();
    private int currentPage = 0;
    private ShopViewPagerAdapter viewPagerAdapter;
    private ArrayList<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        context = ProductDetailActivity.this;
        progressBarHandler = new ProgressBarHandler(context);
        if (getIntent() != null) {
            productId = getIntent().getStringExtra("productId");
        }
        setUpToolBar();
        initView();

//        getProductDetailData(productId);

    }

    private void initView() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        tvDiscountValue = (TextView) findViewById(R.id.tv_discount_value);
        tvUnitValue = (TextView) findViewById(R.id.tv_unit_value);
        tvAmountPaidValue = (TextView) findViewById(R.id.tv_amount_paid_value);
        tvDescriptionValue = (TextView) findViewById(R.id.tvDescriptionValue);
        viewPager = (ViewPager) findViewById(R.id.viewpager_custom);
        viewPagerIndicator = (LinearLayout) findViewById(R.id.viewpagerindicator);

    }

    private void getProductDetailData(String productId) {
        progressBarHandler.show();
        Ion.with(context)
                .load(getString(R.string.webservice_base_url) + "/product_detail/" + productId)
                .setHeader("Authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("Authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result == null) {
                            AndroidUtils.showErrorLog(context, " getProductDetailData webservice result is null.");
                        } else {
                            AndroidUtils.showErrorLog(context, " getProductDetailData webservice result is --> ", result.toString());
                            if (result.get("error").getAsString().equals("false")) {
                                AndroidUtils.showErrorLog(context, " getProductDetailData webservice error is false.");
                                JsonObject resultJsonObject = result.get("result").getAsJsonObject();
                                if (resultJsonObject != null) {
                                    loadProductDetailWithData(resultJsonObject);
                                }

                            } else {
                                AndroidUtils.showErrorLog(context, " getProductDetailData webservice error is true.");
                            }
                        }
                    }
                });
    }

    private void loadProductDetailWithData(JsonObject resultJsonObject) {
        tvProductName.setText(resultJsonObject.get("name").getAsString());
        tvProductPrice.setText(resultJsonObject.get("price").getAsString());
        tvDiscountValue.setText(resultJsonObject.get("discount").getAsString());
        tvUnitValue.setText(resultJsonObject.get("price").getAsString());
//        tvAmountPaidValue.setText();
        tvProductPrice.setText(resultJsonObject.get("price").getAsString());

        setUpViewPager();
    }


    private void setUiPageViewController() {
        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonselected_item));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            viewPagerIndicator.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selecteditem_dot));
    }


    private void setUpViewPager() {
        viewPagerAdapter = new ShopViewPagerAdapter(context, imageList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentPage);
        setUiPageViewController();

        banner_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler().post(new Runnable() {
                    public void run() {
                        if (currentPage == viewPagerAdapter.getCount() - 1) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 0, 3000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonselected_item));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selecteditem_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
        circleIndicator.setViewPager(viewPager);
    }


    private void setUpToolBar() {
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


}
