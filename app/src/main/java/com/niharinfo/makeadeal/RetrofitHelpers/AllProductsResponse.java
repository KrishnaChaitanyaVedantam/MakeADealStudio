package com.niharinfo.makeadeal.RetrofitHelpers;

import com.google.gson.annotations.Expose;
import com.niharinfo.makeadeal.helper.Contributor;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitanya on 27/5/15.
 */
public class AllProductsResponse {

    @Expose
    private List<SubCategoryProduct> products=new ArrayList<SubCategoryProduct>();

    public List<SubCategoryProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SubCategoryProduct> products) {
        this.products = products;
    }

}
