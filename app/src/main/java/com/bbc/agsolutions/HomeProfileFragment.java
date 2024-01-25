package com.bbc.agsolutions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbc.agsolutions.Model.ProfileModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeProfileFragment extends Fragment {
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog dialog;
    TextView name, Firm_Name, Mobile, Email, DOB, Anniversary, Category, Product, Address;

    ImageView logout;
    ImageView myimg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_profile_fragment, container, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sp.edit();

        name = view.findViewById(R.id.name);
        Firm_Name = view.findViewById(R.id.Firm_Name);
        Mobile = view.findViewById(R.id.Mobile);
        Email = view.findViewById(R.id.Email);
        DOB = view.findViewById(R.id.DOB);
        Anniversary = view.findViewById(R.id.Anniversary);
        Category = view.findViewById(R.id.Category);
        Product = view.findViewById(R.id.Product);
        Address = view.findViewById(R.id.Address);
        logout = view.findViewById(R.id.logout);
        myimg = view.findViewById(R.id.myimg);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();


        currantHistory();


        logout.setOnClickListener(v -> {
            countryDialoglogout();
        });

        return view;
    }

    private void countryDialoglogout() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.logout_bottom_sheet_dialog);
        bottomSheetDialog.show();
        TextView logout = bottomSheetDialog.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.clear();
                ed.commit();
                startActivity(new Intent(getActivity(), CheckMobailNumber.class));
                getActivity().finish();
            }
        });
        TextView delete = bottomSheetDialog.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                get_logout();
            }
        });
    }

    public void currantHistory() {
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

        Log.e("token", "token: " + sp.getString("token", ""));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<ProfileModel> call = loginservice.get_profile();
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode() == 200) {

                    ProfileModel apiResponse = response.body();

                    ProfileModel.Profile profile = apiResponse.getData();
                    if (profile != null) {
                        name.setText("" + profile.getName());
                        ed.putString("name", profile.getName());
                        ed.commit();
                        Firm_Name.setText(":   " + profile.getCompany());
                        Mobile.setText(":   " + profile.getMobile());
                        Email.setText(":   " + profile.getEmail());
//                        DOB.setText(":   " + profile.getDob());
                        Anniversary.setText(":   " + profile.getDoa());
                        Category.setText(":   " + profile.getOccupation());
                        Product.setText(":   " + profile.getProduct());
                        Address.setText(":   " + profile.getAddress());
                        Picasso.get().load("http://businessboosters.club/public/images/user_images/" + profile.getImage()).placeholder(R.drawable.loader_gif).error(R.drawable.no_profiles).into(myimg);

                        String date1 = profile.getDob();
                        if(date1 == null) {
                            DOB.setText(":   " + date1);
                        }
                        else {
                            try {
                                SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date date = inputDateFormat.parse(date1);

                                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String datefiormate = outputDateFormat.format(date);

                                DOB.setText(":   " + datefiormate);

                            } catch (ParseException e) {
                                DOB.setText(":   " + date1);
                            }
                        }

                    }
                } else {
                    Toast.makeText(getActivity(), "Mobile Number is Not Registered", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();
            }
        });
    }
}