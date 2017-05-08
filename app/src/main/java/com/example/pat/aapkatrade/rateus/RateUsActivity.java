package com.example.pat.aapkatrade.rateus;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;
import com.google.gson.JsonObject;
import com.hedgehog.ratingbar.RatingBar;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class RateUsActivity extends AppCompatActivity {

    TextView tvProductName, tvCategoriesName, tvReviewHeading;
    RatingBar ratingbar;
    Button butttonExperience, buttonSubmit;
    EditText etTitle, etMessage;
    Context context;
    AppSharedPreference appSharedPreference;
    String user_id, product_id, product_name, product_price, product_image;
    ProgressBarHandler progress_handler;
    ImageView imgProduct;
    public static float rating_count;
    private CoordinatorLayout coordinationRateus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rateus);

        Intent intent = getIntent();

        Bundle b = intent.getExtras();

        product_id = b.getString("product_id");

        product_name = b.getString("product_name");

        product_price = b.getString("product_price");

        product_image = b.getString("product_image");

        System.out.println("product_id------------" + product_id.toString());

        progress_handler = new ProgressBarHandler(this);

        appSharedPreference = new AppSharedPreference(getApplicationContext());

        user_id = appSharedPreference.getsharedpref("userid", "");

        context = RateUsActivity.this;

        setUpToolBar();

        setup_layout();


    }

    private void setup_layout() {

        coordinationRateus = (CoordinatorLayout) findViewById(R.id.coordinationRateus);

        imgProduct = (ImageView) findViewById(R.id.imgProduct);

        Ion.with(imgProduct).load(product_image);

        tvProductName = (TextView) findViewById(R.id.tvProductName);

        tvProductName.setText(product_name);

        tvCategoriesName = (TextView) findViewById(R.id.tvCategoriesName);

        tvCategoriesName.setText(product_price);

        ratingbar = (RatingBar) findViewById(R.id.ratingbar);

        ratingbar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {

                rating_count = RatingCount;
            }
        });

        butttonExperience = (Button) findViewById(R.id.butttonExperience);

        tvReviewHeading = (TextView) findViewById(R.id.tvReviewHeading);

        etTitle = (EditText) findViewById(R.id.edtWriteReview);

        etMessage = (EditText) findViewById(R.id.edtWriteMessage);

        butttonExperience = (Button) findViewById(R.id.butttonExperience);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTitle.getText().toString().length() > 10) {
                    if (etMessage.getText().toString().length() > 10) {
                        callWriteReviewsWebService();
                    } else {
                        AndroidUtils.showSnackBar(coordinationRateus, "Message must be >= 10 Characters");
                    }
                } else {
                    AndroidUtils.showSnackBar(coordinationRateus, "Title must be >= 10 Characters");
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
        findViewById(R.id.logoWord).setVisibility(View.GONE);
        ;
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.write_and_review));
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


    private void callWriteReviewsWebService() {

        progress_handler.show();

        if (ConnetivityCheck.isNetworkAvailable(RateUsActivity.this)) {

            AndroidUtils.showErrorLog(context, "-----------" + product_id + user_id);

            Ion.with(RateUsActivity.this)
                    .load(getResources().getString(R.string.webservice_base_url) + "/write_review")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("user_id", user_id)
                    .setBodyParameter("message", etMessage.getText().toString())
                    .setBodyParameter("product_id", product_id)
                    .setBodyParameter("rating", String.valueOf(rating_count))
                    .setBodyParameter("title", etTitle.getText().toString())
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result == null) {
                                progress_handler.hide();
                            } else {
                                JsonObject jsonObject = result.getAsJsonObject();
                                String message = jsonObject.get("message").getAsString();

                                if (message.equals("Your review already submitted!")) {
                                    progress_handler.hide();
                                    AndroidUtils.showSnackBar(coordinationRateus, message);

                                } else {
                                    progress_handler.hide();
                                    etMessage.setText("");
                                    etTitle.setText("");
                                    AndroidUtils.showSnackBar(coordinationRateus, message);
                                }
                                finish();
                            }
                        }

                    });
        } else {
            Intent intent = new Intent(RateUsActivity.this, ConnectivityNotFound.class);
            intent.putExtra("callerActivity", RateUsActivity.class.getName());
            startActivity(intent);
        }


    }


}
