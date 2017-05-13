package com.example.pat.aapkatrade.user_dashboard.order_list;

/**
 * Created by PPC16 on 17-Jan-17.
 */

public class OrderListData {


    String productName, product_price, order_date, product_qty, product_image, OrderId;

    public OrderListData(String OrderId, String productName, String product_price, String product_qty, String order_date, String product_image) {
        this.productName = productName;
        this.product_image = product_image;
        this.OrderId = OrderId;
        this.product_price = product_price;
        this.product_qty = product_qty;
        this.order_date = order_date;

    }
}


