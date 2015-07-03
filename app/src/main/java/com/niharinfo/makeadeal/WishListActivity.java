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

import com.niharinfo.makeadeal.RetrofitHelpers.WhishListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WishListData;
import com.niharinfo.makeadeal.RetrofitHelpers.whislistapi;
import com.niharinfo.makeadeal.adapter.WishListAdapter;
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


public class WishListActivity extends Activity {

    ListView lstNormalProducts;
    public WishListAdapter itemAdapter;
    List<WhishListResponse> listResponses;
    ActionBar actionBar;
    ProgressBar progressBar;
    public static TextView txtWishlistItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        listResponses = new ArrayList<WhishListResponse>();
        actionBar = getActionBar();
        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Wishlist");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        lstNormalProducts = (ListView)findViewById(R.id.lstProductsWishlist);
        progressBar = (ProgressBar)findViewById(R.id.pbWishList);
        txtWishlistItems = (TextView)findViewById(R.id.txtWishlistitems);
        progressBar.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        whislistapi gitapi = restAdapter.create(whislistapi.class);
        gitapi.whishlist(ServiceHandlers.USERID, new Callback<WishListData>() {
            @Override
            public void success(WishListData wishListData, Response response) {
                listResponses = wishListData.getWhishListResponses();
                itemAdapter = new WishListAdapter(getApplicationContext(), R.layout.wishlist_item_layout, listResponses);
                lstNormalProducts.setAdapter(itemAdapter);
                progressBar.setVisibility(View.INVISIBLE);
                if(listResponses.size()==0){
                    txtWishlistItems.setVisibility(View.VISIBLE);
                }else{
                    txtWishlistItems.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }
}
