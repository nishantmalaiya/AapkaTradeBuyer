package com.example.pat.aapkatrade.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Change_Font;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    private AppSharedPreference appSharedPreference;
    private ProgressBarHandler progressBarHandler;
    private TextView tv_forgot_password, tv_forgot_password_description;
    private EditText et_password, et_confirm_password;
    private Button btn_reset_password;
    private CoordinatorLayout activity_forgot__password;
    private String usertype;
    private String user_id;
    private String otp_id;
    private String classname;
    private ForgotPasswordFragment forgot_password_fragment;
    private ResetPasswordFragment reset_passwordFragment;
    private LinearLayout reset_password_container;
    private String class_index;


    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        otp_id = getArguments().getString("otp_id");

        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        initView(v);


        return v;

    }

    private void initView(View v) {
        appSharedPreference = new AppSharedPreference(getActivity());
        progressBarHandler = new ProgressBarHandler(getActivity());

        tv_forgot_password = (TextView) v.findViewById(R.id.tv_forgot_password);
        tv_forgot_password_description = (TextView) v.findViewById(R.id.tv_forgot_password_description);


        et_password = (EditText) v.findViewById(R.id.et_password);
        et_confirm_password = (EditText) v.findViewById(R.id.et_confirm_password);

        btn_reset_password = (Button) v.findViewById(R.id.btn_change_password);
        btn_reset_password.setOnClickListener(this);

        activity_forgot__password = (CoordinatorLayout) v.findViewById(R.id.coordinate_reset_password);
        reset_password_container = (LinearLayout) v.findViewById(R.id.reset_password_container);
        Change_Font.Change_Font_textview(getActivity(), tv_forgot_password);
        Change_Font.Change_Font_textview(getActivity(), tv_forgot_password_description);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_change_password:

                Log.e("enter", "enter");
                Validatefields();


                break;
        }

    }

    private void Validatefields() {

        if (Validation.isValidPassword(et_password.getText().toString().trim())) {
            Log.e("enter1", "enter1");
            if (Validation.isPasswordMatching(et_password.getText().toString().trim(), et_confirm_password.getText().toString().trim())) {

                call_reset_webservice();
            } else {
                showmessage(getResources().getString(R.string.passwordnotmathed));

            }

        }


//        else if (Validation.isValidPassword(et_confirm_password.getText().toString().trim())) {
//            Log.e("enter2","enter2");
//            if(Validation.isPasswordMatching(et_password.getText().toString().trim(),et_confirm_password.getText().toString().trim()))
//            {
//                call_reset_webservice();
//            }
//            else {
//                showMessage("! password not matched");
//
//            }
//
//
//        }


        else {

            showmessage(getResources().getString(R.string.password_validing_text));

        }


    }

    private void showmessage(String message) {
        AndroidUtils.showSnackBar(activity_forgot__password, message);
    }


    private void call_reset_webservice() {
        progressBarHandler.show();

        user_id = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString(), "notlogin");
        String webservice_reset_password = getResources().getString(R.string.webservice_base_url) + "/resetPwd";


        Log.e("user_id", user_id);

        Ion.with(getActivity())
                .load(webservice_reset_password)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("user_id ", user_id)
                .setBodyParameter("password", et_confirm_password.getText().toString())
                .setBodyParameter("otp_id", otp_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();

                        if (result != null) {
                            String error = result.get("error").getAsString();
                            if (error.contains("false")) {
                                Intent go_to_activity_otp_verify = new Intent(getActivity(), HomeActivity.class);
                                go_to_activity_otp_verify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(go_to_activity_otp_verify);
                            }
                            String message = result.get("message").getAsString();
                            showmessage(message);
                            progressBarHandler.hide();
                        } else {
                            Log.e("error_json", e.toString());
                            progressBarHandler.hide();
                        }
                        Log.e("reset_password", result.toString());


//
                    }

                });

    }

    // TODO: Rename method, update argument and hook method into UI event

}