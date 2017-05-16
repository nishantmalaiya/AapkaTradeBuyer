package com.example.pat.aapkatrade.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.dialogs.track_order.orderdetail.Order_detail;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.adapter.CustomSpinnerAdapter;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.general.progressbar.ProgressDialogHandler;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by PPC17 on 16-May-17.
 */

public class CancelOrderDialog extends DialogFragment {
    ImageView dialog_close;
    Spinner spinnerCancelReason;
    ArrayList stateList = new ArrayList();
    EditText comments;
    ViewGroup container;
    ProgressDialogHandler progressDialogHandler;
    String orderid;
    Button submit;

    public CancelOrderDialog(String orderid) {
        this.orderid = orderid;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragmentcancelreason, container, false);
        this.container = container;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        initview(v);

        progressDialogHandler = new ProgressDialogHandler(getActivity());
        return v;
    }


    private void initview(View v) {


        dialog_close = (ImageView) v.findViewById(R.id.dialog_close_reason);
        comments = (EditText) v.findViewById(R.id.comment);
        submit = (Button) v.findViewById(R.id.submit);


///// set spinner adapter

        spinnerCancelReason = (Spinner) v.findViewById(R.id.spinnerCancelReason);

        stateList.add("--Select a Reason--");
        stateList.add("The delivery is delayed");
        stateList.add("Order placed by mistake");
        stateList.add("Expected delivery time is too long");
        stateList.add("Item price/shipping cost is too long");
        stateList.add("Need to change shipping address");
        stateList.add("Bought it from somewhere else");
        stateList.add("I'll buy later");
        stateList.add("I'm getting a better product offline");
        stateList.add("Any other Reason");


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(), R.layout.black_textcolor_spinner, stateList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.black_textcolor_spinner);

        spinnerCancelReason.setAdapter(spinnerArrayAdapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callCancelOrderWebservice();

            }
        });


    }

    private void callCancelOrderWebservice() {

        if (spinnerCancelReason.getSelectedItemPosition() != 0) {

            if (comments.getText().length() != 0) {
                progressDialogHandler.show();
                String cancel_reason_url = getResources().getString(R.string.webservice_base_url) + "/cancel_order";

                Ion.with(getActivity())
                        .load(cancel_reason_url)
                        .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                        .setBodyParameter("id", orderid)
                        .setBodyParameter("reason", spinnerCancelReason.getSelectedItem().toString())
                        .setBodyParameter("comments", comments.getText().toString())


                        .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if (result != null) {
                            String error = result.get("error").getAsString();
                            if (error.contains("false")) {
                                progressDialogHandler.hide();


                                AndroidUtils.showErrorLog(getActivity(), result.toString());


                                Intent go_to_trackorder = new Intent(getActivity(), Order_detail.class);
                                go_to_trackorder.putExtra("class_name", getActivity().getClass().getName());
                                go_to_trackorder.putExtra("result", result.toString());
                                startActivity(go_to_trackorder);


                            } else {
                                progressDialogHandler.hide();
                            }


                        } else {
                            progressDialogHandler.hide();

                            Log.e("result", result.toString());
                        }


//               JsonObject res result.get("otp_id").getAsString();


                    }
                });


            } else {

                AndroidUtils.showSnackBar(container, "Please Enter Comments");
            }


        } else {
            AndroidUtils.showSnackBar(container, "Select Cancel Reason");
        }


    }
}