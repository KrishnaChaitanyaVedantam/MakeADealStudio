package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chaitanya on 8/6/15.
 */
public class UserRating {

    @SerializedName("userrating")
    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
