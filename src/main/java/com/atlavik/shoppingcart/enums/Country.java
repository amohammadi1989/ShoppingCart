package com.atlavik.shoppingcart.enums;

/**
 * Created By: Ali Mohammadi
 * Date: 12 Jun, 2021
 */
public enum  Country {

    India("India"),US("United States"),
    Brazil("Brazil"),Indonesia("Indonesia");

    String description;
    Country(String desc) {
        this.description=desc;
    }
    public  String getDescripton(){
        return this.description;
    }
}
