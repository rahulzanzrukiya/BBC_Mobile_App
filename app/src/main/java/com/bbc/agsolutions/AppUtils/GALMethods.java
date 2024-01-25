package com.bbc.agsolutions.AppUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;


import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GALMethods {



    public static int RC_GOOGLE = 1;
    public static int RC_CAMERA = 2;
    public static int RC_GALLERY = 3;

    public static void toolbar(Activity activity, String title) {

//        Toolbar toolbar = activity.findViewById(R.id.toolbar);
//
//        toolbar.setTitle(title);
//
//        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
//        appCompatActivity.setSupportActionBar(toolbar);
//        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
////
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.onBackPressed();
//            }
//        });
    }

    public PermissionListener permissionListener;

    public interface PermissionListener {

        void onGranted();

        void onDenied();
    }
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }

    public static String[] storge_permissions = {
            Manifest.permission.POST_NOTIFICATIONS,




    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            Manifest.permission.POST_NOTIFICATIONS,


    };

    public static void checkPermissions(Activity activity, PermissionListener permissionListener) {

        GALMethods GALMethods = new GALMethods();
        GALMethods.permissionListener = permissionListener;

        PermissionX.init((FragmentActivity) activity)
                .permissions(permissions())
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {

                        if (allGranted == true) {
                            permissionListener.onGranted();
                        } else {
                            permissionListener.onDenied();
                        }
                    }
                });
    }

    public DateListener dateListener;

    public interface DateListener {

        void onDateSet(String date);
    }


    public static void hideKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public DialogListener dialogListener;

    public interface DialogListener {

        void onCreated(AlertDialog dialog);
    }

    public static void alertDialog(Activity activity, int layout, DialogListener dialogListener) {

        GALMethods GALMethods = new GALMethods();
        GALMethods.dialogListener = dialogListener;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(layout, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        dialogListener.onCreated(alertDialog);
    }








    public ActivityResultListener activityResultListener;

    public interface ActivityResultListener {

        void onResult(Intent data);
    }



    public static BroadcastReceiver networkReceiver;



    public BottomSheetDialogListener bottomSheetDialogListener;

    public interface BottomSheetDialogListener {

        void onCreated(BottomSheetDialog bottomSheetDialog);
    }

    public static void bottomSheetDialog(Activity activity, int layout, BottomSheetDialogListener bottomSheetDialogListener) {

        GALMethods GALMethods = new GALMethods();
        GALMethods.bottomSheetDialogListener = bottomSheetDialogListener;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();

        bottomSheetDialogListener.onCreated(bottomSheetDialog);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void rateUs(Activity activity) {

        try {
            Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        }
    }


    DataListener dataListener;

    public interface DataListener {
        void onDataLoaded();
    }

    public static boolean isPackageInstalled(String packageName) {
        try {
            Myapplication.getContext().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void openApp(Activity activity, String packageName) {

        if (GALMethods.isPackageInstalled(packageName)) {

            Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
            activity.startActivity(intent);

        } else {

            Toast.makeText(activity, "Application is not currently installed", Toast.LENGTH_SHORT).show();
        }
    }
}
