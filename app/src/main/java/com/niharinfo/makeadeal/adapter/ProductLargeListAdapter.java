package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.niharinfo.makeadeal.CategoriesActivity;
import com.niharinfo.makeadeal.ProductsActivity;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.WhistListAddReponse;
import com.niharinfo.makeadeal.RetrofitHelpers.alertadd;
import com.niharinfo.makeadeal.RetrofitHelpers.wishlistaddapi;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import com.niharinfo.makeadeal.helper.ViewHolderItems;
import com.pinterest.pinit.PinItButton;
import com.pinterest.pinit.PinItListener;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 23/5/15.
 */
public class ProductLargeListAdapter extends ArrayAdapter<SubCategoryProduct> {

    private Context mContext;
    private List<SubCategoryProduct> productList;
    int res;
    List<String> imageUrls;
    SubCategoryProduct currentPosition;
    public ProductLargeListAdapter(Context mContext,int res,List<SubCategoryProduct> productList){
        super(mContext,res,productList);
        this.mContext = mContext;
        this.res = res;
        this.productList = productList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         currentPosition = (SubCategoryProduct)productList.get(position);
        ViewHolderItems holder = new ViewHolderItems();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_large_list_item,null);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleLargeListProduct);
            holder.txtLatestPrice = (TextView)convertView.findViewById(R.id.txtNewPriceLargeListProduct);
            holder.productRating = (RatingBar)convertView.findViewById(R.id.rbListLargeProductItem);
            holder.imgAlert = (ImageView)convertView.findViewById(R.id.imgPLLAlert);
            holder.imgCompare = (ImageView)convertView.findViewById(R.id.imgPLLCompare);
            holder.imgMain = (ImageView)convertView.findViewById(R.id.imgLargeListProductItem);
            holder.imgNew = (ImageView)convertView.findViewById(R.id.imgNewLargeListProducts);
            holder.imgPin = (PinItButton)convertView.findViewById(R.id.imgPinLargeListProducts);
            holder.imgPin.setImageResource(R.drawable.pin);
            holder.imgWishList = (ImageView)convertView.findViewById(R.id.imgPLLWishList);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderItems)convertView.getTag();
        }
        holder.txtTitle.setText(currentPosition.getProductTitle());
        holder.txtLatestPrice.setText("₹"+currentPosition.getProductPrice());
        String imageUrl = currentPosition.getProductThumb();
        String imageUrlSecond = currentPosition.getImageUrl();
        StringTokenizer tokenizer = new StringTokenizer(imageUrl,",");
        for(int i=0;i<tokenizer.countTokens();i++){
            imageUrls = new ArrayList<String>();
            imageUrls.add(tokenizer.nextToken());
        }
        String loadedUrl = imageUrls.get(0);
        Picasso.with(mContext).load(loadedUrl).into(holder.imgMain);
        holder.imgPin.setPartnerId(Globals.pinterestID);
        holder.imgPin.setDebugMode(true);
        holder.imgPin.setUrl(Globals.webLink);
        holder.imgPin.setImageUrl(loadedUrl);
        holder.imgPin.setDescription(currentPosition.getProductTitle());
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(0xff006ba1);
        LayerDrawable stars = (LayerDrawable) holder.productRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(0xff006ba1, PorterDuff.Mode.SRC_ATOP);
        holder.productRating.setRating(Float.parseFloat(currentPosition.getRating()));
        holder.imgPin.setListener(_listener);
        String updatedTime = currentPosition.getCreatedAt();
        Date d = Globals.convertDate(updatedTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        Date cDate = Globals.convertDate(currentDate);
        long difference = d.getDate()-cDate.getDate();
        if(difference>7){
            holder.imgNew.setVisibility(View.INVISIBLE);
        }else{
            holder.imgNew.setVisibility(View.VISIBLE);
        }

        holder.imgWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                wishlistaddapi gitapi = restAdapter.create(wishlistaddapi.class);
                gitapi.whishlistadd(ServiceHandlers.USERID, currentPosition.getProductID(), new Callback<WhistListAddReponse>() {
                    @Override
                    public void success(WhistListAddReponse whistListAddReponse, Response response) {
                        String s = "sucess";
                        if (s.equals(whistListAddReponse.getResponse())) {
                            CategoriesActivity.wish_count = CategoriesActivity.wish_count + 1;
                            ProductsActivity.wishupdateHotCount(CategoriesActivity.wish_count);
                            Toast.makeText(mContext, "Successfully Added", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(mContext, whistListAddReponse.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }
        });

        holder.imgAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                alertadd gitapi = restAdapter.create(alertadd.class);
                gitapi.whishlistadd(ServiceHandlers.USERID, currentPosition.getProductID(), new Callback<WhistListAddReponse>() {
                    @Override
                    public void success(WhistListAddReponse whistListAddReponse, Response response) {
                        String s = "sucess";
                        if (s.equals(whistListAddReponse.getResponse())) {
                            CategoriesActivity.alert_count = CategoriesActivity.alert_count + 1;
                            ProductsActivity.alertupdateHotCount(CategoriesActivity.alert_count);
                            Toast.makeText(mContext, "Successfully Added", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(mContext, whistListAddReponse.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                            }
        });

        holder.imgCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.compareList.size() > 0) {

                    for (int i = 0; i < Globals.compareList.size(); i++) {
                        Globals.cmpExists++;
                        String pid = productList.get(position).getProductID();
                        if (Globals.compareList.get(i).getProductID().equalsIgnoreCase(pid)) {
                            Globals.cmpExists=1;
                            Toast.makeText(mContext, "Already Added To Compare List", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                } else {
                    Globals.counter = CategoriesActivity.compare_count + 1;
                    Globals.compareList.add(productList.get(position));
                    CategoriesActivity.compareUpdateCount(Globals.counter);
                    ProductsActivity.compareUpdateCount(Globals.counter);
                    Toast.makeText(mContext, "Successfully Added To Compare List", Toast.LENGTH_LONG).show();
                }

                if (Globals.cmpExists>1) {
                    Globals.cmpExists=1;
                    Globals.counter = CategoriesActivity.compare_count + 1;
                    Globals.compareList.add(productList.get(position));
                    CategoriesActivity.compareUpdateCount(Globals.counter);
                    ProductsActivity.compareUpdateCount(Globals.counter);
                    Toast.makeText(mContext, "Successfully Added To Compare List", Toast.LENGTH_LONG).show();
                }
            }

        });


        return convertView;
    }

    PinItListener _listener = new PinItListener() {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onComplete(boolean completed) {
            super.onComplete(completed);
        }

        @Override
        public void onException(Exception e) {
            super.onException(e);
        }
    };

}
