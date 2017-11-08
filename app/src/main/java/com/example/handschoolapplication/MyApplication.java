package com.example.handschoolapplication;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.mob.MobSDK;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
//        ZXingLibrary.initDisplayOpinion(this);
        MobSDK.init(this);
    }

    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }

}
