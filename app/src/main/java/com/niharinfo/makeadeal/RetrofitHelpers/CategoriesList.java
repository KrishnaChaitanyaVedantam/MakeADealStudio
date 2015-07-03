package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by chaitanya on 20/5/15.
 */
public class CategoriesList {

    public List<CategoriesItem> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesItem> categories) {
        this.categories = categories;
    }

    @Expose
    private List<CategoriesItem> categories;

}
