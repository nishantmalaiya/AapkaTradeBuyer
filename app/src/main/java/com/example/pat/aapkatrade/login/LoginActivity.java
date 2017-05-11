package com.example.pat.aapkatrade.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.RegistrationActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CallWebService;
import com.example.pat.aapkatrade.general.interfaces.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    private TextView loginText, forgotPassword;
    private EditText etEmail, password;
    private RelativeLayout relativeLayoutLogin, relativeRegister;
    private AppSharedPreference appSharedpreference;
    private CoordinatorLayout coordinatorLayout;
    private Context context;
    ProgressBarHandler progressBarHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = LoginActivity.this;
        appSharedpreference = new AppSharedPreference(context);
        progressBarHandler = new ProgressBarHandler(context);
        setUpToolBar();
        initView();
        putValues();

        relativeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent registerUserActivity = new Intent(context, RegistrationActivity.class);
                startActivity(registerUserActivity);


            }


        });

    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        findViewById(R.id.logoWord).setVisibility(View.INVISIBLE);
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


    private void putValues() {

        relativeLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_email = etEmail.getText().toString().trim();
                String input_password = password.getText().toString();

                if (Validation.isValidEmail(input_email) || Validation.isValidNumber(input_email, Validation.getNumberPrefix(input_email))) {

                    if (Validation.validateEdittext(password)) {
                        String login_url = getResources().getString(R.string.webservice_base_url) + "/buyerlogin";

                        callLoginWebService(login_url, input_email, input_password);


                    } else {
                        showMessage(getResources().getString(R.string.password_validing_text));
                        password.setError(getResources().getString(R.string.password_validing_text));
                    }

                } else {
                    showMessage("Invalid Email Address / Mobile");
                    etEmail.setError("Invalid Email Address / Mobile");
                }


            }
        });
    }

    private void callLoginWebService(String login_url, String input_username, String input_password) {
        AndroidUtils.showErrorLog(context, "Login : "+input_username+"  Password : "+ input_password, "*************   "+login_url);

        progressBarHandler.show();
        Ion.with(context)
                .load(new StringBuilder(getString(R.string.webservice_base_url)).append("/").append("buyerlogin").toString())
                .setHeader("Authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("email", input_username)
                .setBodyParameter("password", input_password)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result)
//                    {
//                        if(result!=null) {
//                            progressBarHandler.hide();
//                        }
//
//                       Log.e("result-----------",result.toString());
//                        AndroidUtils.showErrorLog(context, "Login Result : "+result, "*************");
//
//                    }
//                });
//        AndroidUtils.showErrorLog(context, "Login2 : "+input_username+"  Password2 : "+ input_password, "*************   "+login_url);
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if (result != null) {

                            String error = result.get("error").getAsString();
                            if (error.contains("true"))

                            {
                                String message = result.get("message").getAsString();
                                showMessage(message);
                            }

                            else
                            {

                                showMessage(getResources().getString(R.string.welcomebuyer));
                                Log.e("webservice_returndata", result.toString());
                                saveDataInSharedPreference(result);
                                Intent Homedashboard = new Intent(context, HomeActivity.class);
                                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(Homedashboard);

                            }
                        }

                    }
                });
    }

    private void saveDataInSharedPreference(JsonObject webservice_returndata)
    {


        JsonObject jsonObject = webservice_returndata.getAsJsonObject("all_info");
        Log.e("hi", jsonObject.toString());

        appSharedpreference.setsharedpref("userid", webservice_returndata.get("user_id").getAsString());
        appSharedpreference.setsharedpref("name", jsonObject.get("name").getAsString());
        appSharedpreference.setsharedpref("username", jsonObject.get("name").getAsString());
        appSharedpreference.setsharedpref("lname", jsonObject.get("lastname").getAsString());
        appSharedpreference.setsharedpref("emailid", jsonObject.get("email").getAsString());
        appSharedpreference.setsharedpref("mobile", jsonObject.get("mobile").getAsString());
        appSharedpreference.setsharedpref("dob", jsonObject.get("dob").getAsString());
        appSharedpreference.setsharedpref("country_id", jsonObject.get("country_id").getAsString());
        appSharedpreference.setsharedpref("state_id", jsonObject.get("state_id").getAsString());
        appSharedpreference.setsharedpref("city_id", jsonObject.get("city_id").getAsString());
        appSharedpreference.setsharedpref("address", jsonObject.get("address").getAsString());
        appSharedpreference.setsharedpref("device_id", jsonObject.get("device_id").getAsString());
        appSharedpreference.setsharedpref("updated_at", jsonObject.get("updated_at").getAsString());
        appSharedpreference.setsharedpref("status", jsonObject.get("status").getAsString());
        appSharedpreference.setsharedpref("order", webservice_returndata.get("order").getAsString());
        appSharedpreference.setsharedpref("createdAt", webservice_returndata.get("createdAt").getAsString());



    }


    private void initView() {

        forgotPassword = (TextView) findViewById(R.id.tv_forgotpassword);
        loginText = (TextView) findViewById(R.id.tv_login);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        etEmail = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etpassword);
        relativeLayoutLogin = (RelativeLayout) findViewById(R.id.rl_login);
        relativeRegister = (RelativeLayout) findViewById(R.id.relativeRegister);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword_activity = new Intent(context, ForgotPassword.class);
                forgotpassword_activity.putExtra("forgot_index", "0");
                startActivity(forgotpassword_activity);
            }
        });


    }

    public void showMessage(String message) {
        AndroidUtils.showSnackBar(coordinatorLayout, message);
    }


}
