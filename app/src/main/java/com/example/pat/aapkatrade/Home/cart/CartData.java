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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public void setPrice(String price) {
        this.price = price;
    }


    public String getPrice() {
        return price;
    }
}
