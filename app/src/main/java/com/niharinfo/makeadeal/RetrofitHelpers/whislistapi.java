package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by subbarao on 28/5/15.
 */
public interface whislistapi {

    @FormUrlEncoded
    @POST("/getwishlist.php/")
    public void whishlist(@Field("userid") String username, Callback<WishListData> response);
}
