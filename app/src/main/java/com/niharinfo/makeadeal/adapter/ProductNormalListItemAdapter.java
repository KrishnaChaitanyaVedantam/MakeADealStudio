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
import android.widget.ProgressBar;
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
public class ProductNormalListItemAdapter extends ArrayAdapter<SubCategoryProduct> {

    private Context mContext;
    private List<SubCategoryProduct> productList;
    int res;
    List<String> imageUrls;
    int discountPercentage,discountPrice,productGivenPrice;
    ViewHolderItems holder;
    int k;

    public ProductNormalListItemAdapter(Context mContext,int res,List<SubCategoryProduct> productList){
        super(mContext,res,productList);
        this.mContext = mContext;
        this.res = res;
        this.productList = productList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SubCategoryProduct currentPosition = (SubCategoryProduct)productList.get(position);
        holder = new ViewHolderItems();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.products_normal_list_item,null);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleNormalListProduct);
            holder.txtLatestPrice = (TextView)convertView.findViewById(R.id.txtNewPriceNormalListProduct);
            holder.txtOldPrice = (TextView)convertView.findViewById(R.id.txtOldPriceNormalListProduct);
            holder.productRating = (RatingBar)convertView.findViewById(R.id.rbListNormalProductItem);
            holder.imgAlert = (ImageView)convertView.findViewById(R.id.imgPNLAlert);
            holder.imgCompare = (ImageView)convertView.findViewById(R.id.imgPNLCompare);
            holder.imgMain = (ImageView)convertView.findViewById(R.id.imgNormalListProductItem);
            holder.imgNew = (ImageView)convertView.findViewById(R.id.imgNewNormalListProducts);
            holder.imgPin = (PinItButton)convertView.findViewById(R.id.imgPinNormalListProducts);
            holder.imgPin.setImageResource(R.drawable.pin);
            holder.imgWishList = (ImageView)convertView.findViewById(R.id.imgPNLWishList);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderItems)convertView.getTag();
        }
        holder.txtTitle.setText(currentPosition.getProductTitle());
        String discount = currentPosition.getProductDiscount();
        productGivenPrice = Integer.parseInt(currentPosition.getProductPrice());
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
        int oPrice = discountPrice+Integer.parseInt(currentPosition.getProductPrice());
        holder.txtLatestPrice.setText(currentPosition.getProductPrice());
        holder.txtOldPrice.setText("₹"+String.valueOf(oPrice));
        holder.txtLatestPrice.setText("₹"+currentPosition.getProductPrice());
        holder.txtOldPrice.setPaintFlags(holder.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
        holder.imgPin.setListener(_listener);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(0xff006ba1);
        LayerDrawable stars = (LayerDrawable) holder.productRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(0xff006ba1, PorterDuff.Mode.SRC_ATOP);
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
        String rtn = currentPosition.getRating();
        if(rtn == null){
        }else{
            holder.productRating.setRating(Float.parseFloat(currentPosition.getRating()));
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
