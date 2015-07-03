package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by subbarao on 28/5/15.
 */
public interface wishlistaddapi {
    @FormUrlEncoded
    @POST("/addtowishlist.php/")
    public void whishlistadd(@Field("userid") String username, @Field("productid") String password, Callback<WhistListAddReponse> response);

    //error
    //Already Added to Wishlist
    //sucess

}
