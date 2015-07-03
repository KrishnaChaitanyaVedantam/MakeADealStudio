package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.Expose;

/**
 * Created by subbarao on 28/5/15.
 */
public class WhishListResponse {

    @Expose
    private String pid;
    @Expose
    private String image;
    @Expose
    private String title;
    @Expose
    private String brand;
    @Expose
    private String alias;
    @Expose
    private String price;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Expose
    private String stock;



    public String getPid() {
        return pid;
    }


    public void setPid(String pid) {
        this.pid = pid;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getAlias() {
        return alias;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
