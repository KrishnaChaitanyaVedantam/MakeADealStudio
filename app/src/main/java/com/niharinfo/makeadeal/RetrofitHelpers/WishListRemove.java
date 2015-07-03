package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by subbarao on 28/5/15.
 */
public interface WishListRemove {
    @FormUrlEncoded
    @POST("/removewishlist.php/")
    public void whishlistadd(@Field("userid") String username, @Field("productid") String password, Callback<WhistListAddReponse> response);


}
