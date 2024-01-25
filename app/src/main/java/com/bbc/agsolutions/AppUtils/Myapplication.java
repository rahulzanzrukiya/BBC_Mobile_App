package com.bbc.agsolutions.AppUtils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.google.firebase.FirebaseApp;

public class Myapplication extends Application {
public  static Context context;



    @Override
    public void onCreate() {
        super.onCreate();
//        FirebaseApp.initializeApp(this);

        FirebaseApp.initializeApp(this);

        context = getApplicationContext();

    }


    public static Context getContext() {
        return context;
    }

    private static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext(). getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
