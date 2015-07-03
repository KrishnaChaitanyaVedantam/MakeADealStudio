package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.niharinfo.makeadeal.CompareProductActivity;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import com.niharinfo.makeadeal.helper.ViewHolderItems;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by chaitanya on 29/5/15.
 */
public class CompareProductsListAdapter extends ArrayAdapter<SubCategoryProduct> {

    private Context mContext;
    private List<SubCategoryProduct> productList;
    int res;
    List<String> imageUrls;
    int discountPercentage,discountPrice,productGivenPrice;
    String pids;

    public CompareProductsListAdapter(Context mContext,int res,List<SubCategoryProduct> productList){
        super(mContext,res,productList);
        this.mContext = mContext;
        this.res = res;
        this.productList = productList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SubCategoryProduct currentPosition = (SubCategoryProduct)productList.get(position);
        ViewHolderItems holder = new ViewHolderItems();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_compare_item,null);
            holder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitleCompareListProduct);
            holder.txtLatestPrice = (TextView)convertView.findViewById(R.id.txtNewPriceCompareListProduct);
            holder.txtOldPrice = (TextView)convertView.findViewById(R.id.txtOldPriceCompareListProduct);
            holder.productRating = (RatingBar)convertView.findViewById(R.id.rbListCompareProductItem);
            /*holder.imgAlert = (ImageView)convertView.findViewById(R.id.imgCompareAlert);
            holder.imgCompare = (ImageView)convertView.findViewById(R.id.imgCompareCompare);*/
            holder.imgMain = (ImageView)convertView.findViewById(R.id.imgCompareListProductItem);
            //holder.imgNew = (ImageView)convertView.findViewById(R.id.imgNewCompareListProducts);
            //holder.imgPinterest = (ImageView)convertView.findViewById(R.id.imgPinCompareListProducts);
            //holder.imgDel = (ImageView)convertView.findViewById(R.id.btnRemoveProduct);
            //holder.imgWishList = (ImageView)convertView.findViewById(R.id.imgCompareWishList);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolderItems)convertView.getTag();
        }
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

        holder.txtTitle.setText(currentPosition.getProductTitle());
        holder.txtLatestPrice.setText(currentPosition.getProductPrice());
        holder.txtOldPrice.setText(String.valueOf(oPrice));
        holder.txtLatestPrice.setText(currentPosition.getProductPrice());
        holder.productRating.setRating(Float.parseFloat(currentPosition.getRating()));
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
        /*holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.remove(productList.get(position));
                notifyDataSetChanged();
                Globals.counter--;
                CompareProductActivity cpa = new CompareProductActivity();
                if (productList.size() > 0) {
                    pids = productList.get(0).getProductID();
                    for (int i = 1; i < productList.size(); i++) {
                        pids = pids+","+productList.get(i).getProductID();
                    }
                }
                //cpa.prepareData(cpa);
                //cpa.prepareCompareData(pids);
                //CompareProductActivity.lstExpAdapter.notifyDataSetChanged();
            }
        });*/
        return convertView;
    }

}
