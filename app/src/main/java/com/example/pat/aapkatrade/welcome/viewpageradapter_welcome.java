package com.example.pat.aapkatrade.welcome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.squareup.picasso.Picasso;

/**
 * Created by PPC21 on 13-Jan-17.
 */

public class viewpageradapter_welcome extends PagerAdapter {

    private Context mContext;
    int[] imagepath, layouts;
    String[] slider_header;


    public viewpageradapter_welcome(Context mContext, int layouts[], int imagepaths[], String[] slider_header) {
        this.imagepath = imagepaths;
        this.mContext = mContext;
        this.layouts = layouts;
        this.slider_header = slider_header;

    }


    public int getCount() {
        return layouts.length;

    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(layouts[position], container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);

        Picasso.with(mContext).load(imagepath[position]).into(imageView);

        TextView tvSlideHeader=(TextView)itemView.findViewById(R.id.tvSlideHeader);
        tvSlideHeader.setText(slider_header[position]);
        container.addView(itemView);

        return itemView;


    }


    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);

    }
}