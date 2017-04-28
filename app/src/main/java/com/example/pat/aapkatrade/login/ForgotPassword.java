package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.DashboardFragment;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.aboutus.AboutUsFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.contact_us.ContactUsFragment;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.google.gson.JsonObject;

public class ForgotPassword extends AppCompatActivity {
    private TextView tv_forgot_password, tv_forgot_password_description;
    private Button btn_send_otp;
    private EditText et_email_forgot, et_mobile_no;
    private CoordinatorLayout activity_forgot__password;
    private AppSharedPreference app_sharedpreference;
    private String usertype = "buyer";
    private ProgressBarHandler progressBarHandler;
    private Context context;
    String classname;
    Forgot_password_fragment forgot_password_fragment;
    Reset_password_fragment reset_password_fragment;

    String class_index,otp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        context=ForgotPassword.this;
        app_sharedpreference = new AppSharedPreference(this);


        class_index = getIntent().getStringExtra("forgot_index");
        Log.e("class_index", "" + class_index);
        forgot_password_fragment = new Forgot_password_fragment();

        setUpToolBar();
        setupForgetpassword(class_index);


        setupForgetpassword(class_index);
        setUpToolBar();
        initView();

    }

    public ForgotPassword() {
    }

    private void setupForgetpassword(String class_index) {
        if (class_index.contains("0")) {
            if (forgot_password_fragment == null) {
                forgot_password_fragment = new Forgot_password_fragment();
            }
            String tagName = forgot_password_fragment.getClass().getName();


            replaceFragment(forgot_password_fragment, tagName);


        } else {

            otp_id = getIntent().getStringExtra("otp_id");
            if (reset_password_fragment == null) {
                reset_password_fragment = new Reset_password_fragment();
                Bundle b=new Bundle();
                b.putString("otp_id",otp_id);
                reset_password_fragment.setArguments(b);
            }
            String tagName = reset_password_fragment.getClass().getName();


            replaceFragment(reset_password_fragment, tagName);


        }


    }


    private void initView() {


        activity_forgot__password = (CoordinatorLayout) findViewById(R.id.activity_forgot__password);


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
        AndroidUtils.setBackgroundSolid(toolbar, context, R.color.transparent, 0, GradientDrawable.RECTANGLE);
        setSupportActionBar(toolbar);
        findViewById(R.id.logoWord).setVisibility(View.GONE);
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

    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_forgot_password_container, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        finish();

    }
}
