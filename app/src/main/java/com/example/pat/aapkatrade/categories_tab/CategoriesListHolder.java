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

    RelativeLayout relativeLayout1, linearlayoutMap;
    TextView tvProductName, tvProductCategoryname,distance,tvProductDestination;
    ImageView productimage;




    public CategoriesListHolder(View itemView)
    {

        super(itemView);

        relativeLayout1 = (RelativeLayout) itemView.findViewById(R.id.relativeProductDetail) ;

        linearlayoutMap = (RelativeLayout) itemView.findViewById(R.id.linearlayoutMap);

        tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);

        distance=(TextView)itemView.findViewById(R.id.product_distance) ;

        tvProductDestination = (TextView) itemView.findViewById(R.id.tvProductDestination);

        productimage = (ImageView) itemView.findViewById(R.id.productImage);

        tvProductCategoryname = (TextView) itemView.findViewById(R.id.tvProductCategoryname);


        view = itemView;
    }
}