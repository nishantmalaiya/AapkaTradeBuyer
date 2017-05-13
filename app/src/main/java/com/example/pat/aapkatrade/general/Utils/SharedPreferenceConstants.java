package com.example.pat.aapkatrade.general.Utils;

/**
 * Created by PPC15 on 12-05-2017.
 */

public enum SharedPreferenceConstants {

    USER_ID("USER_ID"),
    FIRST_NAME("FIRST_NAME"),
    LAST_NAME("LAST_NAME"),
    USER_NAME("USER_NAME"),
    ORDER_QUANTITY("ORDER_QUANTITY"),
    CURRENT_STATE_NAME("CURRENT_STATE_NAME"),
    CURRENT_LATTITUDE("CURRENT_LATTITUDE"),
    CURRENT_LONGITUDE("CURRENT_LONGITUDE"),
    FATHER_NAME("FATHER_NAME"),
    REFERENCE_NO("REFERENCE_NO"),
    PINCODE("PINCODE"),
    BRANCH_CODE("BRANCH_CODE"),
    BRANCH_NAME("BRANCH_NAME"),
    IFSC_CODE("IFSC_CODE"),
    MICR_CODE("MICR_CODE"),
    PROFILE_PIC("PROFILE_PIC"),
    REGISTERED_MOBILE("REGISTERED_MOBILE"),
    ACCOUNT_NO("ACCOUNT_NO"),
    USER_TYPE("USER_TYPE"),
    LANGUAGE("LANGUAGE"),
    EMAIL_ID("EMAIL_ID"),
    MOBILE("MOBILE"),
    COUNTRY_ID("COUNTRY_ID"),
    STATE_ID("STATE_ID"),
    CITY_ID("CITY_ID"),
    ADDRESS("ADDRESS"),
    DEVICE_ID("DEVICE_ID"),
    UPDATED_AT("UPDATED_AT"),
    STATUS("STATUS"),
    ORDER("ORDER"),
    CREATED_AT("CREATED_AT"),
    EMAIL_SMALL("email"),
    PASSWORD_SMALL("password"),
    CART_COUNT("CART_COUNT");


    private final String text;

    SharedPreferenceConstants(final String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }
}