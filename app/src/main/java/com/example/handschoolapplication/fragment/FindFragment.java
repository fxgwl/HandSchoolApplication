package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ArtActivity;
import com.example.handschoolapplication.activity.BaiduMapActivity;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.activity.CourseHomePagerActivity;
import com.example.handschoolapplication.activity.CurrentCitysActivity;
import com.example.handschoolapplication.activity.SearchActivity;
import com.example.handschoolapplication.adapter.FindClassAdapter;
import com.example.handschoolapplication.adapter.FindCourseAdapter;
import com.example.handschoolapplication.adapter.GalleryAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.bean.CurrentCityChangeBean;
import com.example.handschoolapplication.bean.CurrentCityChangeBeanS;
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.RankListUtils;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.TagAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;
import static com.example.handschoolapplication.R.id.no;
import static com.example.handschoolapplication.R.id.tv_grade_rank;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment implements AdapterView.OnItemClickListener, CommonPopupWindow.ViewInterface, View.OnClickListener {

    public MyLocationListenner myListener = new MyLocationListenner();
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_current_location)
    TextView tvCurrentLocation;
    @BindView(R.id.tv_allrank)
    TextView tvAllRank;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.iv_sort)
    ImageView ivSort;
    @BindView(R.id.lv_ff_course)
    ListView listView;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rb_search_type)
    CheckBox rbSearchType;
    @BindView(R.id.iv_img_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_allrank)
    ImageView ivAllRank;
    Unbinder unbinder;
    // 定位相关
    LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位
    private View view;
    private List<CourseSortBean> findCourseList;
    private List<ClassSortBean> findClassList;
    private FindCourseAdapter findCourseAdapter;
    private FindClassAdapter findClassAdapter;
    private CommonPopupWindow sortPopupwindow, synthesisRankPopupwindow;
    //    private HorizontalListViewAdapter horizontalListViewAdapter;
    private ArrayList<String> types = new ArrayList();//第二级下拉菜单的数据源
    private MyThirdAdapter myThirdAdapter;
    private List<String> typeThirdList = new ArrayList<>();
    private BaiduMap map;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private int mCurrentDirection = 0;
    private boolean isCourse = true;
    BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         * @param point 点击的地理坐标
         */
        public void onMapClick(LatLng point) {

            if (isCourse) {
                startActivity(new Intent(getActivity(), BaiduMapActivity.class)
                        .putExtra("findCourseList", (Serializable) findCourseList)
                        .putExtra("isCourse", "0"));
                Log.e("aaa",
                        "(FindFragment.java:236)------------" + findCourseList.toString());
            } else {
                startActivity(new Intent(getActivity(), BaiduMapActivity.class)
                        .putExtra("findCourseList", (Serializable) findClassList)
                        .putExtra("isCourse", "1"));
                Log.e("aaa",
                        "(FindFragment.java:242)=================" + findClassList.toString());
            }
        }

        /**
         * 地图内 Poi 单击事件回调函数
         * @param poi 点击的 poi 信息
         */
        public boolean onMapPoiClick(MapPoi poi) {
            return false;
        }
    };
    private String city = "";//这个参数用来界面交互用
    private String city1 = "";//这个参数用来向接口通信用
    private TextView tvGradeRAnk;
    private TextView tvStarRAnk;
    private TextView tvPopularityRAnk;
    private TextView tvUpRank;
    private TextView tvDownRank;
    private TextView tvNearRank;
    private TextView tvFarRank;
    private TextView tvSure;
    private ArrayList<String> typeones;
    private ArrayList<ArrayList<String>> typetwolist;
    private String flag;
    private double[] locations;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    locations = (double[]) msg.obj;
                    findCourseAdapter.setLocations(locations);
                    findCourseAdapter.notifyDataSetChanged();
                    findClassAdapter.setLocations(locations);
                    findClassAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    String address = (String) msg.obj;
                    tvCurrentLocation.setText(address + "");
                    break;
                case 2:
                    tvLocation.setText((String) msg.obj);
                    Log.e("aaa", "(FindFragment.java:200)<---->" + "aaaa");
                    if (isCourse)
                        initLvData();
                    else initLvClassData();
                    break;
            }
        }
    };
    private TagAdapter<String> tagAdapter;
    private GalleryAdapter galleryAdapter;


    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        map = mapView.getMap();
        findCourseList = new ArrayList<>();
        findClassList = new ArrayList<>();
        findCourseAdapter = new FindCourseAdapter(findCourseList, getActivity());
        findClassAdapter = new FindClassAdapter(getActivity(), findClassList);
        myThirdAdapter = new MyThirdAdapter();
        city = (String) SPUtils.get(getActivity(), "city", "");//获取本地存储的定位城市
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setScanSpan(0);
        mLocClient.setLocOption(option);
        mLocClient.start();
        initMap();
//        initLvData();
        //获取类别
        initClassType();
        listView.setOnItemClickListener(this);
        map.setOnMapClickListener(listener);

        /**
         *
         * 获取初始数据
         *
         * 先判断本地是否存储了定位城市
         *
         * */
        if (TextUtils.isEmpty(city)) {
            //若为空时，先不请求数据  地图定位后再获取  见上面的handler执行
        } else {
            //若不为空，则直接请求数据，定位后不会获取数据
            city1 = city;
            if (isCourse)
                initLvData();
            else initLvClassData();
            tvLocation.setText(city);
        }


//        horizontalListViewAdapter = new HorizontalListViewAdapter(getActivity());
//        myThirdAdapter = new MyThirdAdapter(getActivity(), typeThirdList);

        rbSearchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCourse = true;
                    initLvData();
                    tvSort.setText("类别");
                    tvAllRank.setText("综合排序");
                } else {
                    isCourse = false;
                    initLvClassData();
                    tvSort.setText("类别");
                    tvAllRank.setText("综合排序");
                }
            }
        });
        unbinder = ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        return view;
    }

    private void initMap() {

        map.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        builder.zoom(16);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void initClassType() {
        OkHttpUtils.post()
                .url(Internet.CLASSTYPE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(FindFragment.java:285)<---->" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        typeones = new ArrayList<String>();
                        typetwolist = new ArrayList<ArrayList<String>>();
                        galleryAdapter = new GalleryAdapter(getActivity(), typeones);
                        myThirdAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:180)" + response);

                        if (response.contains("没有信息")) {
                            typeones = new ArrayList<String>();
                            typetwolist = new ArrayList<ArrayList<String>>();
                            galleryAdapter = new GalleryAdapter(getActivity(), typeones);
                            myThirdAdapter.notifyDataSetChanged();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Gson gson = new Gson();
                            HomeClassTypeBean homeClassType = gson.fromJson(response, HomeClassTypeBean.class);
                            typeones = new ArrayList<String>();
                            typetwolist = new ArrayList<ArrayList<String>>();
                            for (int i = 0; i < homeClassType.getData().size(); i++) {
                                typeones.add(homeClassType.getData().get(i).getType_one_name());
                                ArrayList<String> typetwos = new ArrayList<String>();
                                if (null != homeClassType.getData().get(i).getTypeTwoInfo()) {
                                    for (int m = 0; m < homeClassType.getData().get(i).getTypeTwoInfo().size(); m++) {
                                        typetwos.add(homeClassType.getData().get(i).getTypeTwoInfo().get(m).getType_two_name());
                                    }
                                }
                                typetwolist.add(typetwos);
                            }

                            Log.e("aaa",
                                    "(FindFragment.java:346)size ===== " + typeones.size());
                            galleryAdapter = new GalleryAdapter(getActivity(), typeones);

                            if (typetwolist.get(0).size() < 1) {

                            } else {
                                types.addAll(typetwolist.get(0));
                                for (int i = 0; i < types.size(); i++) {
                                    typeThirdList.add(types.get(i));
                                }
                                myThirdAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void initLvData() {
        listView.setAdapter(findCourseAdapter);
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_RANK)
                .addParams("course_type", "")
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:136)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:142)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            findCourseAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                findCourseAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initLvClassData() {
        listView.setAdapter(findClassAdapter);
        findClassList.clear();
        Log.e("aaa", "(FindFragment.java:380)<---->" + city1);
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", "")
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:375)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:381)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                                findClassAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_find;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        mapView.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.unRegisterLocationListener(myListener);
        mLocClient.stop();
        // 关闭定位图层
        if (mapView != null) {
            mapView.onDestroy();
            map.setMyLocationEnabled(false);
            mapView = null;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), CourseHomePagerActivity.class);
        intent.putExtra("school_id", findCourseList.get(position).getSchool_id());
        intent.putExtra("course_id", findCourseList.get(position).getCourse_id());
        intent.putExtra("schooluid", findCourseList.get(position).getUser_id());

        String popularity_num = findCourseList.get(position).getPopularity_num();
        int newpopNum = Integer.parseInt(popularity_num) + 1;
        findCourseList.get(position).setPopularity_num(String.valueOf(newpopNum));
        findCourseAdapter.notifyDataSetChanged();
        startActivity(intent);
    }

    @OnClick({R.id.tv_search, R.id.iv_search, R.id.tv_sort, R.id.tv_defaultrank, R.id.tv_allrank, R.id.iv_img_bg, R.id.ll_location})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
            case R.id.iv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra("location", locations));
                break;
            case R.id.ll_location:
                startActivityForResult(new Intent(getActivity(), CurrentCitysActivity.class).putExtra("city", city), 1);
                break;
            case R.id.tv_sort:
            case R.id.iv_sort://类别显示
                tvAllRank.setText("综合排序");
                if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
                    synthesisRankPopupwindow.dismiss();
                }
                showCourse(v);
                break;
            case R.id.tv_defaultrank://默认排序
                tvSort.setText("类别");
                tvAllRank.setText("综合排序");
                if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
                    sortPopupwindow.dismiss();
                }
                if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
                    synthesisRankPopupwindow.dismiss();
                }
                if (isCourse) {
                    initLvData();
                } else {
                    initLvClassData();
                }
                iv_bg.setVisibility(View.GONE);
                break;
            case R.id.tv_allrank://综合排序
                tvSort.setText("类别");
                showSynthesisRankPopupwindow(v);
                if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
                    sortPopupwindow.dismiss();
                }

                break;
            case R.id.iv_img_bg:
                int visibility = iv_bg.getVisibility();
                switch (visibility) {
                    case View.GONE:
                        iv_bg.setVisibility(View.VISIBLE);
                        ivSort.setImageResource(R.drawable.xiangshangsanjiao);
                        break;
                    case View.VISIBLE:
                        if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
                            sortPopupwindow.dismiss();
                        }
                        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
                            synthesisRankPopupwindow.dismiss();
                        }
                        iv_bg.setVisibility(View.GONE);
                        ivSort.setImageResource(R.drawable.xiangxiasanjiao);
                        ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
                        break;
                }
                break;
        }
    }

    //向下弹出
    public void showCourse(View view) {
        if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
            sortPopupwindow.dismiss();
            iv_bg.setVisibility(View.GONE);
            ivSort.setImageResource(R.drawable.xiangxiasanjiao);
            return;
        }
        sortPopupwindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popupwindow_sort_s)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        sortPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        sortPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        sortPopupwindow.showAsDropDown(view, 0, 0);
        iv_bg.setVisibility(View.VISIBLE);
        ivSort.setImageResource(R.drawable.xiangshangsanjiao);
    }

    //向下弹出
    public void showSynthesisRankPopupwindow(View view) {
        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
            synthesisRankPopupwindow.dismiss();
            iv_bg.setVisibility(View.GONE);
            ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
            return;
        }
        synthesisRankPopupwindow = new CommonPopupWindow.Builder(getActivity())
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

    /**
     * 这个方法是下拉列表的实例化内容
     *
     * @param view
     * @param layoutResId
     */
    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popupwindow_sort_s:
                final RecyclerView hlvSort = (RecyclerView) view.findViewById(R.id.hlv_sort);
                GridView gvThirdType = (GridView) view.findViewById(R.id.gv_third_type);
                ImageView ivLast = (ImageView) view.findViewById(R.id.iv_last);
                ImageView ivNext = (ImageView) view.findViewById(R.id.iv_next);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                hlvSort.setLayoutManager(linearLayoutManager);
                hlvSort.setAdapter(galleryAdapter);


                gvThirdType.setAdapter(myThirdAdapter);

                ivLast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (galleryAdapter.getItemCount() > 0)
                            hlvSort.smoothScrollToPosition(0);
                    }
                });
                ivNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (galleryAdapter.getItemCount() > 0)
                            hlvSort.smoothScrollToPosition(galleryAdapter.getItemCount() - 1);
                    }
                });
                galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        galleryAdapter.setSelectedPosition(position);
                        galleryAdapter.notifyDataSetChanged();

                        flag = typeones.get(position);
                        types.clear();
                        typeThirdList.clear();
                        if (typetwolist.get(position).size() < 1) {
                            tvSort.setText(flag);
                            //筛选
                            if (isCourse) {
                                initData(flag);
                            } else {
                                getOrganizationRank(flag);
                            }
                            sortPopupwindow.dismiss();
                            iv_bg.setVisibility(View.GONE);
                            ivSort.setImageResource(R.drawable.xiangxiasanjiao);
                        } else {
                            types.addAll(typetwolist.get(position));
                            for (int i = 0; i < types.size(); i++) {
                                typeThirdList.add(types.get(i));
                            }
                            myThirdAdapter.notifyDataSetChanged();
                        }
                    }
                });

                gvThirdType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s = typeThirdList.get(position);
                        tvSort.setText(s);
                        sortPopupwindow.dismiss();
                        iv_bg.setVisibility(View.GONE);
                        ivSort.setImageResource(R.drawable.xiangxiasanjiao);
                        //筛选
                        if (isCourse) {
                            initData(s);
                        } else {
                            getOrganizationRank(s);
                        }
                    }
                });

                break;
            case R.layout.popupwindow_synthesis_rank:
                //等级排序
                tvGradeRAnk = (TextView) view.findViewById(tv_grade_rank);
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

    private void initData(String sort) {

        findCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", sort)
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:393)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:399)" + response);

                        if (response.contains("没有信息")) {
                            Toast.makeText(activity, "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        findCourseAdapter.notifyDataSetChanged();

                    }
                });
    }

    /**
     * 机构排序
     */
    private void getOrganizationRank(String type) {
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", type)
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:233)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:239)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findClassAdapter.notifyDataSetChanged();

                    }
                });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("aaa",
                "(FindFragment.java:844)" + "进入onhidden       "+hidden);
        if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
            sortPopupwindow.dismiss();
        }
        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
            synthesisRankPopupwindow.dismiss();
        }
        iv_bg.setVisibility(View.GONE);

        if (hidden){
            if (isCourse)
                initLvData();
            else initLvClassData();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11) {
            city1 = data.getStringExtra("cityName");
            tvLocation.setText(city1);
            if (isCourse) {
                Log.e("aaa", "(FindFragment.java:988)<---->" + "bbbbb");
                initLvData();
            } else {
                initLvClassData();
            }
            CurrentCityChangeBeanS currentCityChangeBeans = new CurrentCityChangeBeanS();
            currentCityChangeBeans.setCurrentCity(city1);
            EventBus.getDefault().post(currentCityChangeBeans);
        }
    }


    @Override
    public void onPause() {
        Log.e("aaa",
                "(FindFragment.java:828)" + "进入onpause方法");
        mapView.onPause();
        mapView.setVisibility(View.INVISIBLE);
        if (null != sortPopupwindow && sortPopupwindow.isShowing()) {
            sortPopupwindow.dismiss();
        }
        if (null != synthesisRankPopupwindow && synthesisRankPopupwindow.isShowing()) {
            synthesisRankPopupwindow.dismiss();
        }
        iv_bg.setVisibility(View.GONE);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CurrentCityChangeBean event) {
        Log.e("aaa", "(FindFragment.java:1398)<---->" + event.getCurrentCity());
        city = event.getCurrentCity();
        city1 = city;
        tvLocation.setText(city1);
        if (isCourse) {
            Log.e("aaa", "(FindFragment.java:988)<---->" + "bbbbb");
            initLvData();
        } else {
            initLvClassData();
        }

    }

    interface ChooseItem {
        void cbCheck(int position, int parentPosition, boolean cb);
    }

    class MyCourseAdapter extends BaseAdapter {

        int size = 0;


        @Override
        public int getCount() {
            if (findCourseList != null) {
                size = findCourseList.size();
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
                convertView = View.inflate(getActivity(), R.layout.item_find_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CourseSortBean courseSortBean = findCourseList.get(position);

            String picture_one = courseSortBean.getPicture_one();

            with(getActivity()).load(Internet.BASE_URL + picture_one).centerCrop().into(holder.ivCourse);
            holder.tvCourse.setText(courseSortBean.getCourse_name());
            holder.tvPrice.setText("¥" + courseSortBean.getPreferential_price());//价格是放的优惠价
            holder.popularity.setText("（" + courseSortBean.getPopularity_num() + "人已报名）");

            double school_wei = Double.parseDouble(courseSortBean.getSchool_wei());
            double school_jing = Double.parseDouble(courseSortBean.getSchool_jing());

            if (locations != null) {
                double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
                distance = (distance / 1000);
                BigDecimal bigDecimal = new BigDecimal(distance).setScale(2);
                String string = bigDecimal.toString();

                Log.e("aaa",
                        "(MyCourseAdapter.java:1246)  ==换成千米的距离===" + distance + "字符类型的数据 ==" + string);
                holder.tvDistance.setText("距离:" + (int) distance + "km");
            } else {
                holder.tvDistance.setText("定位失败");
            }
            return convertView;
        }


    }    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //等级排序
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
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
                break;
            //星级排序  没有用到
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
                if (isCourse) {
                    getCourseStarOrGradeRank();
                } else
                    getStarOrGradeRank();
                break;
//            synthesisRankPopupwindow.dismiss();
//            iv_bg.setVisibility(View.GONE);
//            ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
            //人气排序
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
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
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
                    Toast.makeText(getActivity(), "机构不能进行价格排序", Toast.LENGTH_SHORT).show();
                }
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
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
                    Toast.makeText(getActivity(), "机构不能进行价格排序", Toast.LENGTH_SHORT).show();
                }
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
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
                    getDistenceAsc();
                else
                    getOrganizationDistanceDesc();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
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
                    getDistenceDesc();
                else
                    getOrganizationDistanceAse();
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
                break;
            case R.id.tv_sure:
                synthesisRankPopupwindow.dismiss();
                iv_bg.setVisibility(View.GONE);
                ivAllRank.setImageResource(R.drawable.xiangxiasanjiao);
                break;
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
            if (findClassList != null) {
                size = findClassList.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return findClassList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_hf_class_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final ClassSortBean classSortBean = findClassList.get(position);
            with(getActivity())
                    .load(Internet.BASE_URL + classSortBean.getHead_photo())
                    .centerCrop()
                    .error(R.drawable.kecheng)
                    .into(holder.ivCourse);
            holder.tvCourse.setText(classSortBean.getMechanism_name());
            holder.popularity.setText("(" + classSortBean.getUser_renqi() + "人已报名)");
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ClassActivity.class);
                    intent.putExtra("school_id", classSortBean.getSchool_id());
                    startActivity(intent);
                }
            });
            String user_dengji = classSortBean.getUser_dengji();
            if (TextUtils.isEmpty(user_dengji)) {

            } else {

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
            double school_wei = Double.parseDouble(classSortBean.getSchool_wei());//纬度
            double school_jing = Double.parseDouble(classSortBean.getSchool_jing());

            if (locations != null) {
                double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
                holder.tvDistance.setText((int) distance + "m");
            } else {
                holder.tvDistance.setText("定位失败");
            }

            return view;
        }


    }

    class MyThirdAdapter extends BaseAdapter {

        private int size = 0;

        class ViewHolder {
            @BindView(R.id.tv_third_type)
            TextView tvThirdType;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {

            if (typeThirdList != null) {
                size = typeThirdList.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return typeThirdList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, final ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_third_type_gv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvThirdType.setText(typeThirdList.get(position));

            return view;
        }


    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.e("aaa",
                    "(MyLocationListenner.java:153)" + "进入定位监听接口");
            // mapview 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            map.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(16.0f);
                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            String addrStr = location.getAddrStr();

            Log.e("aaa",
                    "(MyLocationListenner.java:146)" + addrStr);
            Message msg = new Message();
            msg.what = 1;
            msg.obj = addrStr;
            mHandler.sendMessage(msg);

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Log.e("aaa",
                    "(MyLocationListenner.java:269)" + latitude + "    " + "经度" + longitude);

            double[] locations = new double[]{latitude, longitude};

            Message message = new Message();
            message.what = 0;
            message.obj = locations;
            mHandler.sendMessage(message);

            if (TextUtils.isEmpty(city1)) {
                city = location.getCity();
                city1 = location.getCity();
                Message message1 = new Message();
                message1.what = 2;
                message1.obj = location.getCity();
                mHandler.sendMessage(message1);
            } else {

            }


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }




    /**
     * 机构的等级推荐  星级排行
     */
    private void getStarOrGradeRank() {
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.CLASS_GRADE_RANK)
                .addParams("course_type", "")
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:232)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:238)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findClassAdapter.notifyDataSetChanged();
                    }
                });
    }


    /**
     * 课程的等级推荐  星级排行
     */
    private void getCourseStarOrGradeRank() {
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_GRADE_RANK)
                .addParams("course_type", "")
                .addParams("course_address", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:232)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:238)" + response);
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                            findCourseAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                findCourseAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    /**
     * 机构的人气排行
     */

    private void getPopularityRank() {
        Log.e("aaa", "(FindFragment.java:1488)<---->" + "机构的人气排行");
//        findCourseList.clear();
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.POPULIRATION_RANK)
                .addParams("course_type", "")
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:266)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:272)-------" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findClassAdapter.notifyDataSetChanged();
                    }
                });
    }


    /**
     * 课程的人气排行
     */
    private void getCoursePopularityRank() {
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_POPULIRATION_RANK)
                .addParams("course_type", "")
                .addParams("course_address", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:266)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:272)----------" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findCourseAdapter.notifyDataSetChanged();

                    }
                });
    }


    /**
     * 价格排序  由高到低
     */

    private void getPriceUpRank() {
        findCourseList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("course_address", city1);
        params.put("course_type", "");
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:298)" + e.getMessage());
                        findCourseAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(FindFragment.java:305)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findCourseAdapter.notifyDataSetChanged();

                    }
                });
    }


    /**
     * 价格排序  由低到高
     */

    private void getPriceDownRank() {
        findCourseList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("course_address", city1);
        params.put("course_type", "");
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:298)" + e.getMessage());
                        findCourseAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(FindFragment.java:305)" + response);
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                ArrayList<CourseSortBean> list = new ArrayList<CourseSortBean>();
                                list.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                if (list.size() > 0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        findCourseList.add(list.get((list.size() - 1) - i));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findCourseAdapter.notifyDataSetChanged();
                    }
                });

    }


    private void getDistenceDesc() {//由远到近
        findCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", "")
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:78)" + e.getMessage());
                        findCourseAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:84)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                if (locations != null)
                                    RankListUtils.rankListsss(findCourseList, new LatLng(locations[0], locations[1]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findCourseAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 机构的距离排序  由远到近
     */

    private void getOrganizationDistanceDesc() {
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", "")
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:233)" + e.getMessage());
                        findClassAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:239)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "该模块尚未接入商家，暂无数据", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                                if (locations != null)
                                    RankListUtils.rankListss(findClassList, new LatLng(locations[0], locations[1]));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        findClassAdapter.notifyDataSetChanged();

                    }
                });

    }

    private void getDistenceAsc() {//由近到远
        findCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", "")
                .addParams("course_province", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:78)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:84)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                if (locations != null) {
                                    RankListUtils.rankList(findCourseList, new LatLng(locations[0], locations[1]));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        findCourseAdapter.notifyDataSetChanged();

                    }
                });
    }

    /**
     * 机构的距离排序  由远到近
     */
    private void getOrganizationDistanceAse() {
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", "")
                .addParams("mechanism_city", city1)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:233)" + e.getMessage());
                        Toast.makeText(getActivity(), "获取数据失败，请检查网络", Toast.LENGTH_SHORT).show();
                        findClassAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:239)" + response);
                        if (response.contains("没有信息")) {
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));

                                if (locations != null) {
                                    RankListUtils.rankListssss(findClassList, new LatLng(locations[0], locations[1]));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        findClassAdapter.notifyDataSetChanged();


                    }
                });

    }
}
