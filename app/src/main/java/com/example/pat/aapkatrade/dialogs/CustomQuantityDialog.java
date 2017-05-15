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

import com.example.pat.aapkatrade.Home.cart.CartData;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.interfaces.CommonInterface;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CustomQuantityDialog extends DialogFragment
{
    EditText etManualQuantity;
    TextView okTv, CancelTv,tvsubtotal,textView_qty;
    public static CommonInterface commonInterface;
    Context context;
    int pos;
    String price;



    public CustomQuantityDialog(Context context)
    {

    }

    public CustomQuantityDialog(Context context,TextView textView,int position,String price,TextView textView_qty)
    {
        this.context = context;
        this.tvsubtotal = textView;
        this.textView_qty = textView_qty;
        this.pos = position;
        this.price = price;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.layout_more_quantity, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initView(v);


        return v;
    }


    private void initView(View v)
    {
        etManualQuantity = (EditText) v.findViewById(R.id.editText);
        okTv = (TextView) v.findViewById(R.id.okDialog);
        CancelTv = (TextView) v.findViewById(R.id.cancelDialog);

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etManualQuantity.getText().toString().trim().equals(""))
                {
                    etManualQuantity.setError("Please Enter Number");
                }
                else {

                    if (Integer.parseInt(etManualQuantity.getText().toString().trim()) > 0)
                    {
                        textView_qty.setText(etManualQuantity.getText().toString().trim());
                        double cart_price = Double.valueOf(price) *Integer.valueOf(etManualQuantity.getText().toString().trim());
                        System.out.println("cart_price----------"+cart_price);
                        tvsubtotal.setText(context.getResources().getText(R.string.Rs)+String.valueOf(cart_price));
                        commonInterface.getData(Integer.parseInt(etManualQuantity.getText().toString().trim()));

                        dismiss();
                    }
                    else
                    {
                    }
                    AndroidUtils.showErrorLog(getActivity(), "ok button");

                }




            }
        });

        CancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AndroidUtils.showErrorLog(getActivity(), "cancel button");
                dismiss();
            }
        });

        etManualQuantity.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if (etManualQuantity.getText().toString().trim().equals(""))
                {
                    etManualQuantity.setError("Please Enter Number");
                }
                else
                {
                    if (s.length() != 0)
                    {
                        if (Integer.parseInt(s.toString()) == 0)
                        {
                            etManualQuantity.setError("Please Enter Valid Quantity");
                        }
                        else
                        {
                            //tvQuantity.setText(s);
                        }
                    }
                }
            }
        });


    }


}
