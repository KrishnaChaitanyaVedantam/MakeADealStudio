package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.getuserreviews;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.Reviewdata;
import com.niharinfo.makeadeal.helper.Reviewrespons;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chaitanya on 25/5/15.
 */
public class SelectedItemReviewFragment extends Fragment {

    static List<Reviewdata> reviewarray = new ArrayList<Reviewdata>();
    List<String> consarray=new ArrayList<String>();
    List<String> prosarray=new ArrayList<String>();
    ListView list;
    String pros;
    String cons;
    String rate;
    ReviewsAdapter imgadopter;
    SubCategoryProduct subCategoryProduct;
    ImageView imgCloseReview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selected_item_review_fragment,container,false);
        list=(ListView)v.findViewById(R.id.lstUserReviews);
        imgCloseReview = (ImageView)v.findViewById(R.id.imgReviewClose);
        subCategoryProduct = getActivity().getIntent().getParcelableExtra("SubCategory");
        String productID = subCategoryProduct.getProductID();
        RestAdapter restadopter=new RestAdapter.Builder()
                .setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        getuserreviews apiservice=restadopter.create(getuserreviews.class);
        apiservice.getKey(productID, new Callback<Reviewrespons>() {
            @Override
            public void success(Reviewrespons reviewrespons, Response response) {
                reviewarray=reviewrespons.getKey();
                for (int i = 0; i < reviewarray.size(); i++) {

                    cons=reviewarray.get(i).getCons();
                    pros=reviewarray.get(i).getPros();
                }
                if(reviewarray.size()>0){
                    imgadopter=new ReviewsAdapter(getActivity());
                    list.setAdapter(imgadopter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        imgCloseReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedProductActivity.i = 1;
                SelectedProductActivity.btnReview.setBackgroundColor(getResources().getColor(R.color.beer));
                getActivity().getFragmentManager().popBackStack();
            }
        });
        return v;
    }

    private class ReviewsAdapter extends BaseAdapter{

        Context mContext;
        public ReviewsAdapter(Context mContext){
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return reviewarray.size();
        }

        @Override
        public Object getItem(int position) {
            return  reviewarray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater in = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ViewHolder view;
            if (convertView == null) {
                view = new ViewHolder();
                convertView = in.inflate(R.layout.user_reviews_item, null);
                view.txtrate=(TextView) convertView.findViewById(R.id.txtRating);
                view.txtsummary=(TextView) convertView.findViewById(R.id.txtSummary);
                view.txtheading=(TextView) convertView.findViewById(R.id.txtTitleheading);
                view.txtdate=(TextView) convertView.findViewById(R.id.txtDate);
                view.txtname=(TextView) convertView.findViewById(R.id.txtname);
                view.txtpros=(TextView) convertView.findViewById(R.id.txtpros);
                view.txtcons=(TextView) convertView.findViewById(R.id.textcons);
                convertView.setTag(view);
            } else {
                view = (ViewHolder) convertView.getTag();
            }
            if (reviewarray.get(position).getRatings_score()==null) {
                rate="0";

            } else {
                rate=reviewarray.get(position).getRatings_score();
            }
            view. txtrate.setText(rate+"/5");
            view.txtheading.setText(""+reviewarray.get(position).getHeading());
            view.txtname.setText(reviewarray.get(position).getUser_name());
            view.txtdate.setText(reviewarray.get(position).getDate());
            view.txtsummary.setText(reviewarray.get(position).getSummary());
            view.txtcons.setText(reviewarray.get(position).getCons());
            view.txtpros.setText(reviewarray.get(position).getPros());
            return convertView;
        }
    }

    public static class ViewHolder {
        TextView txtrate,txtsummary,txtheading,txtdate,txtname,txtpros,txtcons;
    }
}
