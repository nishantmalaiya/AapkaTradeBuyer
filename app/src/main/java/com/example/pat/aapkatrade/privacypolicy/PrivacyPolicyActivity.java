package com.example.pat.aapkatrade.privacypolicy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private Context context;
    private TextView tvPolicy, tvReadMore;
    private RelativeLayout expandableRelativeLayout;
    private LinearLayout policyContentMainLayout;
    private View leftBarTopView, leftBarBottomView, rightBarTopView, rightBarBottomView;
    private LinearLayout policyHeaderLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        context = PrivacyPolicyActivity.this;
        setUpToolBar();
        initView();
        getPolicyData();

    }

    private void getPolicyData() {
        final ProgressBarHandler progressBarHandler = new ProgressBarHandler(context);
        progressBarHandler.show();
        Ion.with(context)
                .load(getString(R.string.webservice_base_url))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        if(result!=null){
                            AndroidUtils.showErrorLog(context, "if**********", Html.fromHtml(result.get("result").getAsString()));
                            tvPolicy.setText(Html.fromHtml(result.get("result").getAsString()));
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
        tvPolicy = (TextView) findViewById(R.id.tvPolicy);
        tvPolicy.setText("");
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
                });;
                tvReadMore.setVisibility(View.GONE);
            }
        });
    }

    private void setUpToolBar() {

    }


}
