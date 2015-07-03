package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.niharinfo.makeadeal.RetrofitHelpers.AlertListData;
import com.niharinfo.makeadeal.RetrofitHelpers.AlertListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.alertapi;
import com.niharinfo.makeadeal.adapter.AlertAdapter;
import com.niharinfo.makeadeal.helper.ServiceHandlers;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBarUtils;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by subbarao on 29/5/15.
 */
public class AlertActivity extends Activity {

    ImageButton btnLoadLargeList;
    ListView lstNormalProducts;
    public AlertAdapter itemAdapter;
    List<AlertListResponse> listResponses;
    ActionBar actionBar;
    ProgressBar progressBar;
    public static TextView txtAlertItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_normal_list_fragment);
        actionBar = getActionBar();
        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Alerts");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        listResponses = new ArrayList<AlertListResponse>();
        lstNormalProducts = (ListView)findViewById(R.id.lstProductsNormal);
        btnLoadLargeList = (ImageButton)findViewById(R.id.btnLoadLargeListLayout);
        progressBar = (ProgressBar)findViewById(R.id.pbNormalListProducts);
        txtAlertItems = (TextView)findViewById(R.id.txtAlertItems);
        progressBar.setVisibility(View.VISIBLE);
        btnLoadLargeList.setVisibility(View.INVISIBLE);
        txtAlertItems.setVisibility(View.INVISIBLE);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        alertapi gitapi = restAdapter.create(alertapi.class);
        gitapi.whishlist(ServiceHandlers.USERID, new Callback<AlertListData>() {
            @Override
            public void success(AlertListData alertListData, Response response) {
                listResponses = alertListData.getalertListResponses();
                itemAdapter = new AlertAdapter(getApplicationContext(), R.layout.wishlist_item_layout, listResponses);
                lstNormalProducts.setAdapter(itemAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                if(listResponses.size()==0){
                    txtAlertItems.setVisibility(View.VISIBLE);
                }else{
                    txtAlertItems.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }



}
