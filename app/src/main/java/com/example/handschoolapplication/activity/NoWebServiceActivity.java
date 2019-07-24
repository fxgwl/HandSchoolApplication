package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.OnClick;
import okhttp3.Call;

public class NoWebServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_no_web_service;
    }

    @OnClick(R.id.btn_try_again)
    public void onViewClicked() {
        checkWebEnvironment();
    }

    private void checkWebEnvironment() {
        Log.e("aaa",
                "(SplashActivity.java:222)<--测试网络环境-->");
        OkHttpUtils.post()
                .url(Internet.HOMEAD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SplashActivity.java:227)<---->"+e.getMessage());
//                        startActivity(new Intent(NoWebServiceActivity.this,NoWebServiceActivity.class));
                        Toast.makeText(NoWebServiceActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SplashActivity.java:228)" + response);
                        startActivity(new Intent(NoWebServiceActivity.this, AdvertisementActivity.class));
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });

    }
}
