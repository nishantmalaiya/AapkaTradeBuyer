package com.example.pat.aapkatrade.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.cart.CartAdapter;
import com.example.pat.aapkatrade.Home.cart.MyCartActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppConfig;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.interfaces.CommonInterface;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CustomQuantityDialog extends DialogFragment {

    EditText etManualQuantity;
    TextView okTv, CancelTv, tvsubtotal, textView_qty;
    public static CommonInterface commonInterface;
    Context context;
    int pos;
    String price;
    ProgressBarHandler progressBarHandler;

    AppSharedPreference appSharedPreference;


    public CustomQuantityDialog(Context context) {

    }

    public CustomQuantityDialog(Context context, TextView textView, int position, String price, TextView textView_qty) {
        this.context = context;
        this.tvsubtotal = textView;
        this.textView_qty = textView_qty;
        this.pos = position;
        this.price = price;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.layout_more_quantity, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        initView(v);

        return v;
    }


    private void initView(View v) {
        etManualQuantity = (EditText) v.findViewById(R.id.editText);
        okTv = (TextView) v.findViewById(R.id.okDialog);
        CancelTv = (TextView) v.findViewById(R.id.cancelDialog);

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etManualQuantity.getText().toString().trim().equals("")) {
                    etManualQuantity.setError("Please Enter Number");
                } else {
                    if (Integer.parseInt(etManualQuantity.getText().toString().trim()) > 0) {

                        progressBarHandler = new ProgressBarHandler(context);
                        appSharedPreference = new AppSharedPreference(context);

                        callWebServiceUpdateCart(CartAdapter.itemList.get(pos).id, 1, etManualQuantity.getText().toString(), CartAdapter.itemList.get(pos).product_id);
                        dismiss();
                    } else {


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

                if (etManualQuantity.getText().toString().trim().equals("")) {
                    etManualQuantity.setError("Please Enter Number");
                } else {
                    if (s.length() != 0) {
                        if (Integer.parseInt(s.toString()) == 0) {
                            etManualQuantity.setError("Please Enter Valid Quantity");
                        } else {
                            //tvQuantity.setText(s);
                        }
                    }
                }
            }
        });


    }


    public void callWebServiceUpdateCart(String id, final int position, String quantity, String product_id) {

        progressBarHandler.show();

        String login_url = context.getResources().getString(R.string.webservice_base_url) + "/cart_update";

        String user_id = appSharedPreference.getSharedPref(SharedPreferenceConstants.USER_ID.toString(), "notlogin");
        if (user_id.equals("notlogin")) {
            user_id = "";
        }

        Ion.with(context)
                .load(login_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", id)
                .setBodyParameter("product_id", product_id)
                .setBodyParameter("quantity", quantity)
                .setBodyParameter("user_id", user_id)
                .setBodyParameter("device_id", AppConfig.getCurrentDeviceId(context))

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        System.out.println("result new update--------------" + result);

                        if (result != null) {
                            String error_message = result.get("error").getAsString();

                            if (error_message.equals("false")) {
                                String message = result.get("message").getAsString();

                                if (message.equals("Product quantity exceeded")) {
                                    progressBarHandler.hide();
                                    Toast.makeText(context, " Product quantity exceeded", Toast.LENGTH_SHORT).show();

                                } else if (message.equals("Failed to update cart")) {

                                    progressBarHandler.hide();
                                    Toast.makeText(context, "Failed to update cart", Toast.LENGTH_SHORT).show();


                                } else if (message.equals("Invalid Device ID!")) {

                                    progressBarHandler.hide();
                                    Toast.makeText(context, "Invalid Device ID!", Toast.LENGTH_SHORT).show();


                                } else {
                                    JsonObject jsonresult = result.getAsJsonObject("result");

                                    String total_amount = jsonresult.get("total_amount").getAsString();
                                    String cart_count = jsonresult.get("total_qty").getAsString();

                                    appSharedPreference.setSharedPrefInt(SharedPreferenceConstants.CART_COUNT.toString(), Integer.valueOf(cart_count));

                                    HomeActivity.tvCartCount.setText(String.valueOf(appSharedPreference.getSharedPrefInt(SharedPreferenceConstants.CART_COUNT.toString(), 0)));

                                    MyCartActivity.tvPriceItemsHeading.setText("Price(" + cart_count + "items)");
                                    MyCartActivity.tvPriceItems.setText(context.getResources().getText(R.string.Rs) + total_amount);
                                    MyCartActivity.tvAmountPayable.setText(context.getResources().getText(R.string.Rs) + total_amount);
                                    MyCartActivity.tvLastPayableAmount.setText(context.getResources().getText(R.string.Rs) + total_amount);

                                    System.out.println("cart updated " + result.toString());

                                    textView_qty.setText(etManualQuantity.getText().toString().trim());
                                    double cart_price = Double.valueOf(price) * Integer.valueOf(etManualQuantity.getText().toString().trim());
                                    System.out.println("cart_price dailog----------" + cart_price);
                                    tvsubtotal.setText(context.getResources().getText(R.string.Rs) + String.valueOf(cart_price));
                                    commonInterface.getData(Integer.parseInt(etManualQuantity.getText().toString().trim()));
                                    //notifyDataSetChanged();
                                    progressBarHandler.hide();
                                }
                            } else {
                                progressBarHandler.hide();
                                Toast.makeText(context, "Server is not responding please try ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressBarHandler.hide();
                            Toast.makeText(context, "Server is not responding please try ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        Ion.getDefault(getActivity()).cancelAll(getActivity());
        progressBarHandler.hide();
    }
}
