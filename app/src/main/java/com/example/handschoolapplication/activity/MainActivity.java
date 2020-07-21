package com.example.handschoolapplication.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CourseNumBean;
import com.example.handschoolapplication.bean.LearningCourseBean;
import com.example.handschoolapplication.bean.MenuBean;
import com.example.handschoolapplication.fragment.FindFragment;
import com.example.handschoolapplication.fragment.HomeFragment;
import com.example.handschoolapplication.fragment.LearnPlanFragment;
import com.example.handschoolapplication.fragment.MeComFragment;
import com.example.handschoolapplication.fragment.MeFragment;
import com.example.handschoolapplication.fragment.NewsComFragment;
import com.example.handschoolapplication.fragment.NewsFragment;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.RomUtil;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.SystemUtil;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends BaseActivity {


    public static final String MESSAGE_RECEIVED_ACTION = "com.example.handschoolapplication.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_TITLE = "title";
    private static final int REQUEST_CALL_PHONE = 400;
    public static boolean isForeground = true;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_find)
    ImageView ivFind;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.iv_plan)
    ImageView ivPlan;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.ll_plan)
    RelativeLayout llPlan;
    @BindView(R.id.iv_news)
    ImageView ivNews;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_find)
    LinearLayout llFind;
    @BindView(R.id.ll_news)
    RelativeLayout llNews;
    @BindView(R.id.ll_me)
    RelativeLayout llMe;
    @BindView(R.id.tv_news_num)
    TextView tvNewsNum;
    @BindView(R.id.tv_me_num)
    TextView tvMeNum;
    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private NewsFragment newsFragment;
    private NewsComFragment newsComFragment;
    private LearnPlanFragment planFragment;
    private MeFragment meFragment;
    private Fragment currentFragment;
    private MeComFragment meComFragment;
    private String flag = "";
    private boolean isLogin = false;
    private MessageReceiver mMessageReceiver;
    private List<LearningCourseBean> mList = new ArrayList<>();
    private List<LearningCourseBean> mList2 = new ArrayList<>();//学习中
    private List<LearningCourseBean> mList3 = new ArrayList<>();//代付款
    private List<LearningCourseBean> mList4 = new ArrayList<>();//待评价
    private List<LearningCourseBean> mList5 = new ArrayList<>();//待评价
    private int allnum;
    private int learningnum;
    private int paynum;
    private int evluaingnum;
    private static final String TAG = "aaa";
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    llHome.performClick();
                    break;
                case 2:
                    llNews.performClick();
                    break;
                case 3:
                    llMe.performClick();
                    break;
                case 4:
                    Log.e("aaa",
                            "(MainActivity.java:148)<--这是推送消息传递的EventBus-->");
                    getRedDot();
                    initData();
                    break;
            }
        }
    };
    private boolean b;
    private long exitTime = 0;

    private String[] permissions = new String[]{
          Manifest.permission.ACCESS_FINE_LOCATION
    };
    private ArrayList<String> mPermission = new ArrayList<>();
    private int REQUEST_CODE_ACCESS_COARSE_LOCATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        MyUtiles.setStatusBar(this, false, false);
        flag = (String) SPUtils.get(this, "flag", "");
        if (null == flag) {
            flag = "0";
        }
        Log.e("aaa",
                "(MainActivity.java:71)flag ==== " + flag);
        if ("1".equals(flag)) {
            llPlan.setVisibility(View.GONE);
        }
        isLogin = (boolean) SPUtils.get(this, "isLogin", false);
        initTab();
        registerMessageReceiver();

        if (isLogin) {
            getRedDot();
            initData();
        }

//        String systemModel = SystemUtil.getSystemModel();

//        Log.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
//        Log.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
//        Log.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
//        Log.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
//        Log.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(getApplicationContext()));
//
//        Toast.makeText(this, "手机厂商："+SystemUtil.getDeviceBrand()+"\n手机型号："+SystemUtil.getSystemModel()+
//                "\n手机当前系统语言："+SystemUtil.getSystemLanguage()+
//                "\nAndroid系统版本号："+SystemUtil.getSystemVersion()+"\n手机IMEI："+SystemUtil.getIMEI(getApplicationContext()), Toast.LENGTH_LONG).show();

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initTab() {
        if (homeFragment == null) {

            /** 默认加载第一个Fragment*/
            homeFragment = new HomeFragment();
        }

        if (!homeFragment.isAdded()) {

            /** 如果第一个未被添加，则添加到管理器中*/
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, homeFragment).commit();

            /** 记录当前Fragment*/
            currentFragment = homeFragment;
        }
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    private void getRedDot() {
        String userId = (String) SPUtils.get(this, "userId", "");
        String user_type = (String) SPUtils.get(this, "user_type", "");
        OkHttpUtils.post()
                .url(Internet.UNREAD_NEWS_NUM)
                .addParams("user_id", userId)
                .addParams("user_type", user_type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(MainActivity.java:345)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MainActivity.java:351)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String allNews = jsonObject.getJSONObject("data").getString("count");
                            int allNum = Integer.parseInt(allNews);
                            if (allNum > 0) {
                                tvNewsNum.setVisibility(View.VISIBLE);
                                tvNewsNum.setText(String.valueOf(allNum));
//                                tvMeNum.setVisibility(View.VISIBLE);
                            } else {
                                tvNewsNum.setVisibility(View.GONE);
//                                tvMeNum.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
        String userId = (String) SPUtils.get(this, "userId", "");
        allnum = 0;
        learningnum = 0;
        paynum = 0;
        evluaingnum = 0;
        mList.clear();
        mList2.clear();
        mList3.clear();
        mList4.clear();
        mList5.clear();
        OkHttpUtils.post()
                .url(Internet.ALLORDER)
                .addParams("user_id", userId)
                .addParams("type", "course")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearningActivity.java:61)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllCourseActivity.java:86)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            ArrayList<LearningCourseBean> learningCourseBeen = new ArrayList<>();
                            learningCourseBeen.addAll((Collection<? extends LearningCourseBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<LearningCourseBean>>() {
                            }.getType()));
                            for (int i = 0; i < learningCourseBeen.size(); i++) {
                                if ("2".equals(learningCourseBeen.get(i).getOrder_state())) {
                                    mList.add(learningCourseBeen.get(i));
                                    mList4.add(learningCourseBeen.get(i));
                                } else if ("1".equals(learningCourseBeen.get(i).getOrder_state()) || "3".equals(learningCourseBeen.get(i).getOrder_state())) {
                                    mList.add(learningCourseBeen.get(i));
                                } else if ("0".equals(learningCourseBeen.get(i).getOrder_state())) {
                                    mList3.add(learningCourseBeen.get(i));
                                }

                                String course_hour = "0";
                                if (null != learningCourseBeen.get(i).getClass_money()) {
                                    String string = learningCourseBeen.get(i).getClass_money();
                                    course_hour = string.split("/")[1].split("节")[0];
                                }

                                int allTime = Integer.parseInt(course_hour);//总学时

                                String order_state = learningCourseBeen.get(i).getOrder_state();//订单状态
                                String course_state = learningCourseBeen.get(i).getCourseInfo().getCourse_state();//课程状态
                                int study_class = 0;
                                if (learningCourseBeen.get(i).getClassSign() != null) {
                                    study_class = learningCourseBeen.get(i).getClassSign().getStudy_class();
                                }
                                boolean isFinish = false;
                                if (study_class >= allTime) {
                                    isFinish = true;
                                }

                                if ("1".equals(order_state) || "2".equals(order_state) || "3".equals(order_state)) {
                                    if ("0".equals(course_state) || order_state.equals("1")) {
                                    } else if (isFinish || course_state.equals("2")) {
                                        mList5.add(learningCourseBeen.get(i));
                                    } else {
                                        mList2.add(learningCourseBeen.get(i));
                                    }
                                }
                            }

                            allnum = mList.size();
                            learningnum = mList2.size();
                            paynum = mList3.size();
                            evluaingnum = mList4.size();
                            int finishLearn = mList5.size();
                            Log.e("aaa",
                                    "(MainActivity.java:461)" + allnum + "   " + learningnum + "    " + paynum + "    " + evluaingnum);
                            allnum -= finishLearn;
                            int totalNum = allnum + learningnum + paynum + evluaingnum;
                            if (totalNum > 0) {
                                tvMeNum.setVisibility(View.VISIBLE);
                                tvMeNum.setText(totalNum + "");
                            } else {
                                tvMeNum.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /*private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CALL_PHONE);
                return;
            } else {
                //已有权限
            }
        } else {
            //API 版本在23以下
        }
    }*/

    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermission.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermission.add(permissions[i]);
                }
            }
            if (mPermission.isEmpty()) {//未授予的权限为空，表示都授予了
//                Toast.makeText(SplashActivity.this, "已经授权", Toast.LENGTH_LONG).show();
//                getVersionInfo();
                tvHome.setTextColor(Color.parseColor("#333333"));
                ivHome.setImageResource(R.drawable.home);
                tvFind.setTextColor(Color.parseColor("#27acf6"));
                ivFind.setImageResource(R.drawable.finds);
                tvNews.setTextColor(Color.parseColor("#333333"));
                ivNews.setImageResource(R.drawable.news);
                tvPlan.setTextColor(Color.parseColor("#333333"));
                ivPlan.setImageResource(R.drawable.plan);
                tvMe.setTextColor(Color.parseColor("#333333"));
                ivMe.setImageResource(R.drawable.me);
                if (findFragment == null) findFragment = new FindFragment();
                addOrShowFragment(getSupportFragmentManager(), findFragment);
//                MyUtiles.setStatusTextColor(false,MainActivity.this);
                MyUtiles.setStatusBar(this, false, false);
            } else {//请求权限方法
                String[] permissions = mPermission.toArray(new String[mPermission.size()]);//将List转为数组
                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_CODE_ACCESS_COARSE_LOCATION);
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
                    "(SplashActivity.java:111)" + "不用获取权限");
            //API 版本在23以下
//            checkWebEnvironment();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {

            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
//                        Toast.makeText(this, "权限未申请", Toast.LENGTH_SHORT).show();
                        showUnLoginDialog("为了更好的应用体验，需要获取您的位置(服近商家服务)！",1);
                        //requestPermission();
                    }else{
                        showUnLoginDialog("点开手机系统设置->应用和通知->权限管理",0);
                    }
                }
            }

            Log.e("aaa",
                    "(SplashActivity.java:176)<--运行自动登录-->");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showUnLoginDialog(String text, final int type) {
        final SelfDialog selfDialog = new SelfDialog(MainActivity.this);
        selfDialog.setMessage(text);
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                if(type==0){
                    selfDialog.dismiss();
                }else if(type==1){
                    selfDialog.dismiss();
                    requestPermission();
                }
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    @OnClick({R.id.ll_home, R.id.ll_find, R.id.ll_plan, R.id.ll_news, R.id.ll_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                tvHome.setTextColor(Color.parseColor("#27acf6"));
                ivHome.setImageResource(R.drawable.homes);
                tvFind.setTextColor(Color.parseColor("#333333"));
                ivFind.setImageResource(R.drawable.find);
                tvNews.setTextColor(Color.parseColor("#333333"));
                ivNews.setImageResource(R.drawable.news);
                tvPlan.setTextColor(Color.parseColor("#333333"));
                ivPlan.setImageResource(R.drawable.plan);
                tvMe.setTextColor(Color.parseColor("#333333"));
                ivMe.setImageResource(R.drawable.me);
                if (homeFragment == null) homeFragment = new HomeFragment();
                addOrShowFragment(getSupportFragmentManager(), homeFragment);
//                MyUtiles.setStatusTextColor(false,MainActivity.this);
                MyUtiles.setStatusBar(this, false, false);
                break;
            case R.id.ll_find:
                requestPermission();
                /*tvHome.setTextColor(Color.parseColor("#333333"));
                ivHome.setImageResource(R.drawable.home);
                tvFind.setTextColor(Color.parseColor("#27acf6"));
                ivFind.setImageResource(R.drawable.finds);
                tvNews.setTextColor(Color.parseColor("#333333"));
                ivNews.setImageResource(R.drawable.news);
                tvPlan.setTextColor(Color.parseColor("#333333"));
                ivPlan.setImageResource(R.drawable.plan);
                tvMe.setTextColor(Color.parseColor("#333333"));
                ivMe.setImageResource(R.drawable.me);
                if (findFragment == null) findFragment = new FindFragment();
                addOrShowFragment(getSupportFragmentManager(), findFragment);
//                MyUtiles.setStatusTextColor(false,MainActivity.this);
                MyUtiles.setStatusBar(this, false, false);*/
                break;
            case R.id.ll_news:
                tvHome.setTextColor(Color.parseColor("#333333"));
                ivHome.setImageResource(R.drawable.home);
                tvFind.setTextColor(Color.parseColor("#333333"));
                ivFind.setImageResource(R.drawable.find);
                tvNews.setTextColor(Color.parseColor("#27acf6"));
                ivNews.setImageResource(R.drawable.newss);
                tvPlan.setTextColor(Color.parseColor("#333333"));
                ivPlan.setImageResource(R.drawable.plan);
                tvMe.setTextColor(Color.parseColor("#333333"));
                ivMe.setImageResource(R.drawable.me);
                if (isLogin) {
                    if (flag.equals("1")) {
                        if (newsComFragment == null) newsComFragment = new NewsComFragment();
                        addOrShowFragment(getSupportFragmentManager(), newsComFragment);
                    } else {
                        if (newsFragment == null) newsFragment = new NewsFragment();
                        addOrShowFragment(getSupportFragmentManager(), newsFragment);
                    }
//                    MyUtiles.setStatusTextColor(false,MainActivity.this);
                    MyUtiles.setStatusBar(this, false, false);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.ll_plan:
                tvHome.setTextColor(Color.parseColor("#333333"));
                ivHome.setImageResource(R.drawable.home);
                tvFind.setTextColor(Color.parseColor("#333333"));
                ivFind.setImageResource(R.drawable.find);
                tvNews.setTextColor(Color.parseColor("#333333"));
                ivNews.setImageResource(R.drawable.news);
                tvPlan.setTextColor(Color.parseColor("#27acf6"));
                ivPlan.setImageResource(R.drawable.plans);
                tvMe.setTextColor(Color.parseColor("#333333"));
                ivMe.setImageResource(R.drawable.me);
                if (isLogin) {
                    if (planFragment == null) planFragment = new LearnPlanFragment();
                    addOrShowFragment(getSupportFragmentManager(), planFragment);
//                    MyUtiles.setStatusTextColor(false,MainActivity.this);
                    MyUtiles.setStatusBar(this, false, false);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.ll_me:
                tvHome.setTextColor(Color.parseColor("#333333"));
                ivHome.setImageResource(R.drawable.home);
                tvFind.setTextColor(Color.parseColor("#333333"));
                ivFind.setImageResource(R.drawable.find);
                tvNews.setTextColor(Color.parseColor("#333333"));
                ivNews.setImageResource(R.drawable.news);
                tvPlan.setTextColor(Color.parseColor("#333333"));
                ivPlan.setImageResource(R.drawable.plan);
                tvMe.setTextColor(Color.parseColor("#27acf6"));
                ivMe.setImageResource(R.drawable.mes);
                if (isLogin) {
                    if (flag.equals("1")) {
                        if (meComFragment == null) meComFragment = new MeComFragment();
                        addOrShowFragment(getSupportFragmentManager(), meComFragment);
                    } else {
                        if (meFragment == null) meFragment = new MeFragment();
                        addOrShowFragment(getSupportFragmentManager(), meFragment);
                    }
                    MyUtiles.setStatusTextColor(false, MainActivity.this);

                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
        }
    }

    /**
     * 添加或者显示碎片
     *
     * @param manager
     * @param fragment
     */
    private void addOrShowFragment(FragmentManager manager,
                                   Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) {

            // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.fl_fragment,
                            fragment).commitAllowingStateLoss();
        } else {

            // 如果当前fragment已被添加，则显示即可
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }
        currentFragment = fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hello(MenuBean event) {
        /* Do something */
        if (event.getNum() == 8) {
            finish();
        } else {
            mhandler.sendEmptyMessageDelayed(event.getNum(), 50);
        }
        Log.e("aaa", "(MainActivity.java:395)<-------EventBus的传递事件------->" + event.getNum());
    }

    /**
     * 此订阅接收来自企业版的消息Fragment页面的EventBus传来的通知
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void newsNum(String event) {
        /* Do something */
        Log.e("aaa", "(MainActivity.java:406)<---->" + "接收消息的总数为" + event);

        int num = 0;
        try {
            num = Integer.parseInt(event);
            if (num > 0) {
                tvNewsNum.setText(event);
                tvNewsNum.setVisibility(View.VISIBLE);
            } else {
                tvNewsNum.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            tvNewsNum.setVisibility(View.GONE);
        }


    }

    /**
     * 此订阅接收来自个人版的我的Fragment页面的EventBus传来的通知
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void courseNum(CourseNumBean event) {
        /* Do something */
        Log.e("aaa", "(MainActivity.java:531)<---->" + "接收我的课程的总数为" + event.getNum());
        if (event.getNum() > 0) {
            tvMeNum.setVisibility(View.VISIBLE);
            tvMeNum.setText(event.getNum() + "");
        } else {
            tvMeNum.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!TextUtils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
//                    setCostomMsg(showMsg.toString());
                    Toast.makeText(context, showMsg.toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
        }
    }
}
