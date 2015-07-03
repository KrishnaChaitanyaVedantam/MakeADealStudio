package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.AlertListData;
import com.niharinfo.makeadeal.RetrofitHelpers.AlertListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.CategoriesItem;
import com.niharinfo.makeadeal.RetrofitHelpers.CategoriesList;
import com.niharinfo.makeadeal.RetrofitHelpers.SubCategory;
import com.niharinfo.makeadeal.RetrofitHelpers.WhishListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WishListData;
import com.niharinfo.makeadeal.RetrofitHelpers.alertapi;
import com.niharinfo.makeadeal.RetrofitHelpers.categoriesapi;
import com.niharinfo.makeadeal.RetrofitHelpers.whislistapi;
import com.niharinfo.makeadeal.adapter.ExpListAdapter;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CategoriesActivity extends Activity {

    ExpListAdapter listAdapter;
    ExpandableListView expListView;
    List<CategoriesItem> listCategoriesDataHeader;
    HashMap<CategoriesItem,List<SubCategory>> listSubCategoriesDataChild;
    SharedPreferences preferences;
    List<WhishListResponse> listResponses;
    List<AlertListResponse> alertListResponses;
    static MenuItem wishlist_menuitem;
    MenuItem alert_menuitem;
    public static int wish_count=0;
    public static int alert_count=0;
    public static int compare_count = 0;
    public ActionBar actionBar;
    public static TextView wish_ui_hot,alert_ui_hot,compare_ui_hot;
    SharedPreferences sharedpreferences;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                System.exit(1);
            }
        });

        sharedpreferences = getSharedPreferences(ServiceHandlers.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String id=sharedpreferences.getString(ServiceHandlers.USER_ID,null);

        if(id==null) {
            editor.putString(ServiceHandlers.USER_ID, ServiceHandlers.USERID);
            editor.commit();
        }

        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        //Globals.counter = 1;
        expListView = (ExpandableListView)findViewById(R.id.expLv);
        progressBar = (ProgressBar)findViewById(R.id.pbCategories);
        preferences = getSharedPreferences(ServiceHandlers.MyPREFERENCES,MODE_PRIVATE);
        listCategoriesDataHeader = new ArrayList<CategoriesItem>();
        listSubCategoriesDataChild = new HashMap<CategoriesItem,List<SubCategory>>();
        listResponses = new ArrayList<WhishListResponse>();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(width - getDipsFromPixel(50), width
                    - getDipsFromPixel(10));
        } else {
            expListView.setIndicatorBoundsRelative(width - getDipsFromPixel(50), width
                    - getDipsFromPixel(10));
        }
        prepareActualData();
        wishListCount();
        alertListCount();

       /* expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //expListView.setSelection(groupPosition);
                return false;
            }
        });*/
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SubCategory currentITem = (SubCategory) listSubCategoriesDataChild.get(listCategoriesDataHeader.get(groupPosition)).get(childPosition);
                Globals.isNavClick = 1;
                String alias = currentITem.getAlias();
                String catid = currentITem.getCategoryId();
                String subcatid = currentITem.getId();
                //ProductsMainFragment.setTitle(alias);
                Globals.isFromCategories = 1;
                Intent i = new Intent(CategoriesActivity.this, ProductsActivity.class);
                i.putExtra("alias", alias);
                i.putExtra("catid", catid);
                i.putExtra("subcatid", subcatid);
                startActivity(i);
                return false;
            }
        });

        /*expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = 1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition!=previousGroup){
                    *//*expListView.collapseGroup(previousGroup);*//*
                    previousGroup = groupPosition;
                    int groupCount = listAdapter.getGroupCount();
                    for(int i=0;i<groupCount;i++){
                        expListView.collapseGroup(i);
                    }
                }

            }
        });*/
    }

    public int getDipsFromPixel(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale+0.5f );
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categories, menu);
        final View wish_ui = menu.findItem(R.id.action_wishlist_log).getActionView();
        final View alert_ui_ho = menu.findItem(R.id.action_offers_log).getActionView();
        final View compare_ui = menu.findItem(R.id.action_compare_log).getActionView();
        wish_ui_hot = (TextView)wish_ui.findViewById(R.id.wishlists_hotlist_hot);
        alert_ui_hot = (TextView)alert_ui_ho.findViewById(R.id.alert_hotlist_hot);
        compare_ui_hot = (TextView)compare_ui.findViewById(R.id.wish_hotlist_hot);
        compareUpdateCount(Globals.counter);
        wish_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wish = new Intent(CategoriesActivity.this,WishListActivity.class);
                startActivity(wish);
            }
        });
        alert_ui_ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert = new Intent(CategoriesActivity.this,AlertActivity.class);
                startActivity(alert);
            }
        });
        compare_ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compare = new Intent(CategoriesActivity.this,CompareProductActivity.class);
                startActivity(compare);
            }
        });
        return true;
    }

    public  void wishListCount(){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        whislistapi gitapi = restAdapter.create(whislistapi.class);
        gitapi.whishlist(ServiceHandlers.USERID, new Callback<WishListData>() {
            @Override
            public void success(WishListData wishListData, Response response) {
                listResponses = wishListData.getWhishListResponses();
                wish_count = listResponses.size();
                wishupdateHotCount(wish_count);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    public  void alertListCount(){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        alertapi gitapi = restAdapter.create(alertapi.class);
        gitapi.whishlist(ServiceHandlers.USERID, new Callback<AlertListData>() {
            @Override
            public void success(AlertListData alertListData, Response response) {
                alertListResponses = alertListData.getalertListResponses();
                alert_count = alertListResponses.size();
                alertupdateHotCount(alert_count);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        wishListCount();
        alertListCount();
        compareUpdateCount(Globals.counter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        switch (id){
            case R.id.action_search_log:
                Intent i = new Intent(CategoriesActivity.this,SearchActivity.class);
                startActivity(i);
                break;
            case R.id.action_logout:
                if(id == R.id.action_logout){
                    //Globals.signout++;
                    LoginActivity loginActivity = new LoginActivity();
                    SharedPreferences pref = getSharedPreferences(ServiceHandlers.MyPREFERENCES, Context.MODE_PRIVATE);
                    if(pref.contains(ServiceHandlers.USER_ID)){
                        if(pref.getString(ServiceHandlers.USER_ID,"").length()>0){
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(ServiceHandlers.USER_ID, "");
                            editor.commit();
                        }
                    }
                            loginActivity.signOutFromGplus();
                            Intent intent = new Intent(CategoriesActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                }
                break;
            case R.id.action_wishlist_log:
                break;

            case R.id.action_offers_log:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void wishupdateHotCount(int new_hot_number) {
        wish_count = new_hot_number;
        if (wish_ui_hot == null) return;
        if (new_hot_number == 0)
            wish_ui_hot.setVisibility(View.INVISIBLE);
        else {
            wish_ui_hot.setVisibility(View.VISIBLE);
            wish_ui_hot.setText(Integer.toString(new_hot_number));
        }

    }

    public static void alertupdateHotCount(int new_hot_number) {
        wish_count = new_hot_number;
        if (wish_ui_hot == null) return;
        if (new_hot_number == 0)
            alert_ui_hot.setVisibility(View.INVISIBLE);
        else {
            alert_ui_hot.setVisibility(View.VISIBLE);
            alert_ui_hot.setText(Integer.toString(new_hot_number));
        }

    }

    public static void compareUpdateCount(int n){
        compare_count = n;
        if (compare_ui_hot == null) return;
        if (compare_count==0)
            compare_ui_hot.setVisibility(View.INVISIBLE);
        else {
            compare_ui_hot.setVisibility(View.VISIBLE);
            compare_ui_hot.setText(Integer.toString(compare_count));
        }
    }

    private void prepareActualData(){
        progressBar.setVisibility(View.VISIBLE);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        categoriesapi categoriesapi = restAdapter.create(categoriesapi.class);
        categoriesapi.getCategories("", new Callback<CategoriesList>() {
            @Override
            public void success(CategoriesList categoriesList, Response response) {
                listCategoriesDataHeader = categoriesList.getCategories();
                for(int i=0;i<listCategoriesDataHeader.size();i++){
                    listSubCategoriesDataChild.put(listCategoriesDataHeader.get(i),listCategoriesDataHeader.get(i).getSubcategories());
                }
                listAdapter = new ExpListAdapter(CategoriesActivity.this, listCategoriesDataHeader, listSubCategoriesDataChild);
                expListView.setAdapter(listAdapter);
                expListView.expandGroup(0);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                //String msg = error.getResponse().toString();
            }
        });
    }
}
