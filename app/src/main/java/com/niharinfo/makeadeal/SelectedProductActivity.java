package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.AlertListData;
import com.niharinfo.makeadeal.RetrofitHelpers.AlertListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WhishListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WishListData;
import com.niharinfo.makeadeal.RetrofitHelpers.alertapi;
import com.niharinfo.makeadeal.RetrofitHelpers.whislistapi;
import com.niharinfo.makeadeal.fragmenthelper.ComparePriceFragment;
import com.niharinfo.makeadeal.fragmenthelper.SelectedItemDescriptionFragment;
import com.niharinfo.makeadeal.fragmenthelper.SelectedItemDisplayFragment;
import com.niharinfo.makeadeal.fragmenthelper.SelectedItemReviewFragment;
import com.niharinfo.makeadeal.fragmenthelper.SelectedItemSpecificationFragment;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SelectedProductActivity extends Activity implements View.OnClickListener{

    public static Button btnCompare,btnDescription,btnSpecification,btnReview;
    FragmentManager fragmentManager;
    int a=1;
    SubCategoryProduct subCategoryProduct;
    public static TextView wish_ui_hot,txtWishList,txtAlert;
    public static int wish_count=0;
    public static int alert_count=0;
    List<WhishListResponse> listResponses;
    List<AlertListResponse> alertListResponses;
    ActionBar actionBar;
    public static int i=1,isfragmentopen = 1,isClosedFromOuter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);
        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        subCategoryProduct = getIntent().getParcelableExtra("SubCategory");
        fragmentManager = getFragmentManager();
        btnCompare = (Button)findViewById(R.id.btnCompare);
        btnDescription = (Button)findViewById(R.id.btnDescription);
        btnSpecification = (Button)findViewById(R.id.btnSpecification);
        btnReview = (Button)findViewById(R.id.btnReview);
        btnCompare.setOnClickListener(this);
        btnDescription.setOnClickListener(this);
        btnSpecification.setOnClickListener(this);
        btnReview.setOnClickListener(this);
        wishListCount();
        alertListCount();
        getFragmentManager().beginTransaction().add(R.id.frmContainerProductsSelection,new SelectedItemDisplayFragment(), "Selected Item Fragment").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selected_product, menu);
        final View wish_menu_hotlist = menu.findItem(R.id.action_compare_selected_product).getActionView();
        final View wishlist_menu = menu.findItem(R.id.action_wishlist_selected_product).getActionView();
        final View alert_menu = menu.findItem(R.id.action_offers_selected_product).getActionView();
        wish_ui_hot = (TextView) wish_menu_hotlist.findViewById(R.id.wish_hotlist_hot);
        txtWishList = (TextView)wishlist_menu.findViewById(R.id.wishlists_hotlist_hot);
        txtAlert = (TextView)alert_menu.findViewById(R.id.alert_hotlist_hot);
        compareUpdateCount(Globals.counter);
        alertupdateHotCount(CategoriesActivity.alert_count);
        wishupdateHotCount(CategoriesActivity.wish_count);
        alert_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alert = new Intent(SelectedProductActivity.this, AlertActivity.class);
                startActivity(alert);
            }
        });
        wishlist_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wish = new Intent(SelectedProductActivity.this, WishListActivity.class);
                startActivity(wish);
            }
        });
        wish_menu_hotlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compare = new Intent(SelectedProductActivity.this,CompareProductActivity.class);
                startActivity(compare);
            }
        });
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /*alertupdateHotCount(CategoriesActivity.alert_count);
        wishupdateHotCount(CategoriesActivity.wish_count);*/
        alertListCount();
        wishListCount();
        compareUpdateCount(Globals.counter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if(id == R.id.action_search_selected_product) {
            Intent i = new Intent(SelectedProductActivity.this,SearchActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void findFragment(){
        Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
        String tag = fragment.getTag();
        if(tag.equalsIgnoreCase("Compare Fragment")){
            btnCompare.setBackgroundColor(getResources().getColor(R.color.fb_blue));
            btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
            btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
            btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
        }else if(tag.equalsIgnoreCase("Specification Fragment")){
            btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
            btnSpecification.setBackgroundColor(getResources().getColor(R.color.fb_blue));
            btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
            btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
        }else if(tag.equalsIgnoreCase("Description Fragment")){
            btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
            btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
            btnDescription.setBackgroundColor(getResources().getColor(R.color.blue));
            btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
        }else if(tag.equalsIgnoreCase("Review Fragment")){
            btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
            btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
            btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
            btnReview.setBackgroundColor(getResources().getColor(R.color.fb_blue));
        }else if(tag.equalsIgnoreCase("Selected Item Fragment")){
            btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
            btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
            btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
            btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
        }
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

    public static void compareUpdateCount(int n){
        wish_count = n;
        if (wish_ui_hot == null) return;
        if (n == 0)
            wish_ui_hot.setVisibility(View.INVISIBLE);
        else {
            wish_ui_hot.setVisibility(View.VISIBLE);
            wish_ui_hot.setText(Integer.toString(n));
        }
    }

    public static void alertupdateHotCount(int new_hot_number) {
        wish_count = new_hot_number;
        if (wish_ui_hot == null) return;
        if (new_hot_number == 0)
            txtAlert.setVisibility(View.INVISIBLE);
        else {
            txtAlert.setVisibility(View.VISIBLE);
            txtAlert.setText(Integer.toString(new_hot_number));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCompare:
                if(i == 1) {
                    i++;
                    isfragmentopen++;
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Compare Fragment")){
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Compare Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).add(R.id.frmContainerProductsSelection, new ComparePriceFragment(), "Compare Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                    }
                    //findFragment();
                }else{
                    /*i=1;
                    btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Compare Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                    getFragmentManager().popBackStack(
                            SelectedItemDisplayFragment.class.getName(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);*/
                    i=1;
                    isfragmentopen=1;
                    //findFragment();
                    /*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.beer));*/
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Compare Fragment")){
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Compare Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).add(R.id.frmContainerProductsSelection, new ComparePriceFragment(), "Compare Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }
                }

                break;
            case R.id.btnSpecification:

                if(i == 1){
                    i++;
                    isfragmentopen++;
                    //findFragment();
                    /*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.beer));*/
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Specification Fragment")){
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Specification Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,0).add(R.id.frmContainerProductsSelection, new SelectedItemSpecificationFragment(), "Specification Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }
                }else{
                    i=1;
                    isfragmentopen=1;
                    /*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.beer));*/
                    //findFragment();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Specification Fragment")){
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Specification Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,0).add(R.id.frmContainerProductsSelection, new SelectedItemSpecificationFragment(), "Specification Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                    }

                }
                break;
            case R.id.btnDescription:

                SelectedItemDescriptionFragment selectedItemDescriptionFragment = new SelectedItemDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",subCategoryProduct.getProductTitle());
                bundle.putString("description", subCategoryProduct.getProductDescription());
                selectedItemDescriptionFragment.setArguments(bundle);

                if(i == 1) {
                    i++;
                    isfragmentopen++;
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Description Fragment")) {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Description Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, 0).replace(R.id.frmContainerProductsSelection, selectedItemDescriptionFragment, "Description Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }
                    //findFragment();
                    /*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.beer));*/

                }else{
                    i=1;
                    isfragmentopen=1;
                        /*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));*/
                    //findFragment();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Description Fragment")) {
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Description Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, 0).replace(R.id.frmContainerProductsSelection, selectedItemDescriptionFragment, "Description Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }

                }
                break;
            case R.id.btnReview:

                if(i == 1) {
                    i++;
                    isfragmentopen++;
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    if(tag.equalsIgnoreCase("Review Fragment")){
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Review Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, 0).replace(R.id.frmContainerProductsSelection, new SelectedItemReviewFragment(), "Review Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                    }
                    /*getFragmentManager().popBackStack(
                            SelectedItemDisplayFragment.class.getName(),
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, 0).replace(R.id.frmContainerProductsSelection, new SelectedItemReviewFragment(), "Review Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                    *//*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.fb_blue));*/
                    //findFragment();
                }else{
                    i=1;
                    isfragmentopen=1;
                    Fragment fragment = fragmentManager.findFragmentById(R.id.frmContainerProductsSelection);
                    String tag = fragment.getTag();
                    /*btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                    btnReview.setBackgroundColor(getResources().getColor(R.color.beer));*/
                    //findFragment();
                    if(tag.equalsIgnoreCase("Review Fragment")){
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up,R.anim.slide_down).remove(getFragmentManager().findFragmentByTag("Review Fragment")).addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                    }else{
                        btnCompare.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnSpecification.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                        btnReview.setBackgroundColor(getResources().getColor(R.color.fb_blue));
                        getFragmentManager().popBackStack(
                                SelectedItemDisplayFragment.class.getName(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_up, 0).replace(R.id.frmContainerProductsSelection, new SelectedItemReviewFragment(), "Review Fragment").addToBackStack(SelectedItemDisplayFragment.class.getName()).commit();
                    }

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i=1;
        findFragment();
    }
}
