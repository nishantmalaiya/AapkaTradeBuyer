package com.example.pat.aapkatrade.termandcondition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class TermsAndConditionActivity extends AppCompatActivity {
    private Context context;
    private TextView tvTermsAndConditions, tvReadMore;
    private RelativeLayout expandableRelativeLayout;
    private LinearLayout policyContentMainLayout;
    private View leftBarTopView, leftBarBottomView, rightBarTopView, rightBarBottomView;
    private LinearLayout policyHeaderLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        context = TermsAndConditionActivity.this;
        setUpToolBar();
        initView();
        getTermsAndConditionsData();

    }

    private void getTermsAndConditionsData() {
        final ProgressBarHandler progressBarHandler = new ProgressBarHandler(context);
        progressBarHandler.show();
        Ion.with(context)
                .load(getString(R.string.webservice_base_url)+"/terms_condition")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if(result!=null){
                            AndroidUtils.showErrorLog(context, "if**********", Html.fromHtml(result.get("result").getAsString()));
                            tvTermsAndConditions.setText(Html.fromHtml(result.get("result").getAsString()));
                        } else {
                            AndroidUtils.showErrorLog(context, "Else**********");
                        }
                    }
                });
    }

    private void initView() {
        leftBarTopView = findViewById(R.id.leftBarTopView);
        leftBarBottomView = findViewById(R.id.leftBarBottomView);
        rightBarTopView = findViewById(R.id.rightBarTopView);
        rightBarBottomView = findViewById(R.id.rightBarBottomView);
        AndroidUtils.setBackgroundSolid(leftBarTopView, context, R.color.purple_2, 0, GradientDrawable.RECTANGLE);
        AndroidUtils.setBackgroundSolid(leftBarBottomView, context, R.color.grey_2, 0, GradientDrawable.RECTANGLE);
        AndroidUtils.setBackgroundSolid(rightBarTopView, context, R.color.red_light, 0, GradientDrawable.RECTANGLE);
        AndroidUtils.setBackgroundSolid(rightBarBottomView, context, R.color.grey_2, 0, GradientDrawable.RECTANGLE);
        policyContentMainLayout = (LinearLayout) findViewById(R.id.policyContentMainLayout);
        AndroidUtils.setBackgroundSolid(policyContentMainLayout, context, android.R.color.transparent, 10, GradientDrawable.RECTANGLE);
        tvTermsAndConditions = (TextView) findViewById(R.id.tvTermsAndConditions);
        tvTermsAndConditions.setText("");
        policyHeaderLayout = (LinearLayout) findViewById(R.id.policyHeaderLayout);
        tvReadMore = (TextView) findViewById(R.id.tvReadMore);
        tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                policyHeaderLayout.animate().alpha(0.0f).setDuration(2000).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        policyHeaderLayout.setVisibility(View.GONE);
                        policyContentMainLayout.removeView(tvReadMore);
                    }
                });
                AndroidUtils.setBackgroundSolid(leftBarTopView, context, R.color.grey_2, 0, GradientDrawable.RECTANGLE);
                AndroidUtils.setBackgroundSolid(rightBarTopView, context, R.color.grey_2, 0, GradientDrawable.RECTANGLE);

                tvReadMore.setVisibility(View.GONE);
            }
        });
    }
    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        AppCompatImageView back_imagview = (AppCompatImageView) findViewById(R.id.back_imagview);
        back_imagview.setVisibility(View.VISIBLE);
        back_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.logoWord).setVisibility(View.GONE);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setVisibility(View.VISIBLE);
        header_name.setText(getResources().getString(R.string.my_profile_heading));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
