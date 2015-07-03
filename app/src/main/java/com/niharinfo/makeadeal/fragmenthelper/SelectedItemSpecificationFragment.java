package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.SpicApiService;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.adapter.ExpSpecificationAdapter;
import com.niharinfo.makeadeal.helper.Details;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.Productspecifications;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SpecificatoinsData;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 25/5/15.
 */
public class SelectedItemSpecificationFragment extends Fragment {

    static List<SpecificatoinsData> specarray = new ArrayList<SpecificatoinsData>();
    ExpandableListView expListView;
    List<String> listDataHeader;
    ExpSpecificationAdapter adopter;
    List<Details> detailarray = new ArrayList<Details>();
    HashMap<String, List<Details>> listDataChild;
    ImageView imgCloseSpecification;
    SubCategoryProduct subCategoryProduct;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selected_item_specification_fragment,container,false);
        expListView = (ExpandableListView)v.findViewById(R.id.expSpecification);
        imgCloseSpecification = (ImageView)v.findViewById(R.id.imgSpecificationClose);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Details>>();
        subCategoryProduct = getActivity().getIntent().getParcelableExtra("SubCategory");
        String productID = subCategoryProduct.getProductID();
        RestAdapter restadopter = new RestAdapter.Builder().setEndpoint(
                ServiceHandlers.retrofitLiveApi).build();
        SpicApiService api = restadopter.create(SpicApiService.class);
        api.getSpecifications(productID, new Callback<Productspecifications>() {

            @Override
            public void success(Productspecifications arg0, Response arg1) {
                // TODO Auto-generated method stub

                specarray = arg0.getSpecifications();

                for (int i = 0; i < specarray.size(); i++) {

                    String header = specarray.get(i).getAttr_group_name();
                    listDataHeader.add(header);
                    detailarray = specarray.get(i).getDetails();
                    listDataChild.put(header, specarray.get(i).getDetails());



                }
                adopter = new ExpSpecificationAdapter(
                        getActivity(), listDataHeader, listDataChild);
                expListView.setAdapter(adopter);
                for (int i = 0; i < listDataHeader.size(); i++) {
                    expListView.expandGroup(i);
                }

            }

            @Override
            public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub

            }
        });
        imgCloseSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedProductActivity.i = 1;
                SelectedProductActivity.btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                getActivity().getFragmentManager().popBackStack();
            }
        });
        return v;
    }
}
