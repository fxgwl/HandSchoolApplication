package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassSchoolAdapter;
import com.example.handschoolapplication.adapter.GalleryAdapter;
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ArtAdBean;
import com.example.handschoolapplication.bean.ClassSchoolBean;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.RankListUtils;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarttop.library.bean.City;
import com.smarttop.library.bean.County;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;
import com.smarttop.library.widget.AddressSelector;
import com.smarttop.library.widget.BottomDialog;
import com.smarttop.library.widget.OnAddressSelectedListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class ChildEduActivity extends BaseActivity implements CommonPopupWindow.ViewInterface, View.OnClickListener, AdapterView.OnItemClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_allrank)
    TextView tvAllRank;
    @BindView(R.id.iv_img_bg)
    ImageView iv_bg;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.rb_search_type)
    CheckBox rbSearchType;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;
    @BindView(R.id.iv_more_nice)
    ImageView ivMoreNice;
    @BindView(R.id.iv_allrank)
    ImageView ivAllRank;
    @BindView(R.id.iv_sort)
    ImageView ivSort;

    private CommonPopupWindow sortPopupwindow, synthesisRankPopupwindow;

    private ListView listView;
    private List<CourseSortBean> mCourseList;
    private List<ClassSortBean> mClassList = new ArrayList<>();
    private MyCourseAdapter myCourseAdapter;
    private MyClassAdapter myClassAdapter;
    private MyThirdAdapter myThirdAdapter;
    //    private ArrayList<String> types = new ArrayList<String>();//第一级下拉菜单的数据源
    private List<String> typeThirdList = new ArrayList<>();
    //    public LocationClient mLocationClient = null;
//    public MyLocationListener myListener = new MyLocationListener();
    private String city;
    private double[] locations;
    private String flag;
    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    //    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat;
    private double mCurrentLon;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private int mCurrentDirection = 0;
    private boolean isFirstLoc;
    private HorizontalListViewAdapter horizontalListViewAdapter;
    private TextView tvGradeRAnk;
    private TextView tvStarRAnk;
    private TextView tvPopularityRAnk;
    private TextView tvUpRank;
    private TextView tvDownRank;
    private TextView tvNearRank;
    private TextView tvFarRank;
    private TextView tvSure;
    private TextView tvOrganizationRank;
    private boolean isCourse = false;
    private GalleryAdapter galleryAdapter;
    private String city1;
    private PoiSearch mPoiSearch;
    private BottomDialog dialog;

    private List<ClassSchoolBean.DataBean> mList = new ArrayList<>();


    private List<PoiInfo> dataList = new ArrayList<>();
    private ClassSchoolAdapter mChooseAadpter;
    private ArrayList<String> convenientBannerImg;
    private List<ArtAdBean.DataBean> mAdList;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.lv_course);
        mCourseList = new ArrayList<>();
        mAdList = new ArrayList<>();
        myCourseAdapter = new MyCourseAdapter();
//        myClassAdapter = new MyClassAdapter();


//        myThirdAdapter = new MyThirdAdapter(this, typeThirdList);
//        horizontalListViewAdapter = new HorizontalListViewAdapter(this, types);
        listView.setAdapter(myCourseAdapter);

//        types = (ArrayList) getIntent().getSerializableExtra("types");
        flag = getIntent().getStringExtra("flag");
        city = getIntent().getStringExtra("city");
        city1 = city;
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        isLogin = (boolean) SPUtils.get(this, "isLogin", false);
        locations = new double[]{latitude, longitude};
        tvLocation.setText(city1);
        tvTitle.setText(flag);

        if (flag.equals("家教")) {
            llParent.setVisibility(View.GONE);
            ivMoreNice.setVisibility(View.VISIBLE);
            return;
        } else {

        }
        if (flag.equals("托教")) {
            rbSearchType.setVisibility(View.GONE);
        }
//        initMap();
        myClassAdapter = new MyClassAdapter();
        listView.setAdapter(myClassAdapter);
        mChooseAadpter = new ClassSchoolAdapter(mList, this);
        getOrganizationRank(flag);
        listView.setOnItemClickListener(this);
        rbSearchType.setChecked(false);
        rbSearchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCourse = true;
                    listView.setAdapter(myCourseAdapter);
                    tvSort.setText("定位");
                    tvAllRank.setText("综合排序");
                    getCourseRank(city1);
                } else {
                    isCourse = false;
                    listView.setAdapter(myClassAdapter);
                    getOrganizationRank(flag);
                    tvSort.setText("定位");
                    tvAllRank.setText("综合排序");
                }
            }
        });

        getConvenientBanner();

        dialog = new BottomDialog(this);
        dialog.setOnAddressSelectedListener(this);
        dialog.setDialogDismisListener(this);

        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                ArtAdBean.DataBean dataBean = mAdList.get(position);
                String advertising_type = dataBean.getAdvertising_type();
                int advertising_id = dataBean.getAdvertising_id();
                String advertising_content = dataBean.getAdvertising_content();


                switch (advertising_type) {
                    case "0"://图片加链接
                        startActivity(new Intent(ChildEduActivity.this, AdvertisingActivity.class)
                                .putExtra("id", advertising_id));
                        break;
                    case "1"://课程广告
                        startActivity(new Intent(ChildEduActivity.this, CourseHomePagerActivity.class)
                                .putExtra("course_id", advertising_content + ""));
                        break;
                    case "2"://优惠券广告
                        if (isLogin) {
                            String userPhone = (String) SPUtils.get(ChildEduActivity.this, "user_phone", "");
                            if (TextUtils.isEmpty(userPhone)) {
                                showNoLoginDialog();
                            } else {
                                getDiscountPager(advertising_id, userPhone);
                            }

                        } else {
                            startActivity(new Intent(ChildEduActivity.this, LoginActivity.class));
                            ChildEduActivity.this.finish();
                        }
                        break;
                    case "3"://图文消息广告
                        startActivity(new Intent(ChildEduActivity.this, AdvertisingActivity.class)
                                .putExtra("id", advertising_id)
                                .putExtra("content", advertising_content));
                        break;
                }
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_class_xiaolei;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != sortPopupwindow && sortPopupwindow.isShowing()) {
            sortPopupwindow.dismiss();
        }
        if (null != synthesisRankPopupwindow && synthesisRankPopupwindow.isShowing()) {
            synthesisRankPopupwindow.dismiss();
        }
        iv_bg.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 机构排序
     */
    private void getOrganizationRank(String flag) {

        Log.e("aaa",
                "(ChildEduActivity.java:827)<--flag-->" + flag);
        Log.e("aaa",
                "(ChildEduActivity.java:829)<--city1-->" + city1);
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", flag)
//                .addParams("mechanism_city", "")
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:835)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:841)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            myClassAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                                myClassAdapter.notifyDataSetChanged();
//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }

    /**
     * 课程排名
     */
    private void getCourseRank(String address) {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_RANK)
                .addParams("course_type", flag)
                .addParams("course_province", address)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:141)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:148)" + response);
                        Log.e("aaa",
                                "(ChildEduActivity.java:504) city === " + city1);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            myCourseAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                                myCourseAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    /**
     * 获取到轮播图的广告
     */
    private void getConvenientBanner() {
        mAdList.clear();
        convenientBannerImg = new ArrayList<>();
        OkHttpUtils.post()
                .url(Internet.GET_BANNER_ADVERTISING)
                .addParams("other_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:180)<---->" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:186)<---->" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    ArtAdBean artAdBean = new Gson().fromJson(response, ArtAdBean.class);
                                    mAdList.addAll(artAdBean.getData());
                                    for (int i = 0; i < data.length(); i++) {
                                        String advert_picture = data.getJSONObject(i).getString("advertising_photo");
                                        convenientBannerImg.add(Internet.BASE_URL + advert_picture);
                                    }
                                    setConvenientBanner(convenientBannerImg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void showNoLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(ChildEduActivity.this);

        selfDialog.setMessage("是否绑定手机号?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(ChildEduActivity.this, RegisterPersonActivity.class)
                        .putExtra("flag", "true")
                        .putExtra("type", "0"));
//                finish();
                selfDialog.dismiss();
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

    //优惠券广告获取
    private void getDiscountPager(int coupons_id, String userPhone) {
        String userId = (String) SPUtils.get(ChildEduActivity.this, "userId", "");

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
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:88)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            int result = jsonObject.getInt("result");
                            Toast.makeText(ChildEduActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
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

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.et_search, R.id.iv_search, R.id.tv_sort, R.id.iv_sort, R.id.tv_defaultrank,
            R.id.tv_allrank, R.id.iv_allrank, R.id.iv_img_bg, R.id.tv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                setResult(22, new Intent().putExtra("cityName", city1));
                finish();
                break;
            case R.id.tv_location:
                startActivityForResult(new Intent(this, CurrentCitysActivity.class).putExtra("city", city), 1);
                tvSort.setText("定位");
                tvAllRank.setText("综合排序");
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.et_search:
            case R.id.iv_search://搜索
                startActivity(new Intent(ChildEduActivity.this, SearchActivity.class).putExtra("location", locations));
                tvSort.setText("定位");
                tvAllRank.setText("综合排序");
                break;
            case R.id.tv_sort:
                tvAllRank.setText("综合排序");
//                showCourse(view);
                dialog.show();
                break;
            case R.id.tv_defaultrank://默认排序
                tvSort.setText("定位");
                tvAllRank.setText("综合排序");
//                initData(flag);
                iv_bg.setVisibility(View.GONE);
                if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
                    sortPopupwindow.dismiss();
                }
                if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
                    synthesisRankPopupwindow.dismiss();
                }
                if (isCourse) {
                    getCourseRank(city1);
                } else {
                    getOrganizationRank(flag);
                }
                break;
            case R.id.tv_allrank://综合排序
                tvSort.setText("定位");
                showSynthesisRankPopupwindow(view);
                if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
                    sortPopupwindow.dismiss();
                }
                break;
            case R.id.iv_img_bg:
                int visibility = iv_bg.getVisibility();
                switch (visibility) {
                    case View.GONE:
                        iv_bg.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
                            sortPopupwindow.dismiss();
                        }
                        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
                            synthesisRankPopupwindow.dismiss();
                        }
                        iv_bg.setVisibility(View.GONE);
                        ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
                        break;
                }
                break;
        }
    }

    private void initData(String sort) {

        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", flag)
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:349)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter = new MyClassAdapter();
                        listView.setAdapter(myClassAdapter);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:355)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            myClassAdapter = new MyClassAdapter();
                            listView.setAdapter(myClassAdapter);
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                                myClassAdapter = new MyClassAdapter();
                                listView.setAdapter(myClassAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    //向下弹出
    public void showSynthesisRankPopupwindow(View view) {
        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
            synthesisRankPopupwindow.dismiss();
            iv_bg.setVisibility(View.GONE);
            ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
            return;
        }
        synthesisRankPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popupwindow_synthesis_rank)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        synthesisRankPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        synthesisRankPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        synthesisRankPopupwindow.showAsDropDown(view, 0, 0);
        iv_bg.setVisibility(View.VISIBLE);
        ivAllRank.setImageResource(R.drawable.xiangshangsanjiao);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11) {
            city1 = data.getStringExtra("cityName");
            MyApplication.selectCity = city1;
            tvLocation.setText(city1);
            if (isCourse) {
                getCourseRank(city1);
            } else
                getOrganizationRank(flag);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String course_id = mCourseList.get(position).getCourse_id();
        String school_id = mCourseList.get(position).getSchool_id();
        String schooluid = mCourseList.get(position).getUser_id();
        Intent intent = new Intent(ChildEduActivity.this, CourseHomePagerActivity.class);
        intent.putExtra("school_id", school_id);
        intent.putExtra("course_id", course_id);
        intent.putExtra("schooluid", schooluid);
        startActivity(intent);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popupwindow_class_school:
                MyListView mLv = (MyListView) view.findViewById(R.id.lv_class_school);
                mLv.setAdapter(mChooseAadpter);

                mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String gakuen_id = mList.get(position).getGakuen_id();
                        getClassListByEid(gakuen_id + "");
                        sortPopupwindow.dismiss();
                        iv_bg.setVisibility(View.GONE);
                    }
                });

                break;
            case R.layout.popupwindow_synthesis_rank:
                //等级排序
                tvGradeRAnk = (TextView) view.findViewById(R.id.tv_grade_rank);
                //星级排序
                tvStarRAnk = (TextView) view.findViewById(R.id.tv_star_rank);
                //人气排序
                tvPopularityRAnk = (TextView) view.findViewById(R.id.tv_popularity_rank);
                //价格高到低
                tvUpRank = (TextView) view.findViewById(R.id.tv_up_rank);
                //价格低到高
                tvDownRank = (TextView) view.findViewById(R.id.tv_down_rank);
                //距离近到远
                tvNearRank = (TextView) view.findViewById(R.id.tv_near_rank);
                //距离远到近
                tvFarRank = (TextView) view.findViewById(R.id.tv_far_rank);
                //确定
                tvSure = (TextView) view.findViewById(R.id.tv_sure);

                tvGradeRAnk.setOnClickListener(this);
                tvStarRAnk.setOnClickListener(this);
                tvPopularityRAnk.setOnClickListener(this);
                tvUpRank.setOnClickListener(this);
                tvDownRank.setOnClickListener(this);
                tvNearRank.setOnClickListener(this);
                tvFarRank.setOnClickListener(this);
                tvSure.setOnClickListener(this);
                break;
        }

    }

    private void getClassListByEid(String id) {
        mClassList.clear();
        OkHttpUtils
                .post()
                .url(Internet.GET_CLASS_LSIT_BY_SCHOOL)
                .addParams("gakuen_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:543)<---->" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:549)<---->" + response);

                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(ChildEduActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                            myClassAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                    }.getType()));
                                    myClassAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                                    myClassAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        String pName = province == null ? "" : province.name;
        String cName = city == null ? "" : city.name;
        String coName = county == null ? "" : county.name;

        String s = "";
        if ("北京,天津,上海,重庆".contains(pName)) {
            coName = cName;
            cName = pName;
        }
        s = pName + cName + coName;

        tvSort.setText(coName);

        if (flag.equals("托教")) {
            getSchoolList(pName, cName, coName);
        } else {
            if (isCourse) {
                getCourseRank(s);
            } else {
                getOrganizationRankS(s, flag);
            }

        }

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 根据省市区获取小学
     */
    private void getSchoolList(String pName, String cName, String coName) {
        mList.clear();
        mClassList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("province", pName);
        params.put("city", cName);
        params.put("district", coName);
        OkHttpUtils.post()
                .url(Internet.GET_SCHOOL_BY_ADDRESS)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AddClassSchoolActivity.java:118)<---->" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        mChooseAadpter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AddClassSchoolActivity.java:124)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            mChooseAadpter.notifyDataSetChanged();
                            myClassAdapter.notifyDataSetChanged();
//                            unHadChooseAadpter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    ClassSchoolBean classSchoolBean = new Gson().fromJson(response, ClassSchoolBean.class);
                                    mList.addAll(classSchoolBean.getData());
                                    mChooseAadpter.notifyDataSetChanged();
                                    myClassAdapter.notifyDataSetChanged();
                                    showCourse(tvSort);
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(ChildEduActivity.this, "该地区暂无数据", Toast.LENGTH_SHORT).show();
                                    mChooseAadpter.notifyDataSetChanged();
                                    myClassAdapter.notifyDataSetChanged();
                                }
//                                unHadChooseAadpter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                    }
                });
    }

    private void getOrganizationRankS(String flag, String type) {
        mClassList.clear();
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", type)
                .addParams("mechanism_city", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:873)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:879)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            myClassAdapter.notifyDataSetChanged();
                            myCourseAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                                myClassAdapter.notifyDataSetChanged();
                                myCourseAdapter.notifyDataSetChanged();


//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }

    //向下弹出
    public void showCourse(View view) {
        if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
            sortPopupwindow.dismiss();
            iv_bg.setVisibility(View.GONE);
            return;
        }
        sortPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popupwindow_class_school)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        sortPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        sortPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        sortPopupwindow.showAsDropDown(view, 0, 0);
        iv_bg.setVisibility(View.VISIBLE);
    }

    @Override
    public void dialogclose() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_grade_rank:
                tvGradeRAnk.setTextColor(Color.parseColor("#ffffff"));
                tvGradeRAnk.setBackgroundColor(Color.parseColor("#FF3C4B"));
                tvStarRAnk.setTextColor(Color.parseColor("#666666"));
                tvStarRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvPopularityRAnk.setTextColor(Color.parseColor("#666666"));
                tvPopularityRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvUpRank.setTextColor(Color.parseColor("#666666"));
                tvUpRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvDownRank.setTextColor(Color.parseColor("#666666"));
                tvDownRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvNearRank.setTextColor(Color.parseColor("#666666"));
                tvNearRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvFarRank.setTextColor(Color.parseColor("#666666"));
                tvFarRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvAllRank.setText("等级推荐");
                if (isCourse) {
                    getCourseStarOrGradeRank();
                } else
                    getStarOrGradeRank();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_star_rank:
                tvStarRAnk.setTextColor(Color.parseColor("#ffffff"));
                tvStarRAnk.setBackgroundColor(Color.parseColor("#FF3C4B"));
                tvGradeRAnk.setTextColor(Color.parseColor("#666666"));
                tvGradeRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvPopularityRAnk.setTextColor(Color.parseColor("#666666"));
                tvPopularityRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvUpRank.setTextColor(Color.parseColor("#666666"));
                tvUpRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvDownRank.setTextColor(Color.parseColor("#666666"));
                tvDownRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvNearRank.setTextColor(Color.parseColor("#666666"));
                tvNearRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvFarRank.setTextColor(Color.parseColor("#666666"));
                tvFarRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvAllRank.setText("由高到低");
                if (isCourse) {
                    getCourseStarOrGradeRank();
                } else
                    getStarOrGradeRank();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_popularity_rank:
                tvPopularityRAnk.setTextColor(Color.parseColor("#ffffff"));
                tvPopularityRAnk.setBackgroundColor(Color.parseColor("#FF3C4B"));
                tvGradeRAnk.setTextColor(Color.parseColor("#666666"));
                tvGradeRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvStarRAnk.setTextColor(Color.parseColor("#666666"));
                tvStarRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvUpRank.setTextColor(Color.parseColor("#666666"));
                tvUpRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvDownRank.setTextColor(Color.parseColor("#666666"));
                tvDownRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvNearRank.setTextColor(Color.parseColor("#666666"));
                tvNearRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvFarRank.setTextColor(Color.parseColor("#666666"));
                tvFarRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvAllRank.setText("人气排行");
                if (isCourse) {
                    getCoursePopularityRank();
                } else
                    getPopularityRank();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_up_rank:

                if (isCourse) {
                    tvUpRank.setTextColor(Color.parseColor("#ffffff"));
                    tvUpRank.setBackgroundColor(Color.parseColor("#FF3C4B"));
                    tvGradeRAnk.setTextColor(Color.parseColor("#666666"));
                    tvGradeRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvStarRAnk.setTextColor(Color.parseColor("#666666"));
                    tvStarRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvPopularityRAnk.setTextColor(Color.parseColor("#666666"));
                    tvPopularityRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvDownRank.setTextColor(Color.parseColor("#666666"));
                    tvDownRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvNearRank.setTextColor(Color.parseColor("#666666"));
                    tvNearRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvFarRank.setTextColor(Color.parseColor("#666666"));
                    tvFarRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvAllRank.setText("由高到低");
                    getPriceUpRank();
                } else {
                    Toast.makeText(ChildEduActivity.this, "机构不能进行价格排序", Toast.LENGTH_SHORT).show();
                }
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_down_rank:
                if (isCourse) {
                    tvDownRank.setTextColor(Color.parseColor("#ffffff"));
                    tvDownRank.setBackgroundColor(Color.parseColor("#FF3C4B"));
                    tvGradeRAnk.setTextColor(Color.parseColor("#666666"));
                    tvGradeRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvStarRAnk.setTextColor(Color.parseColor("#666666"));
                    tvStarRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvPopularityRAnk.setTextColor(Color.parseColor("#666666"));
                    tvPopularityRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvUpRank.setTextColor(Color.parseColor("#666666"));
                    tvUpRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvNearRank.setTextColor(Color.parseColor("#666666"));
                    tvNearRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvFarRank.setTextColor(Color.parseColor("#666666"));
                    tvFarRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                    tvAllRank.setText("由低到高");
                    getPriceDownRank();
                } else {
                    Toast.makeText(ChildEduActivity.this, "机构不能进行价格排序", Toast.LENGTH_SHORT).show();
                }
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_near_rank:
                tvNearRank.setTextColor(Color.parseColor("#ffffff"));
                tvNearRank.setBackgroundColor(Color.parseColor("#FF3C4B"));
                tvGradeRAnk.setTextColor(Color.parseColor("#666666"));
                tvGradeRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvStarRAnk.setTextColor(Color.parseColor("#666666"));
                tvStarRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvPopularityRAnk.setTextColor(Color.parseColor("#666666"));
                tvPopularityRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvUpRank.setTextColor(Color.parseColor("#666666"));
                tvUpRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvDownRank.setTextColor(Color.parseColor("#666666"));
                tvDownRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvFarRank.setTextColor(Color.parseColor("#666666"));
                tvFarRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvAllRank.setText("由近到远");
                if (isCourse)
                    getDistenceDesc();
                else
                    getOrganizationDistanceAse();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);

                break;
            case R.id.tv_far_rank:
                tvFarRank.setTextColor(Color.parseColor("#ffffff"));
                tvFarRank.setBackgroundColor(Color.parseColor("#FF3C4B"));
                tvGradeRAnk.setTextColor(Color.parseColor("#666666"));
                tvGradeRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvStarRAnk.setTextColor(Color.parseColor("#666666"));
                tvStarRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvPopularityRAnk.setTextColor(Color.parseColor("#666666"));
                tvPopularityRAnk.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvUpRank.setTextColor(Color.parseColor("#666666"));
                tvUpRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvDownRank.setTextColor(Color.parseColor("#666666"));
                tvDownRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvNearRank.setTextColor(Color.parseColor("#666666"));
                tvNearRank.setBackgroundColor(Color.parseColor("#f0f2f5"));
                tvAllRank.setText("由远到近");
                if (isCourse)
                    getDistenceAsc();
                else
                    getOrganizationDistanceDesc();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_sure:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                break;
//            case R.id.tv_course_rank:
//                synthesisRankPopupwindow.dismiss();
//                iv_bg.setVisibility(View.GONE);
//                getCourseRank();
//                break;
//            case R.id.tv_organization_rank:
//                synthesisRankPopupwindow.dismiss();
//                iv_bg.setVisibility(View.GONE);
//                getOrganizationRank();
//                break;
        }
    }

    /**
     * 课程的等级推荐  星级排行
     */
    private void getCourseStarOrGradeRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_GRADE_RANK)
                .addParams("course_type", flag)
                .addParams("course_address", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:232)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:238)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            myCourseAdapter.notifyDataSetChanged();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            myCourseAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 机构的等级推荐  星级排行
     */
    private void getStarOrGradeRank() {
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.CLASS_GRADE_RANK)
                .addParams("course_type", flag)
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:232)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:238)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myClassAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 课程的人气排行
     */
    private void getCoursePopularityRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_POPULIRATION_RANK)
                .addParams("course_type", flag)
                .addParams("course_address", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:266)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:272)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                myCourseAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
    }

    /**
     * 机构的人气排行
     */

    private void getPopularityRank() {
//        mCourseList.clear();
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.POPULIRATION_RANK)
                .addParams("course_type", flag)
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:266)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:272)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myClassAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 价格排序  由高到低
     */

    private void getPriceUpRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .addParams("course_type", flag)
                .addParams("course_address", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:298)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ChildEduActivity.java:305)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));

//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myCourseAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 价格排序  由低到高
     */

    private void getPriceDownRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .addParams("course_type", flag)
                .addParams("course_address", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:298)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ChildEduActivity.java:305)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                ArrayList<CourseSortBean> list = new ArrayList<CourseSortBean>();
                                list.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                if (list.size() > 0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        mCourseList.add(list.get((list.size() - 1) - i));
                                    }
                                }
//                            myCourseAdapter.setLocations(locations);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myCourseAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 课程距离排序  由远到近
     */

    private void getDistenceDesc() {//由远到近
        mCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", flag)
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:78)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:84)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                if (locations != null)
                                    RankListUtils.rankListsss(mCourseList, new LatLng(locations[0], locations[1]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myCourseAdapter.notifyDataSetChanged();


                    }
                });
    }

    /**
     * 机构的距离排序  由近到远
     */
    private void getOrganizationDistanceAse() {
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", flag)
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:1296)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:1302)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                                if (locations != null)
                                    RankListUtils.rankListss(mClassList, new LatLng(locations[0], locations[1]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myClassAdapter.notifyDataSetChanged();

                    }
                });

    }

    /**
     * 课程的距离排序  由近到远
     */
    private void getDistenceAsc() {//由近到远
        mCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", flag)
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:78)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:84)" + response);

                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                if (locations != null)
                                    RankListUtils.rankList(mCourseList, new LatLng(locations[0], locations[1]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myCourseAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 机构的距离排序  由远到近
     */

    private void getOrganizationDistanceDesc() {
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", flag)
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:233)" + e.getMessage());
                        Toast.makeText(ChildEduActivity.this, "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();

                        myClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChildEduActivity.java:1217)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ChildEduActivity.this, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
//                            myCourseAdapter.setLocations(locations);
                                if (locations != null)
                                    RankListUtils.rankListssss(mClassList, new LatLng(locations[0], locations[1]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myClassAdapter.notifyDataSetChanged();

                    }
                });

    }

    interface ChooseItem {
        public void cbCheck(int position, int parentPosition, boolean cb);
    }

    class MyCourseAdapter extends BaseAdapter {

        int size = 0;

        @Override
        public int getCount() {
            if (mCourseList != null) {
                size = mCourseList.size();
            }
            return size;
        }

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_distance)
            TextView tvDistance;
            @BindView(R.id.tv_price)
            TextView tvPrice;
            @BindView(R.id.popularity)
            TextView popularity;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ChildEduActivity.this, R.layout.item_find_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CourseSortBean courseSortBean = mCourseList.get(position);
//            String photoUrl = "";
            String photoUrl = courseSortBean.getPicture_one();
//            if (courseSortBean.getCourse_photo().contains(",")) {
//                photoUrl = courseSortBean.getCourse_photo().split(",")[0];
//            } else {
//                photoUrl = courseSortBean.getCourse_photo();
//            }
            with(ChildEduActivity.this).load(Internet.BASE_URL + photoUrl).centerCrop().into(holder.ivCourse);
            holder.tvCourse.setText(courseSortBean.getCourse_name());
            holder.tvPrice.setText("¥" + courseSortBean.getPreferential_price());//价格是放的优惠价
            holder.popularity.setText("（" + courseSortBean.getPopularity_num() + "人）");

            double school_wei = Double.parseDouble(courseSortBean.getSchool_wei());
            double school_jing = Double.parseDouble(courseSortBean.getSchool_jing());

            if (locations != null) {
                double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
                distance = (distance / 1000);
                double v = new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
                holder.tvDistance.setText("距离：" + v + "km");
            } else {
                holder.tvDistance.setText("定位失败");
            }
            return convertView;
        }


    }

    class MyClassAdapter extends BaseAdapter {
        private int size = 0;

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_distance)
            TextView tvDistance;
            @BindView(R.id.popularity)
            TextView popularity;
            @BindView(R.id.rl_item)
            RelativeLayout rlItem;
            @BindView(R.id.iv_star1)
            ImageView star1;
            @BindView(R.id.iv_star2)
            ImageView star2;
            @BindView(R.id.iv_star3)
            ImageView star3;
            @BindView(R.id.iv_star4)
            ImageView star4;
            @BindView(R.id.iv_star5)
            ImageView star5;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {
            if (mClassList != null) {
                size = mClassList.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return mClassList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(ChildEduActivity.this, R.layout.item_hf_class_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final ClassSortBean classSortBean = mClassList.get(position);
            with(ChildEduActivity.this)
                    .load(Internet.BASE_URL + classSortBean.getHead_photo())
                    .centerCrop()
                    .error(R.drawable.kecheng)
                    .into(holder.ivCourse);
            holder.tvCourse.setText(classSortBean.getMechanism_name());
            holder.popularity.setText("(" + classSortBean.getUser_renqi() + "人)");
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChildEduActivity.this, ClassActivity.class);
                    intent.putExtra("school_id", classSortBean.getSchool_id());
                    startActivity(intent);
                }
            });
            String user_dengji = classSortBean.getPingjia();
            if (TextUtils.isEmpty(user_dengji)) {

            } else {
                Log.e("aaa", "(MyClassAdapter.java:1636)<---->" + user_dengji);
                switch (user_dengji) {
                    case "1":
                        holder.star1.setImageResource(R.drawable.wujiaoxing);
                        holder.star2.setImageResource(R.drawable.wujiaoxinghuise);
                        holder.star3.setImageResource(R.drawable.wujiaoxinghuise);
                        holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                        holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                        break;
                    case "2":
                        holder.star1.setImageResource(R.drawable.wujiaoxing);
                        holder.star2.setImageResource(R.drawable.wujiaoxing);
                        holder.star3.setImageResource(R.drawable.wujiaoxinghuise);
                        holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                        holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                        break;
                    case "3":
                        holder.star1.setImageResource(R.drawable.wujiaoxing);
                        holder.star2.setImageResource(R.drawable.wujiaoxing);
                        holder.star3.setImageResource(R.drawable.wujiaoxing);
                        holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                        holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                        break;
                    case "4":
                        holder.star1.setImageResource(R.drawable.wujiaoxing);
                        holder.star2.setImageResource(R.drawable.wujiaoxing);
                        holder.star3.setImageResource(R.drawable.wujiaoxing);
                        holder.star4.setImageResource(R.drawable.wujiaoxing);
                        holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                        break;
                    case "5":
                        holder.star1.setImageResource(R.drawable.wujiaoxing);
                        holder.star2.setImageResource(R.drawable.wujiaoxing);
                        holder.star3.setImageResource(R.drawable.wujiaoxing);
                        holder.star4.setImageResource(R.drawable.wujiaoxing);
                        holder.star5.setImageResource(R.drawable.wujiaoxing);
                        break;
                }
            }
            double school_wei = 0.0;
            double school_jing = 0.0;

            if (TextUtils.isEmpty(classSortBean.getSchool_wei()) || "null".equals(classSortBean.getSchool_wei())) {

            } else {
                school_wei = Double.parseDouble(classSortBean.getSchool_wei());//纬度
                school_jing = Double.parseDouble(classSortBean.getSchool_jing());
            }

            if (locations != null) {
                double latitude = locations[0];//纬度
                double longitude = locations[1];//经度
//            DistanceUtil.getDistance(new LatLng(latitude,longitude));
                double distance = DistanceUtil.getDistance(new LatLng(latitude, longitude), new LatLng(school_wei, school_jing));
                distance = distance / 1000;
                double v = new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                holder.tvDistance.setText("距离：" + v + "km");
            } else {
                holder.tvDistance.setText("定位失败");
            }
            return view;
        }


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
                with(ChildEduActivity.this).load(data).into(imageView);
                // ImageLoader.getInstance().displayImage(data, imageView, options);
            } else {
                imageView.setImageResource(R.drawable.meinv);
            }
        }

    }

    class MyThirdAdapter extends BaseAdapter {

        private List<TimeHourBean> mCourseList;
        private int size = 0;
        private ChooseItem chooseItem;

        public MyThirdAdapter(Context context, List<TimeHourBean> mCourseList) {
            this.mCourseList = mCourseList;
        }

        public void setChooseItem(ChooseItem chooseItem) {
            this.chooseItem = chooseItem;
        }

        class ViewHolder {
            @BindView(R.id.cb_time)
            CheckBox tvTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {

            if (mCourseList != null) {
                size = mCourseList.size();
            }
            return mCourseList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCourseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, final ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(ChildEduActivity.this, R.layout.sort_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvTime.setChecked(mCourseList.get(position).isChecked());
            holder.tvTime.setText(mCourseList.get(position).getTime());
            Log.e("aaa",
                    "(TimeAdapter.java:71)" + mCourseList.toString());
            holder.tvTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        Log.e("aaa",
                                "(TimeAdapter.java:79)" + parent.getTag());
                        chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), true);
                        mCourseList.get(position).setChecked(true);
                        notifyDataSetChanged();
                    } else {
                        chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), false);
                        mCourseList.get(position).setChecked(false);
                        notifyDataSetChanged();
                    }
                }

            });
            return view;
        }


    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            backgroundAlpha(1f);
        }
    }


}
