package com.niharinfo.makeadeal.helper;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaitanya on 18/5/15.
 */
public class Parent {

    private Bitmap imgParent;
    private String txtParent;

    public Parent(){

    }

    public Parent(String txtParent,Bitmap imgParent){
        this.imgParent = imgParent;
        this.txtParent = txtParent;
    }

   /* public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }*/

    public Bitmap getImgParent() {
        return imgParent;
    }

    public void setImgParent(Bitmap imgParent) {
        this.imgParent = imgParent;
    }

    public String getTxtParent() {
        return txtParent;
    }

    public void setTxtParent(String txtParent) {
        this.txtParent = txtParent;
    }

    //private List<Child> childList = new ArrayList<Child>();

}
