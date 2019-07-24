package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.SPUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CourseAddressActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.map_view)
    MapView mapView;

    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    private ArrayList<String> address;
    //    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (TextUtils.isEmpty(getIntent().getStringExtra("flag"))){
            tvTitle.setText("课程地址");
        }else {
            tvTitle.setText("机构地址");
        }
        ivMenu.setVisibility(View.GONE);


//        String school_jing = getIntent().getStringExtra("school_jing");
//        String school_wei = getIntent().getStringExtra("school_wei");
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setScanSpan(0);
        mLocClient.setLocOption(option);
        mLocClient.start();

        address = getIntent().getStringArrayListExtra("addresses");
        double latitudeCurrentPos = Double.parseDouble((String) SPUtils.get(this, "latitude", ""));
        double longitudeCurrentPos = Double.parseDouble((String) SPUtils.get(this, "longitude", ""));
        initMap(latitudeCurrentPos,longitudeCurrentPos,true);
        for (int i = 0; i < address.size(); i++) {
            String[] split = address.get(i).split(",");
            double latitude = Double.parseDouble(split[0]);
            double longitude = Double.parseDouble(split[1]);
            initMap(latitude,longitude,false);
        }




//        BaiduMap map = mapView.getMap();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_course_address;
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }

    private void initMap(double wei,double jing,boolean isCurrentPos) {

        if (isCurrentPos){
            LatLng point = new LatLng(wei, jing);
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.dqwz);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option1 = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option1);

            MapStatusUpdate mMapStatus = MapStatusUpdateFactory.newLatLng(point);
            baiduMap.animateMapStatus(mMapStatus);
            mMapStatus = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(mMapStatus);
        }else {
            LatLng point = new LatLng(wei, jing);
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.dingwei_hong_xiao);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option1 = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option1);

            MapStatusUpdate mMapStatus = MapStatusUpdateFactory.newLatLng(point);
            baiduMap.animateMapStatus(mMapStatus);
            mMapStatus = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(mMapStatus);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView!=null)
            mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
