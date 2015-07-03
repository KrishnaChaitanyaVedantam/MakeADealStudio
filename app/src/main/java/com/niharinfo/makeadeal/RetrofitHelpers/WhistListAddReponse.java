package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.Expose;

/**
 * Created by subbarao on 28/5/15.
 */
public class WhistListAddReponse {

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    @Expose
    private String response;
}
