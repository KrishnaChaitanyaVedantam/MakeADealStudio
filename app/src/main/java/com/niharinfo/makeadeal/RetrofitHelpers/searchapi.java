package com.niharinfo.makeadeal.RetrofitHelpers;

import com.niharinfo.makeadeal.helper.SortSubCategoryListProducts;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 12/6/15.
 */
public interface searchapi {
    @FormUrlEncoded
    @POST("/getsearch.php/")
    public void getSearchItems(@Field("keyword") String key, Callback<SortSubCategoryListProducts> response);
}
