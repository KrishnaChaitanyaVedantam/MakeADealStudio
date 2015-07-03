package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.niharinfo.makeadeal.CompareProductActivity;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.compareapi;
import com.niharinfo.makeadeal.adapter.CompareExpAdapter;
import com.niharinfo.makeadeal.helper.CompareData;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.Specifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 13/6/15.
 */
public class FragmentCompareProducts extends Fragment {

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandableListView expLvCompare;
    String compareProductId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compare_products,container,false);
        expLvCompare = (ExpandableListView)v.findViewById(R.id.expLvCompare);
        if(Globals.compareList.size()>0){
            compareProductId = Globals.compareList.get(0).getProductID();
            for(int i=1;i<Globals.compareList.size();i++){
                compareProductId = compareProductId+","+Globals.compareList.get(i).getProductID();
            }
            prepareCompareData(compareProductId);
        }
        return v;
    }

    public int getDipsFromPixel(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale+0.5f );
    }

    public void prepareCompareData(String productID){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String,List<String>>();

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expLvCompare.setIndicatorBounds(width - getDipsFromPixel(50), width
                    - getDipsFromPixel(10));
        } else {
            expLvCompare.setIndicatorBoundsRelative(width - getDipsFromPixel(50), width
                    - getDipsFromPixel(10));
        }
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        compareapi compareProductsApi = restAdapter.create(compareapi.class);
        compareProductsApi.getSpecifications(productID, new Callback<CompareData>() {
            @Override
            public void success(CompareData compareData, Response response) {
                List<String> productName = compareData.getProductName();
                List<String> pN = productName;
                List<String> price = compareData.getPrice();
                List<String> discount = compareData.getDiscount();
                List<String> brand = compareData.getBrand();
                List<Specifications> specs = compareData.getSpecifications();
                for (int i = 0; i < specs.size(); i++) {
                    String spec = specs.get(i).getAttributeName();
                    listDataHeader.add(specs.get(i).getAttributeName());
                }
                for (int j = 0; j < listDataHeader.size(); j++) {
                    listDataChild.put(specs.get(j).getAttributeName(), specs.get(j).getAttributeDescription());
                }
                CompareExpAdapter lstExpAdapter = new CompareExpAdapter(getActivity(), listDataHeader, listDataChild);
                expLvCompare.setAdapter(lstExpAdapter);
                lstExpAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
