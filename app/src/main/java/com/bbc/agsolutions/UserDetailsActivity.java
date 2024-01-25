package com.bbc.agsolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbc.agsolutions.Model.ProfileModel;
import com.bbc.agsolutions.Model.UserDetailsModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tmall.ultraviewpager.UltraViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetailsActivity extends AppCompatActivity {

    UltraViewPager pager1;
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    TextView name, occupation, company, product, mobile, whatsapp_number, email, oc,about_tx;

    ImageView userimg,back;

    RecyclerView rv;

    LinearLayout chat, call, mail, share;

    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        idd = getIntent().getStringExtra("company");
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
        about_tx = findViewById(R.id.about_tx);

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
                            Uri bmpUri = FileProvider.getUriForFile(UserDetailsActivity.this, getPackageName() + ".provider", file);
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

        pager1 = (UltraViewPager) findViewById(R.id.pager1);
        pager1.setVisibility(View.VISIBLE);
        pager1.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        pager1.initIndicator();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pager1.getIndicator()
                    .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                    .setFocusColor(this.getColor(R.color.colorPrimary))
                    .setNormalColor(Color.WHITE)
                    .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        }
        pager1.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        pager1.setMultiScreen(0.8f);
//                            pager1.
        pager1.getIndicator().setMargin(5, 5, 5, 25);
        pager1.getIndicator().build();
//                            pager1.setInfiniteLoop(true);
        pager1.setAutoScroll(4000);

//        HomeFragment.SliderPagerAdapter adapter = new HomeFragment.SliderPagerAdapter(this, slider_img, title, btn_t, slider_link);
//        pager1.setAdapter(adapter);
//        Slider_Image();
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
    List<String> products = new ArrayList<>();

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
        Call<UserDetailsModel> call = loginservice.User_Details("AGSolutions");
        call.enqueue(new Callback<UserDetailsModel>() {
            @Override
            public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode() == 200) {

                    UserDetailsModel apiResponse = response.body();

                    UserDetailsModel.Profile profile = apiResponse.getProductimages();

                    if (profile != null) {

                        Log.e("profile", "profile: ");

                        name.setText("" + profile.getName());
                        company.setText("" + profile.getCompany());
                        oc.setText("" + profile.getOccupation());
                        about_tx.setText(""+profile.getProduct_about_us());
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
                        Home_Today_list_Adapter home_today_list_adapter = new Home_Today_list_Adapter(UserDetailsActivity.this, products);
                        rv.setAdapter(home_today_list_adapter);

                        List<String> product_imge = new ArrayList<>();
                        product_imge.add(profile.getProduct_image1());
                        product_imge.add(profile.getProduct_image2());
                        product_imge.add(profile.getProduct_image3());
                        product_imge.add(profile.getProduct_image4());
                        product_imge.add(profile.getProduct_image5());

                        SliderPagerAdapter adapter = new SliderPagerAdapter(UserDetailsActivity.this,product_imge);
                        pager1.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(UserDetailsActivity.this, "Mobile Number is Not Registered", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();
            }
        });
    }

    List<String> slider_img = new ArrayList<>();
    List<String> title = new ArrayList<>();
    List<String> btn_t = new ArrayList<>();
    List<String> slider_link = new ArrayList<>();
    public void Slider_Image() {

        dialog.show();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + sp.getString("token", ""))
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.commn_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        Api loginservice = retrofit.create(Api.class);
        Call<SliderModel> call = loginservice.Slider_list();
        call.enqueue(new Callback<SliderModel>() {
            @Override
            public void onResponse(Call<SliderModel> call, Response<SliderModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode().equalsIgnoreCase("200")) {
//
                    ArrayList<SliderModel> branches = response.body().getData();
                    for (SliderModel branch : branches) {


                        slider_img.add(branch.getSlider_image());
                        title.add(branch.getSlider_heading());
                        btn_t.add(branch.getSlider_button_text());
                        slider_link.add(branch.getSlider_link());


//                        fullname.add(branch.getFull_name());
//                        mobile.add(branch.getMobile());
//                        dl_expiry.add(branch.getDl_expiry());
//                        user_status.add(branch.getUser_status());
//                        user_image.add(branch.getUser_image());

                    }
//                    Home_Today_list_Adapter adapter = new Home_Today_list_Adapter(UserDetailsActivity.this, response.body().getData());
//                    pager1.setAdapter(adapter);

//                    Log.e("responce..", "branches:-  " + branches.size());

                } else {
//                    nodata.setVisibility(View.VISIBLE);
//                    driverlist.setVisibility(View.GONE);
                    Toast.makeText(UserDetailsActivity.this, "Network Error!!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
//                swipeRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onFailure(Call<SliderModel> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();

            }
        });

    }
    public class SliderPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater = null;
        UserDetailsActivity activity; List<String> product_imge;

        public SliderPagerAdapter(UserDetailsActivity activity, List<String> product_imge) {
            this.inflater = inflater;
            this.activity = activity;
            this.product_imge = product_imge;
        }

        @Override
        public int getCount() {
            return product_imge.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.slider, container, false);
            ImageView img = (ImageView) itemView.findViewById(R.id.image);
            TextView title = (TextView) itemView.findViewById(R.id.title);
            TextView btn_t = (TextView) itemView.findViewById(R.id.btn_t);
            RelativeLayout rv1 = (RelativeLayout) itemView.findViewById(R.id.rv1);
            RelativeLayout rv = (RelativeLayout) itemView.findViewById(R.id.rv);
            CardView slider_cd = (CardView) itemView.findViewById(R.id.slider_cd);
            try {
//                Picasso.get().load("https://businessboosters.club/public/images/slider_images/" + slider_img).error(R.drawable.testslider).into(img);

                if (product_imge == null) {
                    Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/product_images/no_image.jpg")).error(R.drawable.testslider).into(img);
                } else {
                    Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/product_images/" + product_imge.get(position))).error(R.drawable.testslider).into(img);
                }
//                title.setText(title1.get(position));
//                if (btn_t1.get(position).equals("")) {
//                    rv.setVisibility(View.GONE);
//                    slider_cd.setClickable(false);
//                } else {
//                    rv.setVisibility(View.VISIBLE);
//                    btn_t.setText(btn_t1.get(position));
//                    slider_cd.setClickable(true);
//                    slider_cd.setOnClickListener(v -> {
//
//                        String link = slider_link.get(position);
//
//                        Intent intent = new Intent(activity, LinkOpenActivity.class);
//                        intent.putExtra("link", link);
//                        startActivity(intent);
//
//                    });
//                }


                Log.e("Slider", "Slider: " + product_imge);
            } catch (Exception e) {
            }

            container.addView(itemView, 0);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    public class Home_Today_list_Adapter extends RecyclerView.Adapter<Home_Today_list_Adapter.Holder> {
        UserDetailsActivity context;
        List<String> produc;

        public Home_Today_list_Adapter(UserDetailsActivity context, List<String> products) {
            this.context = context;
            this.produc = products;
        }

//        public Home_Today_list_Adapter(UserDetailsActivity userDetailsActivity, List<String> products) {
//
//        }

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