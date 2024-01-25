package com.bbc.agsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbc.agsolutions.Model.ProfileModel;
import com.bbc.agsolutions.Model.ProfileModel;
import com.bbc.agsolutions.Model.ProfileModel;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    ImageView back;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog dialog;

    RecyclerView rv;

    TextView name, occupation, company, product, mobile, whatsapp_number, email, oc;

    ImageView userimg;

    LinearLayout chat, call, mail, share;

    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        name = findViewById(R.id.name);
        company = findViewById(R.id.company);
        userimg = findViewById(R.id.userimg);
        chat = findViewById(R.id.chat);
        call = findViewById(R.id.call);
        mail = findViewById(R.id.mail);
        share = findViewById(R.id.share);
        rv = findViewById(R.id.rv);
        back = findViewById(R.id.back);
        oc = findViewById(R.id.oc);

        idd = getIntent().getStringExtra("id");

        back.setOnClickListener(v -> {
            onBackPressed();
        });
        chat.setOnClickListener(v -> {
            openWhatsAppChat(number);
        });
        call.setOnClickListener(v -> {
            String phoneNumber = "tel:" + "+91 " + number; // replace with the actual phone number
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
            startActivity(dialIntent);
        });
        mail.setOnClickListener(v -> {
            composeEmail();
            Log.e("mailid", "mailid: " + mailid);
        });

        share.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                try {
                    Picasso.get().load("http://businessboosters.club/public/images/user_images/"+imguri).error(R.drawable.bbc_ic).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            String path = getExternalCacheDir() + "/BBCClub.png";
                            OutputStream out = null;
                            File file = new File(path);
                            Log.e("TAG", "onBitmapLoaded: " );
                            try {
                                out = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                out.flush();
                                out.close();
                                Log.e("TAG", "onBitmapLoaded2: " );
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("TAG", "onBitmapLoaded1: " );
                            }
                            Uri bmpUri = FileProvider.getUriForFile(ProfileActivity.this, getPackageName() + ".provider", file);
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("image/jpeg");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + nname + "*" + "\n\n" + "*" + comp + "*"  + "\n"  + occc +  "\n\n"  + "Mobile no" + " : " +  number + "\n" +  "Email" + " : " +  mailid /*+ "\n" +"*" + "Product" + "*" + " : "+  Product + "\n" + "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                            startActivity(shareIntent);
                        }
                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.e("TAG", "onBitmapLoaded3: " );
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Log.e("TAG", "onBitmapLoaded4: " );
                        }
                    });
                } catch (Exception e) {
                        Log.e("TAG", "Exception: " + e.getMessage());
                }
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        currantHistory(idd);

    }

    private void composeEmail() {
        String[] addresses = {mailid};
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);

            startActivity(intent);
        } catch (Exception e) {
//            throw new RuntimeException(e);
        }

//        try {
//            Picasso.get().load("http://businessboosters.club/public/images/user_images/"+imguri).error(R.drawable.bbc_ic).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    String path = getExternalCacheDir() + "/BBCClub.png";
//                    OutputStream out = null;
//                    File file = new File(path);
//                    Log.e("TAG", "onBitmapLoaded: " );
//                    try {
//                        out = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
//                        out.flush();
//                        out.close();
//                        Log.e("TAG", "onBitmapLoaded2: " );
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e("TAG", "onBitmapLoaded1: " );
//                    }
//                    Uri bmpUri = FileProvider.getUriForFile(ProfileActivity.this, getPackageName() + ".provider", file);
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("image/jpeg");
//                    shareIntent.setPackage("com.google.android.gm");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi, " + sp.getString("name","") + " has shared Bussiness Profile  with you from BBC Club app  " + "\n\n" + "*" + nname + "*" + "\n\n" + "*" + "company" + "*" + " : "+comp + "\n" + "*" + "occupation" + "*" + " : "+  occc + "\n" + "*" + "Mobile no" + "*" + " : "+  number + "\n" + "*" + "Email" + "*" + " : "+  mailid + "\n" +"*" + "Product" + "*" + " : "+  Product + "\n" + "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName());
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                    startActivity(shareIntent);
//                }
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                    Log.e("TAG", "onBitmapLoaded3: " );
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    Log.e("TAG", "onBitmapLoaded4: " );
//                }
//            });
//        } catch (Exception e) {
//            Log.e("TAG", "Exception: " + e.getMessage());
//        }
    }
    private void openWhatsAppChat(String phoneNumber) {
        try {
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + "+91 +" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Picasso.get().load(R.drawable.bbc_ic/*R.string.img_uri+imguri*/).error(R.drawable.bbc_ic).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    String path = getExternalCacheDir() + "/BBCClub.png";
//                    OutputStream out = null;
//                    File file = new File(path);
//                    Log.e("TAG", "onBitmapLoaded: " );
//                    try {
//                        out = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
//                        out.flush();
//                        out.close();
//                        Log.e("TAG", "onBitmapLoaded2: " );
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e("TAG", "onBitmapLoaded1: " );
//                    }
//                    Uri bmpUri = FileProvider.getUriForFile(ProfileActivity.this, getPackageName() + ".provider", file);
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("image/jpeg");
//                    shareIntent.setPackage("com.whatsapp");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi, " + sp.getString("name","") + " has shared Bussiness Profile  with you from BBC Club app  " + "\n\n" + "*" + nname + "*" + "\n\n" + "*" + "company" + "*" + " : "+comp + "\n" + "*" + "occupation" + "*" + " : "+  occc + "\n" + "*" + "Mobile no" + "*" + " : "+  number + "\n" + "*" + "Email" + "*" + " : "+  mailid + "\n" +"*" + "Product" + "*" + " : "+  Product + "\n" + "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName());
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//                    startActivity(shareIntent);
//                }
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                    Log.e("TAG", "onBitmapLoaded3: " );
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    Log.e("TAG", "onBitmapLoaded4: " );
//                }
//            });
//        } catch (Exception e) {
//            Log.e("TAG", "Exception: " + e.getMessage());
//        }

    }


    String number = null;
    String mailid = null;
    String Product = null;
    String nname = null;

    String imguri;
    String occc;
    String comp;

    public void currantHistory(String iddd) {
        dialog.show();
        products.clear();
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
        Call<ProfileModel> call = loginservice.user_profile(iddd);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode() == 200) {

                    ProfileModel apiResponse = response.body();

                    ProfileModel.Profile profile = apiResponse.getData();

                    if (profile != null) {

                        name.setText("" + profile.getName());
                        company.setText("" + profile.getCompany());
                        oc.setText("" + profile.getOccupation());

                        imguri = profile.getImage();

                        if (profile.getImage() == null) {
                            Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/no_images.png")).placeholder(R.drawable.loader_gif).error(R.drawable.no_profiles).into(userimg);
                        } else {
                            Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + profile.getImage())).placeholder(R.drawable.loader_gif).error(R.drawable.no_profiles).into(userimg);
                        }

                        number = profile.getMobile();
                        mailid = profile.getEmail();
                        Product = profile.getProduct();
                        nname = profile.getName();
                        occc = profile.getOccupation();
                        comp = profile.getCompany();

                        String[] separatedStrings = Product.split(",");

                        for (String separatedString : separatedStrings) {
                            products.add(separatedString);
                            Log.e("products", "products: " + products);
                        }
                        Home_Today_list_Adapter home_today_list_adapter = new Home_Today_list_Adapter(ProfileActivity.this, products);
                        rv.setAdapter(home_today_list_adapter);
                    }

                } else {
                    Toast.makeText(ProfileActivity.this, "Mobile Number is Not Registered", Toast.LENGTH_SHORT).show();
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

    List<String> products = new ArrayList<>();

    public class Home_Today_list_Adapter extends RecyclerView.Adapter<Home_Today_list_Adapter.Holder> {
        ProfileActivity context;
        List<String> produc;

        public Home_Today_list_Adapter(ProfileActivity context, List<String> products) {
            this.context = context;
            this.produc = products;
        }

        @Override
        public int getItemCount() {
            return produc.size();
        }

        @NonNull
        @Override
        public Home_Today_list_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list_item1, parent, false);
            return new Home_Today_list_Adapter.Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Home_Today_list_Adapter.Holder holder, @SuppressLint("RecyclerView") final int position) {

            holder.prd.setText(produc.get(position));

            Log.e("TAG", "onBindViewHolder: " + produc.get(position));

        }

        class Holder extends RecyclerView.ViewHolder {

            TextView prd, mobile, date, active;
            ImageView profile, call, whatssap;

            public Holder(@NonNull View itemView) {
                super(itemView);

                prd = itemView.findViewById(R.id.prd);

            }
        }


    }
}