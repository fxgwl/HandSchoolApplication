package com.example.handschoolapplication.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ViewPagerAdapter;
import com.example.handschoolapplication.bean.HomeAdBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

import static com.bumptech.glide.Glide.with;


public class AdvertisementActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private int time;
    private Timer timer;
    private ViewPager vpAd;
    private ViewPagerAdapter vpAdapter;
    //    private List<View> mImageList;
    private List<ImageView> imageViews;
    private List<ImageView> dots;
    private LinearLayout linearLayout;
    private List<String> imgStr;
    private RelativeLayout rlSkip;
    private int size;
    private ArrayList<HomeAdBean.DataBean> homeAdList;
    private List<String> listImg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(AdvertisementActivity.this);
        setContentView(R.layout.activity_advertisement);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ScreenUtils.setTransparentStatusBar(AdvertisementActivity.this);
        }

//        autoLogin();


        vpAd = (ViewPager) findViewById(R.id.vp_ad);
        linearLayout = (LinearLayout) findViewById(R.id.ll_dots);
        rlSkip = (RelativeLayout) findViewById(R.id.rl_skip);
        timer = new Timer();
        time = 5;


        textView = (TextView) findViewById(R.id.tv_tiaoguo);

        initHomeAd();

        vpAd.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < size; i++) {
                    if (i == position) {//当前页面小圆点设置为红色

                        dots.get(i).setImageResource(R.drawable.adver_point_blue);
                    } else {
                        dots.get(i).setImageResource(R.drawable.adver_point_white);
                    }

                    if (i == size - 1) {
                        rlSkip.setVisibility(View.VISIBLE);
                        toLoginTimer();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        textView.setOnClickListener(this);
    }

    private void toLoginTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(time + "s跳过");

                        if (time == 0) {
                            timer.cancel();
                            autoLogin();
//                            AdvertisementActivity.this.startActivity(AdvertisementActivity.this,LoginActivity.class);
//                            finish();
                        }
                        time = time - 1;
                    }
                });

            }
        }, 0, 1000);
    }

    private void initHomeAd() {
        Log.e("aaa",
                "(AdvertisementActivity.java:133)<--获取广告详情的接口-->");
        OkHttpUtils.post()
                .url(Internet.HOMEAD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AdvertisementActivity.java:141)<---->"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(Advertisement.java:183)" + response);
                        if (TextUtils.isEmpty(response)||response.contains("没有信息")){
                            autoLogin();
                        }else{
                            Gson gson = new Gson();
                            homeAdList = (ArrayList<HomeAdBean.DataBean>) gson.fromJson(response, HomeAdBean.class).getData();
                            for (int i = 0; i < homeAdList.size(); i++) {
                                String advertising_type = homeAdList.get(i).getAdvertising_type();
                                if ("4".equals(advertising_type)){
                                    listImg.add(Internet.BASE_URL + homeAdList.get(i).getAdvertising_photo());
                                }
                            }
                            size = listImg.size();

                            Log.e("aaa",
                                    "(AdvertisementActivity.java:157)<--图片集合的大小-->"+size);
                            initView();
                        }

                    }
                });
    }

    private void initView() {
        if (size == 1) {
            rlSkip.setVisibility(View.VISIBLE);
            toLoginTimer();
        }
        Log.e("aaa",
                "(AdvertisementActivity.java:180)" + "size ==== " + size);
        imageViews = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(this);
            WindowManager wm = (WindowManager) this
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
            imageView.setLayoutParams(layoutParams);
            Log.e("aaa",
                    "(AdvertisementActivity.java:191)" + "width==" + width + ",height==" + height);
            imageViews.add(imageView);
        }
        for (int i = 0; i < size; i++) {
//            Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507956348777&di=7f67c7d014ffd22b745a31c024cdafa5&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3726365543%2C1182698468%26fm%3D214%26gp%3D0.jpg").centerCrop().into(imageViews.get(i));
            with(this).load(listImg.get(i)).fitCenter().into(imageViews.get(i));
        }
        vpAdapter = new ViewPagerAdapter(imageViews);
        vpAd.setAdapter(vpAdapter);
        dots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 0, 20, 0);
            dot.setLayoutParams(layoutParams);
            if (i == 0) {
                dot.setImageResource(R.drawable.adver_point_blue);
            } else {
                dot.setImageResource(R.drawable.adver_point_white);
            }
            linearLayout.addView(dot);
            dots.add(dot);
        }
    }

    private void startActivity(AdvertisementActivity advertisementActivity, Class<MainActivity> loginActivityClass) {
        startActivity(new Intent(advertisementActivity, loginActivityClass));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        autoLogin();
        timer.cancel();
    }

    private void autoLogin() {
//        if (isFirst){
//            startActivity(new Intent(AdvertisementActivity.this, LeadActivity.class));
//        }else {
            startActivity(AdvertisementActivity.this,MainActivity.class);

        finish();
    }
}
