package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.Expose;

/**
 * Created by chaitanya on 5/6/15.
 */
public class GooglePlusData {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOauth_provider() {
        return oauth_provider;
    }

    public void setOauth_provider(String oauth_provider) {
        this.oauth_provider = oauth_provider;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Expose
    private String id;
    @Expose
    private String user_name;
    @Expose
    private String email;
    @Expose
    private String oauth_provider;
    @Expose
    private String response;
}
