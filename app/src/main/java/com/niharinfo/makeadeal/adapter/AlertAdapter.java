package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.niharinfo.makeadeal.AlertActivity;
import com.niharinfo.makeadeal.CategoriesActivity;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.AlertListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WhishListResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WhistListAddReponse;
import com.niharinfo.makeadeal.RetrofitHelpers.WishListRemove;
import com.niharinfo.makeadeal.RetrofitHelpers.alertremove;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.squareup.picasso.Picasso;
import java.util.List;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by subbarao on 29/5/15.
 */
public class AlertAdapter extends ArrayAdapter<AlertListResponse> {

    private Context mContext;
    private List<AlertListResponse> productList;
    int res;
    SmoothProgressBar progressBar;

    public AlertAdapter(Context context,int res1,List<AlertListResponse> listResponses){
        super(context,res1,listResponses);
        this.mContext=context;
        this.res=res1;
        this.productList=listResponses;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final AlertListResponse response=productList.get(position);
        ViewHolder holder=new ViewHolder();
        if(convertView==null){

            LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.wishlist_item_layout,null);
            holder.txtTitle=(TextView)convertView.findViewById(R.id.txtTitleNormalWishListProduct);
            holder.txtBrand=(TextView)convertView.findViewById(R.id.txtBrandNameNormalWishListProduct);
            holder.txtCost=(TextView)convertView.findViewById(R.id.txtCostNormalWishListProduct);
            holder.txtStock=(TextView)convertView.findViewById(R.id.txtStockNormalWishListProduct);
            holder.btnDelete=(ImageButton)convertView.findViewById(R.id.btnRemoveWishListProduct);
            holder.imgPic=(ImageView)convertView.findViewById(R.id.imgNormalWishListProductItem);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.txtTitle.setText(response.getTitle()!=null?response.getTitle():"");

        holder.txtBrand.setText(response.getBrand());

        holder.txtCost.setText(response.getPrice());


        if(response.getStock().equals("yes")){
            holder.txtStock.setText("IN STOCK");
        }else{
            holder.txtStock.setText("OUT OF STOCK");
        }

        Picasso.with(mContext).load(response.getImage()).into(holder.imgPic);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                alertremove gitapi =  restAdapter.create(alertremove.class);
                gitapi.whishlistadd(ServiceHandlers.USERID, response.getPid(), new Callback<WhistListAddReponse>() {
                    @Override
                    public void success(WhistListAddReponse whistListAddReponse, Response response) {
                        if(whistListAddReponse.getResponse().equals("sucess")){
                            CategoriesActivity.alert_count = CategoriesActivity.alert_count - 1;
                            productList.remove(position);
                            notifyDataSetChanged();
                            if(CategoriesActivity.alert_count==0){
                                AlertActivity.txtAlertItems.setVisibility(View.VISIBLE);
                            }else{
                                AlertActivity.txtAlertItems.setVisibility(View.INVISIBLE);
                            }
                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });


            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView txtTitle,txtBrand,txtCost,txtStock;
        ImageButton btnBuyNow,btnDelete;
        ImageView imgPic;
    }


}
