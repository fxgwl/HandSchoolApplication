package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.bean.Info;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BaiduMapActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    private MapView mapView;
    private BaiduMap map;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private int mCurrentDirection = 0;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("地址显示");
        mapView = (MapView) findViewById(R.id.map_view);
        map = mapView.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setScanSpan(0);
        mLocClient.setLocOption(option);
        mLocClient.start();
        initMap();
        map.clear();
        if ("1".equals(getIntent().getStringExtra("isCourse"))) {
            ArrayList<ClassSortBean> classList = (ArrayList<ClassSortBean>) getIntent().getSerializableExtra("findCourseList");
            if (null!=classList){
                for (int i = 0; i < classList.size(); i++) {
                    ClassSortBean classSortBean = classList.get(i);
                    Log.e("aaa",
                            "(BaiduMapActivity.java:77)" + classSortBean.toString());
                    addInfosOverlay(new Info(Double.parseDouble(classSortBean.getUser_area()), Double.parseDouble(classSortBean.getUser_name()), null, classSortBean));
                }
            }
        } else {
            ArrayList<CourseSortBean> courseList = (ArrayList<CourseSortBean>) getIntent().getSerializableExtra("findCourseList");
            if (null!=courseList){
                for (int i = 0; i < courseList.size(); i++) {
                    CourseSortBean courseSortBean = courseList.get(i);
                    Log.e("aaa",
                            "(BaiduMapActivity.java:86)" + courseSortBean.toString());
                    addInfosOverlay(new Info(Double.parseDouble(courseSortBean.getSchool_wei()), Double.parseDouble(courseSortBean.getSchool_jing()), courseSortBean, null));
                }
            }
        }
//        map.getUiSettings().setScrollGesturesEnabled(true);
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_baidu_map;
    }

    private void initMap() {

        map.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

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
                builder.target(ll).zoom(18.0f);
                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }


    /**
     * 初始化图层
     */
    public void addInfosOverlay(Info infos) {
        LatLng latLng = null;
        OverlayOptions overlayOptions = null;
        Marker marker = null;
        latLng = new LatLng(infos.getLatitude(), infos.getLongitude());
        // 图标
        overlayOptions = new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dingweis)).zIndex(5);
        marker = (Marker) (map.addOverlay(overlayOptions));
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", infos);
        marker.setExtraInfo(bundle);
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();

                View view = getLayoutInflater().inflate(R.layout.mark, null);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                final Info info = (Info) bundle.getSerializable("info");
                //学堂
                if ("1".equals(getIntent().getStringExtra("isCourse"))) {
                    tv.setText(info.getClassBean().getMechanism_name());

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("aaa",
                                    "(BaiduMapActivity.java:189)" + info.getCourseBean());
                            Intent intent = new Intent(BaiduMapActivity.this, ClassActivity.class);
                            intent.putExtra("school_id", info.getClassBean().getSchool_id());
                            startActivity(intent);

                        }
                    });
                } else {
                    //课程
                    tv.setText(info.getCourseBean().getCourse_name());
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BaiduMapActivity.this, CourseHomePagerActivity.class);
                            intent.putExtra("school_id", info.getCourseBean().getSchool_id());
                            intent.putExtra("course_id", info.getCourseBean().getCourse_id());
                            intent.putExtra("schooluid", info.getCourseBean().getUser_id());
                            startActivity(intent);
                        }
                    });
                }
                LatLng pt = new LatLng(info.getLatitude(), info.getLongitude());
                InfoWindow mInfoWindow = new InfoWindow(view, pt, -120);
                map.showInfoWindow(mInfoWindow);
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView!=null)
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        mapView.setVisibility(View.VISIBLE);

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        mapView.setVisibility(View.INVISIBLE);
        mapView.onDestroy();
    }

}
