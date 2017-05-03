package com.example.pat.aapkatrade.categories_tab;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC16 on 16-Jan-17.
 */

public class CategoriesListHolder extends RecyclerView.ViewHolder
{

    View view;
    LinearLayout linearlayout1;
    RelativeLayout linearlayoutMap;
    TextView tvProductName, tvProductPrice,tvProductCrossPrice, tvProductCategoryname,distance;
    ImageView productimage;




    public CategoriesListHolder(View itemView)
    {

        super(itemView);

        linearlayout1 = (LinearLayout) itemView.findViewById(R.id.linearlayout1) ;

        linearlayoutMap = (RelativeLayout) itemView.findViewById(R.id.linearlayoutMap);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        distance=(TextView)itemView.findViewById(R.id.product_distance) ;



        productimage = (ImageView) itemView.findViewById(R.id.productImage);

        tvProductCategoryname = (TextView) itemView.findViewById(R.id.tvProductCategoryname);


        view = itemView;
    }
}