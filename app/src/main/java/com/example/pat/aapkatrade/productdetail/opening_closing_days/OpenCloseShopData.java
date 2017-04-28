package com.example.pat.aapkatrade.productdetail.opening_closing_days;

/**
 * Created by PPC16 on 4/10/2017.
 */

public class OpenCloseShopData {
    public String dayName;
    public String openingTime;
    public String closingTime;

    public OpenCloseShopData(String dayName, String openingTime, String closingTime) {
        this.dayName = dayName;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
}
