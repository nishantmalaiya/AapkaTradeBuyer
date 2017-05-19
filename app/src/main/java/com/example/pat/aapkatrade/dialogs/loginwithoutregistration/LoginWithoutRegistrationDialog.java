package com.example.pat.aapkatrade.dialogs.loginwithoutregistration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.BlankFragment;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressDialogHandler;
import com.example.pat.aapkatrade.user_dashboard.order_list.order_details.OrderDetailsActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by PPC17 on 16-May-17.
 */

public class LoginWithoutRegistrationDialog extends DialogFragment {
    private ProgressDialogHandler progressDialogHandler;
    private AppSharedPreference appSharedPreference;
    private ImageView dialog_close_image_view, editMobile;
    private Context context;
    private TextView tvTourMsg;
    private EditText etEmailOrMobile, tvMobile;
    private Button submit;
    private CardView loginWithoutRegistrationContainer;
    private LinearLayout row1Layout, row2Layout;
    private RelativeLayout passwordLayout;
    private boolean isStep1 = true;


    public LoginWithoutRegistrationDialog(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login_without_registration, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        initview(v);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStep1WebService();
            }
        });

        dialog_close_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }


    private void callStep1WebService() {
        progressDialogHandler.show();
        final String emailPhone = etEmailOrMobile.getText().toString();
        if (Validation.isValidEmail(emailPhone) || Validation.isValidNumber(emailPhone, Validation.getNumberPrefix(emailPhone))) {
            Ion.with(context)
                    .load(new StringBuilder(getString(R.string.webservice_base_url)).append("/").append("register").toString())
                    .setHeader("Authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("emailphone", emailPhone)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialogHandler.hide();
                            if (result!= null && result.get("error").getAsString().contains("false")) {
                                AndroidUtils.showErrorLog(context, result);
                                appSharedPreference.setSharedPref(SharedPreferenceConstants.USER_ID.toString(), result.get("user_id").getAsString());
                                AndroidUtils.showSnackBar(loginWithoutRegistrationContainer, result.get("message").getAsString());
                                JsonObject resultJsonObject = result.get("result").getAsJsonObject();
                                if (resultJsonObject != null) {
                                    appSharedPreference.setSharedPref(SharedPreferenceConstants.CLIENT_ID.toString(), resultJsonObject.get("client_id").getAsString());
                                    appSharedPreference.setSharedPref(SharedPreferenceConstants.OTP_ID.toString(), resultJsonObject.get("otp_id").getAsString());
                                    isStep1 = false;
                                    passwordLayout.setVisibility(View.VISIBLE);
                                    row1Layout.setVisibility(View.VISIBLE);
                                    row1Layout.setVisibility(View.VISIBLE);
                                    tvMobile.setText(emailPhone);
                                    callStep2WebService();

                                } else {
                                    AndroidUtils.showErrorLog(context, "Null Result Tag");
                                }
                            } else {
                                AndroidUtils.showErrorLog(context, "Register WebService Null Result Found");
                            }
                        }
                    });
        }
    }

    private void callStep2WebService() {
        progressDialogHandler.show();
        Ion.with(context)
                .load(new StringBuilder(getString(R.string.webservice_base_url)).append("/").append("varify_buyer_otp").toString())
                .setHeader("Authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id", appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString()))
                .setBodyParameter("otp", appSharedPreference.getSharedPref(SharedPreferenceConstants.OTP_ID.toString()))
                .setBodyParameter("client_id", appSharedPreference.getSharedPref(SharedPreferenceConstants.CLIENT_ID.toString()))
                .setBodyParameter("type", "2")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressDialogHandler.hide();
                        if (result.get("error").getAsString().contains("false")) {
                            AndroidUtils.showErrorLog(context, result);
                            appSharedPreference.setSharedPref(SharedPreferenceConstants.USER_ID.toString(), result.get("user_id").getAsString());
                            AndroidUtils.showSnackBar(loginWithoutRegistrationContainer, result.get("message").getAsString());
                        } else {
                            AndroidUtils.showErrorLog(context, "Register WebService Null Result Found");
                        }
                    }
                });

    }


    @SuppressLint("NewApi")
    private void initview(View v) {
        progressDialogHandler = new ProgressDialogHandler(context);
        appSharedPreference = new AppSharedPreference(context);
        dialog_close_image_view = (ImageView) v.findViewById(R.id.dialog_close_image_view);
        tvTourMsg = (TextView) v.findViewById(R.id.tvTourMsg);
        tvTourMsg.setText(Html.fromHtml("Get started with our secure <font color = \'#F96004\'> LOGIN </font>flow", 0));
        submit = (Button) v.findViewById(R.id.submit);
        etEmailOrMobile = (EditText) v.findViewById(R.id.etEmailOrMobile);
        loginWithoutRegistrationContainer = (CardView) v.findViewById(R.id.loginWithoutRegistrationContainer);
        tvMobile = (EditText) v.findViewById(R.id.tvMobile);
        editMobile = (ImageView) v.findViewById(R.id.editMobile);

        row1Layout = (LinearLayout) v.findViewById(R.id.row1Layout);
        row2Layout = (LinearLayout) v.findViewById(R.id.row2Layout);
        passwordLayout = (RelativeLayout) v.findViewById(R.id.passwordLayout);
    }

}