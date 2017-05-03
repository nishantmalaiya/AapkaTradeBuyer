package com.example.pat.aapkatrade.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class ServiceEnquiry extends DialogFragment {


    private EditText etFullName, quantity, price, mobile, email, etEndDate, etStartDate, description;
    private TextInputLayout input_layout_start_date, input_layout_end_date;
    private int isStartDate;
    private String date, productName, categoryName;
    private Context context;
    private TextView submit, tvCategoryName, tvProductname;
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar, startDateLayout, endDateLayout;
    private ImageView openStartDateCal, openEndDateCal;
    public ServiceEnquiry() {
        super();
       // this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_enquiry, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        context=getActivity();
        initView(v);


        return v;
    }


    private void initView(View v) {

        dialogue_toolbar = (RelativeLayout) v.findViewById(R.id.dialogue_toolbar);

        imgCLose = (Button) v.findViewById(R.id.imgCLose);
        etFullName = (EditText) v.findViewById(R.id.etFullName);
        quantity = (EditText) v.findViewById(R.id.et_layout_quantity);
        price = (EditText) v.findViewById(R.id.et_layout_price);
        mobile = (EditText) v.findViewById(R.id.et_layout_mobile);

        submit = (TextView) v.findViewById(R.id.buttonSubmit);
        AndroidUtils.setBackgroundSolid(submit, getActivity(), R.color.orange, 8, GradientDrawable.OVAL);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar,getActivity(), R.color.green, 15, GradientDrawable.RECTANGLE);

        tvProductname = (TextView) v.findViewById(R.id.tvProductname);
        tvCategoryName = (TextView) v.findViewById(R.id.tvCategoryName);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation.validateEdittext(etFullName)) {


                } else {

                    etFullName.setError("!Invalid Fullname");


                }


            }


        });


        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


}
