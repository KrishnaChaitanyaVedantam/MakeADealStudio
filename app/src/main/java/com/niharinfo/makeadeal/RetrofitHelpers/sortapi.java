package com.niharinfo.makeadeal.RetrofitHelpers;

import com.niharinfo.makeadeal.helper.SortSubCategoryListProducts;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by subbarao on 30/5/15.
 */
public interface sortapi {
    @FormUrlEncoded
    @POST("/getsortproducts.php/")
    public void getCategoryProducts(@Field("soption") String alias, @Field("cat_id") String catid, @Field("subcat_id") String subcatid, Callback<SortSubCategoryListProducts> subcategoryProductList);

}
