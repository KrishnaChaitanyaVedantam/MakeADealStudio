package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by subbarao on 29/5/15.
 */
public class AlertListData {

    public List<AlertListResponse> getalertListResponses() {

        return alertListResponses;
    }

    public void setAlertListResponses(List<AlertListResponse> alertListResponses) {

        this.alertListResponses = alertListResponses;
    }

    @SerializedName("key")
    private List<AlertListResponse> alertListResponses=new ArrayList<AlertListResponse>();
}
