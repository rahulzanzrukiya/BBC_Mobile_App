package com.bbc.agsolutions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bbc.agsolutions.Model.JoinModel;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinBBCActivity extends Fragment {

    EditText cpname, octype, pddetails, message;

    String cp, oc, pd, mess;
//    CheckBox checkBox;
    ProgressDialog dialog;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_join_bbcactivity, container, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sp.edit();

        cpname = view.findViewById(R.id.cpname);
        octype = view.findViewById(R.id.octype);
        pddetails = view.findViewById(R.id.pddetails);
        message = view.findViewById(R.id.message);
//        checkBox = view.findViewById(R.id.checkBox);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        view.findViewById(R.id.getstart).setOnClickListener(v -> {

            cp = cpname.getText().toString();
            oc = octype.getText().toString();
            pd = pddetails.getText().toString();
            mess = message.getText().toString();

//            if (checkBox.isChecked()) {
                if (!cp.isEmpty() && !oc.isEmpty() && !pd.isEmpty() && !mess.isEmpty()) {
//                    Toast.makeText(getActivity(), "not empty", Toast.LENGTH_SHORT).show();
                    get_updatedata();
                }
                if (cp.isEmpty()) {
                    cpname.setError("Enter Company Name");
                }
                if (oc.isEmpty()) {
                    octype.setError("Enter Occupation");
                }
                if (pd.isEmpty()) {
                    pddetails.setError("Enter Service or Product");
                }
                if (mess.isEmpty()) {
                    message.setError("Enter Message");
                }
//            } else {
//                Toast.makeText(getActivity(), "Chack Terms and Conditions", Toast.LENGTH_SHORT).show();
//            }
        });

        return view;
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
        Call<JoinModel> call = loginservice.join_BBC(cp,oc,pd,mess);
        call.enqueue(new Callback<JoinModel>() {
            @Override
            public void onResponse(Call<JoinModel> call, Response<JoinModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode() == 200) {

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    cpname.setText(null);
                    octype.setText(null);
                    pddetails.setText(null);
                    message.setText(null);

//                    Log.e("responce..", "branches:-  " + branches.size());

                } else {
                    cpname.setText(null);
                    octype.setText(null);
                    pddetails.setText(null);
                    message.setText(null);
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
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