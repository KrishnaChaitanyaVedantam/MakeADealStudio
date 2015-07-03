package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.helper.Globals;

/**
 * Created by chaitanya on 21/5/15.
 */
public class ProductsMainFragment extends Fragment {

    public static TextView txtProductCategory;
    Button btnSort,btnFilter;
    public static String catid=null,subcatid=null,topic=null,alias;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.products_fragment,container,false);
        catid= getActivity().getIntent().getStringExtra("catid");
        subcatid= getActivity().getIntent().getStringExtra("subcatid");
        alias = getActivity().getIntent().getStringExtra("alias");
        txtProductCategory = (TextView)v.findViewById(R.id.txtProductCategory);
        if(Globals.isFromCategories==1){
            txtProductCategory.setText("new arrivals");
            Globals.isFromCategories++;
        }
        btnSort = (Button)v.findViewById(R.id.btnSort);
        btnFilter = (Button)v.findViewById(R.id.btnFilter);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortDailog();
            }
        });
        getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container, new ProductsGridFragment(), "ProductsGridFragment").commit();
        return v;
    }


    public void shortDailog(){
        final   Dialog d=new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.sort_dialog_layout);
        TextView txt_LowtoHigh=(TextView)d.findViewById(R.id.txtLowToHighSort);
        TextView txt_HightoLow=(TextView)d.findViewById(R.id.txtHighToLowShort);
        TextView txt_Latest=(TextView)d.findViewById(R.id.txtLatestSort);
        TextView txt_Popular=(TextView)d.findViewById(R.id.txtPopularSort);
        txt_LowtoHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                topic="price_asc";
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container, new SortGridFragment(), "ShortGridFragment").commit();
            }
        });
        txt_HightoLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                topic="price_desc";
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container, new SortGridFragment(), "ShortGridFragment").commit();

            }
        });
        txt_Latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                topic="newest";
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container, new SortGridFragment(), "ShortGridFragment").commit();
            }
        });

        txt_Popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                topic="popularity";
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_fragment_container, new SortGridFragment(), "ShortGridFragment").commit();

            }
        });
        d.show();





    }
}
