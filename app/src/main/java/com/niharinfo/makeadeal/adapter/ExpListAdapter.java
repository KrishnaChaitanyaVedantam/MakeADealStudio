package com.niharinfo.makeadeal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.CategoriesItem;
import com.niharinfo.makeadeal.RetrofitHelpers.SubCategory;
import java.util.HashMap;
import java.util.List;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.roundedimage.RoundedImageView;
import com.squareup.picasso.Picasso;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    List<CategoriesItem> listCategoriesDataHeader;
    HashMap<CategoriesItem,List<SubCategory>> listSubCategoriesDataChild;
    GridExpAdapter grdAdapter;
    List<SubCategory> su;

    public ExpListAdapter(Context context, List<CategoriesItem> listCategoriesDataHeader,
                          HashMap<CategoriesItem,List<SubCategory>> listSubCategoriesDataChild) {
        this._context = context;
        this.listCategoriesDataHeader = listCategoriesDataHeader;
        this.listSubCategoriesDataChild = listSubCategoriesDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listSubCategoriesDataChild.get(this.listCategoriesDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listSubCategoriesDataChild.get(this.listCategoriesDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listCategoriesDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listCategoriesDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SubCategory childItem = (SubCategory)getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_row,null);
        }
        /*GridView grdExp = (GridView)convertView.findViewById(R.id.gridviewExp);
        int childCount = getChildrenCount(groupPosition);
        for(int i=0;i<=listSubCategoriesDataChild.size();i++){
           if(i == childCount){
                grdExp.setAdapter(grdAdapter);
            }
           // grdExp.setAdapter(grdAdapter);
        }*/


        TextView txtChild = (TextView)convertView.findViewById(R.id.txtChildItem);
        RoundedImageView imageView = (RoundedImageView)convertView.findViewById(R.id.imgChildItem);
        txtChild.setText(childItem.getName());
        Picasso.with(_context).load(childItem.getIcon_path()).into(imageView);

        // imageView.setImageResource(R.mipmap.clothes);
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CategoriesItem currentItem = (CategoriesItem)getGroup(groupPosition);
        List<SubCategory> currentSubCategoryList = listSubCategoriesDataChild.get(groupPosition);
       /* su = listSubCategoriesDataChild.get(listCategoriesDataHeader.get(groupPosition));
        grdAdapter = new GridExpAdapter(_context,R.layout.child_grid_exp,su);*/
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_row,null);
        }

        switch (groupPosition){
            case 0 :
                    convertView.setBackgroundResource(R.color.pink);
                break;
            case 1:
                convertView.setBackgroundResource(R.color.green_blue);
                break;
            case 2:
                convertView.setBackgroundResource(R.color.brick);
                break;
            case 3:
                convertView.setBackgroundResource(R.color.light_blue);
                break;
            case 4:
                convertView.setBackgroundResource(R.color.light_brick);
                break;
            case 5:
                convertView.setBackgroundResource(R.color.blue);
                break;
            case 6:
                convertView.setBackgroundResource(R.color.games_color);
                break;
        }

        TextView txtParent = (TextView)convertView.findViewById(R.id.txtGroupItem);
        RoundedImageView imgParent = (RoundedImageView)convertView.findViewById(R.id.imgGroupItem);
        txtParent.setText(currentItem.getCatName());
        Picasso.with(_context).load(currentItem.getIcon_path()).into(imgParent);
       /* int[] id = new int[]{R.mipmap.man,R.mipmap.women,R.mipmap.child,R.mipmap.books,R.mipmap.furniture,R.mipmap.ball};

        imgParent.setImageResource(id[groupPosition]);*/
        return convertView;
    }
}
