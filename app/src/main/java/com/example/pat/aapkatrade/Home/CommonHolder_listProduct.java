package com.example.pat.aapkatrade.Home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC17 on 24-Apr-17.
 */

class CommonHolder_listProduct extends RecyclerView.ViewHolder {


    TextView tvProductName,tvproductprize;
    ImageView product_imageview,product_addcard,product_description;
    RatingBar ratingbar;
    CardView cardview;
    RelativeLayout rl_cartview;

    View view_grid_left,view_grid_right;

    public CommonHolder_listProduct(View itemView) {
        super(itemView);
        product_addcard=(ImageView)itemView.findViewById(R.id.add_cart);
        product_description=(ImageView)itemView.findViewById(R.id.img_product_information);
        product_imageview=(ImageView)itemView.findViewById(R.id.img_product_image_list) ;
        rl_cartview=(RelativeLayout)itemView.findViewById(R.id.relativelayout_cart);
        cardview=(CardView)itemView.findViewById(R.id.cardview) ;
        tvProductName = (TextView) itemView.findViewById(R.id.tv_productlist);
        tvproductprize=(TextView) itemView.findViewById(R.id.tv_productprize);
        view_grid_left= itemView.findViewById(R.id.view_left);
        view_grid_right= itemView.findViewById(R.id.view_right);


    }
}
