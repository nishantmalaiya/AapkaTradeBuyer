package com.example.pat.aapkatrade.shopdetail.productdetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.dialogs.CustomQuantityDialog;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.interfaces.CommonInterface;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.LoginActivity;
import com.example.pat.aapkatrade.rateus.RateUsActivity;
import com.example.pat.aapkatrade.shopdetail.ShopViewPagerAdapter;
import com.example.pat.aapkatrade.shopdetail.reviewlist.ReviewListAdapter;
import com.example.pat.aapkatrade.shopdetail.reviewlist.ReviewListData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ProductDetailActivity extends AppCompatActivity
{
    private Context context;
    private ProgressBarHandler progressBarHandler;
    private ViewPager viewPager;
    private String productId = "0", quantity = "1";
    private EditText editTextPostalCode, etManualQuantity;
    private TextView tvProductName, tvProductPrice, tvDiscountValue, tvUnitValue, tvAmountPaidValue, tvDescriptionValue, tvPinCodeCheck, tvToatalRatingAndReview, tvRatingAverage, tvQuantity;
    private TextView okButton;
    private TextView cancelButton;
    private LinearLayout viewPagerIndicator;
    private int dotsCount;
    private CircleIndicator circleIndicator;
    private ImageView[] dots;
    private Timer banner_timer = new Timer();
    private int currentPage = 0;
    private ShopViewPagerAdapter viewPagerAdapter;
    private ArrayList<String> imageUrlArrayList = new ArrayList<>();
    private RecyclerView reviewRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ReviewListAdapter reviewListAdapter;
    private ArrayList<ReviewListData> reviewListDatas = new ArrayList<>();
    private RelativeLayout relativeRateReview;
    private AppSharedPreference appSharedPreference;
    private LinearLayout dropDownContainer;
    private DroppyMenuPopup droppyMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        context = ProductDetailActivity.this;
        appSharedPreference = new AppSharedPreference(context);
        progressBarHandler = new ProgressBarHandler(context);
        if (getIntent() != null) {
            productId = getIntent().getStringExtra("productId");
        }
        setUpToolBar();
        initView();

        getProductDetailData(productId);

        relativeRateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_NAME.toString(), "not").contains("not")) {
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    Intent rate_us = new Intent(context, RateUsActivity.class);
                    rate_us.putExtra("product_id", productId);
                    rate_us.putExtra("product_name", tvProductName.getText().toString());
                    rate_us.putExtra("product_price", tvProductPrice.getText().toString());
                    rate_us.putExtra("product_image", imageUrlArrayList.get(0));
                    startActivity(rate_us);
                }

            }
        });


        final DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(context, dropDownContainer);
        droppyBuilder
                .addMenuItem(new DroppyMenuItem("1"))
                .addMenuItem(new DroppyMenuItem("2"))
                .addMenuItem(new DroppyMenuItem("3"))
                .addMenuItem(new DroppyMenuItem("4"))
                .addMenuItem(new DroppyMenuItem("5"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("More"));


        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                switch (id) {
                    case 0:
                        tvQuantity.setText("1");
                        break;
                    case 1:
                        tvQuantity.setText("2");
                        break;
                    case 2:
                        tvQuantity.setText("3");
                        break;
                    case 3:
                        tvQuantity.setText("4");
                        break;
                    case 4:
                        tvQuantity.setText("5");
                        break;
                    case 5:
                        tvQuantity.setText("1");
                        showPopup();

                        break;

                }
            }
        });

        droppyMenu = droppyBuilder.build();
    }

    private void initView() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) findViewById(R.id.tvProductPrice);
        AndroidUtils.setBackgroundSolid(tvProductPrice, context, R.color.orange, 10, GradientDrawable.RECTANGLE);
        tvDiscountValue = (TextView) findViewById(R.id.tv_discount_value);
        tvUnitValue = (TextView) findViewById(R.id.tv_unit_value);
        tvAmountPaidValue = (TextView) findViewById(R.id.tv_amount_paid_value);
        tvDescriptionValue = (TextView) findViewById(R.id.tvDescriptionValue);
        viewPager = (ViewPager) findViewById(R.id.viewpager_custom);
        viewPagerIndicator = (LinearLayout) findViewById(R.id.viewpagerindicator);
        editTextPostalCode = (EditText) findViewById(R.id.editTextPostalCode);
        AndroidUtils.setBackgroundSolidEachRadius(editTextPostalCode, context, R.color.grey_light2, 10, 0, 0, 10, GradientDrawable.RECTANGLE);
        tvPinCodeCheck = (TextView) findViewById(R.id.tvPinCodeCheck);
        AndroidUtils.setBackgroundSolidEachRadius(tvPinCodeCheck, context, R.color.grey_buy_now, 0, 10, 10, 0, GradientDrawable.RECTANGLE);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator_product_detail);
        tvToatalRatingAndReview = (TextView) findViewById(R.id.tvToatalRatingAndReview);
        tvRatingAverage = (TextView) findViewById(R.id.tvRatingAverage);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.reviewList);

        mLayoutManager = new LinearLayoutManager(context);
        reviewRecyclerView.setLayoutManager(mLayoutManager);

        relativeRateReview = (RelativeLayout) findViewById(R.id.relativeRateReview);
        dropDownContainer = (LinearLayout) findViewById(R.id.dropDownContainer);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        CustomQuantityDialog.commonInterface = new CommonInterface() {
            @Override
            public Object getData(Object object) {

                tvQuantity.setText(object.toString());

                return null;
            }
        };

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
                                JsonArray resultJsonArray = result.get("result").getAsJsonArray();
                                JsonObject resultJsonObject = resultJsonArray.get(0).getAsJsonObject();
                                if (resultJsonObject != null) {
                                    loadProductDetailWithData(resultJsonObject);
                                }

                                JsonArray jsonImageUrlArray = result.get("product_images").getAsJsonArray();
                                if (jsonImageUrlArray != null) {
                                    loadImageUrlArrayList(jsonImageUrlArray);
                                }

                                JsonObject jsonTotalRating = result.getAsJsonObject("total_rating");
                                String averageRating = jsonTotalRating.get("avg_rating").getAsString();
                                String totalReviews = jsonTotalRating.get("countreviews").getAsString();
                                tvToatalRatingAndReview.setText(new StringBuilder(averageRating).append(" rating and review ").append(totalReviews));
                                tvRatingAverage.setText(averageRating);


                                JsonArray jsonArrayReview = result.getAsJsonArray("reviews");
                                if (jsonArrayReview.size() >= 0) {

                                    for (int j = 0; j < jsonArrayReview.size(); j++) {
                                        JsonObject jsonreview_data = (JsonObject) jsonArrayReview.get(j);
                                        String email = jsonreview_data.get("email").getAsString();
                                        String name = jsonreview_data.get("name").getAsString();
                                        String message = jsonreview_data.get("message").getAsString();
                                        String rating = jsonreview_data.get("rating").getAsString();
                                        String title = jsonreview_data.get("title").getAsString();
                                        String created_date = jsonreview_data.get("created_at").getAsString();
                                        Log.e("jsonreview---", title);
                                        reviewListDatas.add(new ReviewListData(email, name, message, rating, title, created_date));
                                    }
                                    reviewListAdapter = new ReviewListAdapter(context, reviewListDatas);
                                    reviewRecyclerView.setAdapter(reviewListAdapter);
                                }
                            } else {
                                AndroidUtils.showErrorLog(context, " getProductDetailData webservice error is true.");
                            }
                        }
                    }
                });
    }

    private void loadImageUrlArrayList(JsonArray jsonImageUrlArray) {
        for (int i = 0; i < jsonImageUrlArray.size(); i++) {
            JsonObject jsonUrlObject = (JsonObject) jsonImageUrlArray.get(i);
            imageUrlArrayList.add(jsonUrlObject.get("image_url").getAsString());
            imageUrlArrayList.add(jsonUrlObject.get("image_url").getAsString());
            imageUrlArrayList.add(jsonUrlObject.get("image_url").getAsString());
            imageUrlArrayList.add(jsonUrlObject.get("image_url").getAsString());
        }
        setUpViewPager();
    }

    private void loadProductDetailWithData(JsonObject resultJsonObject) {
        tvProductName.setText(resultJsonObject.get("name").getAsString());
        tvProductPrice.setText(new StringBuilder(getString(R.string.rupay_text)).append(" ").append(resultJsonObject.get("price").getAsString()));
        tvDiscountValue.setText(resultJsonObject.get("discount").getAsString());
        tvUnitValue.setText(resultJsonObject.get("unit_name").getAsString());
        tvDescriptionValue.setText(resultJsonObject.get("short_des").getAsString());
    }


    private void setUiPageViewController() {
        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonselected_item));
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.selecteditem_dot));
    }


    private void setUpViewPager() {
        viewPagerAdapter = new ShopViewPagerAdapter(context, imageUrlArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentPage);
        setUiPageViewController();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewPagerAdapter.getCount() - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };


        banner_timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
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


    public void showPopup() {

        CustomQuantityDialog customQuantityDialog = new CustomQuantityDialog(context);
        FragmentManager fm = getSupportFragmentManager();
        customQuantityDialog.show(fm, "Quantity");


//        final MaterialDialog dialog = new MaterialDialog.Builder(context)
//                .customView(R.layout.layout_more_quantity, true).backgroundColor(ContextCompat.getColor(context,R.color.transparent))
//                .show();
//
////                .onPositive(new MaterialDialog.SingleButtonCallback() {
////                    @Override
////                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
////                        if (Integer.valueOf(etManualQuantity.getText().toString()) > 0) {
////                            quantity = etManualQuantity.getText().toString();
////                            tvQuantity.setText(quantity);
////                        } else {
////                            etManualQuantity.setError("Please Select Valid Quantity.");
////                        }
////                        dialog.dismiss();
////                    }
////                })
//
//
//        etManualQuantity = (EditText) dialog.findViewById(R.id.editText);
//        okButton = (TextView) dialog.findViewById(R.id.okDialog);
//        cancelButton = (TextView) dialog.findViewById(R.id.cancelDialog);
//        disableButton(okButton);
//        disableButton(cancelButton);
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(okButton.isEnabled()){
//                    if(Integer.parseInt(etManualQuantity.getText().toString()) > 0){
//                        enableButton(okButton);
//
//                    }
//                } else {
//                    enableButton(okButton);
//                }
//                AndroidUtils.showErrorLog(context, "ok button");
//                dialog.hide();
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enableButton(cancelButton);
//                AndroidUtils.showErrorLog(context, "cancel button");
//                dialog.hide();
//            }
//        });
//
//        etManualQuantity.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(Integer.parseInt(s.toString()) == 0){
//                    etManualQuantity.setError("Please Enter Valid Quantity");
//                } else {
//                    tvQuantity.setText(s);
////                    dialog.dismiss();
//                }
//            }
//        });

    }

    private void enableButton(TextView textView) {
        if (textView != null) {
            AndroidUtils.setBackgroundSolid(textView, context, R.color.green, 10, GradientDrawable.RECTANGLE);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            textView.setEnabled(true);
        }
    }

    private void disableButton(TextView textView) {
        if (textView != null) {
            AndroidUtils.setBackgroundSolid(textView, context, R.color.white, 10, GradientDrawable.RECTANGLE);
            textView.setTextColor(ContextCompat.getColor(context, R.color.green));
            textView.setEnabled(false);
        }
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
