package com.bbc.agsolutions;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bbc.agsolutions.Model.UserprofileModel;
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

public class HomeFragment extends Fragment {

//    UltraViewPager pagertop;

    RecyclerView rv;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    ProgressDialog dialog;
    EditText edtSearchCountry;
    String user_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

//        pagertop = view.findViewById(R.id.pagertop);
        rv = view.findViewById(R.id.rv);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ed = sp.edit();

//        ed.putBoolean("firsttime",true);
//        ed.commit();

        user_type = sp.getString("user_type", "");

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                currantHistory();
                Slider_Image();
            }
        }, 1000);

        edtSearchCountry = view.findViewById(R.id.edtSearchCountry);

        try {
            edtSearchCountry.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    try {
                        home_today_list_adapter.getFilter().filter(charSequence.toString());
                    } catch (Exception e) {
//                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e) {
//                throw new RuntimeException(e);
        }

        return view;
    }

    Home_Today_list_Adapter home_today_list_adapter;

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
        Call<UserprofileModel> call = loginservice.get_User_profile();
        call.enqueue(new Callback<UserprofileModel>() {
            @Override
            public void onResponse(Call<UserprofileModel> call, Response<UserprofileModel> response) {
                Log.e("responce..", "" + response.toString());

                if (response.body().getCode().equalsIgnoreCase("200")) {

                    ArrayList<UserprofileModel> data = response.body().getData();

                    home_today_list_adapter = new Home_Today_list_Adapter(getActivity(), response.body().getData());
                    rv.setAdapter(home_today_list_adapter);

                } else {
                    Toast.makeText(getActivity(), "Mobile Number is Not Registered", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserprofileModel> call, Throwable t) {
                Log.e("sdfsd", "" + t.toString());
                dialog.dismiss();
            }
        });
    }

    public class SliderPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater = null;
        FragmentActivity activity;
        List<String> slider_img1;
        List<String> title1;
        List<String> btn_t1;
        List<String> slider_link;

        public SliderPagerAdapter(FragmentActivity activity, List<String> slider_img1, List<String> title1, List<String> btn_t1, List<String> slider_link) {
            this.inflater = inflater;
            this.activity = activity;
            this.slider_img1 = slider_img1;
            this.title1 = title1;
            this.btn_t1 = btn_t1;
            this.slider_link = slider_link;
        }

        @Override
        public int getCount() {
            return slider_img1.size();
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

                if (slider_img1 == null) {
                    Picasso.get().load(Uri.parse("https://businessboosters.club/public/images/slider_images/slider1.jpg")).error(R.drawable.testslider).into(img);
                } else {
                    Picasso.get().load(Uri.parse("https://businessboosters.club/public/images/slider_images/" + slider_img1.get(position))).error(R.drawable.testslider).into(img);
                }
                title.setText(title1.get(position));
                if (btn_t1.get(position).equals("")) {
                    rv.setVisibility(View.GONE);
                    slider_cd.setClickable(false);
                } else {
                    rv.setVisibility(View.VISIBLE);
                    btn_t.setText(btn_t1.get(position));
                    slider_cd.setClickable(true);
                    slider_cd.setOnClickListener(v -> {

                        String link = slider_link.get(position);

                        Intent intent = new Intent(getActivity(), LinkOpenActivity.class);
                        intent.putExtra("link", link);
                        startActivity(intent);

                    });
                }



                Log.e("Slider", "Slider: " + slider_img1);
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

    public class Home_Today_list_Adapter extends RecyclerView.Adapter<Home_Today_list_Adapter.Holder> implements Filterable {
        FragmentActivity activity;
        ArrayList<UserprofileModel> data;
        public ArrayList<UserprofileModel> exampleListFull;
        private static final int VIEW_TYPE_REGULAR = 1;
        private static final int VIEW_TYPE_AD = 2;

        //                public List<UserprofileModel> arrayListTopic;
        public Home_Today_list_Adapter(FragmentActivity activity, ArrayList<UserprofileModel> data) {
            this.activity = activity;
            this.data = data;
            this.exampleListFull = new ArrayList<>(data);
//            this.arrayListTopic = new ArrayList<>(data);
        }

        @Override
        public int getItemCount() {
            return data.size()/* + (data.size() / 4)*/;
        }

        @Override
        public int getItemViewType(int position) {
            return (position % 5 == 0) ? VIEW_TYPE_AD : VIEW_TYPE_REGULAR;
        }

        @NonNull
        @Override
        public Home_Today_list_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list_item, parent, false);

            if (viewType == VIEW_TYPE_AD) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                return new Home_Today_list_Adapter.Holder(view1);
            } else {
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list_item, parent, false);
                return new Home_Today_list_Adapter.Holder(view2);
            }

//            return new Home_Today_list_Adapter.Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Holder holder, @SuppressLint("RecyclerView") final int position) {

            try {
                if (holder.getItemViewType() == VIEW_TYPE_AD) {
                    try {
                        if (position == 0) {
                            holder.pager1.setVisibility(View.GONE);
                            holder.name.setText("" + data.get(position).getName());
                            holder.company.setText("" + data.get(position).getCompany());
                            holder.Occupation.setText("Occupation: \n" + data.get(position).getOccupation());
                            String id = data.get(position).getId();
                            String company = data.get(position).getCompany();
                            if (data.get(position).getImage() == null) {
                                Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/no_images.png")).error(R.drawable.no_profiles).into(holder.profile);
                            } else {
                                Picasso.get()
                                        .load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage()))
                                        .placeholder(R.drawable.loader_gif) // Corrected placeholder type and resource
                                        .error(R.drawable.no_profiles)
                                        .into(holder.profile);
                            }
                            holder.share.setOnClickListener(v -> {
                                try {
                                    Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).error(R.drawable.bbc_ic).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            String path = activity.getExternalCacheDir() + "/BBCClub.png";
                                            OutputStream out = null;
                                            File file = new File(path);
                                            Log.e("TAG", "onBitmapLoaded: ");
                                            try {
                                                out = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                                out.flush();
                                                out.close();
                                                Log.e("TAG", "onBitmapLoaded2: ");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("TAG", "onBitmapLoaded1: ");
                                            }
                                            Uri bmpUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/jpeg");
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + data.get(position).getName() + "*" + "\n\n" + "*" + data.get(position).getCompany() + "*" + "\n" + data.get(position).getOccupation() +  "\n\n" + "Mobile" + " : " + data.get(position).getMobile() + "\n" + "Email" + " : " + data.get(position).getEmail() /*+ "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);

                                            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                            startActivity(shareIntent);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                            Log.e("TAG", "onBitmapLoaded3: ");
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                            Log.e("TAG", "onBitmapLoaded4: ");
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e("TAG", "Exception: " + e.getMessage());
                                }
                            });
                            holder.share1.setOnClickListener(v -> {
                                try {
                                    Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).error(R.drawable.bbc_ic).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            String path = activity.getExternalCacheDir() + "/BBCClub.png";
                                            OutputStream out = null;
                                            File file = new File(path);
                                            Log.e("TAG", "onBitmapLoaded: ");
                                            try {
                                                out = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                                out.flush();
                                                out.close();
                                                Log.e("TAG", "onBitmapLoaded2: ");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("TAG", "onBitmapLoaded1: ");
                                            }
                                            Uri bmpUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/jpeg");
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + data.get(position).getName() + "*" + "\n\n" + "*" + data.get(position).getCompany() + "*" + "\n" + data.get(position).getOccupation() +  "\n\n" + "Mobile" + " : " + data.get(position).getMobile() + "\n" + "Email" + " : " + data.get(position).getEmail() /*+ "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);

                                            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                            startActivity(shareIntent);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                            Log.e("TAG", "onBitmapLoaded3: ");
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                            Log.e("TAG", "onBitmapLoaded4: ");
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e("TAG", "Exception: " + e.getMessage());
                                }
                            });
                            holder.root.setOnClickListener(v -> {

                                Log.e("TAG", "onBindViewHolder: "+data.get(position).getDetails_view());
                                int dv = Integer.parseInt(data.get(position).getDetails_view());

                                if(dv == 0){
                                    startActivity(new Intent(activity, ProfileActivity.class).putExtra("id", id));
                                }else{
                                    startActivity(new Intent(activity, UserDetailsActivity.class).putExtra("company", company));
                                }
                            });
                        } else {
                            holder.pager1.setVisibility(View.VISIBLE);
                            holder.pager1.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
                            holder.pager1.initIndicator();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                holder.pager1.getIndicator()
                                        .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                                        .setFocusColor(getActivity().getColor(R.color.colorPrimary))
                                        .setNormalColor(Color.WHITE)
                                        .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
                            }
                            holder.pager1.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                            holder.pager1.setMultiScreen(0.9f);
//                            holder.pager1.
                            holder.pager1.getIndicator().setMargin(5, 5, 5, 25);
                            holder.pager1.getIndicator().build();
//                            holder.pager1.setInfiniteLoop(true);
                            holder.pager1.setAutoScroll(4000);



                            SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), slider_img, title, btn_t, slider_link);
                            holder.pager1.setAdapter(adapter);
                            if (slider_img.isEmpty() && title.isEmpty() && btn_t.isEmpty() && slider_link.isEmpty()) {
                                holder.pager1.setVisibility(View.GONE);
                            }else {
                                holder.pager1.setVisibility(View.VISIBLE);

                            }
                            holder.name.setText("" + data.get(position).getName());
                            holder.company.setText("" + data.get(position).getCompany());
                            holder.Occupation.setText("Occupation: \n" + data.get(position).getOccupation());
                            String id = data.get(position).getId();
                            String company = data.get(position).getCompany();
                            if (data.get(position).getImage() == null) {
                                Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/no_images.png")).error(R.drawable.no_profiles).into(holder.profile);
                            } else {
                                Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).placeholder(R.drawable.loader_gif).error(R.drawable.no_profiles).into(holder.profile);
                            }
                            holder.share.setOnClickListener(v -> {
                                try {
                                    Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).error(R.drawable.bbc_ic).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            String path = activity.getExternalCacheDir() + "/BBCClub.png";
                                            OutputStream out = null;
                                            File file = new File(path);
                                            Log.e("TAG", "onBitmapLoaded: ");
                                            try {
                                                out = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                                out.flush();
                                                out.close();
                                                Log.e("TAG", "onBitmapLoaded2: ");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("TAG", "onBitmapLoaded1: ");
                                            }
                                            Uri bmpUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/jpeg");
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + data.get(position).getName() + "*" + "\n\n" + "*" + data.get(position).getCompany() + "*" + "\n" + data.get(position).getOccupation() +  "\n\n" + "Mobile" + " : " + data.get(position).getMobile() + "\n" + "Email" + " : " + data.get(position).getEmail() /*+ "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);

                                            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                            startActivity(shareIntent);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                            Log.e("TAG", "onBitmapLoaded3: ");
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                            Log.e("TAG", "onBitmapLoaded4: ");
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e("TAG", "Exception: " + e.getMessage());
                                }
                            });
                            holder.share1.setOnClickListener(v -> {
                                try {
                                    Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).error(R.drawable.bbc_ic).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            String path = activity.getExternalCacheDir() + "/BBCClub.png";
                                            OutputStream out = null;
                                            File file = new File(path);
                                            Log.e("TAG", "onBitmapLoaded: ");
                                            try {
                                                out = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                                out.flush();
                                                out.close();
                                                Log.e("TAG", "onBitmapLoaded2: ");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("TAG", "onBitmapLoaded1: ");
                                            }
                                            Uri bmpUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/jpeg");
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + data.get(position).getName() + "*" + "\n\n" + "*" + data.get(position).getCompany() + "*" + "\n" + data.get(position).getOccupation() +  "\n\n" + "Mobile" + " : " + data.get(position).getMobile() + "\n" + "Email" + " : " + data.get(position).getEmail() /*+ "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);

                                            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                            startActivity(shareIntent);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                            Log.e("TAG", "onBitmapLoaded3: ");
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                            Log.e("TAG", "onBitmapLoaded4: ");
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.e("TAG", "Exception: " + e.getMessage());
                                }
                            });
                            holder.root.setOnClickListener(v -> {
                                Log.e("TAG", "onBindViewHolder: "+data.get(position).getDetails_view());

                                int dv = Integer.parseInt(data.get(position).getDetails_view());

                                if(dv == 0){
                                    startActivity(new Intent(activity, ProfileActivity.class).putExtra("id", id));
                                }else{
                                    startActivity(new Intent(activity, UserDetailsActivity.class).putExtra("company", company));
                                }
                            });
                        }

                    } catch (Exception e) {
                        //                    throw new RuntimeException(e);
                    }

                } else {
                    // Bind data for regular items
                    //                ((RegularViewHolder) holder).bind(data.get(position - position / 4));

                    try {
                        holder.name.setText("" + data.get(position).getName());
                        holder.company.setText("" + data.get(position).getCompany());
                        holder.Occupation.setText("Occupation: \n" + data.get(position).getOccupation());
                        String id = data.get(position).getId();
                        String company = data.get(position).getId();
                        if (data.get(position).getImage() == null) {
                            Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/no_images.png")).error(R.drawable.no_profiles).into(holder.profile);
                        } else {
                            Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).placeholder(R.drawable.loader_gif).error(R.drawable.no_profiles).into(holder.profile);
                        }
                        holder.share.setOnClickListener(v -> {
                            try {
                                Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).error(R.drawable.bbc_ic).into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        String path = activity.getExternalCacheDir() + "/BBCClub.png";
                                        OutputStream out = null;
                                        File file = new File(path);
                                        Log.e("TAG", "onBitmapLoaded: ");
                                        try {
                                            out = new FileOutputStream(file);
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                            out.flush();
                                            out.close();
                                            Log.e("TAG", "onBitmapLoaded2: ");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.e("TAG", "onBitmapLoaded1: ");
                                        }
                                        Uri bmpUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        shareIntent.setType("image/jpeg");
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + data.get(position).getName() + "*" + "\n\n" + "*" + data.get(position).getCompany() + "*" + "\n" + data.get(position).getOccupation() +  "\n\n" + "Mobile" + " : " + data.get(position).getMobile() + "\n" + "Email" + " : " + data.get(position).getEmail() /*+ "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);

                                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                        startActivity(shareIntent);
                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                        Log.e("TAG", "onBitmapLoaded3: ");
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                        Log.e("TAG", "onBitmapLoaded4: ");
                                    }
                                });
                            } catch (Exception e) {
                                Log.e("TAG", "Exception: " + e.getMessage());
                            }
                        });
                        holder.share1.setOnClickListener(v -> {
                            try {
                                Picasso.get().load(Uri.parse("http://businessboosters.club/public/images/user_images/" + data.get(position).getImage())).error(R.drawable.bbc_ic).into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        String path = activity.getExternalCacheDir() + "/BBCClub.png";
                                        OutputStream out = null;
                                        File file = new File(path);
                                        Log.e("TAG", "onBitmapLoaded: ");
                                        try {
                                            out = new FileOutputStream(file);
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//Change JPEG to PNG if necessary
                                            out.flush();
                                            out.close();
                                            Log.e("TAG", "onBitmapLoaded2: ");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.e("TAG", "onBitmapLoaded1: ");
                                        }
                                        Uri bmpUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
                                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        shareIntent.setType("image/jpeg");
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "*" + data.get(position).getName() + "*" + "\n\n" + "*" + data.get(position).getCompany() + "*" + "\n" + data.get(position).getOccupation() +  "\n\n" + "Mobile" + " : " + data.get(position).getMobile() + "\n" + "Email" + " : " + data.get(position).getEmail() /*+ "\n\n" + "Check out the App at: https://play.google.com/store/apps/details?id=" + getPackageName()*/);
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                                        startActivity(shareIntent);
                                    }

                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                        Log.e("TAG", "onBitmapLoaded3: ");
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                        Log.e("TAG", "onBitmapLoaded4: ");
                                    }
                                });
                            } catch (Exception e) {
                                Log.e("TAG", "Exception: " + e.getMessage());
                            }
                        });
                        holder.root.setOnClickListener(v -> {
                            Log.e("TAG", "onBindViewHolder: "+data.get(position).getDetails_view());

                            int dv = Integer.parseInt(data.get(position).getDetails_view());

                            if(dv == 0){
                                startActivity(new Intent(activity, ProfileActivity.class).putExtra("id", id));
                            }else{
                                startActivity(new Intent(activity, UserDetailsActivity.class).putExtra("company", company));
                            }
                        });
                    } catch (Exception e) {
                        //                    throw new RuntimeException(e);
                    }

                }
            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
        }

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<UserprofileModel> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(exampleListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (UserprofileModel item : exampleListFull) {
                        if (item.getName().toLowerCase().contains(filterPattern)
                                || item.getCompany().toLowerCase().contains(filterPattern)
                                || item.getOccupation().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                try {
                    data.clear();
                    data.addAll((List) results.values);
                    notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        };

        class Holder extends RecyclerView.ViewHolder {

            TextView name, company, date, Occupation;
            ImageView profile, call, whatssap, share, share1;
            CardView root;

            LottieAnimationView lottieAnimationView;
            UltraViewPager pager1;

            public Holder(@NonNull View itemView) {
                super(itemView);


                name = itemView.findViewById(R.id.name);
                company = itemView.findViewById(R.id.company);
                Occupation = itemView.findViewById(R.id.Occupation);
                root = itemView.findViewById(R.id.root);
                profile = itemView.findViewById(R.id.profile);
                pager1 = itemView.findViewById(R.id.pager1);
                share = itemView.findViewById(R.id.share);
                share1 = itemView.findViewById(R.id.share1);
//                lottieAnimationView = itemView.findViewById(R.id.lottieAnimationView);

            }
        }
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
//                    Home_Today_list_Adapter adapter = new Home_Today_list_Adapter(getActivity(), response.body().getData());
//                    driverlist.setAdapter(adapter);


//                    SliderPagerAdapter adapter = new SliderPagerAdapter(getActivity(), response.body().getData());
//                    pagertop.setAdapter(adapter);
//                    Log.e("responce..", "branches:-  " + branches.size());

                } else {
//                    nodata.setVisibility(View.VISIBLE);
//                    driverlist.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Network Error!!", Toast.LENGTH_SHORT).show();
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

}