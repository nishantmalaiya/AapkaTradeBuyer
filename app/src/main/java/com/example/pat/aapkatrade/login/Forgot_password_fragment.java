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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.Change_Font;
import com.example.pat.aapkatrade.general.interfaces.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;

import java.util.HashMap;


public class Forgot_password_fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private CoordinatorLayout activity_forgot__password;
    private String usertype = "buyer";

    String classname;
    Forgot_password_fragment forgot_password_fragment;
    Reset_password_fragment reset_password_fragment;

    String class_index;

    private AppSharedPreference app_sharedpreference;
    private ProgressBarHandler progressBarHandler;
    private TextView tv_forgot_password, tv_forgot_password_description;
    private EditText et_email_forgot, et_mobile_no;
    private Button btn_send_otp;
    private FrameLayout forgot_password_container;


    public Forgot_password_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

        progressBarHandler = new ProgressBarHandler(getActivity());
        forgot_password_container = (FrameLayout) v.findViewById(R.id.forgot_password_container);

        tv_forgot_password = (TextView) v.findViewById(R.id.tv_forgot_password);
        tv_forgot_password_description = (TextView) v.findViewById(R.id.tv_forgot_password_description);


        et_email_forgot = (EditText) v.findViewById(R.id.et_email_forgot);
        et_mobile_no = (EditText) v.findViewById(R.id.et_mobile_no);

        btn_send_otp = (Button) v.findViewById(R.id.btn_send_otp);
        btn_send_otp.setOnClickListener(this);
        Change_Font.Change_Font_textview(getActivity(), tv_forgot_password);
        Change_Font.Change_Font_textview(getActivity(), tv_forgot_password_description);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_send_otp:


                Validatefields();


                break;
        }

    }

    private void Validatefields() {

        if (Validation.isValidEmail(et_email_forgot.getText().toString())) {
            call_forgotpasswod_webservice();

        } else if (Validation.validateEdittext(et_mobile_no)) {

            call_forgotpasswod_webservice();


        } else {

        }


    }


    private void call_forgotpasswod_webservice() {
        progressBarHandler.show();


        String webservice_forgot_password = getResources().getString(R.string.webservice_base_url) + "/forget";

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", getString(R.string.user_type));
        webservice_body_parameter.put("email", et_email_forgot.getText().toString().trim());
        webservice_body_parameter.put("mobile", et_mobile_no.getText().toString().trim());
        webservice_body_parameter.put("client_id", App_config.getCurrentDeviceId(getActivity()));


        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");


        Call_webservice.forgot_password(getActivity(), webservice_forgot_password, "forgot_password", webservice_body_parameter, webservice_header_type);
        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {
            @Override
            public void Taskcomplete(JsonObject data) {
                if (data != null) {
                    String error = data.get("error").getAsString();
                    if (error.contains("false")) {
                        Log.e("data",data.toString());

                        Intent go_to_activity_otp_verify = new Intent(getActivity(), ActivityOTPVerify.class);
                        go_to_activity_otp_verify.putExtra("class_name", getActivity().getClass().getName());
                        go_to_activity_otp_verify.putExtra("otp_id",data.get("otp_id").getAsString());
                        startActivity(go_to_activity_otp_verify);
                    }
                    String message = data.get("message").getAsString();
                    AndroidUtils.showSnackBar(forgot_password_container, message);
                    progressBarHandler.hide();
                } else {
                    progressBarHandler.hide();
                }
            }
        };
    }


}
