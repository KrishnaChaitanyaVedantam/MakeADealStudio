package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 18/5/15.
 */
public interface loginapi {
    @FormUrlEncoded
    @POST("/login.php/")
    public void login(@Field("username") String username,@Field("password") String password, Callback<LoginResponse> response);
    public void getFeed();
}
