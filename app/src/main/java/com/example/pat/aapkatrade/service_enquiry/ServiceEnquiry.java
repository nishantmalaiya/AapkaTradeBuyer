package com.example.pat.aapkatrade.service_enquiry;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class ServiceEnquiry extends Dialog
{


    private EditText firstName, quantity, price, mobile, email, etEndDate, etStartDate, description;
    private TextInputLayout input_layout_start_date, input_layout_end_date;
    private int isStartDate;
    private String date, productName, categoryName;
    private Context context;
    private TextView submit, tvCategoryName, tvProductname;
    private Button imgCLose;
    private RelativeLayout dialogue_toolbar, startDateLayout, endDateLayout;
    private ImageView openStartDateCal, openEndDateCal;



    public ServiceEnquiry(Context context) {
        super(context);
        this.context = context;
        final TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.style.NewDialog, outValue, true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getWindow()!=null)
           getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);



        setContentView(R.layout.fragment_service_enquiry);


        initView();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }

    private void initView()
    {
        dialogue_toolbar = (RelativeLayout) findViewById(R.id.dialogue_toolbar);

        imgCLose = (Button) findViewById(R.id.imgCLose);
        firstName = (EditText) findViewById(R.id.etFirstName);
        quantity = (EditText) findViewById(R.id.et_layout_quantity);
        price = (EditText) findViewById(R.id.et_layout_price);
        mobile = (EditText) findViewById(R.id.et_layout_mobile);

        submit = (TextView) findViewById(R.id.buttonSubmit);
        AndroidUtils.setBackgroundSolid(submit, context, R.color.orange, 8, GradientDrawable.OVAL);
        AndroidUtils.setBackgroundSolid(dialogue_toolbar, context, R.color.green, 15, GradientDrawable.RECTANGLE);

        tvProductname = (TextView) findViewById(R.id.tvProductname);
        tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
    }



}
