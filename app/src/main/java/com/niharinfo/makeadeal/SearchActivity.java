package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.searchapi;
import com.niharinfo.makeadeal.adapter.ProductNormalListItemAdapter;
import com.niharinfo.makeadeal.adapter.SearchHintAdapter;
import com.niharinfo.makeadeal.fragmenthelper.ProductsNormalListFragment;
import com.niharinfo.makeadeal.helper.ClearableAutoCompleteTextView;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SortSubCategoryListProducts;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchActivity extends Activity {

    ClearableAutoCompleteTextView search;
    ListView listSearch;
    ProgressBar progressBar;
    List<SubCategoryProduct> subCategoryProductsList;
    SubCategoryProduct subCategoryProduct;
    private static final String[] COUNTRIES = new String[] { };
    ProductNormalListItemAdapter listAdapter;
    List<String> searchCaution;
    SearchHintAdapter searchHintAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar bar=getActionBar();
        subCategoryProductsList = new ArrayList<SubCategoryProduct>();
        searchCaution = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,COUNTRIES);
        bar.setCustomView(R.layout.action_search_custom_layout);
        search = (ClearableAutoCompleteTextView)bar.getCustomView().findViewById(R.id.edtSearchText);
        search.hideClearButton();
        search.setThreshold(1);
        search.setAdapter(adapter);
        listSearch = (ListView) findViewById(R.id.listsearch);
        progressBar = (ProgressBar)findViewById(R.id.pbSearch);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                progressBar.setVisibility(View.VISIBLE);
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                searchapi searchser = restAdapter.create(searchapi.class);
                searchser.getSearchItems(search.getText().toString(), new Callback<SortSubCategoryListProducts>() {
                    @Override
                    public void success(SortSubCategoryListProducts sortSubCategoryListProducts, Response response) {
                        subCategoryProductsList = sortSubCategoryListProducts.getSubcatContent();
                        progressBar.setVisibility(View.INVISIBLE);

                        listAdapter = new ProductNormalListItemAdapter(SearchActivity.this, R.layout.products_normal_list_item, subCategoryProductsList);

                        listSearch.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* if (search.getText().length() > 0) {
                    search.showClearButton();
                } else {
                    search.hideClearButton();
                }
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                final searchapi searchser = restAdapter.create(searchapi.class);
                searchser.getSearchItems(search.getText().toString(), new Callback<SortSubCategoryListProducts>() {
                    @Override
                    public void success(SortSubCategoryListProducts sortSubCategoryListProducts, Response response) {
                        if (search.getText().length() > 0) {
                            subCategoryProductsList = sortSubCategoryListProducts.getSubcatContent();
                            for(int i=0;i<subCategoryProductsList.size();i++){
                                searchCaution.add(subCategoryProductsList.get(i).getProductTitle());
                            }
                            searchHintAdapter = new SearchHintAdapter(SearchActivity.this,R.layout.select_dailog_item,searchCaution);
                           *//* ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (this,android.R.layout.select_dialog_item,searchCaution);*//*

                            //listAdapter = new ProductNormalListItemAdapter(SearchActivity.this, R.layout.products_normal_list_item, subCategoryProductsList);

                            search.setAdapter(searchHintAdapter);
                            searchHintAdapter.notifyDataSetChanged();
                            // progressBar.setVisibility(View.INVISIBLE);
                        } else {
                           // progressBar.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = subCategoryProductsList.get(position).getAliasName();
                Intent i = new Intent(SearchActivity.this, SelectedProductActivity.class);
                i.putExtra("SubCategory",subCategoryProductsList.get(position));
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        View v = (View)menu.findItem(R.id.action_search_s).getActionView();
        EditText txtSearch = (EditText)v.findViewById(R.id.edtSearchText);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

}

