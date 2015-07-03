package com.niharinfo.makeadeal.RetrofitHelpers;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RateApiservice {
	@FormUrlEncoded
	@POST("/storerating.php")
	void getResponse(@Field("productid") String productid, @Field("userid") String userid, @Field("cur_rat") String cur_rat, Callback<Rateingresponse> responserate);

}
