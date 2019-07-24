package com.example.handschoolapplication.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.bumptech.glide.Glide.with;


public class LeadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private int time;
    private Timer timer;
    private ViewPager vpAd;
    private ViewPagerAdapter vpAdapter;
    //    private List<View> mImageList;
    private List<ImageView> imageViews;
    private List<Integer> imageUrl;
    private List<ImageView> dots;
    private LinearLayout linearLayout;
    private List<String> imgStr;
    private RelativeLayout rlSkip;
    private int size = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(LeadActivity.this);
        setContentView(R.layout.activity_advertisement);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ScreenUtils.setTransparentStatusBar(LeadActivity.this);
        }

        getSharedPreferences("isFirst", Context.MODE_PRIVATE).edit().putBoolean("first", false).commit();

        vpAd = (ViewPager) findViewById(R.id.vp_ad);
        linearLayout = (LinearLayout) findViewById(R.id.ll_dots);
        rlSkip = (RelativeLayout) findViewById(R.id.rl_skip);

        textView = (TextView) findViewById(R.id.tv_tiaoguo);

        imageUrl = new ArrayList<>();
        imageUrl.add(R.drawable.yindaoyeone);
        imageUrl.add(R.drawable.yindaoyetwo);
        timer = new Timer();
        time = 5;

        initView();

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
            with(this).load(imageUrl.get(i)).centerCrop().into(imageViews.get(i));
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

    @Override
    public void onClick(View v) {
        autoLogin();
        timer.cancel();
    }

    private void autoLogin() {
        startActivity(LeadActivity.this, MainActivity.class);
        finish();
    }

    private void startActivity(LeadActivity advertisementActivity, Class<MainActivity> loginActivityClass) {
        startActivity(new Intent(advertisementActivity, loginActivityClass));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
