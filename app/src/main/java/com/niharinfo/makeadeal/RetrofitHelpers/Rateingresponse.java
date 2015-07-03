package com.niharinfo.makeadeal.RetrofitHelpers;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rateingresponse {
	@Expose
	String response;
	@SerializedName("tot_ratings")
	String totalRatings;

	public String getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(String totalRatings) {
		this.totalRatings = totalRatings;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
