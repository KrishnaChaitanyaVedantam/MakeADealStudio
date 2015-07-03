package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.SubCategoryListProducts;
import com.niharinfo.makeadeal.RetrofitHelpers.productscategoryapi;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.adapter.ProductLargeListAdapter;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 19/5/15.
 */
public class ProductsLargeListFragment extends Fragment implements View.OnClickListener{

    ListView lstProducts;
    ImageButton btnLoadGrid;
    ListView lstNormalProducts;
    ProductLargeListAdapter itemAdapter;
    List<SubCategoryProduct> subCategoryProducts;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.products_large_list_fragment,container,false);
        lstProducts = (ListView)v.findViewById(R.id.lstProductsLarge);
        btnLoadGrid = (ImageButton)v.findViewById(R.id.btnLoadGridLayout);
        progressBar = (ProgressBar)v.findViewById(R.id.pbLargeListProducts);
        btnLoadGrid.setOnClickListener(this);
        btnLoadGrid.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String alias = getActivity().getIntent().getStringExtra("alias");
        RestAdapter restSubProducts = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        productscategoryapi pCategoryApi = restSubProducts.create(productscategoryapi.class);
        pCategoryApi.getCategoryProducts(alias, new Callback<SubCategoryListProducts>() {
            @Override
            public void success(SubCategoryListProducts subCategoryListProducts, Response response) {
                subCategoryProducts = subCategoryListProducts.getSubcatContent();
                itemAdapter = new ProductLargeListAdapter(getActivity(), R.layout.products_normal_list_item, subCategoryProducts);
                lstProducts.setAdapter(itemAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                btnLoadGrid.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        lstProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            case R.id.btnLoadGridLayout:
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container,new ProductsGridFragment(),"GridFragment").commit();
                break;
        }
    }
}
