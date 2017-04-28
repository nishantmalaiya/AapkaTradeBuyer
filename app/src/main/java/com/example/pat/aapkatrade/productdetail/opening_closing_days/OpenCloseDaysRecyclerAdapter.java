package com.example.pat.aapkatrade.productdetail.opening_closing_days;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;

import java.util.ArrayList;

/**
 * Created by PPC16 on 4/10/2017.
 */

public class OpenCloseDaysRecyclerAdapter extends RecyclerView.Adapter<OpenCloseDaysViewHolder> {


    private Context context;
    private ArrayList<OpenCloseShopData> openCloseShopDataArrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private int colorArray[] = {R.color.open_shop_day_color1, R.color.open_shop_day_color2, R.color.open_shop_day_color3, R.color.open_shop_day_color4, R.color.open_shop_day_color5, R.color.open_shop_day_color6, R.color.open_shop_day_color7};

    public OpenCloseDaysRecyclerAdapter(Context context, ArrayList<OpenCloseShopData> openCloseShopDataArrayList) {
        this.openCloseShopDataArrayList = openCloseShopDataArrayList;
        this.context = context;
    }


    @Override
    public OpenCloseDaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OpenCloseDaysViewHolder(inflater.inflate(R.layout.row_open_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(OpenCloseDaysViewHolder holder, final int position) {
        AndroidUtils.setBackgroundSolid(holder.relativeOpenShop, context, colorArray[position], 20, GradientDrawable.RECTANGLE);

        holder.tvDayName.setText(openCloseShopDataArrayList.get(position).dayName.substring(0,2));
        holder.tvOpeningTime.setText(openCloseShopDataArrayList.get(position).openingTime);
        holder.tvClosingTime.setText(openCloseShopDataArrayList.get(position).closingTime);
    }

    @Override
    public int getItemCount() {
        return openCloseShopDataArrayList.size();
    }
}
