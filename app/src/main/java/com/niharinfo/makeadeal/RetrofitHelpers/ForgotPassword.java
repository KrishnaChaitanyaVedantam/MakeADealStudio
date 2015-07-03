package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.Expose;

/**
 * Created by chaitanya on 20/5/15.
 */
public class ForgotPassword {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Expose
    private String message;

}
