package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.SerializedName;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by subbarao on 30/5/15.
 */
public class SortSubCategoryListProducts {
    @SerializedName("key")
    private List<SubCategoryProduct> subcatContent = new ArrayList<SubCategoryProduct>();

    public List<SubCategoryProduct> getSubcatContent() {
        return subcatContent;
    }

    public void setSubcatContent(List<SubCategoryProduct> subcatContent) {
        this.subcatContent = subcatContent;
    }

}
