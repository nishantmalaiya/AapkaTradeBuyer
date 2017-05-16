package com.example.pat.aapkatrade.payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class PaymentCompletionActivity extends AppCompatActivity {
    private Context context;
    private TextView tvStatusTitle, tvStatusMsg;
    private LinearLayout circleTile1Layout, circleTile2Layout;
    private ImageView tickImageView;
    private RelativeLayout rlDoneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_completion);
        context = PaymentCompletionActivity.this;
        setUpToolBar();
        initView();

    }

    private void initView() {
        tvStatusTitle = (TextView) findViewById(R.id.tvStatusTitle);
        tvStatusMsg = (TextView) findViewById(R.id.tvStatusMsg);
        circleTile1Layout = (LinearLayout) findViewById(R.id.circleTile1Layout);
        circleTile2Layout = (LinearLayout) findViewById(R.id.circleTile2Layout);

        ((CircleImageView) circleTile1Layout.findViewById(R.id.circleImageView)).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_amount_paid2));
        AndroidUtils.setBackgroundSolid(((CircleImageView) circleTile1Layout.findViewById(R.id.circleImageView)), context, R.color.white, 0, GradientDrawable.RING);
        AndroidUtils.setImageColor(((CircleImageView) circleTile1Layout.findViewById(R.id.circleImageView)), context, R.color.white);
        ((TextView) circleTile1Layout.findViewById(R.id.tvHeader)).setText("Transaction ID");
        ((TextView) circleTile1Layout.findViewById(R.id.tvSubHeader)).setText("APKTRADE23548464");

        ((CircleImageView) circleTile2Layout.findViewById(R.id.circleImageView)).setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_amount_paid2));
        ((TextView) circleTile2Layout.findViewById(R.id.tvHeader)).setText("Transaction ID");
        ((TextView) circleTile2Layout.findViewById(R.id.tvSubHeader)).setText("APKTRADE23548464");

        rlDoneLayout = (RelativeLayout) findViewById(R.id.rlDoneLayout);
        AndroidUtils.setBackgroundSolid(rlDoneLayout, context, R.color.golden_text, 10, GradientDrawable.RECTANGLE);
    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
