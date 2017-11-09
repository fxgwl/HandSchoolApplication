package com.example.handschoolapplication.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.ScreenUtils;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;

import java.io.File;
import java.util.ArrayList;

import util.DownloadAppUtils;



public class SplashActivity extends BaseActivity {


    private static final int DOWNLOAD_SUCCESS = 6;
    private final int UPDATE_NO_NEED = 0;
    private final int UPDATE_CLIENT = 3;
    private final int GET_UPDATE_INFO_ERROR = 2;
    private final int DOWN_ERROR = 4;
    private static final int REQUEST_CALL_PHONE = 400;
    private String type;

    private String localVersion;
    private File file = null;
    private ProgressDialog pd;

    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CALL_PHONE
            ,Manifest.permission.READ_PHONE_STATE
            ,Manifest.permission.ACCESS_FINE_LOCATION
            ,Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private ArrayList<String> mPermission = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_NO_NEED:
                    checkAutoLogin();
                    break;
                case UPDATE_CLIENT:
                    String url = (String) msg.obj;
                    showUpdateDialog(url);
//                    startActivity(new Intent(SplashActivity.this, ToUpdateActivity.class).putExtra("url",url));
//                    finish();
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case GET_UPDATE_INFO_ERROR:
                    checkAutoLogin();
                    break;
                case DOWN_ERROR:
                    //ToastUtil.showToastShort("下载新版本失败");
                    checkAutoLogin();
                    break;
                case DOWNLOAD_SUCCESS:
                    installApk(file);
                    pd.dismiss(); // 结束掉进度条对话框
            }
        }
    };// 进度条对话框

    private static final int sleepTime = 2000;
    private long startTime;
    private String versions;
    private int REQUEST_CODE_ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle arg0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            ScreenUtils.setTransparentStatusBar(SplashActivity.this);
        }
        super.onCreate(arg0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                getVersionInfo();//获取当前版本是否为最新
            }
        }, 300);

        startTime = System.currentTimeMillis();
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        rootLayout.startAnimation(animation);
        requestPermission();
    }

    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            mPermission.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(SplashActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermission.add(permissions[i]);
                }
            }
            if (mPermission.isEmpty()) {//未授予的权限为空，表示都授予了
                Toast.makeText(SplashActivity.this,"已经授权",Toast.LENGTH_LONG).show();
            } else {//请求权限方法
                String[] permissions = mPermission.toArray(new String[mPermission.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }



//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{
//                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.CALL_PHONE
//                        ,Manifest.permission.READ_PHONE_STATE
//                        ,Manifest.permission.ACCESS_FINE_LOCATION
//                        ,Manifest.permission.READ_EXTERNAL_STORAGE
//                },REQUEST_CODE_ACCESS_COARSE_LOCATION);
//                Log.e("aaa",
//                    "(SplashActivity.java:111)"+"请求获取权限");
//            } else {
//                Log.e("aaa",
//                        "(SplashActivity.java:111)"+"已获取权限");
//                //已有权限
//                checkAutoLogin();
//            }
        } else {
            Log.e("aaa",
                    "(SplashActivity.java:111)"+"不用获取权限");
            //API 版本在23以下
            checkAutoLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {

            Log.e("aaa",
                "(SplashActivity.java:136)grantResults.length   "+grantResults.length);
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i]);
                    if (showRequestPermission) {
//                        Toast.makeText(this, "权限未申请", Toast.LENGTH_SHORT).show();
                        requestPermission();
                    }
                }
            }
            checkAutoLogin();
        }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkAutoLogin() {
        new Thread(new Runnable() {
            public void run() {
                long costTime = System.currentTimeMillis() - startTime;
                //wait
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // boolean is_before_login = (boolean) SPUtils.get(Constants.IS_BEFORE_LOGIN, true);
                //if (false) {
                //enter main screen
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                } else {
//                    if ("true".equals(SPUtils.get(SpConstant.IS_FIRST_LOGIN, "true"))) {
//                        startActivity(new Intent(SplashActivity.this, NavigateActivity.class));
//                    } else {
//                        startActivity(new Intent(SplashActivity.this, LloginActivity.class));
//                    }
//                    finish();
//                }
            }
        }).start();
    }


    //获取服务器和本地版本信息进行对比
    public void getVersionInfo() {

//        OkHttpUtils.post()
//                .url(Internet.UPDATEVERSION)
//                .params(new HashMap<String, String>())
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("aaa",
//                                "(SettingsFragment.java:105)" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("aaa",
//                                "(SettingsFragment.java:112)" + response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            String versions = data.getString("versions");
//                            type = data.getString("type");
//                            try {
//                                if (versions.equals(getVersionName())) {
//                                    Message msg = new Message();
//                                    msg.what = UPDATE_NO_NEED;
//                                    handler.sendMessage(msg);
//                                } else {
//                                    String url = data.getString("url");
//                                    Message msg = new Message();
//                                    msg.what = UPDATE_CLIENT;
//                                    msg.obj = url;
//                                    handler.sendMessage(msg);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }


    // 获取应用当前版本信息
    private String getVersionName() throws Exception {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }

    //显示更新dialog
    protected void showUpdateDialog(final String url) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("更新");
        builer.setMessage("软件有更新版本是否更新？");
        // 当点确定按钮时从服务器上下载 新的apk 然后安装 װ
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk(url);
                dialog.dismiss();
            }
        });
        builer.setNegativeButton("暂不", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent;
                dialog.dismiss();
                if (type.equals("1")){
                    Toast.makeText(SplashActivity.this, "msg", Toast.LENGTH_SHORT).show();
                }else
                checkAutoLogin();
//                if (isFirstIn) {
//                    // start guideactivity1
//                    intent = new Intent(LoginActivity.this,
//                            ExampleGuideActivity.class);
//                } else {
//                    // start TVDirectActivity
//                    intent = new Intent(LoginActivity.this, MainActivity.class);
//                }
//                startActivity(intent);
            }
        });
        builer.setCancelable(false);
        AlertDialog dialog = builer.create();
        dialog.show();
    }

//    //显示更新dialog
//    protected void showUpdateDialog(String versionName,String url) {
////        UpdateAppUtils.from(this)
////                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
////                .serverVersionName(versionName)
////                .apkPath(Internet.BASE_URL+url)
////                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
////                .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
////                .update();
//    }

    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk(final String url) {
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("正在下载更新");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread() {
            @Override
            public void run() {
                try {
                    DownloadAppUtils.downloadForAutoInstall(SplashActivity.this, Internet.BASE_URL + url, "UD.apk", versions);
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();

    }

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }
}
