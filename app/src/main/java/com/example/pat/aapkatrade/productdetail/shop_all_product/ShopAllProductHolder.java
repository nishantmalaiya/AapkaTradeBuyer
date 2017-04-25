package com.example.pat.aapkatrade.productdetail.shop_all_product;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 4/21/2017.
 */

public class ShopAllProductHolder extends RecyclerView.ViewHolder {


    public View view;
    public TextView tvProductName, tvProductUnit, tvProductPrice;
    public ImageView imgProduct;
    public Button buttonAddtoCart;
    public RelativeLayout relativeViewPlus, relativeViewMinus;


    public ShopAllProductHolder(View itemView) {
        super(itemView);
        imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
        tvProductUnit = (TextView) itemView.findViewById(R.id.tvProductUnit);
        tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
        buttonAddtoCart = (Button) itemView.findViewById(R.id.buttonAddtoCart);
        relativeViewPlus = (RelativeLayout) itemView.findViewById(R.id.relativeViewPlus);
        relativeViewMinus = (RelativeLayout) itemView.findViewById(R.id.relativeViewMinus);
        view = itemView;
    }
}