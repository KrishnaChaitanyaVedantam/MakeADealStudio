package com.niharinfo.makeadeal.helper;

import android.graphics.Bitmap;

/**
 * Created by chaitanya on 18/5/15.
 */
public class Child {

    private Bitmap imgChild;
    private String txtChild;

    public Child(){

    }

    public Child(String txtChild,Bitmap imgChild){
        this.imgChild = imgChild;
        this.txtChild = txtChild;
    }

    public Bitmap getImgChild() {
        return imgChild;
    }

    public void setImgChild(Bitmap imgChild) {
        this.imgChild = imgChild;
    }

    public String getTxtChild() {
        return txtChild;
    }

    public void setTxtChild(String txtChild) {
        this.txtChild = txtChild;
    }



}
