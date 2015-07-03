package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.niharinfo.makeadeal.RetrofitHelpers.SubCategoryListProducts;
import com.niharinfo.makeadeal.RetrofitHelpers.productscategoryapi;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.adapter.ProductGridItemAdapter;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.niharinfo.makeadeal.R;

/**
 * Created by chaitanya on 19/5/15.
 */
public class ProductsGridFragment extends Fragment implements View.OnClickListener{

    GridView grdProducts;
    ImageButton imgLoadList;
    ProductGridItemAdapter gridItemAdapter;
    List<SubCategoryProduct> subCategoryProducts;
    String alias;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.products_category_grid_fragment,container,false);
        grdProducts = (GridView)v.findViewById(R.id.grdProducts);
        imgLoadList = (ImageButton)v.findViewById(R.id.btnLoadLayout);
        progressBar = (ProgressBar)v.findViewById(R.id.pbGridProducts);
        progressBar.setVisibility(View.VISIBLE);
        imgLoadList.setVisibility(View.INVISIBLE);
        subCategoryProducts = new ArrayList<SubCategoryProduct>();
        if(Globals.isNavClick==1){
            alias = getActivity().getIntent().getStringExtra("alias");
        }else{
            alias = Globals.aliasNav;
        }

        RestAdapter restSubProducts = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        productscategoryapi pCategoryApi = restSubProducts.create(productscategoryapi.class);
        pCategoryApi.getCategoryProducts(alias, new Callback<SubCategoryListProducts>() {
            @Override
            public void success(SubCategoryListProducts subCategoryListProducts, Response response) {
                subCategoryProducts = subCategoryListProducts.getSubcatContent();
                gridItemAdapter = new ProductGridItemAdapter(getActivity(), R.layout.grid_product_item, subCategoryProducts);
                grdProducts.setAdapter(gridItemAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                imgLoadList.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        imgLoadList.setOnClickListener(this);
        grdProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = subCategoryProducts.get(position).getAliasName();
                Intent i = new Intent(getActivity(), SelectedProductActivity.class);
                i.putExtra("SubCategory",subCategoryProducts.get(position));
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoadLayout:
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container,new ProductsNormalListFragment(),"Normal List Fragment").commit();
                break;
        }
    }
}
