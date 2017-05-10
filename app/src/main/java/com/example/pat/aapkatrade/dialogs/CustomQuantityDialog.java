package com.example.pat.aapkatrade.dialogs;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CustomQuantityDialog extends DialogFragment {

    EditText etManualQuantity;
    TextView okTv,CancelTv;
    public CustomQuantityDialog(Context context) {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.layout_more_quantity, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initView(v);


        return v;
    }


    private void initView(View v) {

        etManualQuantity = (EditText) v.findViewById(R.id.editText);
        okTv = (TextView) v.findViewById(R.id.okDialog);
        CancelTv = (TextView) v.findViewById(R.id.cancelDialog);
        //disableButton(okTv);
       // disableButton(CancelTv);
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(okTv.isEnabled()){
                    if(Integer.parseInt(etManualQuantity.getText().toString()) > 0){
                        enableButton(okTv);

                    }
                } else {
                    enableButton(okTv);
                }
                AndroidUtils.showErrorLog(getActivity(), "ok button");
               // dismiss();
            }
        });

        CancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // enableButton(CancelTv);
                //AndroidUtils.showErrorLog(getActivity(), "cancel button");
              //dismiss();
            }
        });

        etManualQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Integer.parseInt(s.toString()) == 0){
                    etManualQuantity.setError("Please Enter Valid Quantity");
                } else {
                    //dismiss();
                    //tvQuantity.setText(s);
//                    dialog.dismiss();
                }
            }
        });














    }

    private void enableButton(TextView textView){
        if(textView!=null) {
            AndroidUtils.setBackgroundSolid(textView, getActivity(), R.color.green, 10, GradientDrawable.RECTANGLE);
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            textView.setEnabled(true);
        }
    }

    private void disableButton(TextView textView){
        if(textView!=null) {
            AndroidUtils.setBackgroundSolid(textView, getActivity(), R.color.white, 10, GradientDrawable.RECTANGLE);
            textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.green));
            textView.setEnabled(false);
        }
    }





}
