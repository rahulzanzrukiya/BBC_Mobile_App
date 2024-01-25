package com.bbc.agsolutions;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bbc.agsolutions.AppUtils.GALMethods;
import com.bbc.agsolutions.Model.MyResponseData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();

//        ed.putString("user_type", "1");
//        ed.commit();

//        ed.putString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiN2I4ZTljOWYzNTNhYTdkZTgwNzBlOTQyNGJjZWM1ZDE3MGQyM2QxMTBkNjhhZjI2Mjg2YmM4Yjk5YTZhYjIxZTVmNTMzN2E1NGI0ZDJkYWIiLCJpYXQiOjE3MDExNjY4MTguOTEyNDg3MDMwMDI5Mjk2ODc1LCJuYmYiOjE3MDExNjY4MTguOTEyNDkyMDM2ODE5NDU4MDA3ODEyNSwiZXhwIjoxNzMyNzg5MjE4Ljg5ODAzOTEwMjU1NDMyMTI4OTA2MjUsInN1YiI6IjE1Iiwic2NvcGVzIjpbXX0.GU2dutxsB2TmoIxo4tk9jAXN43Xmj2sW3WciNsd2RFtjzr-bW-EX8qlAVj6j5-Qa-zLbwe5mMwqL5fsXDzWQUj9RiUOVhqnpBXiW1T4CTWFSFgK0Hgk_S604P0_B5-vhD0XzdRZ2y7zyl75uRBpI40-rcDMzDMOmA7PWbkWWcnHdwKV5GdFF6zW8FVHk7K9a7kwFxV4BNDstYV3NDD-zFvuc8HsMHFuJHJnn9c06XBNIp-O62oIhdcUcCVsV0b9SIYNrj7jfWdnHi_QYW6bI1z8SitPBd768H9TqHP8RK-dY3CFheFLpjzsUc4iDk-LuymgHsiDk365vu-tGQk23tz7TF9qPk8m4AX9Sq_3Jo-FOQUmykeBXzrV9nAbx1jBucGecfTdZ_CWyoMZDQx1ZZGbm7scBZgydzp43ffA77XCFCDPPWVYPsYwxUxcTpGG7uKbTJmshdvn_f9_j4WssQ3AI-46Lnj5eksFtyEAMVjLBTfsKe41rE5Qq8sPS1tLvqg8rA_jpDZgi3Ac1ndrxDJui1D7uOBy8fCnoaw0M15wivf2vt95t4ejVk5KLQIKgs8vsfgYfXSLmmlSkXgUTInRUUufBGHXgQqr3MxjZGFFRHCeZVr8vryeRV1GbDYFkkXFLiDkRQ2lPqanTS0N8lYdXIzIceEnN8eHdo_NX_MU");
//        ed.commit();

//        Log.e("token", "token: "+sp.getString("token",""));

        // 1 = driver
        //2 = admin
        //3 = manager
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        ed.putString("firbasetoken", token);
                        ed.commit();
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(SplashScreen.this, token, Toast.LENGTH_SHORT).show();
                    }
                });








        GALMethods.checkPermissions(SplashScreen.this, new GALMethods.PermissionListener() {
            @Override
            public void onGranted() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        boolean isUserLoggedIn = sp.getBoolean("firsttime", false);

                        if (isUserLoggedIn) {
                            getlogin();
//                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
//                    finish();
                        } else {
                            startActivity(new Intent(SplashScreen.this, IntroScreen.class));
                            finish();
                        }
//
                    }
                }, 1500);

            }




            @Override
            public void onDenied() {


            }
        });



    }




//    private void countryDialoglogout() {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.SheetDialog);
//        bottomSheetDialog.setContentView(R.layout.item_deactivate);
//        bottomSheetDialog.show();
//
//
//        TextView logout = bottomSheetDialog.findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finishAffinity();
//
//            }
//        });
//
//    }

    public void getlogin() {
//        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<MyResponseData> call = loginservice.get_login(sp.getString("mobail", ""), sp.getString("password", ""), sp.getString("firbasetoken", ""));
        call.enqueue(new Callback<MyResponseData>() {
            @Override
            public void onResponse(Call<MyResponseData> call, Response<MyResponseData> response) {
                Log.e("responce..", "" + response.toString());

                if (response.isSuccessful()) {
                    MyResponseData responseData = response.body();
                    if (responseData != null) {
                        // Access the data from the response
                        String token = null;
                        try {
                            try {
                                token = responseData.getData().getToken();
                            } catch (Exception e) {
                                ed.putString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiOGYzNWM4YjdhZjI1NWRkYTBkNzUwZmM0YjY3ZmQzZWI0NDFkZDEzNTk1MjgxZjljODA4OWU4MzFkMmE3YTQyZjQ1N2UxMTJiYjIxMzc3YjkiLCJpYXQiOjE3MDQzNjk2NDMuNDA4NzYyOTMxODIzNzMwNDY4NzUsIm5iZiI6MTcwNDM2OTY0My40MDg3NjY5ODQ5Mzk1NzUxOTUzMTI1LCJleHAiOjE3MzU5OTIwNDMuNDA2Nzk2OTMyMjIwNDU4OTg0Mzc1LCJzdWIiOiIxMDciLCJzY29wZXMiOltdfQ.ExOqu4Iv7J7imSabbXijDdHCi79JOR3NX8QGEUeYhbyvE3MQu-KTd7GM4hvBhJTdW04CRCS-lTf9HGtJmbhZVO0JaUf7P8uIpHc8Qu226qVGmQEDRHA5G2FnckZlNLj63JurPJ-qfDcPIrHZafFGnCNVZOF3OJfJoj1NACOynDuU_U8PDpHcTFBKxeIb9TO2eKUhu70fd6RFSlUImBZkG3nF0UyFuZOHj6utlIilUe58MkGreRSmAZf7bNDJF9w3Dgns1KrHqNz0kE_mzphytbIicE0UVfv8g7qrTGkpLKgjdAR6yvY9XMEd8XeqaZp9pXRy0lbO1j2tnYM3FxxKLWywR8DlzdQ9fpexS6QXmtQcm0Sc2bXJN69s_4itxO3zA0eoFpc03Dtu_eK5p2PwTVKr8RMerey_T6PGjERuHfimnvshBgIta-ILu6VgNEn6MvxnBkivBhR-pm3GMN7rRjToHKwDYGOf8w58hSeS8ISGrSqn-m6xJLkQ2Zq_f4G3vLoG8F0D3RSRIAYBLttIcfcNj0u8oMYbrJ6jEnKccVggsWfLVxIdabvwOWNwVZ5gaS7i0YLQA8IVtroltPJNZA8Z-7ZIpv3ztM-cvgTYjlLxqM2dIIqERitd_CHB8u-4ZOKpQWwG9rF0J1zMSnFNtseFBIyvB0IIUn5MCE5_BvI");
                            }
//                        if(!token.isEmpty()) {
                            if (!token.isEmpty()) {

                                ed.putString("token", token);
                                ed.commit();
                                String user_type = String.valueOf(responseData.getData().getUser().getUser_type());

                                Log.e(user_type, "user_type: " + user_type);

                                ed.putString("user_type", user_type);
                                ed.commit();

                                startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                                finish();
                            } else {

                                startActivity(new Intent(SplashScreen.this, IntroScreen.class));
                                finish();
                            }
                        } catch (Exception e) {
                            startActivity(new Intent(SplashScreen.this, IntroScreen.class));
                            finish();
                        }
                        Log.e("TAG", "token:----- " + token);
                    }
                } else {
                    startActivity(new Intent(SplashScreen.this, IntroScreen.class));
                    finish();
                }
//            }

//                Log.e("tokensss","token:-  " +response.body().getCode() );
//
//                String jsonString = response.body().toString();
//
//                Gson gson = new Gson();
//                MyResponseData responseData = gson.fromJson(jsonString, MyResponseData.class);
//
//// Access the token
//                String token = responseData.getData().getToken();

//                if (response.body().getCode().equalsIgnoreCase("200")) {
//                    Toast.makeText(LoginActivity.this,  response.body().getMsg(), Toast.LENGTH_SHORT).show();
//
//
//
//
//
////                    String token = response.body().getData().getToken();
////                    Log.e("tokensss","token:-  " +token );
////                    ed.putString("token",token);
////                    ed.commit();
//
//
//                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//
//                } else {
//                    Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                }
//                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<MyResponseData> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
//                dialog.dismiss();
            }
        });
    }
}