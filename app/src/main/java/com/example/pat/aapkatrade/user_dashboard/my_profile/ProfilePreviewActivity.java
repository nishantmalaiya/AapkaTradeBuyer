package com.example.pat.aapkatrade.user_dashboard.my_profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.user_dashboard.changepassword.ChangePassword;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePreviewActivity extends AppCompatActivity {

    private TextView tvTitle, textViewName, tvMobile, tvEmail, tvUserType;
    private LinearLayout linearLayoutLagout, linearLayoutResetpassword;
    private AppSharedPreference appSharedPreference;
    private ImageView btnEdit;
    private Context context;
    private CircleImageView userimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_preview);
        context = ProfilePreviewActivity.this;
        appSharedPreference = new AppSharedPreference(this);
        setUpToolBar();
        setup_layout();

    }

    private void setup_layout() {
        userimage = (CircleImageView) findViewById(R.id.imageviewpp);
        btnEdit = (ImageView) findViewById(R.id.btnEdit);

        linearLayoutLagout = (LinearLayout) findViewById(R.id.linearLayoutLogout);

        linearLayoutResetpassword = (LinearLayout) findViewById(R.id.linearLayoutResetpassword);



        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent profile_edit = new Intent(ProfilePreviewActivity.this, MyProfileActivity.class);
                startActivity(profile_edit);
            }


        });

        linearLayoutLagout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save_shared_pref("notlogin", "notlogin", "notlogin", "profile_pic");
                Intent Homedashboard = new Intent(ProfilePreviewActivity.this, HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);


            }
        });


        linearLayoutResetpassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent change_password = new Intent(ProfilePreviewActivity.this, ChangePassword.class);
                startActivity(change_password);
            }
        });



        textViewName = (TextView) findViewById(R.id.textViewName);

        tvMobile = (TextView) findViewById(R.id.tvMobile);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvUserType = (TextView) findViewById(R.id.tvUserType);

        if (appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_NAME.toString(), "notlogin") != null) {
            String Username = appSharedPreference.getSharedPref(SharedPreferenceConstants.FIRST_NAME.toString(), "not");
            String Emailid = appSharedPreference.getSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), "not");

            String userType = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_TYPE.toString(), "0");
            String user_image = appSharedPreference.getSharedPref(SharedPreferenceConstants.PROFILE_PIC.toString(), "");

            Log.e("user_image", user_image);

            textViewName.setText(Username);
            tvEmail.setText(Emailid);

            if (userType.equals("2")) {
                Log.e("user_image2", user_image);

//                Picasso.with(context).load(user_image)
//
//                        .error(R.drawable.ic_profile_user)
//                        .into(userimage);
                tvUserType.setText("Welcome Buyer");


            }
        }

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


    public void save_shared_pref(String user_id, String user_name, String email_id, String profile_pic) {
        appSharedPreference.setSharedPref(SharedPreferenceConstants.USER_ID.toString(), user_id);
        appSharedPreference.setSharedPref(SharedPreferenceConstants.USER_NAME.toString(), user_name);
        appSharedPreference.setSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), email_id);
        appSharedPreference.setSharedPref(SharedPreferenceConstants.PROFILE_PIC.toString(), profile_pic);

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        if (appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_NAME.toString(), "notlogin") != null) {
            String Username = appSharedPreference.getSharedPref(SharedPreferenceConstants.FIRST_NAME.toString(), "not");
            String Emailid = appSharedPreference.getSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), "not");

            String userType = appSharedPreference.getSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), "0");
            String user_image = appSharedPreference.getSharedPref(SharedPreferenceConstants.PROFILE_PIC.toString(), "");


            textViewName.setText(Username);
            tvEmail.setText(Emailid);

            if (userType.equals("1")) {
                Picasso.with(context).load(user_image)

                        .error(R.drawable.ic_profile_user)
                        .into(userimage);


//                Ion.with(userimage)
//                        .error(ContextCompat.getDrawable(this, R.drawable.ic_profile_user))
//                        .placeholder(ContextCompat.getDrawable(this, R.drawable.ic_profile_user))
//                        .load(user_image);


                tvUserType.setText("Welcome Seller");

            } else if (userType.equals("2")) {
                Picasso.with(context).load(user_image)

                        .error(R.drawable.ic_profile_user)
                        .into(userimage);


//                Ion.with(userimage)
//                        .error(ContextCompat.getDrawable(this, R.drawable.ic_profile_user))
//                        .placeholder(ContextCompat.getDrawable(this, R.drawable.ic_profile_user))
//                        .load(user_image);
                tvUserType.setText("Welcome Buyer");

            } else if (userType.equals("3")) {
                Picasso.with(context).load(user_image)

                        .error(R.drawable.ic_profile_user)
                        .into(userimage);


//                Ion.with(userimage)
//                        .error(ContextCompat.getDrawable(this, R.drawable.ic_profile_user))
//                        .placeholder(ContextCompat.getDrawable(this, R.drawable.ic_profile_user))
//                        .load(user_image);
                tvUserType.setText("Welcome Bussiness Associate");

            }
        }
    }
}


