package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.niharinfo.makeadeal.CategoriesActivity;
import com.niharinfo.makeadeal.ImageViewerActivity;
import com.niharinfo.makeadeal.ProductsActivity;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.RateApiservice;
import com.niharinfo.makeadeal.RetrofitHelpers.Rateingresponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WhistListAddReponse;
import com.niharinfo.makeadeal.RetrofitHelpers.alertadd;
import com.niharinfo.makeadeal.RetrofitHelpers.compareprouductpriceapi;
import com.niharinfo.makeadeal.RetrofitHelpers.getuserproductratingapi;
import com.niharinfo.makeadeal.RetrofitHelpers.wishlistaddapi;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.WriteReviewActivity;
import com.niharinfo.makeadeal.adapter.ComparePriceAdapter;
import com.niharinfo.makeadeal.adapter.GalleryAdapter;
import com.niharinfo.makeadeal.helper.Compare;
import com.niharinfo.makeadeal.helper.CompareProductsList;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import com.niharinfo.makeadeal.helper.UserRating;
import com.niharinfo.makeadeal.roundedimage.TouchImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 25/5/15.
 */
public class SelectedItemDisplayFragment extends Fragment implements View.OnClickListener{

    ImageView imgSelectedProductMain;
    ImageView imgSelectedItemWishList,imgSelectedItemAlert,imgSelectedItemCompare;
    //Gallery gallery;
    GalleryAdapter galleryAdapter;
    ArrayList<String> imageUrls;
    Animation animZoomIn,animZoomOut;
    TextView txtTitle,txtLowestPrice,txtOriginalPrice,txtExistedRating;
    Button btnDownload,btnShare,btnReview,btnTotalViews,txtView;
    SubCategoryProduct subCategoryProduct;
    RatingBar rb;
    List<Compare> compareList;
    int lowPrice,temp;
    int discountPercentage;
    int discountPrice;
    int numStars=0;
    SharedPreferences prefs;
    ProgressBar progressBar,pbSel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_selected_item_display_fragment, container, false);
        compareList = new ArrayList<Compare>();
        txtTitle = (TextView)v.findViewById(R.id.txtTitleSelectedItem);
        txtOriginalPrice = (TextView)v.findViewById(R.id.txtOriginalPriceSelItemProduct);
        txtLowestPrice = (TextView)v.findViewById(R.id.txtLowestPriceSelectedItemProductChanged);
        txtExistedRating = (TextView)v.findViewById(R.id.txtRatingsExisted);
        txtView = (Button)v.findViewById(R.id.txtMore);
        btnDownload = (Button)v.findViewById(R.id.btnDownload);
        btnShare = (Button)v.findViewById(R.id.btnShare);
        btnReview = (Button)v.findViewById(R.id.btnReviews);
        btnTotalViews = (Button)v.findViewById(R.id.btnTotalRatings);
        rb = (RatingBar)v.findViewById(R.id.rbSelectedItemProduct);
        progressBar = (ProgressBar)v.findViewById(R.id.pbSelectedItemDisplay);
        btnDownload.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnReview.setOnClickListener(this);
        imgSelectedItemAlert = (ImageView)v.findViewById(R.id.imgSelectedItemAlert);
        imgSelectedItemCompare = (ImageView)v.findViewById(R.id.imgSelectedItemCompare);
        imgSelectedItemWishList = (ImageView)v.findViewById(R.id.imgSelectedItemWishlist);
        imgSelectedProductMain = (ImageView)v.findViewById(R.id.imgSelectedProductMain);

        pbSel = (ProgressBar)v.findViewById(R.id.pbSelectedItemDisplayImg);
        prefs = getActivity().getSharedPreferences(ServiceHandlers.MyPREFERENCES, Context.MODE_PRIVATE);
        subCategoryProduct = getActivity().getIntent().getParcelableExtra("SubCategory");

        int productGivenPrice = Integer.parseInt(subCategoryProduct.getProductPrice());
        String discount = subCategoryProduct.getProductDiscount();
        if(discount.isEmpty()){
            discountPercentage = 0;
        }else{
            String dis = discount.replace("% OFF","").replace("% Off","");
            discountPercentage = Integer.parseInt(dis);
        }
        if(discountPercentage == 0){
            discountPrice = productGivenPrice;
        }else{
            discountPrice = (discountPercentage*productGivenPrice)/100;
        }


        RestAdapter userRatingAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        getuserproductratingapi ratingapi = userRatingAdapter.create(getuserproductratingapi.class);
        ratingapi.getuserratings(subCategoryProduct.getProductID(), ServiceHandlers.USERID, new Callback<UserRating>() {
            @Override
            public void success(UserRating userRating, Response response) {
                String res = userRating.getRating();

                if (res.equalsIgnoreCase("Not Rated")) {

                } else {
                    Float urating = Float.parseFloat(res);
                    rb.setRating(urating);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

        final String imageLinks = subCategoryProduct.getProductThumb();
        imgSelectedItemAlert.setOnClickListener(this);
        imgSelectedItemWishList.setOnClickListener(this);
        imgSelectedItemCompare.setOnClickListener(this);
       // gallery = (Gallery)v.findViewById(R.id.glSelectedItem);
        animZoomIn = AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in);
        animZoomOut = AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_out);
        txtExistedRating.setText(subCategoryProduct.getRating());
        txtTitle.setText(subCategoryProduct.getProductTitle());
        btnTotalViews.setText(subCategoryProduct.getTotalRatings()+" Ratings");
        txtOriginalPrice.setPaintFlags(txtOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        int oprice = discountPrice+Integer.parseInt(subCategoryProduct.getProductPrice());
        txtOriginalPrice.setText("₹" + oprice);
        pbSel.setVisibility(View.VISIBLE);
        imageUrls = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(imageLinks,",");
        for(int i=0;i<tokenizer.countTokens();i++){
            imageUrls.add(tokenizer.nextToken());
        }
        Picasso.with(getActivity()).load(imageUrls.get(0)).into(imgSelectedProductMain, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                pbSel.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                pbSel.setVisibility(View.INVISIBLE);
            }
        });
        galleryAdapter = new GalleryAdapter(getActivity(),R.layout.gallery_item,imageUrls);
        imgSelectedProductMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ImageViewerActivity.class);
                i.putStringArrayListExtra("imageUrls",imageUrls);
                startActivity(i);
            }
        });


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        compareprouductpriceapi priceapi = restAdapter.create(compareprouductpriceapi.class);
        priceapi.getSeller(subCategoryProduct.getProductID(), new Callback<CompareProductsList>() {
            @Override
            public void success(CompareProductsList compareProductsList, Response response) {
                compareList = compareProductsList.getCompareProductList();
                temp = Integer.parseInt(compareList.get(0).getPrice());
                for (int i = 0; i < compareList.size(); i++) {
                    lowPrice = Integer.parseInt(compareList.get(i).getPrice());
                    if (lowPrice < temp) {
                        //lowPrice = Integer.parseInt(compareList.get(i).getPrice()));
                        temp = lowPrice;
                    }
                }
                int t = temp;
                txtLowestPrice.setText("₹" + String.valueOf(t));
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                progressBar.setVisibility(View.VISIBLE);
                int currentRating = (int) rating;

                numStars = rb.getNumStars();
                ColorDrawable colorDrawable = new ColorDrawable();
                colorDrawable.setColor(0xff006ba1);
                LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(0xff006ba1, PorterDuff.Mode.SRC_ATOP);
                String message = null;
                switch (currentRating) {
                    case 1:
                        message = "Sorry you're really upset with us";
                        break;
                    case 2:
                        message = "Sorry you're not happy";
                        break;
                    case 3:
                        message = "Good enough is not good enough";
                        break;
                    case 4:
                        message = "Thanks, we're glad you liked it.";
                        break;
                    case 5:
                        message = "Awesome - thanks!";
                        break;
                }
                String cur_rat2 = String.valueOf(rb.getRating());
                RestAdapter restadopter = new RestAdapter.Builder()
                        .setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                RateApiservice apiservice = restadopter.create(RateApiservice.class);
                String userid = ServiceHandlers.USERID;
                apiservice.getResponse(subCategoryProduct.getProductID(), userid, cur_rat2, new Callback<Rateingresponse>() {

                    @Override
                    public void success(Rateingresponse arg0, Response arg1) {
                        // TODO Auto-generated method stub
                        String name = arg0.getResponse();
                        String ratingsCount = arg0.getTotalRatings();
                        btnTotalViews.setText(ratingsCount+" Ratings");
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void failure(RetrofitError arg0) {
                        // TODO Auto-generated method stub
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ImageViewerActivity.class);
                i.putStringArrayListExtra("imageUrls",imageUrls);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDownload:
                    progressBar.setVisibility(View.VISIBLE);
                    imgSelectedProductMain.buildDrawingCache();
                Bitmap bitmap = imgSelectedProductMain.getDrawingCache();
                    saveImage(bitmap);
                break;
            case R.id.btnShare:
                Picasso.with(getActivity()).load(imageUrls.get(0)).into(target);
                break;
            case R.id.btnReviews:
                    String productID = subCategoryProduct.getProductID();
                    Intent i = new Intent(getActivity(), WriteReviewActivity.class);
                    i.putExtra("productID",productID);
                    startActivity(i);
                break;
            case R.id.imgSelectedItemAlert:
                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                alertadd gitapi = restAdapter.create(alertadd.class);
                gitapi.whishlistadd(ServiceHandlers.USERID, subCategoryProduct.getProductID(), new Callback<WhistListAddReponse>() {
                    @Override
                    public void success(WhistListAddReponse whistListAddReponse, Response response) {
                        String s = "sucess";
                        if (s.equals(whistListAddReponse.getResponse())) {
                            CategoriesActivity.alert_count = CategoriesActivity.alert_count + 1;
                            ProductsActivity.alertupdateHotCount(CategoriesActivity.alert_count);
                            SelectedProductActivity.alertupdateHotCount(CategoriesActivity.alert_count);
                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), whistListAddReponse.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                break;
            case R.id.imgSelectedItemWishlist:
                RestAdapter resAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                wishlistaddapi gitap = resAdapter.create(wishlistaddapi.class);
                gitap.whishlistadd(ServiceHandlers.USERID, subCategoryProduct.getProductID(), new Callback<WhistListAddReponse>() {
                    @Override
                    public void success(WhistListAddReponse whistListAddReponse, Response response) {
                        String s = "sucess";
                        if (s.equals(whistListAddReponse.getResponse())) {
                            CategoriesActivity.wish_count = CategoriesActivity.wish_count + 1;
                            ProductsActivity.wishupdateHotCount(CategoriesActivity.wish_count);

                            SelectedProductActivity.wishupdateHotCount(CategoriesActivity.wish_count);
                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), whistListAddReponse.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
                break;
            case R.id.imgSelectedItemCompare:
                if (Globals.compareList.size() > 0) {

                    for (int j = 0; j < Globals.compareList.size(); j++) {
                        Globals.cmpExists++;
                        String pid =subCategoryProduct.getProductID();
                        if (Globals.compareList.get(j).getProductID().equalsIgnoreCase(pid)) {
                            Globals.cmpExists=1;
                            Toast.makeText(getActivity(), "Already Added To Compare List", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                } else {
                    Globals.counter = CategoriesActivity.compare_count + 1;
                    Globals.compareList.add(subCategoryProduct);
                    CategoriesActivity.compareUpdateCount(Globals.counter);
                    SelectedProductActivity.compareUpdateCount(Globals.counter);
                    ProductsActivity.compareUpdateCount(Globals.counter);
                    Toast.makeText(getActivity(), "Successfully Added To Compare List", Toast.LENGTH_LONG).show();
                }

                if (Globals.cmpExists>1) {
                    Globals.cmpExists=1;
                    Globals.counter++;// = CategoriesActivity.compare_count + 1;
                    Globals.compareList.add(subCategoryProduct);
                    CategoriesActivity.compareUpdateCount(Globals.counter);
                    SelectedProductActivity.compareUpdateCount(Globals.counter);
                    ProductsActivity.compareUpdateCount(Globals.counter);
                    Toast.makeText(getActivity(), "Successfully Added To Compare List", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            //  saveImage(bitmap);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/actress_wallpaper.jpg");
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                        Uri uri= Uri.fromFile(file);
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, subCategoryProduct.getProductTitle());
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("image/jpeg");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "send"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();


        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }


    };


    public void saveImage(Bitmap bitmap){
        OutputStream fOut = null;
        File file = new File("/sdcard/" +
                "","GE_"+ System.currentTimeMillis() +".jpg");

        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
            Toast.makeText(getActivity(),"Download Successfull.Check Gallery",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"Download failed",Toast.LENGTH_LONG).show();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, getActivity().getString(R.string.hello_world));
        values.put(MediaStore.Images.Media.DESCRIPTION, getActivity().getString(R.string.hello_world));
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
        values.put("_data", file.getAbsolutePath());

        ContentResolver cr = getActivity().getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
