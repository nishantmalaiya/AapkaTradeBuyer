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
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by PPC16 on 4/25/2017.
 */


public class  CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener
{

    private final LayoutInflater inflater;
    List<CartData> itemList;
    private Context context;
    CartHolder viewHolder;
    CartHolder homeHolder;
    TextView  textViewQuantity;
    DroppyMenuPopup droppyMenu;
    LinearLayout linearLayoutQuantity;
    EditText editText;


    public CartAdapter(Context context, List<CartData> itemList)
    {
        this.itemList = itemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = inflater.inflate(R.layout.row_my_cart, parent, false);
        viewHolder = new CartHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
         homeHolder = (CartHolder) holder;

        linearLayoutQuantity=homeHolder.dropdown_ll;

        textViewQuantity=homeHolder.textView64;

        textViewQuantity.setText(itemList.get(position).quantity.toString());

        //linearLayoutQuantity.setOnClickListener(this);

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
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());
                        notifyDataSetChanged();
                        break;
                    case 1:
                        itemList.get(position).setQuantity("2");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());
                        notifyDataSetChanged();
                        break;
                    case 2:
                        itemList.get(position).setQuantity("3");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());
                        notifyDataSetChanged();

                        break;
                    case 3:
                        itemList.get(position).setQuantity("4");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());
                        notifyDataSetChanged();
                        break;
                    case 4:
                        itemList.get(position).setQuantity("5");
                        homeHolder.textView64.setText(itemList.get(position).quantity.toString());
                        notifyDataSetChanged();
                        break;
                    case 5:
                        showPopup("Quantity",position);
                        break;

                }
            }
        });

        droppyMenu = droppyBuilder.build();

      /*  List<String> count = new ArrayList<String>();
        count.add("1");
        count.add("2");
        count.add("3");
        count.add("4");
        count.add("5");
        count.add("6");
        count.add("7");
        count.add("8");
        count.add("9");
        count.add("10");
        count.add("More");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,R.layout.row_spinner,count );

        dataAdapter.setDropDownViewResource(R.layout.row_spinner);
        homeHolder.spinner.setAdapter(dataAdapter);
*/





    }


    @Override
    public int getItemCount()
    {
        return 6;
    }

    @Override
    public void onClick(View v)
    {

    }

    public void showPopup(String description, final int pos)
    {

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

    private void showMessage(String s)
    {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }





}

