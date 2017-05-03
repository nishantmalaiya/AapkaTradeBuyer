package com.example.pat.aapkatrade.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.payment.payment_method.CreditDebitFragment;
import com.example.pat.aapkatrade.payment.payment_method.NetBankingFragment;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PaymentActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    String SECURE_SECRET = "F025C47B53D2FBD4D6B02ED5C0F8A696";
    String accessCode = "28DCA441";
    String merchantId = "TESTCFZATRDPRLTD";
    String amount = "10";
    String orderid;
    String Merchant_transaction_reference ="756756876" ;
    String HashData_prepare;
    String success_url = "http://staging.aapkatrade.com/payment/PHP_VPC_3Party_Order_DR.php";
    String hash_pack;

    String url;

    Handler mHandler = new Handler();

    private int[] tabIcons = {

            R.drawable.new_order_wht,

            R.drawable.cancel_order_wht,

            R.drawable.shipped_order_wht,

            R.drawable.complete_order_wht,
    };
    private WebView webview;
    private ProgressBarHandler progressBarHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        context = PaymentActivity.this;
        setUpToolBar();
        webview = (WebView) findViewById(R.id.webview);
        progressBarHandler = new ProgressBarHandler(this);

        progressBarHandler.show();


        Ion.with(PaymentActivity.this)
                .load("http://staging.aapkatrade.com/payment/PHP_VPC_3Party_Order_DO.php")
                .setBodyParameter("vpc_Version", "1")
                .setBodyParameter("vpc_Command", "pay")
                .setBodyParameter("vpc_AccessCode", accessCode)
                .setBodyParameter("vpc_Merchant", merchantId)
                .setBodyParameter("vpc_OrderInfo", "Test Product")
                .setBodyParameter("vpc_Amount", "10000")
                .setBodyParameter("vpc_Currency", "INR")
                .setBodyParameter("vpc_Locale", "en_INR")
                .setBodyParameter("virtualPaymentClientURL", "https://migs.mastercard.com.au/vpcpay")
                .setBodyParameter("vpc_MerchTxnRef", Merchant_transaction_reference)
                .setBodyParameter("vpc_ReturnURL", "http://staging.aapkatrade.com/payment/PHP_VPC_3Party_Order_DR.php")


                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @SuppressLint("JavascriptInterface")
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        url = result.get("url").getAsString();

                        webview.getSettings().setBuiltInZoomControls(true);
                        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                        webview.getSettings().setDomStorageEnabled(true);
                        webview.clearHistory();
                        webview.clearCache(true);
                        webview.getSettings().setJavaScriptEnabled(true);
                        webview.getSettings().setSupportZoom(true);
                        webview.getSettings().setUseWideViewPort(false);
                        webview.getSettings().setLoadWithOverviewMode(false);


                        CookieManager.getInstance().setAcceptCookie(true);
                        webview.loadUrl(url);

                        webview.setWebChromeClient(new WebChromeClient() {
                            @Override
                            public void onReceivedTitle(WebView view, String title) {
                                getWindow().setTitle(title);
                                view.isPrivateBrowsingEnabled();//Set Activity tile to page title.
                            }
                        });

                        webview.setWebViewClient(new WebViewClient() {
                            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {

                                if (urlx.contains(success_url)) {
                                    Log.e("html2", urlx);

                                    Map<String, List<String>> params = new HashMap<String, List<String>>();
                                    String[] urlParts = urlx.split("\\?");
                                    if (urlParts.length > 1) {
                                        String query = urlParts[1];
                                        for (String param : query.split("&")) {
                                            String pair[] = param.split("=");
                                            String key = null;
                                            try {
                                                key = URLDecoder.decode(pair[0], "UTF-8");

                                                String value = "";
                                                if (pair.length > 1) {
                                                    value = URLDecoder.decode(pair[1], "UTF-8");
                                                }
                                                List<String> values = params.get(key);
                                                if (values == null) {
                                                    values = new ArrayList<String>();
                                                    params.put(key, values);
                                                }
                                                values.add(value);
                                            } catch (UnsupportedEncodingException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    }
                                    Log.e("queryParameter", params.toString());

                                    viewx.loadUrl(urlx);
                                    // startActivity(new Intent(PaymentActivity.this,Payment_confirmation_Activity.class));

                                } else {

                                }


                                viewx.isPrivateBrowsingEnabled();
                                viewx.getSettings().setJavaScriptEnabled(true);

//

                                progressBarHandler.hide();
                                return false;
                            }

                            @Override
                            public void onReceivedError(WebView view, int errorCode,
                                                        String description, String failingUrl) {
                                // TODO Auto-generated method stub
                                System.out.println(">>>>>>>>>>>>>>onReceivedError>>>>>>>>>>>>>>>>>>");
                                Toast.makeText(PaymentActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                            }


                            @Override
                            public void onPageFinished(WebView view, String url) {

                                progressBarHandler.hide();
                                Log.e("pagefinished", "pagefinished");

                                //super.onPageFinished(view, url);

                                webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");


                            }
                        });


                    }


                });


        // viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        //setupViewPager(viewPager);

//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(this);
//
//
//        tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        //setupTabIcons();


    }

    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
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
        header_name.setText(getResources().getString(R.string.select_payment_mode_title));
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


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.e("hi---", "IIIIIIIII" + tab.getPosition());
        if (tab.getPosition() == 0) {
            // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 1) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 2) {
            // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 3) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 1) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 2) {
            // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 3) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.text_order_tab));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[0],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 1) {
            // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[1],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 2) {
            // tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[2],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        } else if (tab.getPosition() == 3) {
            //tab.setIcon(AndroidUtils.setImageColor(context,tabIcons[3],R.color.orange));
            tabLayout.setTabTextColors(Color.parseColor("#066C57"), Color.parseColor("#FF6600"));
        }
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(AndroidUtils.setImageColor(context, tabIcons[0], R.color.orange));
        tabLayout.getTabAt(1).setIcon(AndroidUtils.setImageColor(context, tabIcons[1], R.color.text_order_tab));
        tabLayout.getTabAt(2).setIcon(AndroidUtils.setImageColor(context, tabIcons[2], R.color.text_order_tab));
        tabLayout.getTabAt(3).setIcon(AndroidUtils.setImageColor(context, tabIcons[3], R.color.text_order_tab));
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NetBankingFragment(), "NET BANKING");
        adapter.addFrag(new CreditDebitFragment(), "DEBIT/CREDIT CARD");

        viewPager.setAdapter(adapter);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
