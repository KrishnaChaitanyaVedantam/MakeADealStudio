package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.niharinfo.makeadeal.adapter.FullScreenImageAdapter;

import java.util.ArrayList;


public class ImageViewerActivity extends Activity {

    ViewPager viewPager;
    ArrayList<String> imgUrls;
    FullScreenImageAdapter imgAdapter;
    ImageView imgPrev,imgNext;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        actionBar = getActionBar();
        actionBar.hide();
        viewPager = (ViewPager)findViewById(R.id.pager);
        imgPrev = (ImageView)findViewById(R.id.imgPrev);
        imgNext = (ImageView)findViewById(R.id.imgNext);
        imgUrls = new ArrayList<String>();
        imgUrls = getIntent().getStringArrayListExtra("imageUrls");
        imgAdapter = new FullScreenImageAdapter(ImageViewerActivity.this,imgUrls);
        viewPager.setAdapter(imgAdapter);
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
    }

    private void nextPage() {
        int currentPage = viewPager.getCurrentItem();
        int totalPages = viewPager.getAdapter().getCount();

        int nextPage = currentPage+1;
        if (nextPage >= totalPages) {
            imgNext.setVisibility(View.INVISIBLE);
            imgPrev.setVisibility(View.VISIBLE);
        }

        viewPager.setCurrentItem(nextPage, true);
    }

    private void previousPage() {
        int currentPage = viewPager.getCurrentItem();
        int totalPages = viewPager.getAdapter().getCount();

        int previousPage = currentPage-1;
        if (previousPage < 0) {
            imgPrev.setVisibility(View.INVISIBLE);
            imgNext.setVisibility(View.VISIBLE);
        }

        viewPager.setCurrentItem(previousPage, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
