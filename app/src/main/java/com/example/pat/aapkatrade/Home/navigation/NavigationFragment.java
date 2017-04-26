package com.example.pat.aapkatrade.Home.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.navigation.entity.CategoryHome;
import com.example.pat.aapkatrade.Home.navigation.entity.SubCategory;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.categories_tab.CategoryListActivity;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Call_webservice;
import com.example.pat.aapkatrade.general.TaskCompleteReminder;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.user_dashboard.my_profile.ProfilePreviewActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.X509TrustManager;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment implements View.OnClickListener, ExpandableListAdapter.clickListner {


    public static final String preFile = "textFile";
    public static final String userKey = "key";
    public static ActionBarDrawerToggle mDrawerToggle;
    public static DrawerLayout mDrawerLayout;
    boolean mUserLearnedDrawer;
    boolean mFromSavedInstance;
    View view;
    String Fname;
    private int lastExpandedPosition = -1;
    AppSharedPreference app_sharedpreference;
    public static final String PREFS_NAME = "call_recorder";
    // private SharedPreferences loginPreferences;
    List<String> categoryids;
    List<String> categoryname;
    Context context;
    TextView footer;
    RelativeLayout header;
    TextView textViewName, emailid;
    public static TextView usertype;
    private ImageView imageViewGB;

    private ImageView edit_profile_imgview;
    private ExpandableListAdapter listAdapter;
    private CommonAdapter_navigation_recycleview category_adapter;
    public ArrayList<CategoryHome> listDataHeader = new ArrayList<>();
    public ArrayList<SubCategory> listDataChild = new ArrayList<>();
    RelativeLayout rl_category, rl_logout;

    int flag_categoryclick;
    View rl_main_content;
    private ArrayList nested_dataheader;
    //public NestedScrollView navigation_parent_scrollview;
    ProgressBarHandler progressBarHandler;
    private static String shared_pref_name = "aapkatrade";
    RecyclerView navigation_recycleview;
    LinearLayoutManager navigation_linear_layout_manager;
    ImageView navigation_close;
    android.support.v7.widget.AppCompatImageView user_pic_img_vew;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                save_shared_pref("notlogin", "notlogin", "notlogin", "");


                Intent Homedashboard = new Intent(getActivity(), HomeActivity.class);
                Homedashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(Homedashboard);

            }
        });
        navigation_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        //   sharedPreferences = getActivity().getSharedPreferences(shared_pref_name, MODE_PRIVATE);
        //prepare textviewdata
        categoryname = new ArrayList<>();
        categoryids = new ArrayList<>();

        //sharedprefrance
        // loginPreferences = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        textViewName = (TextView) view.findViewById(R.id.tv_name);
        usertype = (TextView) view.findViewById(R.id.welcome_guest);
        emailid = (TextView) view.findViewById(R.id.tv_email);
        // loginPrefsEditor = loginPreferences.edit();
        prepareListData();
        navigation_linear_layout_manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        navigation_recycleview = (RecyclerView) this.view.findViewById(R.id.recycle_view_navigation);
        navigation_recycleview.setLayoutManager(navigation_linear_layout_manager);


        rl_category = (RelativeLayout) this.view.findViewById(R.id.rl_category);




        if (app_sharedpreference.getsharedpref("username", "notlogin") != null) {

            String Username = app_sharedpreference.getsharedpref("name", "notlogin");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "notlogin");
            String user_image = app_sharedpreference.getsharedpref("profile_pic", "");


            if (Username.equals("notlogin")) {

                rl_logout.setVisibility(View.GONE);

                Log.e("Shared_pref2", "null" + Username);
            } else {

                set_visibility_logout();

                setdata(Username, Emailid);

                Ion.with(user_pic_img_vew)
                        .error(ContextCompat.getDrawable(getActivity(), R.drawable.ic_profile_user))
                        .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.ic_profile_user))
                        .load(user_image);

                usertype.setText("Buyer");






            }
        } else {
            Log.e("Shared_pref1", "null");
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

    @Override
    public void itemClicked(View view, int groupview, int childview) {

        String sub_category_id;

        System.out.println("childview------------" + groupview + childview);

        String category_id = listDataHeader.get(groupview).getCategoryId();

        System.out.println("size-------------------" + listDataHeader.get(groupview).getSubCategoryList().size());

        if (listDataHeader.get(groupview).getSubCategoryList().size() == 0) {

            sub_category_id = "not_available";
        } else {
            sub_category_id = listDataHeader.get(groupview).getSubCategoryList().get(childview).subCategoryId;

        }

        System.out.println("category_id,sub_category_id---------" + category_id + "hi---" + sub_category_id);

        try {

            Intent i = new Intent(getActivity(), CategoryListActivity.class);
            i.putExtra("category_id", category_id);
            i.putExtra("sub_category_id", sub_category_id);
            startActivity(i);

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }


    }


    private void replaceFragment(Fragment newFragment, String tag) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag).addToBackStack(tag);
        transaction.commit();
    }


    private static class Trust implements X509TrustManager {

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

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
        Call_webservice.getcountrystatedata(context, "category", getResources().getString(R.string.webservice_base_url) + "/dropdown", webservice_body_parameter, webservice_header_type);

        Call_webservice.taskCompleteReminder = new TaskCompleteReminder() {

            @Override
            public void Taskcomplete(JsonObject data) {

                Log.e("data",data.toString());
                if (data != null) {
                    JsonObject jsonObject = data.getAsJsonObject();
                    JsonArray jsonResultArray = jsonObject.getAsJsonArray("result");

                    for (int i = 0; i < jsonResultArray.size(); i++) {

                        JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                        JsonArray json_subcategory = jsonObject1.getAsJsonArray("subcategory");

                        listDataChild = new ArrayList<>();

                        for (int k = 0; k < json_subcategory.size(); k++) {
                            JsonObject jsonObject_subcategory = (JsonObject) json_subcategory.get(k);
                            SubCategory subCategory = new SubCategory(jsonObject_subcategory.get("id").getAsString(), jsonObject_subcategory.get("name").getAsString());
                            listDataChild.add(subCategory);
                        }
                        CategoryHome categoryHome = new CategoryHome(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString(), jsonObject1.get("icon").getAsString(), listDataChild);

                        listDataHeader.add(categoryHome);

                        Log.e("listDataHeader_cate", categoryHome.toString());
                    }
                    Collections.sort(listDataHeader, new Comparator<CategoryHome>() {
                        @Override
                        public int compare(CategoryHome o1, CategoryHome o2) {
                            return o1.getCategoryName().compareToIgnoreCase(o2.getCategoryName());
                        }
                    });
                }
                set_recycleview_adapter();



            }



        };


    }

    private void set_recycleview_adapter() {

        if (listDataHeader.size() != 0) {
            category_adapter = new CommonAdapter_navigation_recycleview(context, listDataHeader);
            navigation_recycleview.setAdapter(category_adapter);

        }
    }





    public void save_shared_pref(String user_id, String user_name, String email_id, String profile_pic) {
        app_sharedpreference.setsharedpref("userid", user_id);
        app_sharedpreference.setsharedpref("username", user_name);
        app_sharedpreference.setsharedpref("emailid", email_id);
        app_sharedpreference.setsharedpref("profile_pic", profile_pic);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (app_sharedpreference.getsharedpref("username", "notlogin") != null) {

            String Username = app_sharedpreference.getsharedpref("name", "notlogin");
            String Emailid = app_sharedpreference.getsharedpref("emailid", "notlogin");


            if (Username.equals("notlogin")) {

                rl_logout.setVisibility(View.GONE);

                Log.e("Shared_pref2", "null" + Username);
            } else {

                set_visibility_logout();

                setdata(Username, Emailid);
                usertype.setText("Buyer");


            }
        } else {
            Log.e("Shared_pref1", "null");
        }
    }
}










