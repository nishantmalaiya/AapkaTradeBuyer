package com.example.pat.aapkatrade.user_dashboard.my_profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
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

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfileActivity extends AppCompatActivity {

    private AppSharedPreference app_sharedpreference;
    private EditText etFName, etLName, etEmail, etMobileNo, etAddress;
    private ProgressBarHandler p_handler;
    private TextView tvMyProfileDetailHeading;
    private CoordinatorLayout coordinatorlayout_myprofile;
    private Context context;
    private String fname, lname, email, mobile, address, user_image;
    private CircleImageView userImageView;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        context = MyProfileActivity.this;
        setUpToolBar();

        initView();

    }

    private void initView() {

        app_sharedpreference = new AppSharedPreference(context);
        p_handler = new ProgressBarHandler(context);

        getshared_pref_data();

        Log.e("user_image_profile", user_image);
        coordinatorlayout_myprofile = (CoordinatorLayout) findViewById(R.id.coordinate_myprofile);
        tvMyProfileDetailHeading = (TextView) findViewById(R.id.tvMyProfileDetailHeading);
        userImageView = (CircleImageView) findViewById(R.id.user_imageview);
        etFName = (EditText) findViewById(R.id.etFName);
        tvMyProfileDetailHeading.setText("Hello, " + fname + " To Update your account information.");
        etFName.setText(fname);
        etFName.setSelection(etFName.getText().length());

        etLName = (EditText) findViewById(R.id.etLName);
        etLName.setText(lname);
        etLName.setSelection(etLName.getText().length());

        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setText(email);
        etEmail.setSelection(etEmail.getText().length());

        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etMobileNo.setText(mobile);
        etMobileNo.setSelection(etMobileNo.getText().length());

        etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.setText(address);

        etEmail.setKeyListener(null);

        etMobileNo.setKeyListener(null);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et = etFName.getText().toString();

                System.out.println("et-----------" + et);

                if (!Validation.isEmptyStr(etFName.getText().toString())) {

                    if (!Validation.isEmptyStr(etLName.getText().toString())) {

                        if (Validation.isValidEmail(etEmail.getText().toString())) {

                            if (!Validation.isEmptyStr(etMobileNo.getText().toString())) {

                                if (!Validation.isEmptyStr(etAddress.getText().toString())) {

                                    edit_profile_webservice();

                                } else {
                                    AndroidUtils.showErrorLog(context, "Please Enter Address");
                                }
                            } else {
                                AndroidUtils.showErrorLog(context, "Please Enter Mobile Number");
                            }
                        } else {
                            AndroidUtils.showErrorLog(context, "Please Enter Valid Email Address");
                        }
                    } else {
                        AndroidUtils.showErrorLog(context, "Please Enter Last Name");
                    }
                }else {
                    AndroidUtils.showErrorLog(context, "Please Enter First Name");
                }
            }
        });
    }

    private void getshared_pref_data() {
        user_image = app_sharedpreference.getSharedPref(SharedPreferenceConstants.PROFILE_PIC.toString(), "");
        fname = app_sharedpreference.getSharedPref(SharedPreferenceConstants.FIRST_NAME.toString(), "");
        lname = app_sharedpreference.getSharedPref(SharedPreferenceConstants.LAST_NAME.toString(), "");
        email = app_sharedpreference.getSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), "");
        mobile = app_sharedpreference.getSharedPref(SharedPreferenceConstants.MOBILE.toString(), "");
        address = app_sharedpreference.getSharedPref(SharedPreferenceConstants.ADDRESS.toString(), "");

    }

    private void edit_profile_webservice() {
        p_handler.show();

        Ion.with(MyProfileActivity.this)
                .load((getResources().getString(R.string.webservice_base_url)) + "/my_account")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("name", etFName.getText().toString())
                .setBodyParameter("lastname", etLName.getText().toString())
                .setBodyParameter("mobile", etMobileNo.getText().toString())
                .setBodyParameter("email", etEmail.getText().toString())
                .setBodyParameter("address", etAddress.getText().toString())
                .setBodyParameter("user_id", app_sharedpreference.getSharedPref("userid", ""))
                .setBodyParameter("user_type", "1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if (result == null) {
                            p_handler.hide();
                            System.out.println("result-----------NULLLLLLL");


                        } else {
                            String error = result.get("error").getAsString();
                            if (error.contains("false")) {

                                JsonObject jsonObject = result.getAsJsonObject();

                                String message = jsonObject.get("message").getAsString();

                                if (message.equals("No any changes to update!")) {

                                    showMessage(message);
                                    p_handler.hide();

                                } else {

                                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                                    for (int i = 0; i < jsonResultArray.size(); i++) {

                                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                        String update_name = jsonObject1.get("name").getAsString();
                                        String update_lastname = jsonObject1.get("lastname").getAsString();
                                        String update_mobile = jsonObject1.get("mobile").getAsString();
                                        String update_address = jsonObject1.get("address").getAsString();

                                        app_sharedpreference.setSharedPref("name", update_name);
                                        app_sharedpreference.setSharedPref("lname", update_lastname);
                                        app_sharedpreference.setSharedPref("mobile", update_mobile);
                                        app_sharedpreference.setSharedPref("address", update_address);

                                        System.out.println("Username Data-----------" + update_name);

                                        showMessage(message);
                                        p_handler.hide();

                                    }

                                }

                            } else {
                                JsonObject jsonObject = result.getAsJsonObject();
                                String message = jsonObject.get("message").getAsString();
                                showMessage(message);
                                p_handler.hide();

                            }

                        }
                    }
                });

    }

    private void showMessage(String message) {

        AndroidUtils.showSnackBar(coordinatorlayout_myprofile, message);

    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.logoWord).setVisibility(View.GONE);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.my_profile_heading));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        getMenuInflater().inflate(R.menu.bottom_home_menu, menu);
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


    public void save_shared_pref(String user_id, String user_name, String email_id, String lname, String dob, String address, String mobile, String order_quantity, String product_quantity, String company_quantity, String vendor_quantity, String network_quantity) {

        app_sharedpreference.setSharedPref("userid", user_id);
        app_sharedpreference.setSharedPref("username", user_name);
        app_sharedpreference.setSharedPref("emailid", email_id);
        app_sharedpreference.setSharedPref("lname", lname);
        app_sharedpreference.setSharedPref("address", address);
        app_sharedpreference.setSharedPref("mobile", mobile);

    }


}
