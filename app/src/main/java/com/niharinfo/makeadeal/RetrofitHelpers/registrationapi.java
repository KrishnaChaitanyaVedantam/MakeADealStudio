package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chaitanya on 19/5/15.
 */
public interface registrationapi {
    @FormUrlEncoded
    @POST("/register.php/")
    public void UserRegistration(@Field("username") String uname,@Field("email") String email,@Field("password") String password,@Field("phoneno") String phoneNumber, Callback<RegistrationResponse> response);
}
