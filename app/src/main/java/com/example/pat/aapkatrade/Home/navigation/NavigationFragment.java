package com.example.pat.aapkatrade.Home.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.navigation.adapter.NavigationAdapter;
import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.CallWebService;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Utils.SharedPreferenceConstants;
import com.example.pat.aapkatrade.general.interfaces.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.privacypolicy.PrivacyPolicyActivity;
import com.example.pat.aapkatrade.termandcondition.TermsAndConditionActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener {


    public static final String preFile = "textFile";
    public static final String userKey = "key";
    public static ActionBarDrawerToggle mDrawerToggle;
    public static DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstance;
    private View view;
    private String Fname;
    private int lastExpandedPosition = -1;
    AppSharedPreference app_sharedpreference;
    public static final String PREFS_NAME = "call_recorder";
    private List<String> categoryids;
    private List<String> categoryname;
    private Context context;
    private TextView footer;
    private RelativeLayout header;
    private TextView textViewName, emailid;
    private NavigationAdapter category_adapter;
    public ArrayList<CategoryHome> listDataHeader = new ArrayList<>();
    private RelativeLayout rl_category, rl_logout, rl_policy, rl_terms;
    private View rl_main_content;
    private ProgressBarHandler progressBarHandler;
    private RecyclerView navigation_recycleview;
    private LinearLayoutManager navigation_linear_layout_manager;
    private ImageView navigation_close;
    private AppCompatImageView user_pic_img_vew;

    public NavigationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        progressBarHandler = new ProgressBarHandler(context);
        app_sharedpreference = new AppSharedPreference(getActivity());
        initView(view);
        return view;
    }


    private void initView(View view) {

        user_pic_img_vew = (android.support.v7.widget.AppCompatImageView) view.findViewById(R.id.circular_profile_image_home);

        navigation_close = (ImageView) view.findViewById(R.id.navigation_close);
        rl_logout = (RelativeLayout) view.findViewById(R.id.rl_logout);
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_shared_pref("notlogin", "notlogin", "notlogin", "notlogin");


                Intent Homedashboard = new Intent(getActivity(), HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);

            }
        });

        rl_terms = (RelativeLayout) view.findViewById(R.id.rl_terms);
        rl_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TermsAndConditionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        rl_policy = (RelativeLayout) view.findViewById(R.id.rl_policy);
        rl_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrivacyPolicyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        navigation_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        categoryname = new ArrayList<>();
        categoryids = new ArrayList<>();
        textViewName = (TextView) view.findViewById(R.id.tv_name);
        emailid = (TextView) view.findViewById(R.id.tv_email);
        prepareListData();
        navigation_linear_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        navigation_recycleview = (RecyclerView) this.view.findViewById(R.id.recycle_view_navigation);
        navigation_recycleview.setLayoutManager(navigation_linear_layout_manager);


        rl_category = (RelativeLayout) this.view.findViewById(R.id.rl_category);


        if (app_sharedpreference.getSharedPref(SharedPreferenceConstants.USER_NAME.toString(), "notlogin") != null) {

            String Username = app_sharedpreference.getSharedPref(SharedPreferenceConstants.FIRST_NAME.toString(), "notlogin");
            String Emailid = app_sharedpreference.getSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), "notlogin");
            String user_image = app_sharedpreference.getSharedPref(SharedPreferenceConstants.PROFILE_PIC.toString(), "notlogin");
            Log.e("Shared_pref2", "null" + Username);

            if (Username.contains("notlogin")) {

                rl_logout.setVisibility(View.GONE);
                setdata(getString(R.string.welcomeguest), "");


            } else {

                set_visibility_logout();

                setdata(Username, Emailid);

                Ion.with(user_pic_img_vew)
                        .error(ContextCompat.getDrawable(getActivity(), R.drawable.ic_profile_user))
                        .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.ic_profile_user))
                        .load(user_image);


            }
        } else {
            AndroidUtils.showErrorLog(getActivity(),"sharedpref null");
        }
    }

    private void set_visibility_logout() {

        rl_logout.setVisibility(View.VISIBLE);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), userKey, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstance = true;
        }
    }

    public void setup(int id, final DrawerLayout drawer, Toolbar toolbar) {

        view = getActivity().findViewById(id);

        mDrawerLayout = drawer;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.drawer_open, R
                .string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                rl_main_content = getActivity().findViewById(R.id.rl_main_content);
                rl_main_content.setBackgroundColor(Color.parseColor("#33000000"));
                hideSoftKeyboard(getActivity());
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    savedInstances(getActivity(), userKey, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


        toolbar.setNavigationIcon(R.drawable.menu_24dp);


        mDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.menu24dp));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static void savedInstances(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharePreference = context.getSharedPreferences(preFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePreference.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharePreference = context.getSharedPreferences(preFile, MODE_PRIVATE);
        return sharePreference.getString(preferenceName, defaultValue);
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }


    public void setdata(String username, String email) {
        Fname = username;

        Log.e("Username", username);
        textViewName.setText(username);
        emailid.setText(email);


    }

    @Override
    public void onClick(View v) {

    }

    private void prepareListData() {
        getCategory();
    }

    private void getCategory() {

        HashMap<String, String> webservice_body_parameter = new HashMap<>();
        webservice_body_parameter.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        webservice_body_parameter.put("type", "category");

        HashMap<String, String> webservice_header_type = new HashMap<>();
        webservice_header_type.put("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3");
        CallWebService.getcountrystatedata(context, "category", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        CallWebService.taskCompleteReminder = new TaskCompleteReminder() {

            @Override
            public void Taskcomplete(JsonObject data) {

                if (data != null) {
                    Log.e("data", data.toString());
                    JsonObject jsonObject = data.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                    for (int i = 0; i < jsonResultArray.size(); i++) {

                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString());

                        listDataHeader.add(categoryHome);

                        Log.e("listDataHeader_cate", categoryHome.toString() + "----------------->" + categoryHome.getCategoryId() + "----------------->" + categoryHome.getCategoryName());
                    }

                    if (listDataHeader != null) {
                        Collections.sort(listDataHeader, new Comparator<CategoryHome>() {
                            @Override
                            public int compare(CategoryHome o1, CategoryHome o2) {
                                return o1.getCategoryName().compareToIgnoreCase(o2.getCategoryName());
                            }
                        });
                    }
                }
                set_recycleview_adapter();


            }


        };


    }

    private void set_recycleview_adapter() {

        if (listDataHeader.size() != 0) {
            category_adapter = new NavigationAdapter(context, listDataHeader);
            navigation_recycleview.setAdapter(category_adapter);
        }
    }


    public void save_shared_pref(String user_id, String user_name, String email_id, String profile_pic) {
        app_sharedpreference.setSharedPref(SharedPreferenceConstants.USER_ID.toString(), user_id);
        app_sharedpreference.setSharedPref(SharedPreferenceConstants.USER_NAME.toString(), user_name);
        app_sharedpreference.setSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), email_id);
        app_sharedpreference.setSharedPref(SharedPreferenceConstants.PROFILE_PIC.toString(), profile_pic);

    }


    @Override
    public void onResume() {
        super.onResume();

        if (app_sharedpreference.getSharedPref(SharedPreferenceConstants.USER_NAME.toString(), "notlogin") != null) {

            String userName = app_sharedpreference.getSharedPref(SharedPreferenceConstants.USER_NAME.toString(), "notlogin");
            String emailId = app_sharedpreference.getSharedPref(SharedPreferenceConstants.EMAIL_ID.toString(), "notlogin");


            if (userName.contains("notlogin")) {
                setdata(getString(R.string.welcomeguest), "");

                rl_logout.setVisibility(View.GONE);

                Log.e("Shared_pref2", "null" + userName);
            } else {

                set_visibility_logout();

                setdata(userName, emailId);


            }
        } else {
            Log.e("Shared_pref1", "null");
        }
    }
}










