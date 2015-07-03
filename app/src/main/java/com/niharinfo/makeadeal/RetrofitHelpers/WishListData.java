package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by subbarao on 28/5/15.
 */
public class WishListData {
    public List<WhishListResponse> getWhishListResponses() {
        return whishListResponses;
    }

    public void setWhishListResponses(List<WhishListResponse> whishListResponses) {
        this.whishListResponses = whishListResponses;
    }

    @SerializedName("key")
    private List<WhishListResponse> whishListResponses=new ArrayList<WhishListResponse>();

}
