package com.bbc.agsolutions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bbc.agsolutions.Model.JoinModel;
import com.bbc.agsolutions.Model.ProfileModel;
import com.bbc.agsolutions.Model.UserType;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_upActivity extends AppCompatActivity {

    EditText ename, egmail, emobile, earea;

    String name, email, mobile, usertype, area;

    CheckBox checkBox;
    ProgressDialog dialog;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    Dialog dialog1;
    Spinner spinner;
    String mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.popup);
        dialog1.setCancelable(false);
//        dialog1.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_bg));
        dialog1.getWindow().setLayout(-1, -2);

        mob = getIntent().getStringExtra("mob");

        ImageView ok = dialog1.findViewById(R.id.ok);
        ok.setOnClickListener(v -> {
//            dialog1.dismiss();
//            startActivity(new Intent(this,LoginActivity.class));
//            get_updatedata();
        });

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();

        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);
        ename = findViewById(R.id.ename);
        egmail = findViewById(R.id.egmail);
        emobile = findViewById(R.id.emobile);
//        eusertype = findViewById(R.id.eusertype);
        earea = findViewById(R.id.earea);

        emobile.setText(mob);

        spinner.getBackground().setColorFilter(getResources().getColor(R.color.grey2), PorterDuff.Mode.SRC_ATOP);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                usertype = (String) parentView.getItemAtPosition(position);

                if (position > 0) {
                    usertype = (String) parentView.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        findViewById(R.id.getstart).setOnClickListener(v -> {

            name = ename.getText().toString();
            email = egmail.getText().toString();
            mobile = emobile.getText().toString();
            area = earea.getText().toString();

//            if (checkBox.isChecked()) {
                if (!name.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !usertype.equals("Select Type") && !area.isEmpty()) {
                    if (emobile.getText().toString().trim().length() < 10) {
                        Toast.makeText(Sign_upActivity.this, "Valid Mobile Number required!", Toast.LENGTH_SHORT).show();
                    } else {

                        get_updatedata();
                    }
                }
                if (name.isEmpty()) {
                    ename.setError("Enter Name");
                }
                if (email.isEmpty()) {
                    egmail.setError("Enter email");
                }
                if (mobile.isEmpty()) {
                    emobile.setError("Enter mobile");
                }
                if (usertype.equals("Select Type")) {
                    Toast.makeText(Sign_upActivity.this, "Please Select Type", Toast.LENGTH_SHORT).show();
                }
                if (area.isEmpty()) {
                    earea.setError("Enter area");
                }
//            } else {
//                Toast.makeText(Sign_upActivity.this, "Chack Terms and Conditions", Toast.LENGTH_SHORT).show();
//            }
        });

        User_type();

    }

    List<String> vendorrarray = new ArrayList<>();

    public void User_type() {
        dialog.show();
        vendorrarray.clear();
        vendorrarray.add("Select Type");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//        if (token != null) {
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + sp.getString("token", ""))
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<UserType> call = loginservice.get_User_Type();
        call.enqueue(new Callback<UserType>() {
            @Override
            public void onResponse(Call<UserType> call, Response<UserType> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode().equalsIgnoreCase("200")) {

                    ArrayList<UserType> branches = response.body().getData();
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreatTrip.this, R.layout.simple_spinner_item, vhicalarray);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerBranches.setAdapter(adapter);
                    for (UserType branch : branches) {
                        vendorrarray.add(branch.getUser_type_name());
                    }


                    ArrayAdapter<String> adapterdriver = new ArrayAdapter<>(Sign_upActivity.this, R.layout.simple_spinner_item, vendorrarray);
                    adapterdriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapterdriver);


//                    setupSpinner(branchNames);
                    Log.e("responce..", "branches:-  " + branches.size());

                } else {
                    Toast.makeText(Sign_upActivity.this, "Network Error!!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserType> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();
            }
        });
    }

    public void get_updatedata() {
        dialog.show();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        if (token != null) {
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + sp.getString("token", ""))
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
//        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<JoinModel> call = loginservice.Sign_Up(name, email, mobile, usertype, area);
        call.enqueue(new Callback<JoinModel>() {
            @Override
            public void onResponse(Call<JoinModel> call, Response<JoinModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode() == 200) {

                    Toast.makeText(Sign_upActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    JoinModel apiResponse = response.body();

                    JoinModel.Profile profile = apiResponse.getData();

                    ed.putString("mobail", mobile);
//                        ed.putString("userbranch", userbrance);
                    ed.putString("password", profile.getCpassword());
//                        ed.putString("fullname", fillname);
//                        ed.putString("user_type", user_type_id);
//                    int sign = 1;

                    ed.putInt("sign",1);
                    startActivity(new Intent(Sign_upActivity.this, LoginActivity.class));
                    ed.commit();


//                    dialog1.show();

//                    Log.e("responce..", "branches:-  " + branches.size());

                } else {
                    Toast.makeText(Sign_upActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<JoinModel> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();
            }
        });
    }
}