package com.example.pat.aapkatrade.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * A simple {@link Fragment} subclass.
 */
public class Track_order_dialog extends DialogFragment {
    ImageView dialog_close;
    EditText tracking_id;
    Button validate_order_id;

    public Track_order_dialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_track_order_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        initview(v);


        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        validate_order_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(Validation.validateEdittext(tracking_id))
             {
                 call_Validate_order_webservice();
             }
             else{AndroidUtils.showSnackBar(container,"!Enter correct Order id");}

            }
        });

        return v;
    }

    private void call_Validate_order_webservice() {
        String track_order_url=getString(R.string.webservice_base_url)+"/track_order";

//                .setBodyParameter("ORDER_ID ", tracking_id.getText().toString().trim())
//                .setBodyParameter("client_id", App_config.getCurrentDeviceId(getActivity()))
        Ion.with(getActivity())
                .load(track_order_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization","xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("ORDER_ID","AT210417060350")
                .setBodyParameter("client_id","12")
              .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {


                Log.e("result",result.toString());
//               JsonObject res result.get("otp_id").getAsString();


            }
        });








    }

    private void initview(View v) {

        dialog_close = (ImageView) v.findViewById(R.id.dialog_close);

        tracking_id=(EditText)v.findViewById(R.id.et_orderid) ;
        validate_order_id=(Button)v.findViewById(R.id.btn_trackorder);


    }

}
