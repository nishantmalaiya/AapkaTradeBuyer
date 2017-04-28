package com.example.pat.aapkatrade.Home.navigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pat.aapkatrade.Home.navigation.viewholder.NavigationViewHolder;
import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by Netforce on 7/25/2016.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationViewHolder> {
    private Context context;
    private ArrayList<CategoryHome> listDataHeader;
    private View view;


    public NavigationAdapter(Context context, ArrayList<CategoryHome> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public NavigationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group, parent, false);
        return new NavigationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationViewHolder viewHolder, int position) {
        final int currentPosition = position;
        final ImageView imageView = viewHolder.imageViewIcon;

        viewHolder.tvCategoryname.setText(listDataHeader.get(position).getCategoryName());

        setUpIconBackground(imageView);
        Ion.with(context).load(listDataHeader.get(position).getCategoryIconPath()).withBitmap().asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        if (result != null) {
                            imageView.setImageBitmap(result);
                        }
                    }

                });


        viewHolder.rl_category_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoryListActivity.class);
                i.putExtra("category_id", listDataHeader.get(currentPosition).getCategoryId());
                context.startActivity(i);

            }
        });
    }

    private void setUpIconBackground(ImageView imageView) {
        TypedArray images = context.getResources().obtainTypedArray(R.array.category_icon_background);
        int choice = (int) (Math.random() * images.length());
        imageView.setBackgroundResource(images.getResourceId(choice, R.drawable.circle_sienna));
        images.recycle();
    }

    @Override
    public int getItemCount() {
        return listDataHeader.size();
    }


}