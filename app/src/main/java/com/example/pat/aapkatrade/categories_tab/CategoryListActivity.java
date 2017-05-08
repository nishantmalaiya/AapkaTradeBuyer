package com.example.pat.aapkatrade.categories_tab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.entity.State;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.filter.FilterDialog;
import com.example.pat.aapkatrade.filter.entity.FilterObject;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.carlom.stikkyheader.core.StikkyHeaderBuilder;


public class CategoryListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CategoriesListAdapter categoriesListAdapter;
    private ArrayList<CategoriesListData> shopArrayListByCategory = new ArrayList<>();
    private ProgressBarHandler progress_handler;
    private FrameLayout layout_container;
    private static String category_id, latitude = "0.0", longitude = "0.0";

    private AppSharedPreference appSharedPreference;
    private ArrayList<State> productAvailableStateList = new ArrayList<>();
    private Context context;
    private TextView toolbarRightText;

    private ArrayMap<String, ArrayList<FilterObject>> filterHashMap = null;
    ViewGroup view;
    private LinearLayoutManager linearLayoutManager;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories_list);

        context = CategoryListActivity.this;
        Intent intent = getIntent();
//        FilterDialog.filterString = "";
        Bundle b = intent.getExtras();
        if (b != null) {
            latitude = b.getString("latitude");
            longitude = b.getString("longitude");

            category_id = b.getString("category_id");
        }

        appSharedPreference = new AppSharedPreference(this);
        setUpToolBar();
        initView();
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        StikkyHeaderBuilder.stickTo(mRecyclerView).setHeader(R.id.header_simple, view).minHeightHeaderDim(R.dimen.min_header_height).build();
        getShopListData("0");
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = linearLayoutManager.getItemCount();

                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                int lastVisibleItemCount = linearLayoutManager.findLastVisibleItemPosition();

                if (totalItemCount > 0) {
                    if ((totalItemCount - 1) == lastVisibleItemCount) {
                        page = page + 1;
                        getShopListData(String.valueOf(page));
                    }
                }
            }

        });

    }

    private void initView() {
        view = (ViewGroup) findViewById(android.R.id.content);
        progress_handler = new ProgressBarHandler(this);
        layout_container = (FrameLayout) view.findViewById(R.id.layout_container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    private void getShopListData(final String pageNumber) {
        State state = new State("-1", "Select State", "0");
        productAvailableStateList.add(state);
        AndroidUtils.showErrorLog(context, shopArrayListByCategory.size() + "^^^^^^^^^");

        Log.e(AndroidUtils.getTag(context), "called categorylist webservice for category_id : " + category_id + "**" + latitude + "*****" + longitude);


        if (!(FilterDialog.filterString!= null && FilterDialog.filterString.length()>0)) {

            AndroidUtils.showErrorLog(context, "shoplist by NOOOOOOOOOOOOOOO filter");
            Ion.with(CategoryListActivity.this)
                    .load(getResources().getString(R.string.webservice_base_url) + "/shoplist")
                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", "category")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("category_id", category_id)
                    .setBodyParameter("apply", "1")
                    .setBodyParameter("page", pageNumber)
                    .setBodyParameter("lat", latitude)
                    .setBodyParameter("long", longitude)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {


                            if (result == null) {
                                layout_container.setVisibility(View.INVISIBLE);
                            } else {

                                Log.e(AndroidUtils.getTag(context), result.toString());
                                if (result.get("error").getAsString().contains("false")) {
                                    if (Validation.isNumber(result.get("total_result").getAsString()) && Integer.parseInt(result.get("total_result").getAsString()) > 1) {
                                        toolbarRightText.setVisibility(View.VISIBLE);

                                        JsonArray statesArray1 = result.get("filter").getAsJsonArray();
                                        for (int i = 0; i < statesArray1.size(); i++) {
                                            JsonObject stateObject = (JsonObject) statesArray1.get(i);
                                            Log.e("stateobject", stateObject.toString());
                                        }

                                        String message = result.get("message").toString();

                                        String message_data = message.replace("\"", "");
                                        AndroidUtils.showErrorLog(context, "message_data " + message_data);
                                        Log.e("message_product_list", result.toString());

                                        if (message_data.equals("No record found")) {
                                            layout_container.setVisibility(View.INVISIBLE);
                                        } else {
                                            JsonArray resultJsonArray = result.getAsJsonArray("result");
                                            JsonArray filterArray = result.getAsJsonArray("filter");
                                            if (filterArray != null) {
                                                loadFilterDataInHashMap(filterArray);
                                            }
                                        AndroidUtils.showErrorLog(context, resultJsonArray.size()+"*********************", resultJsonArray.toString());
                                            if (resultJsonArray != null && resultJsonArray.size() > 0) {
                                                loadResultData(resultJsonArray);
                                            }
                                            if (categoriesListAdapter == null) {
                                                categoriesListAdapter = new CategoriesListAdapter(CategoryListActivity.this, shopArrayListByCategory);
                                                mRecyclerView.setAdapter(categoriesListAdapter);
                                            } else {
                                                categoriesListAdapter.notifyDataSetChanged();
                                            }
                                            progress_handler.hide();
                                        }
                                    }
                                }
                            }
                        }


                    });



        } else {

            AndroidUtils.showErrorLog(context, "shoplist by filter --> "+FilterDialog.filterString);

            Ion.with(CategoryListActivity.this)
                    .load(getResources().getString(R.string.webservice_base_url) + "/shoplist")

                    .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("type", "category")
                    .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                    .setBodyParameter("category_id", category_id)
                    .setBodyParameter("apply", "1")
                    .setBodyParameter("extrafilter", FilterDialog.filterString)
                    .setBodyParameter("page", pageNumber)
                    .setBodyParameter("lat", latitude)
                    .setBodyParameter("long", longitude)

//                    .asString()
//                    .setCallback(new FutureCallback<String>() {
//                        @Override
//                        public void onCompleted(Exception e, String result) {
//                             if(result == null){
//                                AndroidUtils.showErrorLog(context, "++++++++++++++++"+e.toString()+"___________");
//
//                            }else {AndroidUtils.showErrorLog(context, "shoplist by filter daaaaaaaaaata is "+result);
//
//
//                             }
//                        }
//                    });

                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {


                            AndroidUtils.showErrorLog(context, "shoplist by filter daaaaaaaaaata is "+result.toString());
                            if (result == null) {
                                layout_container.setVisibility(View.INVISIBLE);
                            } else {

                                Log.e(AndroidUtils.getTag(context), result.toString());
                                if (result.get("error").getAsString().contains("false")) {
                                    if (Validation.isNumber(result.get("total_result").getAsString()) && Integer.parseInt(result.get("total_result").getAsString()) > 1) {
                                        toolbarRightText.setVisibility(View.VISIBLE);

                                        JsonArray statesArray1 = result.get("filter").getAsJsonArray();
                                        for (int i = 0; i < statesArray1.size(); i++) {
                                            JsonObject stateObject = (JsonObject) statesArray1.get(i);
                                            Log.e("stateobject", stateObject.toString());
                                        }

                                        String message = result.get("message").toString();

                                        String message_data = message.replace("\"", "");
                                        AndroidUtils.showErrorLog(context, "message_data " + message_data);
                                        Log.e("message_product_list", result.toString());

                                        if (message_data.equals("No record found")) {
                                            AndroidUtils.showErrorLog(context, "message_data " + "IFFFFFFFFFFF No record found");
                                            layout_container.setVisibility(View.INVISIBLE);
                                        } else {
                                            AndroidUtils.showErrorLog(context, "message_data " + "ELSEEEEEEEEEE No record found");
                                            JsonArray resultJsonArray = result.getAsJsonArray("result");
                                            JsonArray filterArray = result.getAsJsonArray("filter");
                                            if (filterArray != null) {
                                                loadFilterDataInHashMap(filterArray);
                                            }
                                            AndroidUtils.showErrorLog(context, resultJsonArray.size()+"*********************", resultJsonArray.toString());

                                            if (resultJsonArray != null && resultJsonArray.size() > 0) {
                                                loadResultData(resultJsonArray);
                                            }
                                            if (categoriesListAdapter == null) {
                                                categoriesListAdapter = new CategoriesListAdapter(CategoryListActivity.this, shopArrayListByCategory);
                                                mRecyclerView.setAdapter(categoriesListAdapter);
                                            } else {
                                                categoriesListAdapter.notifyDataSetChanged();
                                            }


                                            progress_handler.hide();

                                        }

                                    }
                                }
                            }


                        }


                    });

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FilterDialog.filterString = "";
    }


    @Override
    protected void onStop() {
        super.onStop();
        FilterDialog.filterString = "";
    }

    private void loadResultData(JsonArray resultJsonArray) {
        for (int i = 0; i < resultJsonArray.size(); i++) {
            JsonObject jsonObject2 = (JsonObject) resultJsonArray.get(i);
            AndroidUtils.showErrorLog(context, "<--result-->cITY" + i + jsonObject2.toString());
            String shopId = jsonObject2.get("id").getAsString();
            String shopName = jsonObject2.get("name").getAsString();
            String shopImage = jsonObject2.get("image_url").getAsString();
            String distance = jsonObject2.get("distance").getAsString();
            String shopLocation = /*jsonObject2.get("city_name").getAsString() + "," +*/ jsonObject2.get("state_name").getAsString() + "," + jsonObject2.get("country_name").getAsString();


            Log.e("shop_list", shopImage);
            shopArrayListByCategory.add(new CategoriesListData(shopId, shopName, shopImage, shopLocation, distance));


            AndroidUtils.showErrorLog(context, "shopArrayListByCategory : " + shopArrayListByCategory.get(i).shopImage);

        }
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
        toolbarRightText = (TextView) findViewById(R.id.toolbarRightText);
        toolbarRightText.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_filter));
        toolbarRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog filterDialog = new FilterDialog(context, category_id, filterHashMap);
                filterDialog.show();
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


    private void loadFilterDataInHashMap(JsonArray filterArray) {
        filterHashMap = new ArrayMap<>();
        if (filterArray.size() > 0) {
            AndroidUtils.showErrorLog(context, "size of filter Array is  :  " + filterArray.size());
            for (int i = 0; i < filterArray.size(); i++) {
                JsonObject filterObject = (JsonObject) filterArray.get(i);
                String filterName = filterObject.get("name").getAsString();
                JsonArray valueJsonArray = filterObject.get("values").getAsJsonArray();
                ArrayList<FilterObject> valueArrayList = new ArrayList<>();

                if (valueJsonArray != null) {

                    for (int j = 0; j < valueJsonArray.size(); j++) {
                        FilterObject filterObjectData = new FilterObject();
                        JsonObject filterValueObject = (JsonObject) valueJsonArray.get(j);
                        String[] filterValueObjectArray = filterValueObject.toString().replaceAll("\\{", "").replaceAll("\\}", "").trim().split(",");
                        AndroidUtils.showErrorLog(context, "Length of filter value array is : ******" + filterValueObjectArray.length);

                        for (int k = 0; k < filterValueObjectArray.length; k++) {
                            AndroidUtils.showErrorLog(context, "filterValueObjectArray[k]" + filterValueObjectArray[k]);
                            String key = filterValueObjectArray[k].split(":")[0].replaceAll("\"", "");
                            String value = filterValueObjectArray[k].split(":")[1].replaceAll("\"", "");
                            if (key.contains("id")) {
                                filterObjectData.id.key = key;
                                filterObjectData.id.value = value;
                            } else if (key.contains("name")) {
                                filterObjectData.name.key = key;
                                filterObjectData.name.value = value;
                            } else if (key.contains("count")) {
                                filterObjectData.count.key = key;
                                filterObjectData.count.value = value;
                            }
                        }

                        valueArrayList.add(filterObjectData);
                    }
                }
                filterHashMap.put(filterName, valueArrayList);
            }
        }
    }


}

