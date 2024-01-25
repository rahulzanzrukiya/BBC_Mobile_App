package com.bbc.agsolutions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.dfc.agsolutions.Model.MyResponseData;
import com.bbc.agsolutions.Model.MyResponseData;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    EditText edtmobail, edtpassword;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    TextView mobailno;
    private FirebaseAuth mAuth;
    private OtpTextView otpTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        edtmobail = findViewById(R.id.edtmobail);
//        edtpassword = findViewById(R.id.edtpassword);
        mobailno = findViewById(R.id.mobailno);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        mobailno.setText("" + sp.getString("mobail", ""));

        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode("+91" + sp.getString("mobail", ""));


//        ed.putString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjVhYzA2YjA5ZWNmNGVhMzgzM2UxZTE3Yzg2NmFkMThmOTg2MDQyOWJkNGQwYTZiOWFhMzZlMjMyYzQyNDZkYzU2OTNiMzMyNjQ5ODdhNmMiLCJpYXQiOjE3MDI4MDE1MTkuMjM4MzI5ODg3MzkwMTM2NzE4NzUsIm5iZiI6MTcwMjgwMTUxOS4yMzgzMzIwMzMxNTczNDg2MzI4MTI1LCJleHAiOjE3MzQ0MjM5MTkuMjM2Njc0MDcwMzU4Mjc2MzY3MTg3NSwic3ViIjoiMSIsInNjb3BlcyI6W119.UgRdSmMZgdFaZKMQ6PsjO4AgCgsPfg75WDlGZ-VPYi4gCArZt36jvCvjxpKx6ddXr_Ls5TUTiXTXX074IYXEQmrv_LyBLvqz4yPiMVA8bcsnVEhTJ4ymO_xy1U-bMrklda3wUcsMyiJ-advc-DsPcvzWefGgIn3t73slG6qxSBIaJ85cToNgese8j_wNjW2038KqVOtXNObUCo5Wx6L5PbQPU99VKjAOrctDlJOmf8Hr6e3u98CEPCKtiJIm_-0Pb7Rc2K0oio7peg9f9nqGmJJrwxlHoevbsttL9ULp6XfkSVfa4ttE_jEvJLYO6J7W7mGWaVFzEeVw31a61_mSDPk4Y_1fpo7UtzdLCA-Xl8CBW1w5d7NH_mJHDJu7Lq8lEzmeIX_RsP9yHZYhzEzZuhDNPtgblyDFkQvxIOiO1-8mLHRUmIZTl_TYUHCV-jyqxAFyhTgVKM_EGFKzG21pGUiFTfN8ZdfO2RUCY3afK1bKRKmv0W5tVF0opNdx-MJksn0AaVAYIgsNT6AOdqgNojpswQkwPaXJzgVh4FkDIwRvpJBWdC-Azu7iKlzAXhNhQwl-_bci0y_mhHPbQ5u1iM2JbU0XvoNQb9bp7RqYngMDlywFe6Y2kux5fZEHjwMjvK7S41X8NIksfJwNVPTLfKPvwPMA1-1-IDWSaCJjdpU");
//        ed.commit();

        otpTextView = findViewById(R.id.otp_view);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }

            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
//                Toast.makeText(LoginActivity.this, "The OTP is " + otp, Toast.LENGTH_SHORT).show();

            }
        });


        findViewById(R.id.getstart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (otpTextView.getOTP().length() == 0) {

                    Toast.makeText(LoginActivity.this, "Please Enter OTP First", Toast.LENGTH_SHORT).show();
//                 otpcode
                } else {

                    getlogin();
//                 Toast.makeText(LoginActivity.this, "Please Enter validate OTP", Toast.LENGTH_SHORT).show();

                }


//
//                if (edtmobail.getText().toString().length() == 0) {
//                    Toast.makeText(LoginActivity.this, "Please Enter Mobile Number!!", Toast.LENGTH_SHORT).show();
//                } else if (edtmobail.getText().toString().trim().length() < 10) {
//                    Toast.makeText(LoginActivity.this, "Valid Mobile Number required!", Toast.LENGTH_SHORT).show();
//                } else if (edtpassword.getText().toString().length() == 0) {
//                    Toast.makeText(LoginActivity.this, "Please Enter Password!!", Toast.LENGTH_SHORT).show();
//                } else {
//                    getlogin();
//
//                }

            }
        });

    }

    private String verificationId;
    private String otpcode;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            dialog.dismiss();
            Log.e("authcheck", "verificationId:-  " + verificationId);

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.e("authcheck", "code:-  " + code);
            otpcode = code;
            if (code != null) {
//                edOtp1.setText("" + code.substring(0, 1));
//                edOtp2.setText("" + code.substring(1, 2));
//                edOtp3.setText("" + code.substring(2, 3));
//                edOtp4.setText("" + code.substring(3, 4));
//                edOtp5.setText("" + code.substring(4, 5));
//                edOtp6.setText("" + code.substring(5, 6));


                otpTextView.setOTP(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            getlogin();

//            ed.putString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMzg5ZGM4MmJlZTQxYTYwZGE3MzQ3NzEwOTNmZjYwMTk2YTg4NGE5OWI0ZGIwZjM5NmJjZDI1OGQ2NTQ1OTkyMzBhNjc0NWMzYmMyNWFiOWYiLCJpYXQiOjE3MDI5MDIxODguMzcwODA1MDI1MTAwNzA4MDA3ODEyNSwibmJmIjoxNzAyOTAyMTg4LjM3MDgwNzg4NjEyMzY1NzIyNjU2MjUsImV4cCI6MTczNDUyNDU4OC4zNTk1NDA5MzkzMzEwNTQ2ODc1LCJzdWIiOiIzNjEiLCJzY29wZXMiOltdfQ.AzaI4JRsSfzXJHIzIixkRn73Mqn6X80sYWmKtxgVoJL0132Q1ifSMznWTv9V21ABwb4C6gPH-0BRs3Cg_CbwlsdBnvfQ-8dY4flt2phYr7HZoOWevqnep-L2DPifqV8y7r4csQpQFXXoTeOX0qnSi9GtT6nEpuCPPzaByR-3iF4KpqxI7jvBGKgAk83ofqB03RCvQIw40rsOjdFGxBU6jvdf70S3ncasFlHzBchQs13zk8u02-myY6z9f2xNr00mspgww7sj9EsRcTkXoUbASBF9EbdIKZwVLtD87cx43CPdx7BnrCUwT3NVmE4M5Gc7dqjiwmWU7WRewWpbm3SpeuWj1QT2Yj7ZdXJ012X0I2X2KqnJgT3WSMhsk4WcGM65Z4Azkx7zzCZ0hOjDOhfk08x7jwaSDuBrwA9Ma7tcVYYbIXMqCOf0m8L-3Ir46VZXbgOi9xNmjnWQJo2NW-CMy6jpwQe5U2YoygDar-ITnr9ZMexKwS4a7tz30WhaUMCJPcCX2rRm3haao9zRVU8sTIbtKo3XwMyk2odKR_Fmtip1sVJL0XgokV5wQa3hh8ceXoUbnNa037RiGE7KHdm29fQkEx_DCrKELFnku2BbEX_iXhV7-LPVky2DeTtNkVO19YQSwBR3ljxbAtht7twCEKsP8-s9aNnRU9CKRrHgl18");
//            ed.putBoolean("firsttime",true);
//            ed.commit();


//            startActivity(new Intent(LoginActivity.this, HomeActivity.class));

//            User user1 = new User();
//            user1.setId("0");
//            user1.setFname("User");
//            user1.setEmail("user@gmail.com");
//            user1.setMobile("8888888888");
//            user1.setCcode("+91");
//            sessionManager.setUserDetails("", user1);
//            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//            finish();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    public static int isvarification = -1;

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Log.e("taskcheck", "task successfully");

                dialog.dismiss();
                getlogin();


//                        switch (isvarification) {
//                            case 0:
////                                Intent intent = new Intent(VerifyPhoneActivity.this, ChanegPasswordActivity.class);
////                                intent.putExtra("phone", user.getMobile());
////                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                                startActivity(intent);
//                                break;
//                            case 1:
////                                createUser();
//                                break;
//                            case 2:
//                                break;
//                            default:
//                                break;
//                        }
            } else {
                dialog.dismiss();

                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendVerificationCode(String number) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                number,
//                60,
//                TimeUnit.SECONDS,
//                this,
//                mCallBack
//        );


        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // (optional) Activity for callback binding
                // If no activity is passed, reCAPTCHA verification can not be used.
                .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                number,
//                60L,
//                TimeUnit.SECONDS,
//                this,
//                mCallBack
//        );
    }


    public void getlogin() {
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.commn_url)).addConverterFactory(GsonConverterFactory.create()).build();
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
                        try {
                            String token = responseData.getData().getToken();
                            ed.putString("token", token);
                            ed.putBoolean("firsttime", true);
                            String user_type = String.valueOf(responseData.getData().getUser().getUser_type());

                            Log.e(user_type, "user_type: " + user_type);

                            ed.putString("user_type", user_type);
//                        ed.commit();
                            ed.commit();
                        } catch (Exception e) {
                            ed.putString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiOGYzNWM4YjdhZjI1NWRkYTBkNzUwZmM0YjY3ZmQzZWI0NDFkZDEzNTk1MjgxZjljODA4OWU4MzFkMmE3YTQyZjQ1N2UxMTJiYjIxMzc3YjkiLCJpYXQiOjE3MDQzNjk2NDMuNDA4NzYyOTMxODIzNzMwNDY4NzUsIm5iZiI6MTcwNDM2OTY0My40MDg3NjY5ODQ5Mzk1NzUxOTUzMTI1LCJleHAiOjE3MzU5OTIwNDMuNDA2Nzk2OTMyMjIwNDU4OTg0Mzc1LCJzdWIiOiIxMDciLCJzY29wZXMiOltdfQ.ExOqu4Iv7J7imSabbXijDdHCi79JOR3NX8QGEUeYhbyvE3MQu-KTd7GM4hvBhJTdW04CRCS-lTf9HGtJmbhZVO0JaUf7P8uIpHc8Qu226qVGmQEDRHA5G2FnckZlNLj63JurPJ-qfDcPIrHZafFGnCNVZOF3OJfJoj1NACOynDuU_U8PDpHcTFBKxeIb9TO2eKUhu70fd6RFSlUImBZkG3nF0UyFuZOHj6utlIilUe58MkGreRSmAZf7bNDJF9w3Dgns1KrHqNz0kE_mzphytbIicE0UVfv8g7qrTGkpLKgjdAR6yvY9XMEd8XeqaZp9pXRy0lbO1j2tnYM3FxxKLWywR8DlzdQ9fpexS6QXmtQcm0Sc2bXJN69s_4itxO3zA0eoFpc03Dtu_eK5p2PwTVKr8RMerey_T6PGjERuHfimnvshBgIta-ILu6VgNEn6MvxnBkivBhR-pm3GMN7rRjToHKwDYGOf8w58hSeS8ISGrSqn-m6xJLkQ2Zq_f4G3vLoG8F0D3RSRIAYBLttIcfcNj0u8oMYbrJ6jEnKccVggsWfLVxIdabvwOWNwVZ5gaS7i0YLQA8IVtroltPJNZA8Z-7ZIpv3ztM-cvgTYjlLxqM2dIIqERitd_CHB8u-4ZOKpQWwG9rF0J1zMSnFNtseFBIyvB0IIUn5MCE5_BvI");
                            ed.putString("user_type", "1");
                            ed.commit();
//                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                            finish();
                        }


//                        Log.e("ttdddffff", "token:-  " + token);

//                        String user_type = sp.getString("user_type", "");

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    }
                } else {

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
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<MyResponseData> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();
            }
        });
    }
}