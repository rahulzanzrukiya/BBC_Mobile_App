package com.bbc.agsolutions;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bbc.agsolutions.Model.CheckNomberModel;
import com.google.android.material.checkbox.MaterialCheckBox;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckMobailNumber extends AppCompatActivity {

    EditText edtmobail;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    MaterialCheckBox checkBox;

    //    TextView privacy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mobail_number);

        edtmobail = findViewById(R.id.edtmobail);
        checkBox = findViewById(R.id.checkBox);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        dialog = new ProgressDialog(CheckMobailNumber.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        edtmobail.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        // Set InputFilter to allow only numbers
        edtmobail.setFilters(new InputFilter[]{new NumberInputFilter()});

        findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateAppDialog();

//                String url = "https://dfclogistics.online/privacypolicy.html";
//
//                // Create a Uri object from the URL
//                Uri uri = Uri.parse(url);
//
//                // Create an Intent with ACTION_VIEW to open the URL in a browser
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//                // Check if there is an app available to handle the Intent
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    // Start the activity if there is an app available
//                    startActivity(intent);
//                } else {
//                    // Handle the case where there is no app available
//                    // (You may want to show a message to the user or implement an alternative behavior)
//                }


            }
        });

        findViewById(R.id.getstart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CheckMobailNumber.this, Login2.class));
                if(checkBox.isChecked()) {
                    if (edtmobail.getText().toString().length() == 0) {
                        Toast.makeText(CheckMobailNumber.this, "Please Enter Mobile Number!!", Toast.LENGTH_SHORT).show();
                    } else if (edtmobail.getText().toString().trim().length() < 10) {
                        Toast.makeText(CheckMobailNumber.this, "Valid Mobile Number required!", Toast.LENGTH_SHORT).show();
                    } else {
                        getcheckMobail();
                    }
                }else {
                    Toast.makeText(CheckMobailNumber.this, "Chack Privacy", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private static class NumberInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // Only allow numbers
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(source.charAt(i))) {
                    return ""; // Remove the non-numeric character
                }
            }
            return null; // Accept the input
        }
    }
    private void showUpdateAppDialog() {

        Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -2;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, android.R.color.transparent)));
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_update_app);
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
        WebView webwiew = dialog.findViewById(R.id.webwiew);

//        ((LinearLayout) dialog.findViewById(R.id.exit)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mactivity.finishAffinity();
//            }
//        });

        WebSettings webSettings = webwiew.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Load a URL
        webwiew.loadUrl("https://dfclogistics.online/privacypolicy.html");




        ((LinearLayout) dialog.findViewById(R.id.ln_try_again)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dialog.dismiss();
//                    webView = findViewById(R.id.webView);

                    // Enable JavaScript (optional)

//                    mactivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Myapplication.getString(Const.updatelink))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getcheckMobail() {
        dialog.show();

        Log.e("number", "getcheckMobail: "+edtmobail.getText().toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<CheckNomberModel> call = loginservice.get_check_mobail(edtmobail.getText().toString());
        call.enqueue(new Callback<CheckNomberModel>() {
            @Override
            public void onResponse(Call<CheckNomberModel> call, Response<CheckNomberModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode() == 200) {

//                    CheckNomberModel apiResponse = response.body();
//
//                    CheckNomberModel.UserData userData = apiResponse.getData();
//                    if (userData != null) {
//                        String mobileNumber = userData.getMobile();
//                        String userbrance = userData.getUser_branch();
//                        String cpassword = userData.getCpassword();
//                        String fillname = userData.getFull_name();
//                        String user_type_id = userData.getUser_type_id();
//
                        ed.putString("mobail", edtmobail.getText().toString());
//                        ed.putString("userbranch", userbrance);
                        ed.putString("password", String.valueOf(response.body().getData()));
//                        ed.putString("fullname", fillname);
//                        ed.putString("user_type", user_type_id);
//
                        ed.commit();
//
//                        // Handle the data as needed
//                        Log.e("mobaicheck", "getmobail:-  " + mobileNumber);
//                        Toast.makeText(CheckMobailNumber.this, "Mobile Number is Active", Toast.LENGTH_SHORT).show();
//
                        startActivity(new Intent(CheckMobailNumber.this, LoginActivity.class));
                        finish();
//
//                    }

                } else {

                    startActivity(new Intent(CheckMobailNumber.this, Sign_upActivity.class).putExtra("mob",edtmobail.getText().toString()));
                    finish();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<CheckNomberModel> call, Throwable t) {
                Log.e("onFailure", "" + t.toString());
                dialog.dismiss();
            }
        });
    }
}