package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.niharinfo.makeadeal.R;

import java.util.List;

/**
 * Created by chaitanya on 12/6/15.
 */
public class SearchHintAdapter extends ArrayAdapter<String> {

    Context context;
    int res;
    List<String> list;

    public SearchHintAdapter(Context context,int res,List<String> list){
        super(context,res,list);
        this.context = context;
        this.res = res;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String currentItem = list.get(position);
        ViewHolder holder = new ViewHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_dailog_item,null);
            holder.txtSuggetion = (TextView)convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtSuggetion.setText(currentItem!=null?currentItem:"");
        return convertView;
    }

    private class ViewHolder{
        TextView txtSuggetion;
    }
}
