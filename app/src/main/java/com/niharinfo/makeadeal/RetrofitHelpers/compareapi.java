package com.niharinfo.makeadeal.RetrofitHelpers;


import com.niharinfo.makeadeal.helper.CompareData;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 13/6/15.
 */
public interface compareapi {
    @FormUrlEncoded
    @POST("/compare.php/")
    public void getSpecifications(@Field("id") String product_id,
                                  Callback<CompareData> responce);
}
