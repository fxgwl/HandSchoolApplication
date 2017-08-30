package com.example.handschoolapplication;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        ZXingLibrary.initDisplayOpinion(this);
    }
}
