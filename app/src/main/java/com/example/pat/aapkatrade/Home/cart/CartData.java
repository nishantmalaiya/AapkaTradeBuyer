package com.example.pat.aapkatrade.Home.cart;

/**
 * Created by PPC16 on 4/25/2017.
 */

public class CartData
{
    public String productName,product_id,price,quantity;

    public CartData(String product_id, String productName, String quantity,String price)
    {
        this.product_id = product_id;
        this.productName = productName;
        this.quantity = quantity;
        this.price=price;
    }


}
