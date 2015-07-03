package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.niharinfo.makeadeal.ProductsBrowserActivity;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.helper.Compare;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by chaitanya on 1/6/15.
 */
public class ComparePriceAdapter extends ArrayAdapter<Compare> {

    int res;
    List<Compare> compareList;
    Context mContext;
    ViewHolder viewHolder;


    public ComparePriceAdapter(Context mContext, int res, List<Compare> compareList){
        super(mContext, res, compareList);
        this.res = res;
        this.compareList = compareList;
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Compare currentItem = (Compare)compareList.get(position);
        View v = convertView;
        viewHolder = new ViewHolder();
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.compare_price_item,null);
            viewHolder.imgSellerLogo = (ImageView)v.findViewById(R.id.imgSellerLogo);
            viewHolder.rbCompare = (RatingBar)v.findViewById(R.id.rbComparePrice);
            viewHolder.txtPrice = (TextView)v.findViewById(R.id.txtComparePriceOfProduct);
            viewHolder.btnBuy = (Button)v.findViewById(R.id.btnBuyNowCompare);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        Picasso.with(mContext).load(currentItem.getSellerLogo()).into(viewHolder.imgSellerLogo);
        viewHolder.txtPrice.setText(currentItem.getSellerName());
        viewHolder.txtPrice.setText(currentItem.getPrice());
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(mContext.getResources().getColor(R.color.app_blue));
        LayerDrawable layerDrawable = (LayerDrawable)viewHolder.rbCompare.getProgressDrawable();
        layerDrawable.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.app_blue), PorterDuff.Mode.SRC_ATOP);
        viewHolder.rbCompare.setRating(Float.parseFloat(currentItem.getSellerRating()));
        viewHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem.getSellerName().equalsIgnoreCase("Flipkart")) {
                    if (isPackageInstalled("com.flipkart.android", mContext)) {
                        String currentLink = currentItem.getLinkUrl();
                        String[] parts = currentLink.split("&");
                        String part = parts[0]+"&affid=affiliate179";
                        String p = part.replace("www.flipkart.com","dl.flipkart.com/dl");
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(p));
                        i.setPackage("com.flipkart.android");
                        mContext.startActivity(i);
                    }
                }else {/*else if(currentItem.getSellerName().equalsIgnoreCase("Snapdeal")){
                    if(isPackageInstalled("com.snapdeal.main",mContext)){
                        try{
                            String url = "www.snapdeal.com?utm_source=aff_prog&utm_campaign=afts&offer_id=17&aff_id=31998";
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            i.setPackage("com.snapdeal.main");
                            mContext.startActivity(i);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }else if(currentItem.getSellerName().equalsIgnoreCase("Amazon")){
                    if(isPackageInstalled("in.amazon.mShop.android.shopping",mContext)){
                        //String url = "amzn://apps/android?asin?="+currentItem.getProductCode();
                        String url = "amzn://apps/android?asin=B004FRX0MY";
                        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                        //i.setPackage("in.amazon.mShop.android.shopping");
                        mContext.startActivity(i);
                    }
                } else {*/
                    Intent i = new Intent(mContext, ProductsBrowserActivity.class);//AVMDQ37RY4GYJZ6D
                    i.putExtra("browser", currentItem.getLinkUrl());
                    mContext.startActivity(i);
                }
                //}
                /*Uri uri = Uri
                        .parse("http://dl.flipkart.com/dl/nike-dewired-premium-sneakers/p/itmdzthskkpuquue?pid=SHODVZ42TNXHXBDR&affid=affiliate179");
                                http://www.flipkart.com/puma-running-shoes/p/itmdzbegmbyuw3p8?pid=SHODVZ42TNXHXBDR&srno=b_1&offer=b%3Amp%3Ac%3A01e6420011.&ref=28d14138-1592-4414-8b57-5f491b450dd1&affid=affiliate179
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.flipkart.android");
                mContext.startActivity(intent);*/
            }
        });
        return v;
    }

    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private class ViewHolder{
        ImageView imgSellerLogo;
        RatingBar rbCompare;
        TextView txtPrice;
        Button btnBuy;
    }
}
