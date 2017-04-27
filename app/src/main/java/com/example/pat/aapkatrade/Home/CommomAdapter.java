package com.example.pat.aapkatrade.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Tabletsize;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.productdetail.ShopDetailActivity;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Netforce on 7/25/2016.
 */
public class CommomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<CommomData> commomDatas;
    private String arrangementtype, categorytype;
    private View v;
    private String TAG;
    float initialX, initialY;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    public CommomAdapter(Context context, ArrayList<CommomData> commomDatas, String arrangementtype, String categorytype) {
        this.context = context;
        this.commomDatas = commomDatas;
        this.arrangementtype = arrangementtype;
        this.categorytype = categorytype;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        TAG = context.getClass().getSimpleName();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        CommomHolder viewHolder1;
        CommonHolder_grid viewHolder2;
        CommonHolder_listProduct viewHolder_listProduct;

        //RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (arrangementtype == "list") {
            v = inflater.inflate(R.layout.row_dashboard, parent, false);
            viewHolder2 = new CommonHolder_grid(v);
            return viewHolder2;


        } else if (arrangementtype == "list_product") {
            v = inflater.inflate(R.layout.row_dashboard_productlist, parent, false);
            viewHolder_listProduct = new CommonHolder_listProduct(v);
            return viewHolder_listProduct;
        } else {
            v = inflater.inflate(R.layout.row_dashboard_grid, parent, false);
            viewHolder1 = new CommomHolder(v);
            return viewHolder1;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, final int position) {
        if (arrangementtype == "list")

        {
            final CommomHolder holder = new CommomHolder(v);


            if (Tabletsize.isTablet(context)) {
                Picasso.with(context)
                        .load(commomDatas.get(position).imageurl)
                        .error(R.drawable.banner)
                        .placeholder(R.drawable.default_noimage)
                        .error(R.drawable.default_noimage)
                        .into(holder.cimageview);
                String product_imageurl = commomDatas.get(position).imageurl.replace("small", "large");

                Ion.with(holder.cimageview)
                        .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .load(product_imageurl);
                Log.e("image_large", "image_large");

            } else if (Tabletsize.isMedium(context)) {
                String product_imageurl = commomDatas.get(position).imageurl.replace("small", "medium");

                Ion.with(holder.cimageview)
                        .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .load(product_imageurl);
                Log.e("image_medium", "image_medium" + product_imageurl);

            } else if (Tabletsize.isSmall(context)) {
                String product_imageurl = commomDatas.get(position).imageurl.replace("small", "medium");

                Ion.with(holder.cimageview)
                        .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .load(product_imageurl);

                Log.e("image_small", "image_small");
            }


            Log.e("imageurl", commomDatas.get(0).imageurl);

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("time Adapter", String.valueOf(System.currentTimeMillis()));

                    Intent intent = new Intent(context, ShopDetailActivity.class);
                    intent.putExtra("product_id", commomDatas.get(position).id);
                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);

                }
            });
            holder.tvProductName.setText(commomDatas.get(position).name);

        } else if (arrangementtype == "list_product") {

            final CommonHolder_listProduct viewHolder_listProduct = new CommonHolder_listProduct(v);

            if (Tabletsize.isTablet(context)) {
                Picasso.with(context)
                        .load(commomDatas.get(position).imageurl)
                        .error(R.drawable.banner)
                        .placeholder(R.drawable.default_noimage)
                        .error(R.drawable.default_noimage)
                        .into(viewHolder_listProduct.product_imageview);
                String product_imageurl = commomDatas.get(position).imageurl.replace("small", "large");

                Ion.with(viewHolder_listProduct.product_imageview)
                        .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .load(product_imageurl);
                Log.e("image_large", "image_large");

            } else if (Tabletsize.isMedium(context)) {
                String product_imageurl = commomDatas.get(position).imageurl.replace("small", "medium");

                Ion.with(viewHolder_listProduct.product_imageview)
                        .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .load(product_imageurl);
                Log.e("image_medium", "image_medium" + product_imageurl);

            } else if (Tabletsize.isSmall(context)) {
                String product_imageurl = commomDatas.get(position).imageurl.replace("small", "medium");

                Ion.with(viewHolder_listProduct.product_imageview)
                        .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                        .load(product_imageurl);

                Log.e("image_small", "image_small");
            }


            Log.e("imageurl", commomDatas.get(0).imageurl);

            viewHolder_listProduct.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("time Adapter", String.valueOf(System.currentTimeMillis()));


                }
            });
            viewHolder_listProduct.tvProductName.setText(commomDatas.get(position).name);
            AndroidUtils.setImageColor(viewHolder_listProduct.product_addcard, context, R.color.color_voilet);
            AndroidUtils.setImageColor(viewHolder_listProduct.product_description, context, R.color.color_voilet);
            viewHolder_listProduct.product_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopDetailActivity.class);
                    intent.putExtra("product_id", commomDatas.get(position).id);
                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            });
            viewHolder_listProduct.product_addcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            viewHolder_listProduct.cardview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    viewHolder_listProduct.rl_cartview.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (viewHolder_listProduct.rl_cartview.getVisibility() == View.VISIBLE) {
                                viewHolder_listProduct.rl_cartview.setVisibility(View.GONE);
                            }
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                    return true;
                }
            });
        } else {

            final CommonHolder_grid grid_holder = new CommonHolder_grid(v);

            Picasso.with(context).load(commomDatas.get(position).imageurl)
                    .error(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_applogo1))
                    .into(grid_holder.product_imageview);
            grid_holder.tvProductName.setText(commomDatas.get(position).name);
            grid_holder.rl_grid_row_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShopDetailActivity.class);
                    intent.putExtra("product_id", commomDatas.get(position).id.toString());
                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            });

            if (Tabletsize.isTablet(context)) {
                if (position % 3 == 0) {
                    grid_holder.view_grid_left.setVisibility(View.GONE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);
                } else {
                    grid_holder.view_grid_left.setVisibility(View.VISIBLE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);
                }

            } else {
                if (position % 2 == 0) {

                    grid_holder.view_grid_left.setVisibility(View.GONE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);


                } else {
                    grid_holder.view_grid_left.setVisibility(View.VISIBLE);
                    grid_holder.view_grid_right.setVisibility(View.GONE);
                }


            }
        }


    }

    @Override
    public int getItemCount() {
        return commomDatas.size();
    }


}
