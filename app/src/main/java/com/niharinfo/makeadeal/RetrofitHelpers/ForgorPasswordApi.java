package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 20/5/15.
 */
public interface ForgorPasswordApi {

    @FormUrlEncoded
    @POST("/forgot.php/")
    public void GetNewPassword(@Field("email") String email, Callback<ForgotPassword> response);

}
