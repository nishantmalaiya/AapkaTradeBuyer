package com.example.pat.aapkatrade.user_dashboard.order_list;

/**
 * Created by PPC17 on 15-May-17.
 */




public class OrderDetailData {

    String ProductImage,ProductName,ProductQty,DateTime,Prize,discount;



    public OrderDetailData(String productImage, String productName, String Prize,String productQty, String dateTime,String discount) {
        ProductImage = productImage;
        ProductName = productName;
        this.discount=discount;
        this.Prize=Prize;
        ProductQty = productQty;
        DateTime = dateTime;
    }
}
