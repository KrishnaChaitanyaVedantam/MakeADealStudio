package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by chaitanya on 22/5/15.
 */
public interface productscategoryapi {
    @FormUrlEncoded
    @POST("/subcategory.php/")
    public void getCategoryProducts(@Field("alias") String alias,Callback<SubCategoryListProducts> subcategoryProductList);
}
