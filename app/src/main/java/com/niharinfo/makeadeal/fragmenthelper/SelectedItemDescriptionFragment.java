package com.niharinfo.makeadeal.fragmenthelper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.niharinfo.makeadeal.R;
import com.niharinfo.makeadeal.SelectedProductActivity;
import com.niharinfo.makeadeal.helper.SubCategoryProduct;

/**
 * Created by chaitanya on 25/5/15.
 */
public class SelectedItemDescriptionFragment extends Fragment {

    TextView txtTitle;
    WebView webDescription;
    SubCategoryProduct subCategoryProduct;
    ImageView imgCloseDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_description, container, false);
        txtTitle = (TextView)v.findViewById(R.id.txtTitleDesc);
        webDescription = (WebView)v.findViewById(R.id.webViewDesc);
        imgCloseDescription = (ImageView)v.findViewById(R.id.imgDescriptionClose);
        imgCloseDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedProductActivity.i = 1;
                SelectedProductActivity.btnDescription.setBackgroundColor(getResources().getColor(R.color.beer));
                getActivity().getFragmentManager().popBackStack();
            }
        });
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String desc = bundle.getString("description");
        txtTitle.setText(title);
        String htmlText = "%s";
        webDescription.loadData(String.format(htmlText,desc),"text/html","utf-8");
        return v;
    }
}
