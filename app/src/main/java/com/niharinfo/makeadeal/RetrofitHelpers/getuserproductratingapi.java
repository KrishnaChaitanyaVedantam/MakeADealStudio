package com.niharinfo.makeadeal.RetrofitHelpers;

import com.niharinfo.makeadeal.helper.UserRating;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 8/6/15.
 */
public interface getuserproductratingapi {
    @FormUrlEncoded
    @POST("/getuserrating.php/")
    public void getuserratings(@Field("productid") String productID,@Field("userid") String userID,Callback<UserRating> response);
}
