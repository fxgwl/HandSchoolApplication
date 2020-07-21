package com.example.handschoolapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.mob.MobSDK;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyApplication extends Application {

    public static int selectPosition;
    public static String selectCity;
    public static String open_id="";
    public static int loginkg=1;
    private List<Activity> activityList = new LinkedList();
    private static MyApplication application;

    public static Context getContext() {
        return application;
    }
    public static MyApplication getInstance() {

        synchronized (Application.class){
            if (null == application) {
                application = new MyApplication();
            }
            return application;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
//        ZXingLibrary.initDisplayOpinion(this);
        MobSDK.init(this);
        closeAndroidPDialog();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    public static synchronized String getSelectCity() {

        if (selectCity == null)
            selectCity = "";
        return selectCity;
    }

    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }

    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class); declaredConstructor.setAccessible(true); } catch (Exception e) { e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true); Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true); mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    public void out() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
