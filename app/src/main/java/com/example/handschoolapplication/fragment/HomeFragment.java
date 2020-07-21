package com.example.handschoolapplication.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.AdvertisingActivity;
import com.example.handschoolapplication.activity.AgreementWebActivity;
import com.example.handschoolapplication.activity.ArtActivity;
import com.example.handschoolapplication.activity.ChildEduActivity;
import com.example.handschoolapplication.activity.CourseHomePagerActivity;
import com.example.handschoolapplication.activity.CurrentCitysActivity;
import com.example.handschoolapplication.activity.EducationActivity;
import com.example.handschoolapplication.activity.LoginActivity;
import com.example.handschoolapplication.activity.MainActivity;
import com.example.handschoolapplication.activity.SearchActivity;
import com.example.handschoolapplication.adapter.GalleryAdapter;
import com.example.handschoolapplication.adapter.HPClassAdapter;
import com.example.handschoolapplication.adapter.HPCourseAdapter;
import com.example.handschoolapplication.adapter.HorizontalActivityListViewAdapter;
import com.example.handschoolapplication.adapter.HorizontalLearnListViewAdapter;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.ClassBean;
import com.example.handschoolapplication.bean.CourseBean;
import com.example.handschoolapplication.bean.CurrentCityChangeBean;
import com.example.handschoolapplication.bean.CurrentCityChangeBeanS;
import com.example.handschoolapplication.bean.HomeAdBean;
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.bean.TeachNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.Marquee;
import com.example.handschoolapplication.view.MarqueeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public LocationClient mLocationClient = null;
    @BindView(R.id.et_search)
    TextView etSearch;
    @BindView(R.id.iv_style_art)
    ImageView ivStyleArt;
    @BindView(R.id.iv_learn_help)
    ImageView ivLearnHelp;
    @BindView(R.id.iv_activity)
    ImageView ivActivity;
    @BindView(R.id.iv_child_edu)
    ImageView ivChildEdu;
    @BindView(R.id.iv_trusteeship)
    ImageView ivTrusteeship;
    @BindView(R.id.iv_family_edu)
    ImageView ivFamilyEdu;
    @BindView(R.id.ll_learn)
    LinearLayout llLearn;
    Unbinder unbinder;
    @BindView(R.id.hl_art)
    RecyclerView hlArt;
    @BindView(R.id.hl_learn)
    RecyclerView hlLearn;
    @BindView(R.id.lv_learn_name)
    ListView lvLearnName;
    @BindView(R.id.hl_activity)
    RecyclerView hlActivity;
    @BindView(R.id.lv_activity_name)
    ListView lvActivityName;
    @BindView(R.id.lv_child_name)
    ListView lvChildName;
    @BindView(R.id.lv_trusteeship_name)
    ListView lvTrusteeshipName;
    @BindView(R.id.lv_homelearn_name)
    ListView lvHomelearnName;
    @BindView(R.id.ll_sign_in)
    LinearLayout llSignIn;
    @BindView(R.id.ll_sign_ins)
    LinearLayout llSignIns;
    @BindView(R.id.tv_hometype1)
    TextView tvHometype1;
    @BindView(R.id.tv_hometype2)
    TextView tvHometype2;
    @BindView(R.id.tv_hometype3)
    TextView tvHometype3;
    @BindView(R.id.tv_hometype4)
    TextView tvHometype4;
    @BindView(R.id.tv_hometype5)
    TextView tvHometype5;
    @BindView(R.id.tv_hometype6)
    TextView tvHometype6;
    @BindView(R.id.tv_hometypelist1)
    TextView tvHometypelist1;
    @BindView(R.id.tv_hometypelist2)
    TextView tvHometypelist2;
    @BindView(R.id.tv_hometypelist3)
    TextView tvHometypelist3;
    @BindView(R.id.tv_hometypelist4)
    TextView tvHometypelist4;
    @BindView(R.id.tv_hometypelist5)
    TextView tvHometypelist5;
    @BindView(R.id.tv_hometypelist6)
    TextView tvHometypelist6;
    @BindView(R.id.iv_sign)
    ImageView ivSign;
    Unbinder unbinder1;
    @BindView(R.id.mScrollView)
    PullToRefreshScrollView mScrollView;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_text)
    TextView tv_text;
    HorizontalListViewAdapter mAdapter;
    HorizontalLearnListViewAdapter mLearnAdapter;
    HorizontalActivityListViewAdapter mActivityAdapter;
    List<Marquee> marquees = new ArrayList<>();
    private View view;
    private GalleryAdapter galleryAdapter1;
    private GalleryAdapter galleryAdapter2;
    private GalleryAdapter galleryAdapter3;
    private TextView tvLocation;
    private ListView LvCourseName;
    private HPCourseAdapter courseAdapter1;
    private HPCourseAdapter courseAdapter2;
    private HPCourseAdapter courseAdapter3;
    private HPClassAdapter classAdapter1;
    private HPClassAdapter classAdapter2;
    private HPClassAdapter classAdapter3;
    private List<CourseBean.DataBean> courseBeanList1 = new ArrayList<>();
    private List<CourseBean.DataBean> courseBeanList2 = new ArrayList<>();
    private List<CourseBean.DataBean> courseBeanList3 = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList1 = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList2 = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList3 = new ArrayList<>();
    private MarqueeView marqueeView;
    private TextView[] type;
    private TextView[] typelist;
    private ArrayList<ArrayList<String>> typetwolist;
    private String user_id;
    private ArrayList<String> typeones;
    private MyLocationListener myListener = new MyLocationListener();
    private String city = "廊坊";
    private double[] locations;
    private boolean isLogin;
    private int position = 0;

    private ScaleAnimation sato0 = new ScaleAnimation(1, 0, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0, 1, 1, 1,
            Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
    private Animation animation;
    private String city1 = "廊坊";
    private String city2 = "";//本界面保存的city
    private ArrayList<HomeAdBean.DataBean> homeAdList;//接受所有的广告
    private ArrayList<HomeAdBean.DataBean> homeAdLists = new ArrayList<>();//筛选后的广告
    private AnimatorSet animSet;
    private LoadingDailog dialog;//加载的等待动画
    private String flag;
    private ConvenientBanner convenientBanner;
    private List<String> listImg = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initOrgan("托教", classBeanList2, classAdapter2, lvTrusteeshipName);
                    break;
                case 1:
                    initOrgan("家教", classBeanList3, classAdapter3, lvHomelearnName);
                    break;
                case 3:
                    if (null != tvLocation) {
                        tvLocation.setText(city);
                        city1 = city;
                    }
                    break;
                case 2:
                    Log.e("aaa",
                            "(HomeFragment.java:233)<--定位成功，获取到当前位置的坐标，请求早教接口-->");
                    locations = (double[]) msg.obj;
                    //初始化课程类型
                    initClassType();
                    //初始化
                    initOrgan("早教", classBeanList1, classAdapter1, lvChildName);

                    break;
            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getActivity());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, view);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        isLogin = (boolean) SPUtils.get(getActivity(), "isLogin", false);
        flag = (String) SPUtils.get(getActivity(), "flag", "");
        type = new TextView[]{tvHometype1, tvHometype2, tvHometype3, tvHometype4, tvHometype5, tvHometype6};
        typelist = new TextView[]{tvHometypelist1, tvHometypelist2, tvHometypelist3, tvHometypelist4, tvHometypelist5, tvHometypelist6};
        LvCourseName = (ListView) view.findViewById(R.id.lv_course_name);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        if (flag.equals("1")) {//判断是否为企业版的 隐藏签到按钮
            llSignIn.setVisibility(View.INVISIBLE);
        }

        EventBus.getDefault().register(this);

        initScorllView();
        startLocate();
        initLvData();
        //教育资讯跑马灯
        initTeachNews();
        //初始化首页广告位
        initHomeAd();
        //初始化课程类型
//        initClassType();
        //初始化
//        initOrgan("早教", classBeanList1, classAdapter1, lvChildName);
        //判断签到  后台需要  前台没用
        isSign();

        initAnim();

//        mScrollView.smoothScrollTo(0, 20);


        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(getActivity())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();


        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String advertising_type = homeAdLists.get(position).getAdvertising_type();
                String advertising_phone = homeAdLists.get(position).getAdvertising_phone();
                String advertising_content = homeAdLists.get(position).getAdvertising_content();
                int advertising_id = homeAdLists.get(position).getAdvertising_id();

                Log.e("aaa",
                        "(HomeFragment.java:312)<--轮播广告的 position 和type-->" + position + "   " + advertising_type);
                Log.e("aaa", "(HomeFragment.java:331)<--advertising_content-->" + advertising_content);

                switch (advertising_type) {
                    case "0"://图片加链接
                        startActivity(new Intent(getActivity(), AdvertisingActivity.class)
                                .putExtra("id", advertising_id));
                        break;
                    case "1"://课程广告
                        startActivity(new Intent(getActivity(), CourseHomePagerActivity.class)
                                .putExtra("course_id", advertising_content + ""));
                        break;
                    case "2"://优惠券广告
                        if (isLogin) {
                            getDiscountPager(advertising_id);
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                        }
                        break;
                    case "3"://图文消息广告
                        startActivity(new Intent(getActivity(), AdvertisingActivity.class)
                                .putExtra("id", advertising_id)
                                .putExtra("content", advertising_phone));
                        break;
                }
            }
        });

        String str= (String) SPUtils.get(getContext(),"YinSi","");
        if(str==""){
            showPopUpWindow(ivStyleArt);
        }
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mLocationClient != null)
//            mLocationClient.start();
    }

    private void initScorllView() {
        mScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        //2.通过调用getLoadingLayoutProxy方法，设置下拉刷新状况布局中显示的文字 ，
        // 第一个参数为true,代表下拉刷新
        ILoadingLayout headLables = mScrollView.getLoadingLayoutProxy(true, false);
        headLables.setPullLabel("下拉刷新");
        headLables.setRefreshingLabel("正在刷新");
        headLables.setReleaseLabel("松开刷新");


        //3.设置监听事件
        mScrollView.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                        //教育资讯跑马灯
                        initTeachNews();
                        //初始化首页广告位
                        initHomeAd();
                        //初始化课程类型
                        initClassType();
                        //初始化
                        initOrgan("早教", classBeanList1, classAdapter1, lvChildName);
                        refreshComplete();
                        //数据加载完成后，关闭header,footer
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//         addToBottom()//请求网络数据，并更新listview组件
                        refreshComplete();//数据加载完成后，关闭header,footer
                    }
                });


    }

    /**
     * 刷新完成时关闭
     */
    public void refreshComplete() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.onRefreshComplete();
            }
        }, 1000);
    }

    private void initAnim() {
//        iv.setVisibility(View.VISIBLE);

        ObjectAnimator translationX = ObjectAnimator.ofFloat(iv, "translationX", 0f, -70, -120);  // 平移动画
        ObjectAnimator translationY = ObjectAnimator.ofFloat(iv, "translationY", 0f, 150, 300);  // 平移动画
//                ObjectAnimator rotate = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);  // 旋转动画
        ObjectAnimator alphaX = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 3f);  // 透明度动画 // 步骤2：创建组合动画的对象
        ObjectAnimator alphaY = ObjectAnimator.ofFloat(iv, "scaleY", 0f, 3f);// 透明度动画 // 步骤2：创建组合动画的对象
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 1f, 0f);  // 透明度动画 // 步骤2：创建组合动画的对象
        // 步骤3：根据需求组合动画
        animSet = new AnimatorSet();
        animSet.play(translationX).with(translationY).with(alphaY).with(alphaX).before(alpha);
//                animSet.play(alpha);
        animSet.setDuration(1000);  // 步骤4：启动动画

        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("aaa",
                        "(HomeFragment.java:356)<--动画直接开始时调用-->");
                iv.setVisibility(View.VISIBLE);
                tv_text.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("aaa",
                        "(HomeFragment.java:361)<--动画执行结束时  调用-->");
                iv.setVisibility(View.GONE);
                tv_text.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //判断签到  后台需要  前台没用
    private void isSign() {

        OkHttpUtils.post()
                .url(InternetS.IS_SIGN)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:231)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:237)" + response);
                    }
                });
    }

    private void initHomeAd() {
        listImg.clear();
        homeAdLists.clear();
        OkHttpUtils.post()
                .url(Internet.HOMEADs)
                .addParams("other_type", "首页")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:613)<--广告失败返回-->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:183)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                        } else {

                            try {
                                Gson gson = new Gson();
                                homeAdList = (ArrayList<HomeAdBean.DataBean>) gson.fromJson(response, HomeAdBean.class).getData();
                                for (int i = 0; i < homeAdList.size(); i++) {
                                    if ("4".equals(homeAdList.get(i).getAdvertising_type())) {
                                    } else {
                                        listImg.add(Internet.BASE_URL + homeAdList.get(i).getAdvertising_photo());
                                        homeAdLists.add(homeAdList.get(i));
                                    }
                                }
                                try {
                                    setConvenientBanner(listImg);
                                } catch (Exception e) {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void initTeachNews() {
        OkHttpUtils.post()
                .url(Internet.TEACHNEWS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:185)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                        } else {
                            Gson gson = new Gson();
                            ArrayList<TeachNewsBean.DataBean> teachNewsList =
                                    (ArrayList<TeachNewsBean.DataBean>) gson.fromJson(response, TeachNewsBean.class).getData();
                            for (int i = 0; i < teachNewsList.size(); i++) {
                                if (i % 2 == 0 && i != teachNewsList.size() - 1) {
                                    Marquee marquee = new Marquee();
                                    marquee.setFirstimgUrl(teachNewsList.get(i).getNews_type());
                                    marquee.setFirsttitle(teachNewsList.get(i).getNews_title());
                                    marquee.setImgUrl(teachNewsList.get(i + 1).getNews_type());
                                    marquee.setTitle(teachNewsList.get(i + 1).getNews_title());
                                    marquees.add(marquee);
                                }
                            }
                            marqueeView.startWithList(marquees);
                            marqueeView.setOnClick(new MarqueeView.OnClick() {
                                @Override
                                public void onClick() {
                                    startActivity(new Intent(getActivity(), EducationActivity.class));
                                }
                            });
                        }

                    }
                });
    }

    private void initLvData() {
        courseAdapter1 = new HPCourseAdapter(courseBeanList1, getActivity());
        courseAdapter2 = new HPCourseAdapter(courseBeanList2, getActivity());
        courseAdapter3 = new HPCourseAdapter(courseBeanList3, getActivity());
        classAdapter1 = new HPClassAdapter(getActivity(), classBeanList1);
        classAdapter2 = new HPClassAdapter(getActivity(), classBeanList2);
        classAdapter3 = new HPClassAdapter(getActivity(), classBeanList3);

        LvCourseName.setAdapter(courseAdapter1);

        lvLearnName.setAdapter(courseAdapter2);

        lvActivityName.setAdapter(courseAdapter3);

        lvChildName.setAdapter(classAdapter1);

        lvTrusteeshipName.setAdapter(classAdapter2);

        lvHomelearnName.setAdapter(classAdapter3);


    }

    private void setConvenientBanner(List<String> bannerList) {
        convenientBanner.setPages(
                new CBViewHolderCreator<NetWorkImageHolderView>() {
                    @Override
                    public NetWorkImageHolderView createHolder() {
                        return new NetWorkImageHolderView();
                    }
                }, bannerList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.conven_point_grey, R.drawable.conven_point_blue})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(2000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);  //  集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        //   convenientBanner.setManualPageable(false);//设置不能手动影响
    }

    @OnClick({R.id.iv_search, R.id.ll_sign_in, R.id.rl_style_art, R.id.rl_learn_help, R.id.rl_activity,
            R.id.rl_child_edu, R.id.rl_trusteeship, R.id.rl_family_edu,
            R.id.tv_more_art, R.id.tv_more_learn, R.id.tv_more_activity,
            R.id.tv_more_child, R.id.tv_more_trusteeship, R.id.tv_more_home
            , R.id.et_search, R.id.ll_address, R.id.iv_add, R.id.iv_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search://搜索
                break;
            case R.id.ll_sign_in://签到
                Log.e("aaa",
                        "(HomeFragment.java:843)<---->" + "点击签到按钮");
                if (isLogin) {
//                    dialog.show();
                    OkHttpUtils.post()
                            .url(Internet.SIGN)
                            .addParams("user_id", user_id)
                            .addParams("sign_city", city)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("aaa",
                                            "(HomeFragment.java:905)<---->" + e.getMessage());
                                    Toast.makeText(getActivity(), "签到失败，请检查网络", Toast.LENGTH_SHORT).show();
//                                    dialog.dismiss();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(HomeFragment.java:603)" + response);
//                                    dialog.dismiss();
                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String msg = json.getString("msg");
                                        if (msg.contains("成功")) {
                                            iv.setVisibility(View.VISIBLE);
                                            animSet.start();
                                        }
                                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }

                break;

            case R.id.iv_add:
//                Log.e("aaa",
//                    "(HomeFragment.java:893)123---321"+galleryAdapter1.getItemCount());
                if (galleryAdapter1.getItemCount() > 0)
                    hlArt.smoothScrollToPosition(galleryAdapter1.getItemCount() - 1);
                break;
            case R.id.iv_sub:
                if (galleryAdapter1.getItemCount() > 0)
                    hlArt.smoothScrollToPosition(0);
                break;
            case R.id.iv_sub1:
                if (galleryAdapter2.getItemCount() > 0) {
                    hlLearn.smoothScrollToPosition(0);
                }
                break;
            case R.id.iv_add1:
                if (galleryAdapter2.getItemCount() > 0) {
                    hlLearn.smoothScrollToPosition(galleryAdapter2.getItemCount() - 1);
                }
                break;
            case R.id.iv_sub2:
                if (galleryAdapter3.getItemCount() > 0) hlActivity.smoothScrollToPosition(0);
                break;
            case R.id.iv_add2:
                if (galleryAdapter3.getItemCount() > 0)
                    hlActivity.smoothScrollToPosition(galleryAdapter3.getItemCount() - 1);
                break;
            case R.id.rl_style_art://文艺
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "文体艺术")
                        .putExtra("types", typetwolist.get(0))
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.rl_learn_help://学习辅导
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "学习辅导")
                        .putExtra("types", typetwolist.get(1))
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.rl_activity://活动
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "活动拓展")
                        .putExtra("types", typetwolist.get(2))
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.rl_child_edu://早教
                startActivity(new Intent(getActivity(), ChildEduActivity.class)
                        .putExtra("flag", "早教")
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.rl_trusteeship://托教
                startActivity(new Intent(getActivity(), ChildEduActivity.class)
                        .putExtra("flag", "托教")
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.rl_family_edu://家教
                startActivity(new Intent(getActivity(), ChildEduActivity.class)
                        .putExtra("flag", "家教")
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_art:
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "文体艺术")
                        .putExtra("types", typetwolist.get(0))
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_learn:
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "学习辅导")
                        .putExtra("types", typetwolist.get(1))
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_activity:
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "活动拓展")
                        .putExtra("types", typetwolist.get(2))
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_child:
                startActivity(new Intent(getActivity(), ChildEduActivity.class)
                        .putExtra("flag", "早教")
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_trusteeship:
                startActivity(new Intent(getActivity(), ChildEduActivity.class)
                        .putExtra("flag", "托教")
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_home:
                startActivity(new Intent(getActivity(), ChildEduActivity.class)
                        .putExtra("flag", "家教")
                        .putExtra("city", city1)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.et_search:
                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra("location", locations));
                break;
            case R.id.ll_address:
                startActivityForResult(new Intent(getActivity(), CurrentCitysActivity.class).putExtra("city", city), 1);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getActivity(), "id=" + mData.get(position), Toast.LENGTH_SHORT).show();
        mAdapter.setSelectedPosition(position);
        mAdapter.notifyDataSetChanged();

        mActivityAdapter.setSelectedPosition(position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 11) {
            city1 = data.getStringExtra("cityName");
            tvLocation.setText(city1);
            initOrgan("早教", classBeanList1, classAdapter1, lvChildName);
            CurrentCityChangeBean currentCityChangeBean = new CurrentCityChangeBean();
            currentCityChangeBean.setCurrentCity(city1);
            EventBus.getDefault().post(currentCityChangeBean);
            initClassType();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initOrgan(final String type, final List<ClassBean.DataBean> classBeanList,
                           final HPClassAdapter classAdapter, final ListView lv) {

        HashMap<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(city1)) {
            params.put("mechanism_city", city1);
        } else {
            params.put("mechanism_city", "");
        }
        params.put("mechanism_type", type);

        OkHttpUtils.post()
                .url(Internet.ORGANLIST)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:385)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:217)" + type + "===" + response);

                        switch (type) {
                            case "早教":
                                mHandler.sendEmptyMessage(0);
                                break;
                            case "托教":
                                mHandler.sendEmptyMessage(1);
                                break;
                        }

                        try {

                            classBeanList.clear();
                            Gson gson = new Gson();
                            if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
                                Log.e("aaa",
                                        "(HomeFragment.java:409)" + "适配器刷新");
                                classAdapter.notifyDataSetChanged();
                                classAdapter.setLocations(locations);
                            } else {
                                List<ClassBean.DataBean> data = gson.fromJson(response, ClassBean.class).getData();
                                if (data != null && data.size() > 5) {
                                    for (int i = 0; i < 5; i++) {
                                        classBeanList.add(data.get(i));
                                    }
                                } else if (data != null && data.size() <= 5) {
                                    classBeanList.addAll(data);
                                }

                                classAdapter.notifyDataSetChanged();
                                classAdapter.setLocations(locations);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast.makeText(getActivity(), "数据解析错误", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }

    private void initClassType() {
        OkHttpUtils.post()
                .url(Internet.CLASSTYPE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:487)<--获取6大类数据  失败返回-->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:493)<--获取6大类数据  成功返回-->" + response);
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            return;
                        }

                        try {
                            Gson gson = new Gson();
                            HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                            typeones = new ArrayList<String>();
                            typetwolist = new ArrayList<ArrayList<String>>();
                            for (int i = 0; i < 6; i++) {
                                typeones.add(homeClassType.getData().get(i).getType_one_name());
                                ArrayList<String> typetwos = new ArrayList<String>();
                                if (null != homeClassType.getData().get(i).getTypeTwoInfo()) {
                                    for (int m = 0; m < homeClassType.getData().get(i).getTypeTwoInfo().size(); m++) {
                                        typetwos.add(homeClassType.getData().get(i).getTypeTwoInfo().get(m).getType_two_name());
                                    }
                                }
                                type[i].setText(typeones.get(i));
                                typelist[i].setText(typeones.get(i));
                                typetwolist.add(typetwos);
                            }

                            //初始化第二个列表
                            initHLArtData(typetwolist.get(0));
                            initHLLearnData(typetwolist.get(1));
                            if (typetwolist.size() > 2) {
                                initHLActivityData(typetwolist.get(2));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //初始化二小类
    private void initHLArtData(final ArrayList<String> strings) {

        galleryAdapter1 = new GalleryAdapter(getActivity(), strings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hlArt.setLayoutManager(linearLayoutManager);
        hlArt.setAdapter(galleryAdapter1);
        galleryAdapter1.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                galleryAdapter1.setSelectedPosition(position);
                galleryAdapter1.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList1, courseAdapter1, LvCourseName);
            }
        });


    }

    private void initHLLearnData(final ArrayList<String> strings) {

        galleryAdapter2 = new GalleryAdapter(getActivity(), strings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hlLearn.setLayoutManager(linearLayoutManager);
        hlLearn.setAdapter(galleryAdapter2);
        galleryAdapter2.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                galleryAdapter2.setSelectedPosition(position);
                galleryAdapter2.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList2, courseAdapter2, lvLearnName);
            }
        });

    }

    private void initHLActivityData(final ArrayList<String> strings) {

        galleryAdapter3 = new GalleryAdapter(getActivity(), strings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hlActivity.setLayoutManager(linearLayoutManager);
        hlActivity.setAdapter(galleryAdapter3);
        initCourseData();
        galleryAdapter3.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                galleryAdapter3.setSelectedPosition(position);
                galleryAdapter3.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList3, courseAdapter3, lvActivityName);
            }
        });


    }

    //选择而小类之后列表
    private void chooseClassTwoType(final String s, final List<CourseBean.DataBean> list, final HPCourseAdapter sAdapter, final ListView lv) {
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", s)
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:801)<--获取" + s + "小类的失败返回-->" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(HomeFragment.java:808)<--获取" + s + "小类的成功返回-->" + response);
                        Log.e("aaa",
                                "(HomeFragment.java:810)city=====" + city1);
                        list.clear();
                        Gson gson = new Gson();
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            sAdapter.notifyDataSetChanged();
                            MyUtiles.setListViewHeightBasedOnChildren(lv);
                        } else {
                            try {
                                list.addAll(gson.fromJson(response, CourseBean.class).getData());
                                sAdapter.setLocation(locations);
                                sAdapter.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lv);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    private void initCourseData() {
        if (typetwolist.get(2) != null) {
            OkHttpUtils.post()
                    .url(Internet.COURSELIST)
                    .addParams("course_type", typetwolist.get(2).get(0))
                    .addParams("course_province", city1)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("aaa",
                                    "(HomeFragment.java:418)" + e.toString());

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e("aaa",
                                    "(HomeFragment.java:542)" + response);
                            Log.e("aaa",
                                    "(HomeFragment.java:544)city===" + city1);
                            courseBeanList3.clear();
                            Gson gson = new Gson();
                            if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                courseAdapter3.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lvActivityName);
                            } else {
                                try {
                                    List<CourseBean.DataBean> data = gson.fromJson(response, CourseBean.class).getData();
                                    if (data != null && data.size() > 5) {
                                        for (int i = 0; i < 5; i++) {
                                            courseBeanList3.add(data.get(i));
                                        }
                                    } else {
                                        courseBeanList3.addAll(data);
                                    }
                                    courseAdapter3.setLocation(locations);
                                    courseAdapter3.notifyDataSetChanged();
                                    MyUtiles.setListViewHeightBasedOnChildren(lvActivityName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (typetwolist.get(1) != null) {
                                OkHttpUtils.post()
                                        .url(Internet.COURSELIST)
                                        .addParams("course_type", typetwolist.get(1).get(0))
                                        .addParams("course_province", city1)
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                Log.e("aaa",
                                                        "(HomeFragment.java:418)" + e.toString());

                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                Log.e("aaa",
                                                        "(HomeFragment.java:573)" + response);

                                                Log.e("aaa",
                                                        "(HomeFragment.java:577)city==" + city1);
                                                try {
                                                    courseBeanList2.clear();
                                                    Gson gson = new Gson();
                                                    if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                                        courseAdapter2.notifyDataSetChanged();
                                                        MyUtiles.setListViewHeightBasedOnChildren(lvLearnName);
                                                    } else {

                                                        List<CourseBean.DataBean> data = gson.fromJson(response, CourseBean.class).getData();
                                                        if (data != null && data.size() > 5) {
                                                            for (int i = 0; i < 5; i++) {
                                                                courseBeanList2.add(data.get(i));
                                                            }
                                                        } else {
                                                            courseBeanList2.addAll(data);
                                                        }
//                                                    courseBeanList2.addAll(gson.fromJson(response, CourseBean.class).getData());
                                                        courseAdapter2.setLocation(locations);
                                                        courseAdapter2.notifyDataSetChanged();
                                                        MyUtiles.setListViewHeightBasedOnChildren(lvLearnName);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                if (typetwolist.get(0) != null) {
                                                    OkHttpUtils.post()
                                                            .url(Internet.COURSELIST)
                                                            .addParams("course_type", typetwolist.get(0).get(0))
                                                            .addParams("course_province", city1)
                                                            .build()
                                                            .execute(new StringCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int id) {
                                                                    Log.e("aaa",
                                                                            "(HomeFragment.java:418)" + e.toString());

                                                                }

                                                                @Override
                                                                public void onResponse(String response, int id) {
                                                                    Log.e("aaa",
                                                                            "(HomeFragment.java:603)" + response);
                                                                    Log.e("aaa",
                                                                            "(HomeFragment.java:608)city====" + city1);
                                                                    courseBeanList1.clear();
                                                                    Gson gson = new Gson();
                                                                    if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                                                        courseAdapter1.notifyDataSetChanged();
                                                                        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);
                                                                    } else {

                                                                        List<CourseBean.DataBean> data = gson.fromJson(response, CourseBean.class).getData();
                                                                        if (data != null && data.size() > 5) {
                                                                            for (int i = 0; i < 5; i++) {
                                                                                courseBeanList1.add(data.get(i));
                                                                            }
                                                                        } else {
                                                                            courseBeanList1.addAll(data);
                                                                        }
//                                                                        courseBeanList1.addAll(gson.fromJson(response, CourseBean.class).getData());
                                                                        courseAdapter1.setLocation(locations);
                                                                        courseAdapter1.notifyDataSetChanged();
                                                                        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        });
                            }
                        }
                    });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CurrentCityChangeBeanS event) {
        Log.e("aaa", "(HomeFragment.java:840)<---->" + event.getCurrentCity());
        city1 = event.getCurrentCity();
        tvLocation.setText(city1);
        initOrgan("早教", classBeanList1, classAdapter1, lvChildName);
        initClassType();
    }

    private void getDiscountPager(int coupons_id) {
        String userId = (String) SPUtils.get(getActivity(), "userId", "");
        String userPhone = (String) SPUtils.get(getActivity(), "user_phone", "");
        HashMap<String, String> params = new HashMap<>();
        params.put("coupons_id", coupons_id + "");
        params.put("user_id", userId);
        params.put("user_phone", userPhone);
        OkHttpUtils.post()
                .url(InternetS.GET_DISCOUNT_PAPER)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:82)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:88)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            int result = jsonObject.getInt("result");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    class NetWorkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
//            imageView.setImageResource(data);
            if (data != null) {
                with(getActivity()).load(data).into(imageView);
                // ImageLoader.getInstance().displayImage(data, imageView, options);
            } else {
                imageView.setImageResource(R.drawable.meinv);
            }
        }

    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            city = location.getCity();
            city=city==null?"廊坊":city;
            city1 = city;
            city2 = city;
            try {
                if (TextUtils.isEmpty((String) (SPUtils.get(getActivity(), "lastCity", "")))) {
                    SPUtils.put(getActivity(), "city", city);
                    SPUtils.put(getActivity(), "lastCity", city);
                } else {
                    String citys = (String) SPUtils.get(getActivity(), "city", "");
                    SPUtils.put(getActivity(), "city", city);
                    SPUtils.put(getActivity(), "lastCity", citys);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            double[] locations = new double[2];
            locations[0] = location.getLatitude();//纬度
            locations[1] = location.getLongitude();//经度
            SPUtils.put(getActivity(), "latitude", location.getLatitude());
            SPUtils.put(getActivity(), "longitude", location.getLongitude());
            SPUtils.put(getActivity(), "province", location.getLongitude());
            Message message = new Message();
            message.obj = locations;
            message.what = 2;
            mHandler.sendMessage(message);
            mHandler.sendEmptyMessage(3);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//
//        Log.e("aaa",
//                "(HomeFragment.java:1150)<---->" + "进入到首页fragment执行的刷新的操作");
//
//        //首先判断city是否有值  进而判断出是否定位成功
//        if (!TextUtils.isEmpty(city)) {
//
//            initClassType();
//            //初始化
//            initOrgan("早教", classBeanList1, classAdapter1, lvChildName);
//        }
//
//    }

    private void showPopUpWindow(final ImageView lpsm) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_yin_si, null);
        final PopupWindow popWnd = new PopupWindow(getActivity());
        popWnd.setContentView(contentView);
//    popWnd.setWidth(263);
//    popWnd.setHeight(320);
        popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View contentView1 = popWnd.getContentView();

        Button bNo = contentView1.findViewById(R.id.b_no);
        final Button bYes = contentView1.findViewById(R.id.b_yes);
        TextView tvYinsi = contentView1.findViewById(R.id.tv_yinsi);
        final ImageView ivMengban = contentView1.findViewById(R.id.iv_mengban);

        final SpannableStringBuilder style = new SpannableStringBuilder();

        //设置文字
        style.append("\u3000\u3000本应用非常重视用户隐私政策并严格遵守相关的法律规定。请您仔细阅读《隐私政策》后再继续使用。如果您继续使用我们的服务，表示您已经充分阅读和理解我们协议的全部内容。\n" +
                "\u3000\u3000本app尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更优质的服务，本应用会按照本隐私权政策的规定使用和披露您的个人信息。除本隐私权政策另有规定外，在未征得" +
                "您事先许可的情况下，本应用不会将这些信息对外披露或向第三方提供。本应用会不时更新本隐私权政策。 您在同意本应用服务使用协议之时，即视为您已经同意本隐私权政策全部内容。");

        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getContext(), AgreementWebActivity.class)
                        .putExtra("url","privacy_policy1.html")
                        .putExtra("title","隐私权政策"));
                //Toast.makeText(getActivity(), "查看协议!", Toast.LENGTH_SHORT).show();
            }
        };
        style.setSpan(clickableSpan, 34, 40, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvYinsi.setText(style);

        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 34, 40, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //配置给TextView
        tvYinsi.setMovementMethod(LinkMovementMethod.getInstance());
        tvYinsi.setText(style);
        bYes.setClickable(false);
        bYes.setBackgroundResource(R.color.c6c6c6);
        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMengban.setVisibility(View.GONE);
                popWnd.dismiss();
                MyApplication.getInstance().exit();
            }
        });
        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),yuanyin,Toast.LENGTH_SHORT).show();
                ivMengban.setVisibility(View.GONE);
                popWnd.dismiss();
                SPUtils.put(getContext(),"YinSi","yes");
            }
        });
        /*rgCancel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)contentView1.findViewById(rgCancel.getCheckedRadioButtonId());
                yuanyin=radioButton.getText().toString().trim();
            }
        });*/
        CountDownTimer timer = new CountDownTimer(6*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                bYes.setText("还剩"+millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                bYes.setText("确定");
                bYes.setClickable(true);
                bYes.setBackgroundResource(R.color.blue);
            }
        }.start();
        popWnd.setTouchable(true);
        popWnd.setFocusable(true);
        popWnd.setOutsideTouchable(false);
        popWnd.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        backgroundAlpha(0.6f);

        ivMengban.setVisibility(View.VISIBLE);
        //添加pop窗口关闭事件
        popWnd.setOnDismissListener(new poponDismissListener());
        popWnd.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popWnd.dismiss();
                    return true;
                }
                return false;
            }
        });

        //popWnd.showAsDropDown(mTvLine, 200, 0);
        lpsm.post(new Runnable() {
            @Override
            public void run() {
                popWnd.showAtLocation(lpsm, Gravity.CENTER, 0, 0);
            }
        });


    }

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
