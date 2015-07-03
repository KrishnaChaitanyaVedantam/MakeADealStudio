package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chaitanya on 19/5/15.
 */
public class RegistrationResponse {

    @SerializedName("response")
    private String registrationResponse;

    public String getRegistrationResponse(){
        return registrationResponse;
    }

    public void setRegistrationResponse(String registrationResponse){
        this.registrationResponse = registrationResponse;
    }

}
