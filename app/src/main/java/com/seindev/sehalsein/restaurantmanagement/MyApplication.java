package com.seindev.sehalsein.restaurantmanagement;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by sehalsein on 18/02/16.
 */
public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
