package com.example.pat.aapkatrade.welcome;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pat.aapkatrade.Home.banner_home.viewpageradapter_home;
import com.example.pat.aapkatrade.R;

import java.util.ArrayList;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class WelcomeActivity extends AppCompatActivity {

    private viewpageradapter_welcome viewpageradapter;
    ViewPager vp;
    CircleIndicator circleIndicator;
    private int[] layouts = new int[]{
            R.layout.welcome_slide1,
            R.layout.welcome_slide1,
            R.layout.welcome_slide1,
            R.layout.welcome_slide1,
            R.layout.welcome_slide1};
    ;
    private Button btnSkip, btnNext;

    int imagepaths[] = new int[]{R.drawable.slide1image, R.drawable.slide2image, R.drawable.slide3image, R.drawable.slide4image,
            R.drawable.slide5image};
    String slider_header[]=new String[]{getResources().getString(R.string.slide1HeaderText),getResources().getString(R.string.slide2HeaderText),getResources().getString(R.string.slide3HeaderText),getResources().getString(R.string.slide4HeaderText),getResources().getString(R.string.slide5HeaderText)};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        vp = (ViewPager) findViewById(R.id.viewpagerWelcome);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator_welcome);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        setupviewpager();


    }


    private void setupviewpager() {

        viewpageradapter = new viewpageradapter_welcome(WelcomeActivity.this, layouts,imagepaths,slider_header);
        vp.setAdapter(viewpageradapter);
        vp.setCurrentItem(0);

        circleIndicator.setViewPager(vp);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == layouts.length - 1) {
//                    // last page. make button text to GOT IT
//                    btnNext.setText(getString(R.string.start));
//                    btnSkip.setVisibility(View.GONE);
//                } else {
//                    // still pages are left
//                    btnNext.setText(getString(R.string.next));
//                    btnSkip.setVisibility(View.VISIBLE);
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}
