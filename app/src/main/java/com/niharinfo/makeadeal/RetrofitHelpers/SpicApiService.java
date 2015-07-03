package com.niharinfo.makeadeal.RetrofitHelpers;


import com.niharinfo.makeadeal.helper.Productspecifications;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface SpicApiService {
@FormUrlEncoded
@POST("/specifications.php/")
void getSpecifications(@Field("product_id") String product_id,
					   Callback<Productspecifications> responce);

}
