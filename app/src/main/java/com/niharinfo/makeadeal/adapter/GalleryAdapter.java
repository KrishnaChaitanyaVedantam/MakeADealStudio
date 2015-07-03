package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.niharinfo.makeadeal.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by chaitanya on 26/5/15.
 */
public class GalleryAdapter extends ArrayAdapter<String> {

    Context mContext;
    int res;
    List<String> lstImages;

    public GalleryAdapter(Context mContext,int res,List<String> lstImages){
        super(mContext,res,lstImages);
        this.lstImages = lstImages;
        this.mContext = mContext;
        this.res = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String currentImage = lstImages.get(position);
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gallery_item,null);
            holder.imgGallery = (ImageView)convertView.findViewById(R.id.imgGallerySelItem);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(mContext).load(currentImage).into(holder.imgGallery);
        return convertView;
    }

    private class ViewHolder{
        ImageView imgGallery;
    }
}
