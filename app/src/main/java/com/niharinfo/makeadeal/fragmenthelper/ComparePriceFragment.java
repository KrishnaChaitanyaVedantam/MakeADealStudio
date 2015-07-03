package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.compareprouductpriceapi;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.adapter.ComparePriceAdapter;
import com.niharinfo.makeadeal.helper.Compare;
import com.niharinfo.makeadeal.helper.CompareProductsList;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 25/5/15.
 */
public class ComparePriceFragment extends Fragment {

    ListView lstCompare;
    ComparePriceAdapter mAdapter;
    SubCategoryProduct subCategoryProduct;
    List<Compare> compareList;
    ImageView imgCloseCompare;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compare_price, container, false);
        lstCompare = (ListView)v.findViewById(R.id.lstComparePrice);
        compareList = new ArrayList<Compare>();
        imgCloseCompare = (ImageView)v.findViewById(R.id.imgCompareClose);
        imgCloseCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedProductActivity.i = 1;
                SelectedProductActivity.btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                SelectedProductActivity.isfragmentopen=1;
                getActivity().getFragmentManager().popBackStack();
            }
        });
        subCategoryProduct = getActivity().getIntent().getParcelableExtra("SubCategory");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        compareprouductpriceapi priceapi = restAdapter.create(compareprouductpriceapi.class);
        priceapi.getSeller(subCategoryProduct.getProductID(), new Callback<CompareProductsList>() {
            @Override
            public void success(CompareProductsList compareProductsList, Response response) {
                compareList = compareProductsList.getCompareProductList();
                mAdapter = new ComparePriceAdapter(getActivity(),R.layout.compare_price_item,compareList);
                lstCompare.setAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        return v;
    }

}
