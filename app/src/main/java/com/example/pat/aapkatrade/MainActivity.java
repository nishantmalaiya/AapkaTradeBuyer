package com.example.pat.aapkatrade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.ConnectivityNotFound;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.LocationManager_check;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.animation_effects.App_animation;
import com.example.pat.aapkatrade.general.interfaces.CommonInterface;
import com.example.pat.aapkatrade.general.progressbar.Custom_progress_bar;
import com.example.pat.aapkatrade.location.AddressEnum;
import com.example.pat.aapkatrade.location.GeoCoderAddress;
import com.example.pat.aapkatrade.location.Mylocation;
import com.example.pat.aapkatrade.search.Search;
import com.example.pat.aapkatrade.service.LocationService;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ConnetivityCheck connetivity_check;
    TextView tv_aapkatrade;
    ImageView left_image, right_image;

    ProgressDialog pd;
    ImageView circle_image;
    Custom_progress_bar custom_progress_bar;
    private Mylocation mylocation;
    private GeoCoderAddress geoCoderAddressAsync;
    private String AddressAsync;
    AppSharedPreference appSharedpreference;

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
        connetivity_check = new ConnetivityCheck();
        custom_progress_bar = new Custom_progress_bar(MainActivity.this);
        custom_progress_bar.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (ConnetivityCheck.isNetworkAvailable(MainActivity.this)) {

                    boolean permission_status = CheckPermission.checkPermissions(MainActivity.this);


                    if (permission_status)

                    {
                        mylocation = new Mylocation(MainActivity.this);
                        LocationManager_check locationManagerCheck = new LocationManager_check(
                                MainActivity.this);
                        Location location = null;
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
        AndroidUtils.showErrorLog(MainActivity.this,"work while on Resume");

//        Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
//        startActivity(mainIntent);
//        finish();
    }
}
