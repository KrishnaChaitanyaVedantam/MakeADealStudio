package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chaitanya on 20/5/15.
 */
public class CategoriesItem {

    @SerializedName("cat_id")
    private String catId;
    @SerializedName("cat_name")
    private String catName;
    @SerializedName("parent_id")
    private String parentId;
    @SerializedName("alias")
    private String alias;
    @SerializedName("metakeys")
    private String metakeys;
    @SerializedName("metadesc")
    private String metadesc;

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }

    @Expose
    private String icon_path;
    @SerializedName("subcategories")
    private List<SubCategory> subcategories = new ArrayList<SubCategory>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMetakeys() {
        return metakeys;
    }

    public void setMetakeys(String metakeys) {
        this.metakeys = metakeys;
    }

    public String getMetadesc() {
        return metadesc;
    }

    public void setMetadesc(String metadesc) {
        this.metadesc = metadesc;
    }

    public List<SubCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
