package com.niharinfo.makeadeal.helper;

import android.app.Application;
import android.content.Intent;
import com.niharinfo.makeadeal.CategoriesActivity;

/**
 * Created by chaitanya on 10/6/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    private void handleUncaughtException (Thread thread, Throwable e)
    {
        Intent intent = new Intent(getApplicationContext(),CategoriesActivity.class);
        startActivity(intent);
    }
}
