package com.niharinfo.makeadeal.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by chaitanya on 20/5/15.
 */
public class Globals {

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static String alias;
    public static List<SubCategoryProduct> wishListItems = new ArrayList<SubCategoryProduct>();
    public static int counter = 0;
    public static String pinterestID = "1445385";
    public static String webLink = "http://www.makeadeal.in";
    public static GoogleApiClient mGoogleApiClient;
    public static SharedPreferences preferences;
    public static int signout = 1;
    public static boolean mIntentInProgress;
    public static boolean mSignInClicked;
    public static ConnectionResult mConnectionResult;
    public static int isNavClick = 1;
    public static String aliasNav;
    public static int isFromCategories = 1;
    public static int isFromClose = 1;
    public static List<SubCategoryProduct> compareList = new ArrayList<SubCategoryProduct>();
    public static int cmpExists = 1;

    public static Date convertDate(String date) {
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d = format.parse(date);
            return d;
        }catch (Exception e){

        }
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate;
    }

}
