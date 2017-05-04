package com.example.pat.aapkatrade.Home;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.IntegerRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.pat.aapkatrade.Home.aboutus.AboutUsFragment;
import com.example.pat.aapkatrade.Home.cart.MyCartActivity;
import com.example.pat.aapkatrade.Home.navigation.NavigationFragment;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.contact_us.ContactUsFragment;
import com.example.pat.aapkatrade.dialogs.Track_order_dialog;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.CheckPermission;
import com.example.pat.aapkatrade.general.ConnetivityCheck;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.location.Mylocation;
import com.example.pat.aapkatrade.login.LoginActivity;
import com.example.pat.aapkatrade.user_dashboard.User_DashboardFragment;
import com.example.pat.aapkatrade.user_dashboard.my_profile.ProfilePreviewActivity;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
{

    private NavigationFragment drawer;
    private Toolbar toolbar;
    private DashboardFragment homeFragment;
    private AboutUsFragment aboutUsFragment;
    Context context;
    public static String shared_pref_name = "aapkatrade";
    App_config aa;
    AHBottomNavigation bottomNavigation;
    CoordinatorLayout coordinatorLayout;
    User_DashboardFragment user_dashboardFragment;
    ContactUsFragment contactUsFragment;
    ProgressBar progressBar;
    Boolean permission_status;
    public static String userid, username;
    NestedScrollView scrollView;
    float initialX, initialY;
    public static RelativeLayout rl_main_content, rl_searchview_dashboard;
    AppSharedPreference app_sharedpreference;
    private final int SPEECH_RECOGNITION_CODE = 1;
    Mylocation mylocation;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout linearlayout_home;
    ProgressBarHandler progressBarHandler;
    public  TextView countTextView;
    FrameLayout redCircle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rl_main_content = (RelativeLayout) findViewById(R.id.rl_main_content);

        progressBarHandler = new ProgressBarHandler(this);

        linearlayout_home = (LinearLayout) findViewById(R.id.ll_toolbar_container_home);

        app_sharedpreference = new AppSharedPreference(HomeActivity.this);

        App_config.set_defaultfont(HomeActivity.this);

        aboutUsFragment = new AboutUsFragment();

        contactUsFragment = new ContactUsFragment();

        user_dashboardFragment = new User_DashboardFragment();

        permission_status = CheckPermission.checkPermissions(HomeActivity.this);

        if (permission_status)
        {
            setContentView(R.layout.activity_homeactivity);
            //prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
            context = this;
            //  permissions  granted.
            setupToolBar();
            //setupNavigation();
            setupNavigationCustom();
            setupDashFragment();
            Intent iin = getIntent();
            Bundle b = iin.getExtras();
            setup_bottomNavigation();
            App_config.deleteCache(HomeActivity.this);


        }
        else
        {
            setContentView(R.layout.activity_homeactivity);
            //prefs = getSharedPreferences(shared_pref_name, Activity.MODE_PRIVATE);
            context = this;
            //  permissions  granted.
            setupToolBar();
            //setupNavigation();
            setupNavigationCustom();
            setupDashFragment();
            Intent iin = getIntent();
            Bundle b = iin.getExtras();
            setup_bottomNavigation();
            checked_wifispeed();
            App_config.deleteCache(HomeActivity.this);


        }
    }


    private void checked_wifispeed() {

        int a = ConnetivityCheck.get_wifi_speed(this);
        Log.e("a", a + "");

    }


    private void setupNavigationCustom()
    {
        drawer = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        drawer.setup(R.id.fragment, (DrawerLayout) findViewById(R.id.drawer), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);


        final MenuItem alertMenuItem = menu.findItem(R.id.cart_total_item);

         RelativeLayout badgeLayout = (RelativeLayout) alertMenuItem.getActionView();

        badgeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();

                onOptionsItemSelected(alertMenuItem);


            }
        });


        return true;


        /*getMenuInflater().inflate(R.menu.home_menu, menu);

        FrameLayout badgeLayout = (FrameLayout) menu.findItem(R.id.cart_total_item).getActionView();

        redCircle = (FrameLayout) badgeLayout.findViewById(R.id.view_alert_red_circle);
        countTextView = (TextView) badgeLayout.findViewById(R.id.view_alert_count_textview);

        return true;*/


    }






    private void setupToolBar()
    {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(null);
        ImageView home_link = (ImageView) toolbar.findViewById(R.id.iconHome);
        AndroidUtils.setImageColor(home_link, context, R.color.white);
        home_link.setVisibility(View.GONE);


       /* RelativeLayout cartContainer = (RelativeLayout) toolbar.findViewById(R.id.cart_container);
        TextView textView = (TextView) toolbar.findViewById(R.id.tvCart);

        if(Integer.parseInt(textView.getText().toString())>0)
        {
            cartContainer.setVisibility(View.VISIBLE);
        }

        cartContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(HomeActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });*/




    }

    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }

    private void setupDashFragment() {
        if (homeFragment == null) {
            homeFragment = new DashboardFragment();
        }
        String tagName = homeFragment.getClass().getName();
        replaceFragment(homeFragment, tagName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        switch (id) {
            case R.id.cart_total_item:

               // Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeActivity.this, MyCartActivity.class);
                startActivity(intent);

                break;
            case R.id.login:


                if (app_sharedpreference.getsharedpref("userid", "notlogin").equals("notlogin")) {
                    Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    break;
                } else {
                    Intent i = new Intent(HomeActivity.this, ProfilePreviewActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                    break;
                }

            case R.id.language:
                View menuItemView = findViewById(R.id.language);
                PopupMenu popup = new PopupMenu(HomeActivity.this.context, menuItemView);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_language_list, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.english_language:
                                saveLocale("en");

                                // getIntent().start
                                return true;

                            case R.id.hindi_language:
                                saveLocale("hi");

                                return true;
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        DashboardFragment dashboardFragment = (DashboardFragment) fm.findFragmentByTag(homeFragment.getClass().getName());
        ContactUsFragment showcontactUsFragment = (ContactUsFragment) fm.findFragmentByTag(contactUsFragment.getClass().getName());
        AboutUsFragment showaboutUsFragment = (AboutUsFragment) fm.findFragmentByTag(aboutUsFragment.getClass().getName());

        User_DashboardFragment showuserdashboardfragment = (User_DashboardFragment) fm.findFragmentByTag(user_dashboardFragment.getClass().getName());


        if (dashboardFragment != null && dashboardFragment.isVisible()) {

            double_back_pressed("finish");
            //finish();


            Log.e("myfragment_visible", "myfragment visible");
        } else if (showcontactUsFragment != null && showcontactUsFragment.isVisible()) {
            double_back_pressed("finish");
            // finish();
            Log.e("contact us visible", "contact us visible");

        } else if (showaboutUsFragment != null && showaboutUsFragment.isVisible()) {
            double_back_pressed("finish");
            //finish();
            Log.e("showabout visible", "showaboutUsFragment visible");
        } else if (showuserdashboardfragment != null && showuserdashboardfragment.isVisible()) {
            double_back_pressed("finish");
            //finish();
            Log.e("userdashboard visible", "userdashboard visible");
        } else {

            double_back_pressed("onbackpressed");
            // super.onBackPressed();
        }

    }


    public void loadLocale() {
        String langPref = "Language";

        String language = app_sharedpreference.getsharedpref(langPref, "");
        App_config.setLocaleFa(HomeActivity.this, language);
        Log.e("language", language);
        // changeLang(language);
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        app_sharedpreference.setsharedpref(langPref, lang);
        Log.e("language_pref", langPref + "****" + lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CheckPermission.MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("permission_granted", "permission_granted");
                    // permissions granted.
                } else {
                    Log.e("permission_requried", "permission_requried");
                    // no permissions granted.
                }
                return;
            }
        }
    }


    private void setup_bottomNavigation() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordination_home_activity);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        scrollView = (NestedScrollView) findViewById(R.id.scroll_main);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_navigation_home, R.color.dark_green);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_home_dashboard_aboutus, R.color.orange);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.track, R.color.dark_green);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.ic_home_bottom_account, R.color.dark_green);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_5, R.drawable.ic_about_us, R.color.dark_green);

        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                Log.d("DemoActivity", "BottomNavigation Position: " + y);
            }
        });
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.dark_green));
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setSelectedBackgroundVisible(true);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.white));
        bottomNavigation.setInactiveColor(Color.parseColor("#000000"));
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setColored(false);
        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:

                        if (homeFragment == null) {
                            homeFragment = new DashboardFragment();
                        }
                        String tagName = homeFragment.getClass().getName();
                        replaceFragment(homeFragment, tagName);
                        // showOrHideBottomNavigation(true);

                        break;
                    case 1:

                        Log.e("time  fragment", String.valueOf(System.currentTimeMillis()));

                        if (aboutUsFragment == null) {
                            aboutUsFragment = new AboutUsFragment();
                        }
                        String aboutUsFragment_tagName = aboutUsFragment.getClass().getName();
                        replaceFragment(aboutUsFragment, aboutUsFragment_tagName);
                        // showOrHideBottomNavigation(true);
                        break;


                    case 2:

                        FragmentManager fm = getSupportFragmentManager();
                        Track_order_dialog track_order_dialog = new Track_order_dialog();
                        track_order_dialog.show(fm, "Track Order");

                        //goToMyApp(true);


                        break;
                    case 3:
                        if (user_dashboardFragment == null) {
                            user_dashboardFragment = new User_DashboardFragment();
                        }

                        // startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        if (app_sharedpreference.getsharedpref("username", "not").contains("not")) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        } else {
                            Log.e("hiiii", app_sharedpreference.getsharedpref("username", "not"));
                            String tagName_dashboardFragment = user_dashboardFragment.getClass().getName();
                            replaceFragment(user_dashboardFragment, tagName_dashboardFragment);
                            //showOrHideBottomNavigation(true);
                        }

                        break;

                    case 4:
                        if (contactUsFragment == null) {
                            contactUsFragment = new ContactUsFragment();
                        }
                        String contact_us_fragment = contactUsFragment.getClass().getName();
                        replaceFragment(contactUsFragment, contact_us_fragment);
                        //showOrHideBottomNavigation(true);
                        break;


                }
                // Do something cool here...
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setup_scrollview(final NestedScrollView scrollView) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+

            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int pos = scrollView.getChildCount() - 1;
                    if (oldScrollY < scrollY) {

                        showOrHideBottomNavigation(true);
//                    setForceTitleHide(true);
//                    setForceTitleHide(true);
                    } else {
                        showOrHideBottomNavigation(false);
                    }

                    if (oldScrollY == scrollY) {

                        showOrHideBottomNavigation(true);

                    }

                }
            });
        } else {

            scrollView.setOnTouchListener(new View.OnTouchListener() {
                float height;

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action = event.getAction();
                    float height2 = event.getY();
                    if (action == MotionEvent.ACTION_DOWN) {
                        height = height2;
                    } else if (action == MotionEvent.ACTION_UP) {
                        if (this.height > height2) {
                            Log.e("up", "Scrolled up");
                            showOrHideBottomNavigation(false);
                        } else if (this.height < height2) {
                            Log.e("down", "Scrolled down");
                            showOrHideBottomNavigation(true);
                        }
                    }
                    return false;
                }

            });


            // Pre-Marshmallow
        }


    }

    private void setForceTitleHide(boolean forceTitleHide) {

        AHBottomNavigation.TitleState state = forceTitleHide ? AHBottomNavigation.TitleState.ALWAYS_HIDE : AHBottomNavigation.TitleState.ALWAYS_SHOW;
        bottomNavigation.setTitleState(state);
    }


    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            bottomNavigation.restoreBottomNavigation(true);
        } else {
            bottomNavigation.hideBottomNavigation(true);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void double_back_pressed(String type) {
        if (type.contains("finish")) {
            if (doubleBackToExitPressedOnce) {

                finish();

                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to quit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            if (doubleBackToExitPressedOnce) {


                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            //   AndroidUtils.showSnackBar(linearlayout_home,"Please click BACK again to exit"));
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        }

    }

    public void goToMyApp(boolean googlePlay) {
        //true if Google Play, false if Amazone Store
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "market://details?id=" : "amzn://apps/android?p=") + getPackageName())));
        } catch (ActivityNotFoundException e1) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "http://play.google.com/store/apps/details?id=" : "http://www.amazon.com/gp/mas/dl/android?p=") + getPackageName())));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(HomeActivity.this, "You don't have any app that can open this link", Toast.LENGTH_SHORT).show();
            }
        }
    }


}


