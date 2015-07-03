package com.niharinfo.makeadeal.RetrofitHelpers;

import com.niharinfo.makeadeal.helper.GooglePlusData;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 5/6/15.
 */
public interface gloginapi {

    @FormUrlEncoded
    @POST("/google.php/")
    public void login(@Field("email") String email,@Field("username") String uname,@Field("oauth_provider") String oauth, Callback<GooglePlusData> response);

}
