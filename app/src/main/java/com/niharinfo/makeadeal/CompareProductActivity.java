package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.niharinfo.makeadeal.RetrofitHelpers.compareapi;
import com.niharinfo.makeadeal.adapter.CompareExpAdapter;
import com.niharinfo.makeadeal.adapter.CompareProductsListAdapter;
import com.niharinfo.makeadeal.fragmenthelper.FragmentCompareProducts;
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


public class CompareProductActivity extends Activity {

    public static CompareExpAdapter lstExpAdapter;
    ListView lstCompare;
    //ExpandableListView expLvCompare;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    CompareProductsListAdapter lstProductsAdapter;
    List<CompareData> compareArray;
    String compareProductId;
    public static FragmentManager fragmentManager;
    TextView txtMessage;
    ActionBar actionBar;
    ExpandableListView expLvCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_product);
        expLvCompare = (ExpandableListView)findViewById(R.id.expLvCompare);
        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Compare Products");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        lstCompare = (ListView)findViewById(R.id.lstCompare);
        /*txtMessage = (TextView)findViewById(R.id.txtCompareMessage);
        txtMessage.setVisibility(View.INVISIBLE);
        compareArray = new ArrayList<CompareData>();
        if(Globals.compareList.size()>0){
            txtMessage.setVisibility(View.INVISIBLE);
        }else{
            txtMessage.setVisibility(View.VISIBLE);
        }*/
        //prepareData(CompareProductActivity.this);

        //prepareListData();
        if(Globals.compareList.size()>0){
            compareProductId = Globals.compareList.get(0).getProductID();
            for(int i=1;i<Globals.compareList.size();i++){
                compareProductId = compareProductId+","+Globals.compareList.get(i).getProductID();
            }
            prepareCompareData(compareProductId);
            lstExpAdapter = new CompareExpAdapter(this, listDataHeader, listDataChild);
            lstProductsAdapter = new CompareProductsListAdapter(CompareProductActivity.this,R.layout.list_compare_item, Globals.compareList);
            expLvCompare.setAdapter(lstExpAdapter);
            lstCompare.setAdapter(lstProductsAdapter);
        }

        //getFragmentManager().beginTransaction().add(R.id.frmContainerCompareProducts,new FragmentCompareProducts(),"Compare Products").commit();
    }

   /* public void prepareData(Activity activity){

        activity.getFragmentManager().beginTransaction().add(R.id.frmContainerCompareProducts,new FragmentCompareProducts(),"Compare Products").commit();
    }*/

    public int getDipsFromPixel(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale+0.5f );
    }

    public void prepareCompareData(String productId){
        //CompareExpAdapter lstExpAdapter;
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String,List<String>>();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
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
        compareProductsApi.getSpecifications(productId, new Callback<CompareData>() {
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
                CompareExpAdapter lstExpAdapter = new CompareExpAdapter(CompareProductActivity.this, listDataHeader, listDataChild);
                expLvCompare.setAdapter(lstExpAdapter);
                lstExpAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compare_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Globals.compareList.clear();
        Globals.counter = 0;
        CategoriesActivity.compare_count=0;
    }
}
