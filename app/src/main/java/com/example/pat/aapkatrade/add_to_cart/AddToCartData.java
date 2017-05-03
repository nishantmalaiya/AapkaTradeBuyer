package com.example.pat.aapkatrade.add_to_cart;

/**
 * Created by PPC16 on 4/22/2017.
 */

public class AddToCartData {
    public String productId;
    public String productName;
    public String productImage;
    public String productQuantity;

    public AddToCartData(String productId, String productName, String productImage, String productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
    }
}
