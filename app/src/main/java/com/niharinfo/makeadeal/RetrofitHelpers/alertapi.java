package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by subbarao on 29/5/15.
 */
public interface alertapi {

    @FormUrlEncoded
    @POST("/getpricealerts.php/")
    public void whishlist(@Field("userid") String username, Callback<AlertListData> response);
}
