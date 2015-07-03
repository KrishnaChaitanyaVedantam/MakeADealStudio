package com.niharinfo.makeadeal.RetrofitHelpers;

import com.niharinfo.makeadeal.helper.Reviewrespons;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

public interface getuserreviews {

	@FormUrlEncoded
	@POST("/getreviews.php/")
	void getKey(@Field("productid") String pid, Callback<Reviewrespons> responce);
}
