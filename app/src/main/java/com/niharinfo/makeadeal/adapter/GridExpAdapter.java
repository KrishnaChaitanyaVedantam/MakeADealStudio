package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.RetrofitHelpers.SubCategory;
import com.niharinfo.makeadeal.roundedimage.RoundedImageView;
import java.util.List;

/**
 * Created by chaitanya on 27/5/15.
 */
public class GridExpAdapter extends ArrayAdapter<SubCategory> {

    Context context;
    int res;
    List<SubCategory> subCategoryList;

    public GridExpAdapter(Context context,int res,List<SubCategory> subCategoryList){
        super(context,res,subCategoryList);
        this.context = context;
        this.res = res;
        this.subCategoryList = subCategoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubCategory currentItem = (SubCategory)subCategoryList.get(position);
        View v = convertView;
        ViewHolder holder = new ViewHolder();
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.child_row,null);
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }
        TextView txtChild = (TextView)v.findViewById(R.id.txtChildItem);
        RoundedImageView imageView = (RoundedImageView)v.findViewById(R.id.imgChildItem);
        txtChild.setText(currentItem.getName());
        imageView.setImageResource(R.mipmap.clothes);
        return v;
    }

    private class ViewHolder{
        TextView txtSubTitle;
        RoundedImageView imgSubItem;
    }
}
