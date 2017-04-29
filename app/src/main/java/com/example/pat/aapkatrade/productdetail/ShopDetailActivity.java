package com.example.pat.aapkatrade.productdetail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.CommomAdapter;
import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.LoginActivity;
import com.example.pat.aapkatrade.map.GoogleMapActivity;
import com.example.pat.aapkatrade.productdetail.opening_closing_days.OpenCloseDaysRecyclerAdapter;
import com.example.pat.aapkatrade.productdetail.opening_closing_days.OpenCloseShopData;
import com.example.pat.aapkatrade.productdetail.reviewlist.ReviewListAdapter;

import com.example.pat.aapkatrade.productdetail.reviewlist.ReviewListData;
import com.example.pat.aapkatrade.productdetail.shop_all_product.ShopAllProductActivity;
import com.example.pat.aapkatrade.rateus.RateusActivity;
import com.example.pat.aapkatrade.service_enquiry.ServiceEnquiry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.shehabic.droppy.DroppyMenuPopup;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import github.nisrulz.stackedhorizontalprogressbar.StackedHorizontalProgressBar;
import me.relex.circleindicator.CircleIndicator;


public class ShopDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private LinearLayout viewpagerindicator, linearlayoutShare, linearlayoutLocation;
    private RelativeLayout shopProductsLayout, openingClosingRelativeLayout;
    private Spinner spinner;
    private int max = 10;
    private ArrayList<String> imageList;
    private int currentPage = 0;
    private int isStartDate = -1;
    private ServiceEnquiry serviceEnquiry;
    private String date;
    private StackedHorizontalProgressBar progressbarFive, progressbarFour, progressbarThree, progressbarTwo, progressbarOne;
    private ViewPager vp;
    private ProductViewPagerAdapter viewpageradapter;
    private int dotsCount;
    private CircleIndicator  circleIndicator;
    private ImageView[] dots;
    private Timer banner_timer = new Timer();
    private RelativeLayout relativeBuyNow, RelativeProductDetail, relativeRateReview;
    private LinearLayout linearProductDetail;
    private TextView tvshopName, tvProPrice, tvCrossPrice, tvDiscription, tvSpecification, tvQuatity;
    private ProgressBarHandler progress_handler;
    private String product_id, product_location;
    private ImageView imgViewPlus, imgViewMinus;
    private int quantity_value = 1;
    private ProgressBarHandler progressBarHandler;
    private String productlocation, categoryName;
    private LinearLayout linearLayoutQuantity;
    private EditText firstName, quantity, price, mobile, email, etEndDate, etStatDate, description, editText;

    TextView tvServiceBuy, textViewQuantity, tvRatingAverage, tvTotal_rating_review, tvShopAddress, tvMobile, tvPhone;

  

    // TextView tvDurationHeading,tvDuration;
    Dialog dialog;
    private Context context;
    ArrayList<CommomData> productlist=new ArrayList<>();
    private String product_name;
    DroppyMenuPopup droppyMenu;
    AppSharedPreference app_sharedpreference;

    RecyclerView reviewList, openShopList, recyclerProduct ;
    LinearLayoutManager mLayoutManager, mLayoutManagerShoplist,llmanagerProductList;

   
    ReviewListAdapter reviewListAdapter;
    OpenCloseDaysRecyclerAdapter openCloseDaysRecyclerAdapter;
    ArrayList<ReviewListData> reviewListDatas = new ArrayList<>();
    CommomAdapter  commomAdapter_latestpost;
    private ArrayList<OpenCloseShopData> openCloseDayArrayList = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("time  Product Detail", String.valueOf(System.currentTimeMillis()));

        setContentView(R.layout.activity_shop_detail);

        app_sharedpreference = new AppSharedPreference(ShopDetailActivity.this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        context = ShopDetailActivity.this;

        Intent intent = getIntent();

        Bundle b = intent.getExtras();

        product_id = b.getString("product_id");

        Log.e("product_id", product_id);

        product_location = b.getString("product_location");

        progressBarHandler = new ProgressBarHandler(context);
        circleIndicator=(CircleIndicator)findViewById(R.id.indicator_product_detail) ;
        setUpToolBar();
        initView();

        getShopDetailsData();

    }


    private void getShopDetailsData() {
        relativeBuyNow.setVisibility(View.INVISIBLE);
        linearProductDetail.setVisibility(View.INVISIBLE);
        progress_handler.show();
        AndroidUtils.showErrorLog(context, "data_productdetail", getResources().getString(R.string.webservice_base_url) + "     " + product_id);

        Ion.with(getApplicationContext())
                .load(getResources().getString(R.string.webservice_base_url) + "/shop_detail/" + product_id)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", "0")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.e("result---------", result.toString());
                            JsonObject jsonObject = result.getAsJsonObject();
                            JsonObject json_result = jsonObject.getAsJsonObject("result");
                            JsonObject json_total_rating = jsonObject.getAsJsonObject("total_rating");
                            String avg_rating = json_total_rating.get("avg_rating").getAsString();
                            tvRatingAverage.setText(avg_rating);
                            String total_review = json_total_rating.get("countreviews").getAsString();
                            tvTotal_rating_review.setText(total_review + " rating and " + "review " + total_review);
                            JsonArray jsonArray_image = json_result.getAsJsonArray("product_images");
                            JsonArray jsonArray_review = result.getAsJsonArray("reviews");
                            if (jsonArray_review.size() == 0) {
                            } else {
                                for (int j = 0; j < jsonArray_review.size(); j++) {
                                    JsonObject jsonreview_data = (JsonObject) jsonArray_review.get(j);
                                    String email = jsonreview_data.get("email").getAsString();
                                    String name = jsonreview_data.get("name").getAsString();
                                    String message = jsonreview_data.get("message").getAsString();
                                    String rating = jsonreview_data.get("rating").getAsString();
                                    String title = jsonreview_data.get("title").getAsString();
                                    String created_date = jsonreview_data.get("created_at").getAsString();
                                    Log.e("jsonreview---", title);
                                    reviewListDatas.add(new ReviewListData(email, name, message, rating, title, created_date));
                                }
                                reviewListAdapter = new ReviewListAdapter(ShopDetailActivity.this, reviewListDatas);
                                reviewList.setAdapter(reviewListAdapter);
                            }
                            AndroidUtils.showErrorLog(context, "jsonArray_image------" + jsonArray_review.toString());
                            for (int i = 0; i < jsonArray_image.size(); i++) {
                                JsonObject jsonimage = (JsonObject) jsonArray_image.get(i);
                                String image_url = jsonimage.get("image_url").getAsString();
                                AndroidUtils.showErrorLog(context, "image_url---------" + image_url);
                                imageList.add(image_url);
                            }
                            product_name = json_result.get("name").getAsString();
                            categoryName = json_result.get("catname").getAsString();
                            String product_price = json_result.get("price").getAsString();
                            String product_cross_price = json_result.get("cross_price").getAsString();
                            String description = json_result.get("short_des").getAsString();
                            String duration = json_result.get("deliverday").getAsString();
                            String pincode = json_result.get("pincode").getAsString();
                            String address = json_result.get("address").getAsString() + "," + pincode;
                            String mobile = json_result.get("mobile").getAsString();
                            String phone = json_result.get("phone").getAsString();


                            JsonArray jsonProductList = json_result.getAsJsonArray("associate_product");
                            if(jsonProductList!=null && jsonProductList.size()>0){
                                shopProductsLayout.setVisibility(View.VISIBLE);
                                for (int i = 0; i < jsonProductList.size(); i++) {
                                    JsonObject jsonproduct = (JsonObject) jsonProductList.get(i);
                                    String product_id = jsonproduct.get("id").getAsString();
                                    String product_name = jsonproduct.get("name").getAsString();
                                    String product_short_description = jsonproduct.get("short_des").getAsString();
                                    String price = jsonproduct.get("price").getAsString();
                                    String product_image = jsonproduct.get("image_url").getAsString();
                                    productlist.add(new CommomData(product_id, product_name, price, product_image, address));
                                }
                                commomAdapter_latestpost = new CommomAdapter(context, productlist, "list_product", "latestdeals");
                                recyclerProduct.setAdapter(commomAdapter_latestpost);
                            } else {
                                shopProductsLayout.setVisibility(View.GONE);
                            }

                            tvShopAddress.setText(address);
                            tvPhone.setText(phone);
                            tvMobile.setText(mobile);

                            String enquiry = json_result.get("enquiry").getAsString();

                            productlocation = json_result.get("city_name").getAsString() + "," + json_result.get("state_name").getAsString() + "," + json_result.get("country_name").getAsString();




                            JsonArray openCloseDayArray = json_result.getAsJsonArray("opening_time");

                            if(openCloseDayArray!=null && openCloseDayArray.size()>0){
                                openingClosingRelativeLayout.setVisibility(View.VISIBLE);
//                                for(int i = 0; i < openCloseDayArray.size(); i++){
//                                    JsonObject jsonObjectDays = (JsonObject) openCloseDayArray.get(i);
//                                    OpenCloseShopData openCloseShopData = new OpenCloseShopData(jsonObjectDays.get("days").getAsString(), jsonObjectDays.get("open_time").getAsString(), jsonObjectDays.get("close_time").getAsString());
//                                    openCloseDayArrayList.add(openCloseShopData);
//
//                                }



                                for(int i = 0; i < 7; i++){
                                    OpenCloseShopData openCloseShopData = new OpenCloseShopData("Sunday", "600AM", "900PM");
                                    openCloseDayArrayList.add(openCloseShopData);

                                }

                                openShopList.setLayoutManager(mLayoutManagerShoplist);
                                openCloseDaysRecyclerAdapter = new OpenCloseDaysRecyclerAdapter(context, openCloseDayArrayList);
                                openShopList.setAdapter(openCloseDaysRecyclerAdapter);
                            } else {
                                openingClosingRelativeLayout.setVisibility(View.GONE);
                            }

                            tvshopName.setText(product_name);
                            tvDiscription.setText(description);
                            setUpViewPager();

                            progress_handler.hide();

                            linearProductDetail.setVisibility(View.VISIBLE);
                            relativeBuyNow.setVisibility(View.VISIBLE);

                        } else {
                            progress_handler.hide();
                            linearProductDetail.setVisibility(View.INVISIBLE);
                            relativeBuyNow.setVisibility(View.INVISIBLE);
                        }


                    }


                });

    }


    private void setUiPageViewController() {
        dotsCount = viewpageradapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            viewpagerindicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }


    private void setUpViewPager() {

        viewpageradapter = new ProductViewPagerAdapter(ShopDetailActivity.this, imageList);
        vp.setAdapter(viewpageradapter);
        vp.setCurrentItem(currentPage);
        setUiPageViewController();

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == viewpageradapter.getCount() - 1) {
                    currentPage = 0;
                }
                vp.setCurrentItem(currentPage++, true);
            }
        };


        banner_timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 0, 3000);


        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
                    }

                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
                } catch (Exception e) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
        circleIndicator.setViewPager(vp);
    }


    private void initView() {
        context = ShopDetailActivity.this;
        shopProductsLayout = (RelativeLayout) findViewById(R.id.shop_products_relative_layout);
        progress_handler = new ProgressBarHandler(this);

        imageList = new ArrayList<>();

        relativeRateReview = (RelativeLayout) findViewById(R.id.relativeRateReview);
        openingClosingRelativeLayout = (RelativeLayout) findViewById(R.id.opening_closing_relative_layout);

        linearlayoutShare = (LinearLayout) findViewById(R.id.linearlayoutShare);

        linearlayoutLocation = (LinearLayout) findViewById(R.id.linearlayoutLocation);

        tvRatingAverage = (TextView) findViewById(R.id.tvRatingAverage);
        tvShopAddress = (TextView) findViewById(R.id.tvShopAddress);
        tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvPhone = (TextView) findViewById(R.id.tvPhone);


        tvTotal_rating_review = (TextView) findViewById(R.id.tvToatal_rating_review);

        tvServiceBuy = (TextView) findViewById(R.id.tvServiceBuy);

        reviewList = (RecyclerView) findViewById(R.id.reviewList);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        reviewList.setLayoutManager(mLayoutManager);

        openShopList = (RecyclerView) findViewById(R.id.openShopList);
        recyclerProduct = (RecyclerView) findViewById(R.id.recyclerproduct);
        llmanagerProductList = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerProduct.setLayoutManager(llmanagerProductList);






        relativeRateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (app_sharedpreference.getsharedpref("username", "not").contains("not")) {
                    startActivity(new Intent(ShopDetailActivity.this, LoginActivity.class));
                } else {
                    Intent rate_us = new Intent(ShopDetailActivity.this, RateusActivity.class);
                    rate_us.putExtra("product_id", product_id);

                    rate_us.putExtra("product_name", tvshopName.getText().toString());


                    rate_us.putExtra("product_price", tvProPrice.getText().toString());
                    rate_us.putExtra("product_image", imageList.get(0));
                    startActivity(rate_us);
                }


            }
        });


        linearlayoutLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permission_status = CheckPermission.checkPermissions(ShopDetailActivity.this);
                if (permission_status) {
                    progressBarHandler.show();
                    LocationManager_check locationManagerCheck = new LocationManager_check(ShopDetailActivity.this);
                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable()) {
                        Intent intent = new Intent(ShopDetailActivity.this, GoogleMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("product_location", productlocation);
                        ShopDetailActivity.this.startActivity(intent);
                        progressBarHandler.hide();
                    } else {

                        locationManagerCheck.createLocationServiceError(ShopDetailActivity.this);
                        progress_handler.hide();


                    }
                }
            }
        });
        linearlayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "Text I want to share.";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

            }
        });

        RelativeProductDetail = (RelativeLayout) findViewById(R.id.RelativeProductDetail);
        linearProductDetail = (LinearLayout) findViewById(R.id.linearProductDetail);
        tvshopName = (TextView) findViewById(R.id.tvShopName);


        tvDiscription = (TextView) findViewById(R.id.tvDiscription);
        tvSpecification = (TextView) findViewById(R.id.tvSpecification);


        relativeBuyNow = (RelativeLayout) findViewById(R.id.relativeService_enquiry);
        vp = (ViewPager) findViewById(R.id.viewpager_custom);
        viewpagerindicator = (LinearLayout) findViewById(R.id.viewpagerindicator);

        progressbarFive = (StackedHorizontalProgressBar) findViewById(R.id.progressbarFive);
        progressbarFive.setMax(max);
        progressbarFive.setProgress(10);
        progressbarFive.setSecondaryProgress(0);

        progressbarFour = (StackedHorizontalProgressBar) findViewById(R.id.progressbarFour);
        progressbarFour.setMax(max);
        progressbarFour.setProgress(7);
        progressbarFour.setSecondaryProgress(3);

        progressbarThree = (StackedHorizontalProgressBar) findViewById(R.id.progressbarThree);
        progressbarThree.setMax(max);
        progressbarThree.setProgress(5);
        progressbarThree.setSecondaryProgress(5);

        progressbarTwo = (StackedHorizontalProgressBar) findViewById(R.id.progressbarTwo);
        progressbarTwo.setMax(max);
        progressbarTwo.setProgress(4);
        progressbarTwo.setSecondaryProgress(6);

        progressbarOne = (StackedHorizontalProgressBar) findViewById(R.id.progressbarOne);
        progressbarOne.setMax(max);
        progressbarOne.setProgress(3);
        progressbarOne.setSecondaryProgress(7);

        relativeBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialog d=new Dialog(context,R.style.NewDialog);
                ServiceEnquiry serviceEnquiry=new ServiceEnquiry(context);

                serviceEnquiry.show();

//                Intent i = new Intent(ShopDetailActivity.this, ShopAllProductActivity.class);
//                startActivity(i);


            }
        });


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


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }


    private void showDate(int year, int month, int day) {
        date = (new StringBuilder()).append(year).append("-").append(month).append("-").append(day).toString();
        if (isStartDate == 0) {
            etStatDate.setText(date);
        } else if (isStartDate == 1) {
            etEndDate.setText(date);
        }
    }


}
