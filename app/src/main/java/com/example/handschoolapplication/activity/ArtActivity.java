package com.example.handschoolapplication.activity;

import android.content.Context;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.handschoolapplication.adapter.HorizontalListViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.RankListUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.example.handschoolapplication.view.HorizontalListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ArtActivity extends BaseActivity implements CommonPopupWindow.ViewInterface, View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.iv_img_bg)
    ImageView iv_bg;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rb_search_type)
    CheckBox rbSearchType;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;

    private CommonPopupWindow sortPopupwindow, synthesisRankPopupwindow;

    private ListView listView;
    private List<CourseSortBean> mCourseList;
    private List<ClassSortBean> mClassList = new ArrayList<>();
    private MyCourseAdapter myCourseAdapter;
    private MyClassAdapter myClassAdapter;
    private MyThirdAdapter myThirdAdapter;
    private ArrayList types = new ArrayList();//第一级下拉菜单的数据源
    private List<TimeHourBean> typeThirdList = new ArrayList<>();
    //    public LocationClient mLocationClient = null;
//    public MyLocationListener myListener = new MyLocationListener();
    private String city;
    private double[] locations;
    private String flag;
    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
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
    private boolean isCourse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (ListView) findViewById(R.id.lv_course);
        mCourseList = new ArrayList<>();
        myCourseAdapter = new MyCourseAdapter();
//        myClassAdapter = new MyClassAdapter();
        myThirdAdapter = new MyThirdAdapter(this, typeThirdList);
        horizontalListViewAdapter = new HorizontalListViewAdapter(this);
        listView.setAdapter(myCourseAdapter);

//        types = (ArrayList) getIntent().getSerializableExtra("types");
        flag = getIntent().getStringExtra("flag");
        city = getIntent().getStringExtra("city");
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        locations = new double[]{latitude, longitude};
        tvLocation.setText(city);
        tvTitle.setText(flag);
        initMap();
        getSecondList(flag);
        getCourseRank();
//        startLocate();
        //获取文体艺术的小类

        baiduMap.setOnMapClickListener(listener);
        listView.setOnItemClickListener(this);

        rbSearchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCourse = true;
                    getCourseRank();
                } else {
                    isCourse = false;
                    getOrganizationRank();
                }
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_art;
    }

    private void getThirdList(String flag) {
        typeThirdList.clear();

        OkHttpUtils.post()
                .url(Internet.GET_THIRD)
                .addParams("two_name", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:140)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:146)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(ArtActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                    String type_three_name = jsonObject1.getString("type_three_name");
                                    TimeHourBean timeHourBean = new TimeHourBean(false, type_three_name);
                                    typeThirdList.add(timeHourBean);
                                }
                                myThirdAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initMap() {
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setScanSpan(0);
        mLocClient.setLocOption(option);
        mLocClient.start();


        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         * @param point 点击的地理坐标
         */
        public void onMapClick(LatLng point) {
            startActivity(new Intent(ArtActivity.this, BaiduMapActivity.class));
            baiduMap.clear();
        }

        /**
         * 地图内 Poi 单击事件回调函数
         * @param poi 点击的 poi 信息
         */

        public boolean onMapPoiClick(MapPoi poi) {
            return false;
        }
    };


    private void initData(String sort) {

        mCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", sort)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:78)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:84)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            myCourseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void getSecondList(final String typeOne) {
        types.clear();
        OkHttpUtils.post()
                .url(Internet.GET_SECOND)
                .addParams("type_one_name", typeOne)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:203)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:209)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                String type_two_name = jsonObject1.getString("type_two_name");
                                types.add(type_two_name);
                            }
                            horizontalListViewAdapter.setList(types);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.et_search, R.id.iv_search, R.id.tv_sort, R.id.iv_sort, R.id.tv_defaultrank,
            R.id.tv_allrank, R.id.iv_allrank, R.id.iv_img_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.et_search:
            case R.id.iv_search://搜索
                startActivity(new Intent(ArtActivity.this, SearchActivity.class));
                break;
            case R.id.tv_sort:

                showCourse(view);
                iv_bg.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_defaultrank://默认排序
                initData(flag);
                break;
            case R.id.tv_allrank://综合排序
                showSynthesisRankPopupwindow(view);
                iv_bg.setVisibility(View.VISIBLE);
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
                        break;
                }
                break;
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popupwindow_sort:
                HorizontalListView hlvSort = (HorizontalListView) view.findViewById(R.id.hlv_sort);
                GridView gv = (GridView) view.findViewById(R.id.gv_third);
                hlvSort.setAdapter(horizontalListViewAdapter);
                gv.setAdapter(myThirdAdapter);
                hlvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(ArtActivity.this, types.get(position).toString(), Toast.LENGTH_SHORT).show();
                        horizontalListViewAdapter.setSelectedPosition(position);
                        horizontalListViewAdapter.notifyDataSetChanged();
//                        sortPopupwindow.dismiss();
//                        iv_bg.setVisibility(View.GONE);
                        getThirdList(types.get(position).toString());
//                        initData(types.get(position).toString());
                    }
                });
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String typeThird = typeThirdList.get(position).getTime();
                        sortPopupwindow.dismiss();
                        iv_bg.setVisibility(View.GONE);
                        Toast.makeText(ArtActivity.this, typeThird, Toast.LENGTH_SHORT).show();
                        //筛选
                        initData(typeThird);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String course_id = mCourseList.get(position).getCourse_id();
        String school_id = mCourseList.get(position).getSchool_id();
        String schooluid = mCourseList.get(position).getUser_id();
        Intent intent = new Intent(ArtActivity.this, CourseHomePagerActivity.class);
        intent.putExtra("school_id", school_id);
        intent.putExtra("course_id", course_id);
        intent.putExtra("schooluid", schooluid);
        startActivity(intent);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            Log.e("aaa",
                    "(MyLocationListenner.java:153)" + "进入定位监听接口");
            // map view 销毁后不在处理新接收的位置
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
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            String addrStr = location.getAddrStr();
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 课程排名
     */
    private void getCourseRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_RANK)
                .addParams("course_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:141)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:148)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
//                            myCourseAdapter.setLocations(locations);
                            listView.setAdapter(myCourseAdapter);
                            myCourseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 机构排序
     */
    private void getOrganizationRank() {
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:239)" + response);
                        if (response.contains("没有信息")) {
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

    /**
     * 机构的等级推荐  星级排行
     */
    private void getStarOrGradeRank() {
        mClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.CLASS_GRADE_RANK)
                .addParams("course_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:232)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:238)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                            }.getType()));
//                            myCourseAdapter.setLocations(locations);
                            myClassAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 课程的等级推荐  星级排行
     */
    private void getCourseStarOrGradeRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_GRADE_RANK)
                .addParams("course_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:232)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:238)" + response);
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
                });
    }

    /**
     * 机构的人气排行
     */

    private void getPopularityRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.POPULIRATION_RANK)
                .addParams("course_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:266)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:272)" + response);
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
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:266)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:272)" + response);
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
                });
    }

    /**
     * 价格排序  由高到低
     */

    private void getPriceUpRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:298)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ArtActivity.java:305)" + response);
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
                });
    }

    /**
     * 价格排序  由低到高
     */

    private void getPriceDownRank() {
        mCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:298)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ArtActivity.java:305)" + response);
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
                            myCourseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getDistenceDesc() {//由远到近
        mCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:78)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:84)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            if (locations != null)
                                RankListUtils.rankListsss(mCourseList, new LatLng(locations[0], locations[1]));
                            myCourseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:239)" + response);
                        if (response.contains("没有信息")) {
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

                    }
                });

    }

    private void getDistenceAsc() {//由近到远
        mCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", flag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:78)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:84)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            if (locations != null)
                                RankListUtils.rankList(mCourseList, new LatLng(locations[0], locations[1]));
                            myCourseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:233)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ArtActivity.java:239)" + response);
                        if (response.contains("没有信息")) {
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

                    }
                });

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
                if (isCourse) {
                    getCourseStarOrGradeRank();
                } else
                    getStarOrGradeRank();
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
                if (isCourse) {
                    getCourseStarOrGradeRank();
                } else
                    getStarOrGradeRank();
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
                if (isCourse) {
                    getCoursePopularityRank();
                } else
                    getPopularityRank();
                break;
            case R.id.tv_up_rank:
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
                if (isCourse) {
                    getPriceUpRank();
                } else {

                }
                break;
            case R.id.tv_down_rank:
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
                if (isCourse) {
                    getPriceDownRank();
                } else {
                }
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
                if (isCourse)
                    getDistenceDesc();
                else
                    getOrganizationDistanceDesc();
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
                if (isCourse)
                    getDistenceAsc();
                else
                    getOrganizationDistanceAse();
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


    class MyCourseAdapter extends BaseAdapter {

        int size = 0;

        @Override
        public int getCount() {
            if (mCourseList != null) {
                size = mCourseList.size();
            }
            return size;
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
                convertView = View.inflate(ArtActivity.this, R.layout.item_find_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CourseSortBean courseSortBean = mCourseList.get(position);
            Glide.with(ArtActivity.this).load(Internet.BASE_URL + courseSortBean.getCourse_photo()).centerCrop().into(holder.ivCourse);
            holder.tvCourse.setText(courseSortBean.getCourse_name());
            holder.tvPrice.setText("¥" + courseSortBean.getPreferential_price());//价格是放的优惠价
            holder.popularity.setText("（" + courseSortBean.getPopularity_num() + "人已报名）");

            double school_wei = Double.parseDouble(courseSortBean.getSchool_wei());
            double school_jing = Double.parseDouble(courseSortBean.getSchool_jing());

            if (locations != null) {
                double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
                holder.tvDistance.setText((int) distance + "m");
            } else {
                holder.tvDistance.setText("定位失败");
            }
            return convertView;
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
    }

    class MyClassAdapter extends BaseAdapter {
        private int size = 0;

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
                view = View.inflate(ArtActivity.this, R.layout.item_hf_class_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final ClassSortBean classSortBean = mClassList.get(position);
            Glide.with(ArtActivity.this)
                    .load(Internet.BASE_URL + classSortBean.getHead_photo())
                    .centerCrop()
                    .error(R.drawable.kecheng)
                    .into(holder.ivCourse);
            holder.tvCourse.setText(classSortBean.getMechanism_name());
            holder.popularity.setText("(" + classSortBean.getUser_renqi() + "人已报名)");
            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ArtActivity.this, ClassActivity.class);
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
            double school_wei = Double.parseDouble(classSortBean.getUser_area());//纬度
            double school_jing = Double.parseDouble(classSortBean.getUser_name());

            if (locations != null) {
                double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
                holder.tvDistance.setText((int) distance + "m");
            } else {
                holder.tvDistance.setText("定位失败");
            }

            return view;
        }

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
    }

    //向下弹出
    public void showCourse(View view) {
        if (sortPopupwindow != null && sortPopupwindow.isShowing()) return;
        sortPopupwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popupwindow_sort)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        sortPopupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        sortPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        sortPopupwindow.showAsDropDown(view, 0, 0);
    }

    //向下弹出
    public void showSynthesisRankPopupwindow(View view) {
        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) return;
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
                view = View.inflate(ArtActivity.this, R.layout.sort_item, null);
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

        class ViewHolder {
            @BindView(R.id.cb_time)
            CheckBox tvTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

    interface ChooseItem {
        public void cbCheck(int position, int parentPosition, boolean cb);
    }
}
