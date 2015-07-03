package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chaitanya on 13/6/15.
 */
public class Specifications {
    @SerializedName("attr_name")
    private String attributeName;
    @SerializedName("attr_desc")
    private List<String> attributeDescription;

    public String getAttributeName() {
        return attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    public List<String> getAttributeDescription() {
        return attributeDescription;
    }
    public void setAttributeDescription(List<String> attributeDescription) {
        this.attributeDescription = attributeDescription;
    }
}
