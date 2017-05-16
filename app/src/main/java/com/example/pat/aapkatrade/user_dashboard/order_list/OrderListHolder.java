package com.example.pat.aapkatrade.user_dashboard.order_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListHolder extends RecyclerView.ViewHolder {

    View view;
    TextView  tvOrderDate, tvOrderPrize,productName;
    Button btn_trackorder;

    CircleImageView productImage;
    ImageView img_order_detail;

    public OrderListHolder(View itemView) {
        super(itemView);

        productName=(TextView)itemView.findViewById(R.id.productName);

        img_order_detail=(ImageView)itemView.findViewById(R.id.img_order_detail);


        tvOrderDate = (TextView) itemView.findViewById(R.id.tvOrderDate);
        tvOrderPrize = (TextView) itemView.findViewById(R.id.tvOrderPrize);

        btn_trackorder = (Button) itemView.findViewById(R.id.btn_trackorder);

        productImage=(CircleImageView)itemView.findViewById(R.id.circleImage);
        view = itemView;
    }
}
