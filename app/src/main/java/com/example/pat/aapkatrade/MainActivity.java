package com.example.pat.aapkatrade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.LocationManagerCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.animation_effects.App_animation;
import com.example.pat.aapkatrade.general.progressbar.Custom_progress_bar;
import com.example.pat.aapkatrade.location.GeoCoderAddress;
import com.example.pat.aapkatrade.location.Mylocation;
import com.example.pat.aapkatrade.service.LocationService;
import com.example.pat.aapkatrade.welcome.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private ConnetivityCheck connetivityCheck;
    private TextView tv_aapkatrade;
    private ImageView left_image, right_image;
    private ProgressDialog pd;
    private ImageView circle_image;
    private Custom_progress_bar custom_progress_bar;
    private Mylocation mylocation;
    private GeoCoderAddress geoCoderAddressAsync;
    private String AddressAsync;
    private AppSharedPreference appSharedpreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        appSharedpreference = new AppSharedPreference(MainActivity.this);

        circle_image = (ImageView) findViewById(R.id.circle_image);

        App_animation.circularanimation(circle_image);

        String fontPath = "impact.ttf";

        // text view label
        tv_aapkatrade = (TextView) findViewById(R.id.tv_aapkatrade);
        left_image = (ImageView) findViewById(R.id.left_panel);
        right_image = (ImageView) findViewById(R.id.right_panel);

        //Animation
        App_animation.left_animation(left_image, this);
        App_animation.right_animation(right_image, this);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);


        // Applying font
        tv_aapkatrade.setTypeface(tf);
        connetivityCheck = new ConnetivityCheck();
        custom_progress_bar = new Custom_progress_bar(MainActivity.this);
        custom_progress_bar.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ConnetivityCheck.isNetworkAvailable(MainActivity.this)) {


                    mylocation = new Mylocation(MainActivity.this);
                    LocationManagerCheck locationManagerCheck = new LocationManagerCheck(MainActivity.this);

                    Location location = null;
                    if (locationManagerCheck.isLocationServiceAvailable()) {
                        AndroidUtils.showErrorLog(MainActivity.this, "viewpager welcome", appSharedpreference.getSharedPrefInt(SharedPreferenceConstants.IS_FIRST_TIME.toString()));
                        if (appSharedpreference.getSharedPrefInt(SharedPreferenceConstants.IS_FIRST_TIME.toString()) == 1)

                        {


                            Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(mainIntent);
                            finish();


                        } else {

                            // appSharedpreference.setSharedPrefInt(SharedPreferenceConstants.IS_FIRST_TIME.toString(), 1);


                            Intent mainIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                            startActivity(mainIntent);
                            finish();

                        }


                        if (pd != null) {
                            pd.dismiss();
                        }

                        Intent serviceIntent = new Intent(MainActivity.this, LocationService.class);
                        startService(serviceIntent);


                    } else {
                        locationManagerCheck.createLocationServiceError(MainActivity.this);
                    }


                } else {
                    Intent mainIntent = new Intent(MainActivity.this, ConnectivityNotFound.class);
                    mainIntent.putExtra("callerActivity", MainActivity.class.getName());
                    startActivity(mainIntent);
                    finish();
                    if (pd != null) {
                        pd.dismiss();
                    }
                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidUtils.showErrorLog(MainActivity.this, "work while on Resume");

//        Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
//        startActivity(mainIntent);
//        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AndroidUtils.showErrorLog(MainActivity.this, "work while on postResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        AndroidUtils.showErrorLog(MainActivity.this, "work while on start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        AndroidUtils.showErrorLog(MainActivity.this, "work while on stop");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        AndroidUtils.showErrorLog(MainActivity.this, "work while on onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AndroidUtils.showErrorLog(MainActivity.this, "onPause");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AndroidUtils.showErrorLog(MainActivity.this, "onActivityResult" + requestCode + "***" + resultCode);
        if (resultCode == 0) {
            switch (requestCode) {

                case 1:
                    LocationManagerCheck locationManagerCheck = new LocationManagerCheck(MainActivity.this);
                    if (locationManagerCheck.isLocationServiceAvailable()) {
                        Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(mainIntent);
                        finish();
                        if (pd != null) {
                            pd.dismiss();
                        }

                        Intent serviceIntent = new Intent(MainActivity.this, LocationService.class);
                        startService(serviceIntent);
                    } else {
                        locationManagerCheck.createLocationServiceError(MainActivity.this);
                    }


                    break;
            }
        }
    }


}
