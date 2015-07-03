package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.SerializedName;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitanya on 22/5/15.
 */
public class SubCategoryListProducts {

    @SerializedName("subcat_content")
    private List<SubCategoryProduct> subcatContent = new ArrayList<SubCategoryProduct>();

    public List<SubCategoryProduct> getSubcatContent() {
        return subcatContent;
    }

    public void setSubcatContent(List<SubCategoryProduct> subcatContent) {
        this.subcatContent = subcatContent;
    }
}
