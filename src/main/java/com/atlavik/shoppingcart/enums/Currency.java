package com.atlavik.shoppingcart.enums;
/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public enum Currency {
     USD("U.S. Dollar"), EUR("European Euro"),
     JPY("Japanese Yen"),GBP("British Pound"),
     CHF("Swiss Franc"), CAD("Canadian Dollar");

     String description;

    Currency(String desc) {
        this.description=desc;
    }

    public  String getDescription(){
        return this.description;
    }
}
