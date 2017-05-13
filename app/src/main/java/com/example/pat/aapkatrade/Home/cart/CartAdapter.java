package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.dialogs.CustomQuantityDialog;
import com.example.pat.aapkatrade.general.AppConfig;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.interfaces.CommonInterface;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PPC16 on 4/25/2017.
 */


public class CartAdapter extends RecyclerView.Adapter<CartHolder> implements View.OnClickListener
{

    private final LayoutInflater inflater;
    private List<CartData> itemList;
    private Context context;
    private TextView textViewQuantity;
    private DroppyMenuPopup droppyMenu;
    private LinearLayout linearLayoutQuantity;
    private EditText editText;
    AppSharedPreference appSharedPreference;
    private ProgressBarHandler progressBarHandler;
    public static ArrayList<CartData>  place_order = new ArrayList<>();



    public CartAdapter(Context context, List<CartData> itemList)
    {

        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        appSharedPreference = new AppSharedPreference(context);
        progressBarHandler = new ProgressBarHandler(context);
        System.out.println("itemlist_cartdata-----------------"+itemList.size());

    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartHolder(inflater.inflate(R.layout.row_my_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position)
    {

        linearLayoutQuantity = holder.dropdown_ll;

        textViewQuantity = holder.textView64;

        Ion.with(holder.productimage)
                .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                .load(itemList.get(position).product_image);

        //linearLayoutQuantity.setOnClickListener(this);
        place_order.add(new CartData(itemList.get(position).id,itemList.get(position).productName,itemList.get(position).quantity,itemList.get(position).price,itemList.get(position).product_image,itemList.get(position).product_id,itemList.get(position).subtotal_price));

        textViewQuantity.setText(itemList.get(position).quantity);

        holder.tvProductName.setText(itemList.get(position).productName);

        holder.tvProductPrice.setText(context.getResources().getText(R.string.Rs)+itemList.get(position).price);

        holder.tvProductSubtotalPrice.setText(context.getResources().getText(R.string.Rs)+itemList.get(position).subtotal_price);

        System.out.println("itemlist-------------"+itemList.get(position).product_image);

        final DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(context, linearLayoutQuantity);
        droppyBuilder.addMenuItem(new DroppyMenuItem("1"))
                .addMenuItem(new DroppyMenuItem("2"))
                .addMenuItem(new DroppyMenuItem("3"))
                .addMenuItem(new DroppyMenuItem("4"))
                .addMenuItem(new DroppyMenuItem("5"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("More"));

        CustomQuantityDialog.commonInterface = new CommonInterface() {
            @Override
            public Object getData(Object object) {

                textViewQuantity.setText(object.toString());

                return null;
            }
        };


        droppyBuilder.setOnClick(new DroppyClickCallbackInterface()
        {

            @Override
            public void call(View v, int id)
            {
                double cart_price= 0;

                switch (id)
                {
                    case 0:
                        itemList.get(position).setQuantity("1");
                        holder.textView64.setText(itemList.get(position).quantity);
                        cart_price = Double.valueOf(itemList.get(position).price) *1;
                       /// itemList.set(position, new CartData(itemList.get(position).id,itemList.get(position).productName,"1",cart_price,itemList.get(position).product_image,itemList.get(position).product_id));
                        holder.tvProductSubtotalPrice.setText(context.getResources().getText(R.string.Rs)+String.valueOf(cart_price));
                        place_order.add(position,new CartData(itemList.get(position).id,itemList.get(position).productName,"1",String.valueOf(cart_price),itemList.get(position).product_image,itemList.get(position).product_id,itemList.get(position).subtotal_price));
                        callwebservice__update_cart(itemList.get(position).id,position,"1");

                        break;
                    case 1:
                        itemList.get(position).setQuantity("2");
                        holder.textView64.setText(itemList.get(position).quantity);
                        cart_price = Double.valueOf(itemList.get(position).price) *2;
                        System.out.println("cart_price----------"+cart_price);
                        holder.tvProductSubtotalPrice.setText(context.getResources().getText(R.string.Rs)+String.valueOf(cart_price));
                        place_order.add(position,new CartData(itemList.get(position).id,itemList.get(position).productName,"2",String.valueOf(cart_price),itemList.get(position).product_image,itemList.get(position).product_id,itemList.get(position).subtotal_price));
                        callwebservice__update_cart(itemList.get(position).id,position,"2");
                        break;
                    case 2:
                        itemList.get(position).setQuantity("3");
                        holder.textView64.setText(itemList.get(position).quantity);
                        cart_price = Double.valueOf(itemList.get(position).price) *3;
                        holder.tvProductSubtotalPrice.setText(context.getResources().getText(R.string.Rs)+String.valueOf(cart_price));
                        place_order.add(position,new CartData(itemList.get(position).id,itemList.get(position).productName,"3",String.valueOf(cart_price),itemList.get(position).product_image,itemList.get(position).product_id,itemList.get(position).subtotal_price));
                        callwebservice__update_cart(itemList.get(position).id,position,"3");
                        break;

                    case 3:
                        itemList.get(position).setQuantity("4");
                        holder.textView64.setText(itemList.get(position).quantity);
                        cart_price = Double.valueOf(itemList.get(position).price) *4;
                        holder.tvProductSubtotalPrice.setText(context.getResources().getText(R.string.Rs)+String.valueOf(cart_price));

                        place_order.add(position,new CartData(itemList.get(position).id,itemList.get(position).productName,"4",String.valueOf(cart_price),itemList.get(position).product_image,itemList.get(position).product_id,itemList.get(position).subtotal_price));

                        callwebservice__update_cart(itemList.get(position).id,position,"4");
                        break;
                    case 4:
                        itemList.get(position).setQuantity("5");
                        holder.textView64.setText(itemList.get(position).quantity);
                        cart_price = Double.valueOf(itemList.get(position).price) *5;
                        holder.tvProductSubtotalPrice.setText(context.getResources().getText(R.string.Rs)+String.valueOf(cart_price));

                        place_order.add(position,new CartData(itemList.get(position).id,itemList.get(position).productName,"5",String.valueOf(cart_price),itemList.get(position).product_image,itemList.get(position).product_id,itemList.get(position).subtotal_price));
                        callwebservice__update_cart(itemList.get(position).id,position,"5");

                        break;
                    case 5:
                        showPopup("Quantity", position);
                        break;

                }
            }
        });

        droppyMenu = droppyBuilder.build();

        holder.buttonAddtoCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String id = itemList.get(position).id;
                callwebservice__delete_cart(id,position);

            }
        });


    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onClick(View v)
    {

    }

    public void showPopup(String description, final int pos)
    {

        CustomQuantityDialog customQuantityDialog = new CustomQuantityDialog(context);
        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        customQuantityDialog.show(fm, "Quantity");



//        boolean wrapInScrollView = true;
//        MaterialDialog dialog = new MaterialDialog.Builder(context)
//                .title("Quantity")
//                .customView(R.layout.layout_more_quantity, wrapInScrollView)
//                .positiveText("ok")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        if (editText.getText().length() <= 0) {
//                            showMessage("Nothing done");
//                        } else {
//                            showMessage(editText.getText().toString());
//                            itemList.get(pos).setQuantity(editText.getText().toString());
//                            textViewQuantity.setText(editText.getText().toString());
//                            notifyDataSetChanged();
//                        }
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//        editText = (EditText) dialog.findViewById(R.id.editText);

    }

    private void showMessage(String s) {
        AndroidUtils.showErrorLog(context, s, Toast.LENGTH_SHORT);
    }


    private void callwebservice__delete_cart(String product_id, final int position)
    {

        progressBarHandler.show();

        String login_url = context.getResources().getString(R.string.webservice_base_url) + "/cart_remove";

        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        System.out.println("devece_id------------"+android_id);

        Ion.with(context)
                .load(login_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", product_id)
                .setBodyParameter("device_id", android_id)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        if (result.toString().equals("null")){

                            Toast.makeText(context,"Server is not responding please try ",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            System.out.println("result--------------"+ result);
                            JsonObject jsonObject = result.getAsJsonObject("result");
                            String total_amount = jsonObject.get("total_amount").getAsString();
                            String cart_count = jsonObject.get("total_qty").getAsString();
                            if (cart_count.equals("0"))
                            {

                                MyCartActivity.cardviewProductDeatails.setVisibility(View.INVISIBLE);
                                MyCartActivity.cardBottom.setVisibility(View.INVISIBLE);

                            }
                            else
                             {

                                 MyCartActivity.cardviewProductDeatails.setVisibility(View.VISIBLE);
                                 MyCartActivity.cardBottom.setVisibility(View.VISIBLE);

                            }
                            appSharedPreference.setSharedPrefInt(SharedPreferenceConstants.CART_COUNT.toString(), Integer.valueOf(cart_count));

                            MyCartActivity.tvPriceItemsHeading.setText("Price("+cart_count+"items)");
                            MyCartActivity.tvPriceItems.setText(context.getResources().getText(R.string.Rs)+total_amount);
                            MyCartActivity.tvAmountPayable.setText(context.getResources().getText(R.string.Rs)+total_amount);
                            MyCartActivity.tvLastPayableAmount.setText(context.getResources().getText(R.string.Rs)+total_amount);

                            place_order.remove(position);
                            itemList.remove(position);
                            notifyDataSetChanged();

                            progressBarHandler.hide();

                        }

                    }
                });

    }

    private void callwebservice__update_cart(String product_id, final int position,String quantity)
    {

        progressBarHandler.show();

        String login_url = context.getResources().getString(R.string.webservice_base_url) + "/cart_update";

        Ion.with(context)
                .load(login_url)
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("id", product_id)
                .setBodyParameter("quantity", quantity)
                .setBodyParameter("device_id", AppConfig.getCurrentDeviceId(context))

                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
                    {

                        JsonObject jsonresult = result.getAsJsonObject("result");

                        String total_amount = jsonresult.get("total_amount").getAsString();
                        String cart_count = jsonresult.get("total_qty").getAsString();
                        appSharedPreference.setSharedPrefInt("cart_count", Integer.valueOf(cart_count));

                        MyCartActivity.tvPriceItemsHeading.setText("Price("+cart_count+"items)");
                        MyCartActivity.tvPriceItems.setText(context.getResources().getText(R.string.Rs)+total_amount);
                        MyCartActivity.tvAmountPayable.setText(context.getResources().getText(R.string.Rs)+total_amount);
                        MyCartActivity.tvLastPayableAmount.setText(context.getResources().getText(R.string.Rs)+total_amount);

                        System.out.println("cart updated "+result.toString());
                        //notifyDataSetChanged();
                        progressBarHandler.hide();


                    }
                });
    }



}

