package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 20/5/15.
 */
public interface categoriesapi {

    @FormUrlEncoded
    @POST("/getcategory.php/")
    public void getCategories(@Field("") String item,Callback<CategoriesList> response);

}
