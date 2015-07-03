package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitanya on 1/6/15.
 */
public class CompareProductsList {
    public List<Compare> getCompareProductList() {
        return compareProductList;
    }

    public void setCompareProductList(List<Compare> compareProductList) {
        this.compareProductList = compareProductList;
    }

    @SerializedName("key")
    private List<Compare> compareProductList = new ArrayList<Compare>();
}
