package com.example.handschoolapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ArtActivity;
import com.example.handschoolapplication.activity.ChildEduActivity;
import com.example.handschoolapplication.activity.CurrentCityActivity;
import com.example.handschoolapplication.activity.EducationActivity;
import com.example.handschoolapplication.activity.HomeEduActivity;
import com.example.handschoolapplication.activity.LoginActivity;
import com.example.handschoolapplication.activity.SearchActivity;
import com.example.handschoolapplication.activity.TrusteeshipActivity;
import com.example.handschoolapplication.adapter.HPClassAdapter;
import com.example.handschoolapplication.adapter.HPCourseAdapter;
import com.example.handschoolapplication.adapter.HorizontalActivityListViewAdapter;
import com.example.handschoolapplication.adapter.HorizontalLearnListViewAdapter;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.ClassBean;
import com.example.handschoolapplication.bean.CourseBean;
import com.example.handschoolapplication.bean.HomeAdBean;
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.bean.TeachNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.HorizontalActivityListView;
import com.example.handschoolapplication.view.HorizontalLearnListView;
import com.example.handschoolapplication.view.HorizontalListView;
import com.example.handschoolapplication.view.Marquee;
import com.example.handschoolapplication.view.MarqueeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.example.handschoolapplication.R.id.iv_sign;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {


//    @BindView(R.id.tv_location)
//    TextView tvLocation;
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
    //    @BindView(R.id.tv_tip)
//    TextView tvTip;
//    @BindView(R.id.tv_text)
//    TextView tvText;
//    @BindView(R.id.tv_tips)
//    TextView tvTips;
//    @BindView(R.id.tv_tetxs)
//    TextView tvTetxs;
    Unbinder unbinder;
    @BindView(R.id.hl_art)
    HorizontalListView hlArt;

    @BindView(R.id.hl_learn)
    HorizontalLearnListView hlLearn;
    @BindView(R.id.lv_learn_name)
    ListView lvLearnName;
    @BindView(R.id.hl_activity)
    HorizontalActivityListView hlActivity;
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
    @BindView(iv_sign)
    ImageView ivSign;
    private View view;
    HorizontalListViewAdapter mAdapter;
    HorizontalLearnListViewAdapter mLearnAdapter;
    HorizontalActivityListViewAdapter mActivityAdapter;
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
    List<Marquee> marquees = new ArrayList<>();
    private TextView[] type;
    private TextView[] typelist;
    private ArrayList<ArrayList<String>> typetwolist;
    private String user_id;
    private ArrayList<String> typeones;
    public LocationClient mLocationClient = null;
    public MyLocationListener myListener = new MyLocationListener();
    private String city;
    private double[] locations;
    private boolean isLogin;

    private ScaleAnimation sato0 = new ScaleAnimation(1,0,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    private ScaleAnimation sato1 = new ScaleAnimation(0,1,1,1,
            Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);

    public HomeFragment() {
        // Required empty public constructor
    }

    private ConvenientBanner convenientBanner;
    private List<String> listImg = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    initOrgan("托管", classBeanList2, classAdapter2, lvTrusteeshipName);
                    break;
                case 1:
                    initOrgan("家教", classBeanList3, classAdapter3, lvHomelearnName);
                    break;
                case 3:
                        if (null!=tvLocation){
                            tvLocation.setText(city);
                        }
                    break;
                case 2:
                    locations = (double[]) msg.obj;
                    break;
            }
        }
    };


    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());     //声明LocationClient类
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
        view = super.onCreateView(inflater,container,savedInstanceState);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        isLogin = (boolean) SPUtils.get(getActivity(), "isLogin", false);
        type = new TextView[]{tvHometype1, tvHometype2, tvHometype3, tvHometype4, tvHometype5, tvHometype6};
        typelist = new TextView[]{tvHometypelist1, tvHometypelist2, tvHometypelist3, tvHometypelist4, tvHometypelist5, tvHometypelist6};
        LvCourseName = (ListView) view.findViewById(R.id.lv_course_name);
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        locationAt();
        initLvData();
        //教育资讯跑马灯
        initTeachNews();
        //初始化首页广告位
        initHomeAd();
        //初始化课程类型
        initClassType();
        //初始化
        initOrgan("早教", classBeanList1, classAdapter1, lvChildName);

        //判断签到  后台需要  前台没用
        isSign();
        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llSignIn.setVisibility(View.GONE);
                llSignIns.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return view;
    }
    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    //定位
    private void locationAt() {
        startLocate();
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

    private void initOrgan(final String type, final List<ClassBean.DataBean> classBeanList, final HPClassAdapter classAdapter, final ListView lv) {
        OkHttpUtils.post()
                .url(Internet.ORGANLIST)
                .addParams("mechanism_type", type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:217)" + type + "===" + response);

                        if (response.contains("没有信息")){
                        }else {
                            switch (type) {
                                case "早教":
                                    mHandler.sendEmptyMessage(0);
                                    break;
                                case "托管":
                                    mHandler.sendEmptyMessage(1);
                                    break;
                            }
                            classBeanList.clear();
                            Gson gson = new Gson();
                            if (TextUtils.isEmpty(response)) {
                                classAdapter.notifyDataSetChanged();
                            } else {
                                classBeanList.addAll(gson.fromJson(response, ClassBean.class).getData());
                                classAdapter.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lv, getActivity());
                            }

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

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:180)" + response);
                        Gson gson = new Gson();
                        HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                        typeones = new ArrayList<String>();
                        typetwolist = new ArrayList<ArrayList<String>>();
                        for (int i = 0; i < homeClassType.getData().size(); i++) {
                            typeones.add(homeClassType.getData().get(i).getType_one_name());
                            ArrayList<String> typetwos = new ArrayList<String>();
                            if (null!=homeClassType.getData().get(i).getTypeTwoInfo()){
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
                        if (typetwolist.size()>2){
                            initHLActivityData(typetwolist.get(2));
                        }
                    }
                });
    }

    private void initHomeAd() {
        OkHttpUtils.post()
                .url(Internet.HOMEAD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:183)" + response);
                        Gson gson = new Gson();
                        ArrayList<HomeAdBean.DataBean> homeAdList =
                                (ArrayList<HomeAdBean.DataBean>) gson.fromJson(response, HomeAdBean.class).getData();
                        for (int i = 0; i < homeAdList.size(); i++) {
                            listImg.add(Internet.BASE_URL + homeAdList.get(i).getAdvertising_photo());
                        }
                        setConvenientBanner(listImg);
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


    private void initHLLearnData(final ArrayList<String> strings) {

        mLearnAdapter = new HorizontalLearnListViewAdapter(getActivity(), strings);
        hlLearn.setAdapter(mLearnAdapter);
        hlLearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + strings.get(position), Toast.LENGTH_SHORT).show();
                mLearnAdapter.setSelectedPosition(position);
                mLearnAdapter.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList2, courseAdapter2, lvLearnName);
            }
        });
    }

    private void initHLActivityData(final ArrayList<String> strings) {
        mActivityAdapter = new HorizontalActivityListViewAdapter(getActivity(), strings);
        hlActivity.setAdapter(mActivityAdapter);
        initCourseData();
        hlActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + strings.get(position), Toast.LENGTH_SHORT).show();
                mActivityAdapter.setSelectedPosition(position);
                mActivityAdapter.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList3, courseAdapter3, lvActivityName);
            }
        });
    }

    private void initCourseData() {
        if (typetwolist.get(2) != null) {
            OkHttpUtils.post()
                    .url(Internet.COURSELIST)
                    .addParams("course_type", typetwolist.get(2).get(0))
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
                                    "(HomeFragment.java:425)" + response);
                            courseBeanList3.clear();
                            Gson gson = new Gson();
                            if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                courseAdapter3.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lvActivityName);
                            } else {
                                courseBeanList3.addAll(gson.fromJson(response, CourseBean.class).getData());
                                courseAdapter3.setLocation(locations);
                                courseAdapter3.notifyDataSetChanged();
                                MyUtiles.setListViewHeightBasedOnChildren(lvActivityName);
                            }

                            if (typetwolist.get(1) != null) {
                                OkHttpUtils.post()
                                        .url(Internet.COURSELIST)
                                        .addParams("course_type", typetwolist.get(1).get(0))
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
                                                        "(HomeFragment.java:425)" + response);

                                                courseBeanList2.clear();
                                                Gson gson = new Gson();
                                                if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                                    courseAdapter2.notifyDataSetChanged();
                                                    MyUtiles.setListViewHeightBasedOnChildren(lvLearnName);
                                                } else {
                                                    courseBeanList2.addAll(gson.fromJson(response, CourseBean.class).getData());
                                                    courseAdapter2.setLocation(locations);
                                                    courseAdapter2.notifyDataSetChanged();
                                                    MyUtiles.setListViewHeightBasedOnChildren(lvLearnName);
                                                }
                                                if (typetwolist.get(0) != null) {
                                                    OkHttpUtils.post()
                                                            .url(Internet.COURSELIST)
                                                            .addParams("course_type", typetwolist.get(0).get(0))
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
                                                                            "(HomeFragment.java:425)" + response);
                                                                    courseBeanList1.clear();
                                                                    Gson gson = new Gson();
                                                                    if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                                                                        courseAdapter1.notifyDataSetChanged();
                                                                        MyUtiles.setListViewHeightBasedOnChildren(LvCourseName);
                                                                    } else {
                                                                        courseBeanList1.addAll(gson.fromJson(response, CourseBean.class).getData());
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

    //初始化二小类
    private void initHLArtData(final ArrayList<String> strings) {
        mAdapter = new HorizontalListViewAdapter(getActivity(), strings);
        hlArt.setAdapter(mAdapter);
        hlArt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "id=" + strings.get(position), Toast.LENGTH_SHORT).show();
                mAdapter.setSelectedPosition(position);
                mAdapter.notifyDataSetChanged();
                chooseClassTwoType(strings.get(position), courseBeanList1, courseAdapter1, LvCourseName);
            }
        });

    }

    //选择而小类之后列表
    private void chooseClassTwoType(final String s, final List<CourseBean.DataBean> list, final HPCourseAdapter sAdapter, final ListView lv) {
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", s)
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
                                "(HomeFragment.java:425)" + response);
                        list.clear();
                        Gson gson = new Gson();
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            sAdapter.notifyDataSetChanged();
                            MyUtiles.setListViewHeightBasedOnChildren(lv);
                        } else {
                            list.addAll(gson.fromJson(response, CourseBean.class).getData());
                            sAdapter.setLocation(locations);
                            sAdapter.notifyDataSetChanged();
                            MyUtiles.setListViewHeightBasedOnChildren(lv);
                        }
                    }
                });
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
            , R.id.et_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search://搜索
                break;
            case R.id.ll_sign_in://签到
//                城市根据定位重新调整
                ivSign.startAnimation(sato0);
                if (isLogin){
                    OkHttpUtils.post()
                            .url(Internet.SIGN)
                            .addParams("user_id", user_id)
                            .addParams("sign_city", city)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(HomeFragment.java:603)" + response);
                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String msg = json.getString("msg");
                                        if (msg.contains("成功")) {
//                                            llSignIn.setVisibility(View.GONE);
//                                            llSignIns.setVisibility(View.VISIBLE);
                                        }
                                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }else {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    getActivity().finish();
                }

                break;
            case R.id.rl_style_art://文艺
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "文体艺术")
                        .putExtra("types", typetwolist.get(0))
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.rl_learn_help://学习辅导
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "学习辅导")
                        .putExtra("types", typetwolist.get(1))
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1])
                );
                break;
            case R.id.rl_activity://活动
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "活动拓展")
                        .putExtra("types", typetwolist.get(2))
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1])
                );
                break;
            case R.id.rl_child_edu://早教
                startActivity(new Intent(getActivity(), ChildEduActivity.class));
                break;
            case R.id.rl_trusteeship://托管
                startActivity(new Intent(getActivity(), TrusteeshipActivity.class));
                break;
            case R.id.rl_family_edu://家教
                startActivity(new Intent(getActivity(), HomeEduActivity.class));
                break;
            case R.id.tv_more_art:
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "文体艺术")
                        .putExtra("types", typetwolist.get(0))
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_learn:
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "学习辅导")
                        .putExtra("types", typetwolist.get(1))
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_activity:
                startActivity(new Intent(getActivity(), ArtActivity.class)
                        .putExtra("flag", "活动拓展")
                        .putExtra("types", typetwolist.get(2))
                        .putExtra("city", city)
                        .putExtra("latitude", locations[0])
                        .putExtra("longitude", locations[1]));
                break;
            case R.id.tv_more_child:
                startActivity(new Intent(getActivity(), ChildEduActivity.class));
                break;
            case R.id.tv_more_trusteeship:
                startActivity(new Intent(getActivity(), TrusteeshipActivity.class));
                break;
            case R.id.tv_more_home:
                startActivity(new Intent(getActivity(), HomeEduActivity.class));
                break;
            case R.id.et_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.ll_address:
                startActivity(new Intent(getActivity(), CurrentCityActivity.class).putExtra("city", city));
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
                Glide.with(getActivity()).load(data).into(imageView);
                // ImageLoader.getInstance().displayImage(data, imageView, options);
            } else {
                imageView.setImageResource(R.drawable.meinv);
            }
        }

    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("描述：", sb.toString());

            city = location.getCity();
            double[] locations = new double[2];
            locations[0] = location.getLatitude();//纬度
            locations[1] = location.getLongitude();//经度
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

}
