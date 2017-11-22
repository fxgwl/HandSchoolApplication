package com.example.handschoolapplication.fragment;


import android.content.Context;
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
import com.example.handschoolapplication.bean.HomeClassTypeBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.RankListUtils;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.CommonPopupWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.example.handschoolapplication.R.id.tv_grade_rank;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment implements AdapterView.OnItemClickListener, CommonPopupWindow.ViewInterface, View.OnClickListener {

    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_current_location)
    TextView tvCurrentLocation;
    @BindView(R.id.tv_allrank)
    TextView tvAllRank;
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.lv_ff_course)
    ListView listView;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.rb_search_type)
    CheckBox rbSearchType;
    @BindView(R.id.iv_img_bg)
    ImageView iv_bg;
    Unbinder unbinder;
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
    private MyCourseAdapter myCourseAdapter = new MyCourseAdapter();
    private MyClassAdapter myClassAdapter = new MyClassAdapter();

    private BaiduMap map;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private int mCurrentDirection = 0;

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
                    break;
            }
        }
    };
    private boolean isCourse = true;
    private String city;
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
        city = (String) SPUtils.get(getActivity(), "city", "");
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
        initLvData();
        //获取类别
        initClassType();
        listView.setOnItemClickListener(this);
        map.setOnMapClickListener(listener);

//        horizontalListViewAdapter = new HorizontalListViewAdapter(getActivity());
//        myThirdAdapter = new MyThirdAdapter(getActivity(), typeThirdList);

        rbSearchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isCourse = true;
                    initLvData();
                } else {
                    isCourse = false;
                    initLvClassData();
                }
            }
        });
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

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

    private void initMap() {

        map.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_find;
    }


    private void initLvData() {
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_RANK)
                .addParams("course_type", "")
                .addParams("course_address", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:136)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:142)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                                }.getType()));
                                listView.setAdapter(findCourseAdapter);
                                findCourseAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
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

                        if (response.contains("没有信息")) {
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
                                "(FindFragment.java:342)"+"asdsadsadsaddfg");

//                            horizontalListViewAdapter.setList(typeones);
                            Log.e("aaa",
                                "(FindFragment.java:346)size ===== "+typeones.size());
                            galleryAdapter = new GalleryAdapter(getActivity(), typeones);
                        }
                    }
                });
    }

    private void initLvClassData() {
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", "")
                .addParams("mechanism_city", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:136)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:142)" + response);
                        if (response.contains("没有信息")) {
                            Toast.makeText(getActivity(), "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
                                }.getType()));
                                listView.setAdapter(findClassAdapter);
                                findClassAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

    }

    private void initData(String sort) {

        findCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", sort)
                .addParams("course_address", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:393)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:399)" + response);

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
                });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), CourseHomePagerActivity.class);
        intent.putExtra("school_id", findCourseList.get(position).getSchool_id());
        intent.putExtra("course_id", findCourseList.get(position).getCourse_id());
        intent.putExtra("schooluid", findCourseList.get(position).getUser_id());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_search, R.id.iv_search, R.id.tv_sort, R.id.tv_defaultrank, R.id.tv_allrank, R.id.iv_img_bg,R.id.ll_location})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
            case R.id.iv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra("location",locations));
                break;
            case R.id.ll_location:
                startActivityForResult(new Intent(getActivity(), CurrentCitysActivity.class).putExtra("city", city),1);
                break;
            case R.id.tv_sort:
                showCourse(v);
                break;
            case R.id.tv_defaultrank:
                break;
            case R.id.tv_allrank:
                showSynthesisRankPopupwindow(v);
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
                RecyclerView hlvSort = (RecyclerView) view.findViewById(R.id.hlv_sort);
//                GridView gv = (GridView) view.findViewById(R.id.gv_third);
                TagFlowLayout flow = (TagFlowLayout) view.findViewById(R.id.flow_layout);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                hlvSort.setLayoutManager(linearLayoutManager);
                hlvSort.setAdapter(galleryAdapter);
                galleryAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        galleryAdapter.setSelectedPosition(position);
                        galleryAdapter.notifyDataSetChanged();

                        flag = typeones.get(position);
                        types.clear();
                        typeThirdList.clear();
                        types.addAll(typetwolist.get(position));
                        for (int i = 0; i < types.size(); i++) {
                            typeThirdList.add(types.get(i));
                        }
                        tagAdapter.notifyDataChanged();
                    }
                });


                tagAdapter = new TagAdapter<String>((ArrayList<String>) typeThirdList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        TextView textView = (TextView) View.inflate(getActivity(), R.layout.textview, null);
                        textView.setText(s);
                        return textView;
                    }
                };
                flow.setAdapter(tagAdapter);

                flow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        String s = typeThirdList.get(position);
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        tvSort.setText(s);
                        sortPopupwindow.dismiss();
                        iv_bg.setVisibility(View.GONE);
                        //筛选
                        if (isCourse){
                            initData(s);
                        }else {
                            getOrganizationRank(s);
                        }
                        return true;
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

    /**
     * 获取三级列表的数据源
     *
     * @param typeOne
     */

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
                                "(FindFragment.java:203)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:209)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                String type_two_name = jsonObject1.getString("type_two_name");
                                types.add(type_two_name);
                            }
                            galleryAdapter.setList(types);
//                            horizontalListViewAdapter.setList(types);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                tvAllRank.setText("等级推荐");
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
                tvAllRank.setText("人气排行");
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
                tvAllRank.setText("由低到高");
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
                tvAllRank.setText("由高到低");
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
                tvAllRank.setText("由近到远");
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
                tvAllRank.setText("由远到近");
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

    @Override
    public void onResume() {
        mapView.onResume();
        mapView.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        mapView.setVisibility(View.INVISIBLE);
        super.onPause();
    }

    /**
     * 课程排名
     */
    private void getCourseRank() {
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_RANK)
                .addParams("course_type", flag)
                .addParams("course_address", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:141)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:148)" + response);
                        Log.e("aaa",
                                "(FindFragment.java:504) city === " + city);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
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
    private void getOrganizationRank(String type) {
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", type)
                .addParams("mechanism_city", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:233)" + e.getMessage());
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
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.CLASS_GRADE_RANK)
                .addParams("course_type", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:232)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:238)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findClassList.addAll((Collection<? extends ClassSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassSortBean>>() {
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
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_GRADE_RANK)
                .addParams("course_type", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:232)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:238)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
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
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.POPULIRATION_RANK)
                .addParams("course_type", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:266)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:272)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
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
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.COURSE_POPULIRATION_RANK)
                .addParams("course_type", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:266)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:272)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
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
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:298)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(FindFragment.java:305)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
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
        findCourseList.clear();
        OkHttpUtils.post()
                .url(InternetS.PRICE_UP_RANK)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:298)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(FindFragment.java:305)" + response);
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
//                            myCourseAdapter.setLocations(locations);
                            myCourseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getDistenceDesc() {//由远到近
        findCourseList.clear();
        OkHttpUtils.post()
                .url(Internet.COURSELIST)
                .addParams("course_type", "")
                .addParams("course_address", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:78)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:84)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            if (locations != null)
                                RankListUtils.rankList(findCourseList, new LatLng(locations[0], locations[1]));
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
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:233)" + e.getMessage());
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
//                            myCourseAdapter.setLocations(locations);
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
                .addParams("course_address", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:78)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:84)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            findCourseList.addAll((Collection<? extends CourseSortBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<CourseSortBean>>() {
                            }.getType()));
                            if (locations != null) {
                                RankListUtils.rankListsss(findCourseList, new LatLng(locations[0], locations[1]));
                            }
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
        findClassList.clear();
        OkHttpUtils.post()
                .url(InternetS.ORGANIZATION_RANK)
                .addParams("mechanism_type", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(FindFragment.java:233)" + e.getMessage());
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
//                            myCourseAdapter.setLocations(locations);
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

    class MyCourseAdapter extends BaseAdapter {

        int size = 0;


        @Override
        public int getCount() {
            if (findCourseList != null) {
                size = findCourseList.size();
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
                convertView = View.inflate(getActivity(), R.layout.item_find_course_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CourseSortBean courseSortBean = findCourseList.get(position);

            String course_photo = courseSortBean.getCourse_photo();
            String photo = "";
            if (course_photo.contains(",")) {
                String[] split = course_photo.split(",");
                photo = split[0];
            } else {
                photo = course_photo;
            }
            Glide.with(getActivity()).load(Internet.BASE_URL + photo).centerCrop().into(holder.ivCourse);
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
            Glide.with(getActivity())
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
        if (sortPopupwindow != null && sortPopupwindow.isShowing()) {
            sortPopupwindow.dismiss();
            iv_bg.setVisibility(View.GONE);
            return;
        }
        sortPopupwindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popupwindow_sort)
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

    //向下弹出
    public void showSynthesisRankPopupwindow(View view) {
        if (synthesisRankPopupwindow != null && synthesisRankPopupwindow.isShowing()) {
            synthesisRankPopupwindow.dismiss();
            iv_bg.setVisibility(View.GONE);
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
    }

    class MyThirdAdapter extends BaseAdapter {

        private List<TimeHourBean> findCourseList;
        private int size = 0;
        private ChooseItem chooseItem;

        public MyThirdAdapter(Context context, List<TimeHourBean> findCourseList) {
            this.findCourseList = findCourseList;
        }

        public void setChooseItem(ChooseItem chooseItem) {
            this.chooseItem = chooseItem;
        }

        @Override
        public int getCount() {

            if (findCourseList != null) {
                size = findCourseList.size();
            }
            return findCourseList.size();
        }

        @Override
        public Object getItem(int position) {
            return findCourseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, final ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(getActivity(), R.layout.sort_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvTime.setChecked(findCourseList.get(position).isChecked());
            holder.tvTime.setText(findCourseList.get(position).getTime());
            Log.e("aaa",
                    "(TimeAdapter.java:71)" + findCourseList.toString());
            holder.tvTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // TODO Auto-generated method stub
                    if (isChecked) {
                        Log.e("aaa",
                                "(TimeAdapter.java:79)" + parent.getTag());
                        chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), true);
                        findCourseList.get(position).setChecked(true);
                        notifyDataSetChanged();
                    } else {
                        chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), false);
                        findCourseList.get(position).setChecked(false);
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
            map.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(17.0f);
                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            String addrStr = location.getAddrStr();
            city = location.getCity();
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

            Message message1 = new Message();
            message1.what = 2;
            message1.obj = location.getCity();
            mHandler.sendMessage(message1);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
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


}
