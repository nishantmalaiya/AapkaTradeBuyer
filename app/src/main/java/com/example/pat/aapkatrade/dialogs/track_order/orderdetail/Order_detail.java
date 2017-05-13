package com.example.pat.aapkatrade.dialogs.track_order.orderdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.CommomData;
import com.example.pat.aapkatrade.Home.CommomHolder;
import com.example.pat.aapkatrade.dialogs.track_order.CommonHolderOrderListAdapter;
import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.shopdetail.ShopViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class Order_detail extends AppCompatActivity {
    Context context;

    RecyclerView recyclerProductList;
    private ArrayList<String> imageUrlArrayList = new ArrayList<>();
    private String class_name, json_response;
    ArrayList<CommomData> commomDatas_products = new ArrayList<>();
    ViewPager view_pager_products;
    private ShopViewPagerAdapter viewPagerAdapter;
    ArrayList<CommomDataTrackingList> commomDatas_order = new ArrayList<>();
    private CircleIndicator circleIndicator;
    ArrayList<OrderDetailsDatas> orderDetailsDatas = new ArrayList<>();
    ArrayList<ProductDatas> productDatasArrayList = new ArrayList<>();
    private CommonHolderOrderListAdapter commomAdapter_latestpost;
    LinearLayoutManager llmanager_productlist;

    TextView tvOrderid, tvOrderDate, tvAmountPaid, tvAddress, tvMobile;

    String orderId = "", OrderDate = "", AmoutPaid = "", Address = "", PhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        context = this;
        class_name = getIntent().getStringExtra("class_name");
        initview();

        json_response = "{\"error\":false,\"message\":\"Order Track!\",\"code\":\"200\",\"result\":{\"id\":133,\"ORDER_ID\":\"AT210417060350\",\"payment_method\":null,\"user_id\":41,\"name\":\"Md Sabir\",\"email\":\"\",\"phone\":\"9907340880\",\"pincode\":\"110025\",\"address\":\"H65 Ground Floor Abul Fazal Anclave Part I, Jamia Nagar\",\"city\":\"Md Sabir\",\"state\":\"Delhi\",\"landmark\":\"\",\"product_id\":0,\"bank_id\":0,\"deliveryday\":0,\"product_name\":\"\",\"product_qty\":3,\"product_express\":null,\"product_price\":2040,\"product_subtotal\":0,\"product_option\":\"\",\"reason\":\"\",\"comments\":\"\",\"status\":0,\"payment_status\":1,\"vpc_AuthorizeId\":\"499113\",\"vpc_BatchNo\":\"20170421\",\"vpc_Card\":\"MC\",\"vpc_Message\":\"Approved\",\"vpc_ReceiptNo\":\"711122499113\",\"vpc_TransactionNo\":\"2000000012\",\"payment_datetime\":\"2017-04-21 18:04:31\",\"created_at\":\"2017-04-21 18:03:50\",\"updated_at\":\"2017-04-21 18:03:50\",\"orders\":{\"id\":133,\"ORDER_ID\":\"AT210417060350\",\"payment_method\":null,\"user_id\":41,\"name\":\"Md Sabir\",\"email\":\"\",\"phone\":\"9907340880\",\"pincode\":\"110025\",\"address\":\"H65 Ground Floor Abul Fazal Anclave Part I, Jamia Nagar\",\"city\":\"Md Sabir\",\"state\":\"Delhi\",\"landmark\":\"\",\"product_id\":0,\"bank_id\":0,\"deliveryday\":0,\"product_name\":\"\",\"product_qty\":3,\"product_express\":null,\"product_price\":2040,\"product_subtotal\":0,\"product_option\":\"\",\"reason\":\"\",\"comments\":\"\",\"status\":0,\"payment_status\":1,\"vpc_AuthorizeId\":\"499113\",\"vpc_BatchNo\":\"20170421\",\"vpc_Card\":\"MC\",\"vpc_Message\":\"Approved\",\"vpc_ReceiptNo\":\"711122499113\",\"vpc_TransactionNo\":\"2000000012\",\"payment_datetime\":\"2017-04-21 18:04:31\",\"created_at\":\"2017-04-21 18:03:50\",\"updated_at\":\"2017-04-21 18:03:50\"},\"orders_details\":[{\"id\":130,\"orders_id\":133,\"ORDER_ID\":\"AT210417060350\",\"payment_method\":null,\"user_id\":41,\"product_id\":567,\"deliveryday\":0,\"product_name\":\"Round Wheel\",\"product_qty\":2,\"product_price\":2000,\"product_net_price\":\"1800\",\"discount\":\"10\",\"product_option\":\"\",\"reason\":\"\",\"comments\":\"\",\"status\":0,\"cancel_datetime\":\"0000-00-00 00:00:00\",\"cancel_reason\":\"\",\"cancel_comments\":\"\",\"created_at\":\"2017-04-21 18:03:50\",\"updated_at\":\"2017-04-21 18:03:50\",\"image_url\":\"http://www.staging.aapkatrade.com/public/upload/567/small_car-accessories-shop.jpg\"},{\"id\":130,\"orders_id\":133,\"ORDER_ID\":\"AT210417060350\",\"payment_method\":null,\"user_id\":41,\"product_id\":567,\"deliveryday\":0,\"product_name\":\"Round Wheel\",\"product_qty\":2,\"product_price\":2000,\"product_net_price\":\"1800\",\"discount\":\"10\",\"product_option\":\"\",\"reason\":\"\",\"comments\":\"\",\"status\":0,\"cancel_datetime\":\"0000-00-00 00:00:00\",\"cancel_reason\":\"\",\"cancel_comments\":\"\",\"created_at\":\"2017-04-21 18:03:50\",\"updated_at\":\"2017-04-21 18:03:50\",\"image_url\":\"http://www.staging.aapkatrade.com/public/upload/567/small_s-k-auto-spares-salem-ho-salem-0.jpg\"},{\"id\":131,\"orders_id\":133,\"ORDER_ID\":\"AT210417060350\",\"payment_method\":null,\"user_id\":41,\"product_id\":573,\"deliveryday\":0,\"product_name\":\"Worldone 480 Business Card Holder BC105 - Set of 2 \",\"product_qty\":1,\"product_price\":240,\"product_net_price\":\"240\",\"discount\":\"0\",\"product_option\":\"\",\"reason\":\"\",\"comments\":\"\",\"status\":0,\"cancel_datetime\":\"0000-00-00 00:00:00\",\"cancel_reason\":\"\",\"cancel_comments\":\"\",\"created_at\":\"2017-04-21 18:03:50\",\"updated_at\":\"2017-04-21 18:03:50\",\"image_url\":\"http://www.staging.aapkatrade.com/public/upload/573/small_5-Worldone 480 Business Card Holder BC 105- 2 Nos.jpg\"}]}}";

        setUpToolBar();


        String_to_json_conversion(json_response);

    }

    private void initview() {

        llmanager_productlist = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        recyclerProductList = (RecyclerView) findViewById(R.id.recycleviewProductList);
        recyclerProductList.setLayoutManager(llmanager_productlist);
        tvOrderid = (TextView) findViewById(R.id.tvOrderId);

        tvAddress = (TextView) findViewById(R.id.tvAddress);

        tvAmountPaid = (TextView) findViewById(R.id.tvAmountPaid);

        tvOrderDate = (TextView) findViewById(R.id.tvOrderDate);

        tvMobile = (TextView) findViewById(R.id.tvMobile);


    }

    private void String_to_json_conversion(String server_response_string) {
        try {
            JSONObject server_result = new JSONObject(server_response_string);
            String error = server_result.getString("error");
            if (error.contains("false")) {

                JSONObject result = server_result.getJSONObject("result");

                ///// order Details

                orderId = result.get("ORDER_ID").toString();


                String name = result.get("name").toString();
                String email = result.get("email").toString();
                PhoneNumber = result.get("phone").toString();
                String pincode = result.get("pincode").toString();
                Address = result.get("address").toString();
                String city = result.get("city").toString();
                String state = result.get("state").toString();
                String product_qty = result.get("product_qty").toString();
                AmoutPaid = result.get("product_price").toString();
                String vpc_AuthorizeId = result.get("vpc_AuthorizeId").toString();
                String vpc_BatchNo = result.get("vpc_BatchNo").toString();
                String vpc_Card = result.get("vpc_Card").toString();
                String vpc_TransactionNo = result.get("vpc_TransactionNo").toString();
                OrderDate = result.get("payment_datetime").toString();

                //  set Text


                tvOrderDate.setText(OrderDate);

                tvOrderid.setText(orderId);

                tvAmountPaid.setText(AmoutPaid);

                tvAddress.setText(Address);


                tvMobile.setText(PhoneNumber);

                orderDetailsDatas.add(new OrderDetailsDatas(orderId, name, email, PhoneNumber, pincode, Address, city, state, product_qty, AmoutPaid, vpc_AuthorizeId, vpc_BatchNo, vpc_Card, vpc_TransactionNo, OrderDate));

                //  Products Details

                JSONArray order_detalis = result.getJSONArray("orders_details");

                for (int i = 0; i < order_detalis.length(); i++) {

                    JSONObject jsonObject_product = (JSONObject) order_detalis.get(i);
                    String product_name = jsonObject_product.get("product_name").toString();
                    String product_qty_order = jsonObject_product.get("product_qty").toString();
                    String product_price_order = jsonObject_product.get("product_price").toString();
                    String product_net_price = jsonObject_product.get("product_net_price").toString();
                    String discount = jsonObject_product.get("discount").toString();
                    String image_url = jsonObject_product.get("image_url").toString();
                    String product_id = jsonObject_product.get("product_id").toString();
                    imageUrlArrayList.add(image_url);


                    commomDatas_order.add(new CommomDataTrackingList(image_url, product_price_order, product_name, product_id, "demo", Address, "demo",
                            "demo"));

                    commomAdapter_latestpost = new CommonHolderOrderListAdapter(context, commomDatas_order, "OrderedProductList", "latestdeals");
                    recyclerProductList.setAdapter(commomAdapter_latestpost);
                    recyclerProductList.setNestedScrollingEnabled(false);

                    productDatasArrayList.add(new ProductDatas(product_name, product_qty_order, product_price_order, product_net_price, discount, image_url));


                }


                AndroidUtils.showErrorLog(context, "order info", orderId + "*****" + name);


            }


        } catch (JSONException e) {
            AndroidUtils.showErrorLog(context, e.toString());

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


}
