package com.niharinfo.makeadeal.RetrofitHelpers;

import com.niharinfo.makeadeal.helper.CompareProductsList;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 1/6/15.
 */
public interface compareprouductpriceapi {
    @FormUrlEncoded
    @POST("/getsellers.php/")
    public void getSeller(@Field("productid") String product,Callback<CompareProductsList> response);
}
