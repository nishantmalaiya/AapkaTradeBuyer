package com.example.pat.aapkatrade.Home.cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PPC16 on 4/25/2017.
 */


public class CartAdapter extends RecyclerView.Adapter<CartHolder> implements View.OnClickListener {

    private final LayoutInflater inflater;
    private List<CartData> itemList;
    private Context context;
    private TextView textViewQuantity;
    private DroppyMenuPopup droppyMenu;
    private LinearLayout linearLayoutQuantity;
    private EditText editText;


    public CartAdapter(Context context, List<CartData> itemList) {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartHolder(inflater.inflate(R.layout.row_my_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {

        linearLayoutQuantity = holder.dropdown_ll;

        textViewQuantity = holder.textView64;

        textViewQuantity.setText(itemList.get(position).quantity);
        final DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(context, linearLayoutQuantity);
        droppyBuilder.addMenuItem(new DroppyMenuItem("1"))
                .addMenuItem(new DroppyMenuItem("2"))
                .addMenuItem(new DroppyMenuItem("3"))
                .addMenuItem(new DroppyMenuItem("4"))
                .addMenuItem(new DroppyMenuItem("5"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("More"));
        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                switch (id) {
                    case 0:
                        itemList.get(position).setQuantity("1");
                        holder.textView64.setText(itemList.get(position).quantity);
                        notifyDataSetChanged();
                        break;
                    case 1:
                        itemList.get(position).setQuantity("2");
                        holder.textView64.setText(itemList.get(position).quantity);
                        notifyDataSetChanged();
                        break;
                    case 2:
                        itemList.get(position).setQuantity("3");
                        holder.textView64.setText(itemList.get(position).quantity);
                        notifyDataSetChanged();

                        break;
                    case 3:
                        itemList.get(position).setQuantity("4");
                        holder.textView64.setText(itemList.get(position).quantity);
                        notifyDataSetChanged();
                        break;
                    case 4:
                        itemList.get(position).setQuantity("5");
                        holder.textView64.setText(itemList.get(position).quantity);
                        notifyDataSetChanged();
                        break;
                    case 5:
                        showPopup("Quantity", position);
                        break;

                }
            }
        });

        droppyMenu = droppyBuilder.build();
    }


    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public void onClick(View v) {

    }

    public void showPopup(String description, final int pos) {

        boolean wrapInScrollView = true;
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("Quantity")
                .customView(R.layout.layout_more_quantity, wrapInScrollView)
                .positiveText("ok")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (editText.getText().length() <= 0) {
                            showMessage("Nothing done");
                        } else {
                            showMessage(editText.getText().toString());
                            itemList.get(pos).setQuantity(editText.getText().toString());
                            textViewQuantity.setText(editText.getText().toString());
                            notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                })
                .show();
        editText = (EditText) dialog.findViewById(R.id.editText);

    }

    private void showMessage(String s) {
        AndroidUtils.showErrorLog(context, s, Toast.LENGTH_SHORT);
    }


}

