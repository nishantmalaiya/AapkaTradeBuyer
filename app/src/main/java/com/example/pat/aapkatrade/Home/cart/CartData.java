package com.example.pat.aapkatrade.Home.cart;

/**
 * Created by PPC16 on 4/25/2017.
 */

public class CartData
{
    public String productName,id,price,quantity,product_image,product_id;

    public CartData(String id, String productName, String quantity,String price,String product_image,String product_id)
    {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.price=price;
        this.product_image = product_image;
        this.product_id = product_id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduct_id() {
        return id;
    }

    public void setProduct_id(String product_id) {
        this.id = product_id;
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
