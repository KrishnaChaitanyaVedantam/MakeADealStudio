package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.niharinfo.makeadeal.adapter.NavDrawerListAdapter;
import com.niharinfo.makeadeal.fragmenthelper.ProductsMainFragment;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.NavDrawerItem;
import com.niharinfo.makeadeal.helper.ServiceHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ProductsActivity extends FragmentActivity{

    ExpListAdapter listAdapter;
    //ExpandableListView expListView;
    List<CategoriesItem> listCategoriesDataHeader;
    HashMap<CategoriesItem,List<SubCategory>> listSubCategoriesDataChild;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    ActionBar actionBar;
    int badgeCount = 10;
    private static final int SAMPLE2_ID = 34535;
    public static TextView txtCompare,txtWishList,txtAlert;
    //public static int wish_hot_number = 0,wish_update_count=0,alert_update_count=0;
    List<WhishListResponse> listResponses;
    List<AlertListResponse> alertListResponses;
    public static int wish_count=0;
    public static int alert_count=0;
    public static int compare_count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        //expListView = (ExpandableListView)findViewById(R.id.expLvSlider);

        mDrawerList = (ExpandableListView)findViewById(R.id.expLvSlider);
        listCategoriesDataHeader = new ArrayList<CategoriesItem>();
        listSubCategoriesDataChild = new HashMap<CategoriesItem,List<SubCategory>>();

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //expListView = (ExpandableListView) findViewById(R.id.expLvSlider);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mDrawerList.setIndicatorBounds(width - getDipsFromPixel(50), width
                    - getDipsFromPixel(10));
        } else {
            mDrawerList.setIndicatorBoundsRelative(width - getDipsFromPixel(50), width
                    - getDipsFromPixel(10));
        }
        prepareActualData();

        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                mDrawerList.setSelection(groupPosition);
                return false;
            }
        });
        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SubCategory currentITem = (SubCategory) listSubCategoriesDataChild.get(listCategoriesDataHeader.get(groupPosition)).get(childPosition);
                Globals.isNavClick++;
                Globals.aliasNav = currentITem.getAlias();
                String catid = currentITem.getCategoryId();
                String subcatid = currentITem.getId();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ProductsMainFragment(), "ProductsMainFragment").commit();
                mDrawerLayout.closeDrawer(mDrawerList);
                return false;
            }
        });
        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        navMenuIcons.recycle();

       // mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(R.mipmap.ic_launcher);
        getActionBar().setDisplayUseLogoEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        //getActionBar().setDisplayShowTitleEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.menu_nav, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                //invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

        wishListCount();
        alertListCount();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,new ProductsMainFragment(),"ProductsMainFragment").commit();
    }

    public int getDipsFromPixel(float pixels) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pixels * scale+0.5f );
    }

    private void prepareActualData(){
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
                listAdapter = new ExpListAdapter(ProductsActivity.this, listCategoriesDataHeader, listSubCategoriesDataChild);
                //expListView.setAdapter(listAdapter);

                mDrawerList.setAdapter(listAdapter);
                mDrawerList.expandGroup(0);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //fragment = new ProductsMainFragment();
                break;
            case 1:
                //fragment = new FindPeopleFragment();
                break;
            case 2:
                //fragment = new PhotosFragment();
                break;
            case 3:
                //fragment = new CommunityFragment();
                break;
            case 4:
                //fragment = new PagesFragment();
                break;
            case 5:
                //fragment = new WhatsHotFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
        final View wish_menu_hotlist = menu.findItem(R.id.action_compare).getActionView();
        final View wishlist_menu = menu.findItem(R.id.action_wishlist).getActionView();
        final View alert_menu = menu.findItem(R.id.action_offers).getActionView();
        txtCompare = (TextView) wish_menu_hotlist.findViewById(R.id.wish_hotlist_hot);
        txtWishList = (TextView)wishlist_menu.findViewById(R.id.wishlists_hotlist_hot);
        txtAlert = (TextView)alert_menu.findViewById(R.id.alert_hotlist_hot);
        wishlist_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wish = new Intent(ProductsActivity.this,WishListActivity.class);
                startActivity(wish);
            }
        });
        alert_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert = new Intent(ProductsActivity.this,AlertActivity.class);
                startActivity(alert);
            }
        });
        wish_menu_hotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compare = new Intent(ProductsActivity.this,CompareProductActivity.class);
                startActivity(compare);
            }
        });
        compareUpdateCount(Globals.counter);
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
//-------------------------------------------------------------
    public static void wishupdateHotCount(int new_hot_number) {
        wish_count = new_hot_number;
        if (txtWishList == null) return;
        if (new_hot_number == 0)
            txtWishList.setVisibility(View.INVISIBLE);
        else {
            txtWishList.setVisibility(View.VISIBLE);
            txtWishList.setText(Integer.toString(new_hot_number));
        }

    }
//--------------------------------------------------------------------
    public static void alertupdateHotCount(int new_hot_number) {
        wish_count = new_hot_number;
        if (txtAlert == null) return;
        if (new_hot_number == 0)
            txtAlert.setVisibility(View.INVISIBLE);
        else {
            txtAlert.setVisibility(View.VISIBLE);
            txtAlert.setText(Integer.toString(new_hot_number));
        }

    }
    //----------------------------------------------------------------

    public static void compareUpdateCount(int n){
        compare_count = n;
        if (txtCompare == null) return;
        if (compare_count == 0)
            txtCompare.setVisibility(View.INVISIBLE);
        else {
            txtCompare.setVisibility(View.VISIBLE);
            txtCompare.setText(Integer.toString(n));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {

           /* case R.id.action_settings:
                return true;*/

            case R.id.action_search:
                Intent i = new Intent(ProductsActivity.this,SearchActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //alertupdateHotCount(CategoriesActivity.alert_count);
        //wishupdateHotCount(CategoriesActivity.wish_count);
        alertListCount();
        wishListCount();
        compareUpdateCount(Globals.counter);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
