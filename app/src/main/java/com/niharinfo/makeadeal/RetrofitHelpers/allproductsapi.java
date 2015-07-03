package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 27/5/15.
 */
public interface allproductsapi {
    @FormUrlEncoded
    @POST("/allproducts.php/")
    void getProducts(@Field("") String Title, Callback<AllProductsResponse> response );
}
