package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
//import com.example.gjj.pingcha;

/**
 * Created by Administrator on 2016/7/27.
 */
public class register_dingwei extends BaseActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient = null;
    private BDLocationListener myListener = new MyBdlocationListener();
    private LocationListener mLocationListener;
    private String myAddress;
    private double mylatitude;
    private double mylongitude;//获取经度坐标;
    private Marker marker;
    private boolean isdingwei = true;
    private Projection projection;
    private ImageView mycenter;
    private android.graphics.Point point2;
    private boolean mypoint = true;
    private Intent it;
    private String province, city, district;//省市区
    private String currentAddress;
    private LatLng latLng;


    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    if (isdingwei) {
                        if ("1".equals(a)) {
//                            point = new LatLng(Double.parseDouble(new SPUtils("user").getString("latitude", mylatitude + "")),
//                                    Double.parseDouble(new SPUtils("user").getString("longitude", mylongitude + "")));
//                            point =

                        } else {
                            isdingwei = false;
                            point = new LatLng(mylatitude, mylongitude);
//                            System.out.println("point.latitude===========================" + point.latitude);
//                            System.out.println("point.longitude===========================" + point.longitude);
                            //设定中心点坐标
//                    LatLng cenpt =  new LatLng(39.12087445341667,117.22135919823971);
                            //定义地图状态
                            MapStatus mMapStatus = new MapStatus.Builder()
                                    .target(point)
                                    .zoom(15)
                                    .build();
                            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                            //改变地图状态
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                            mBaiduMap.setMapStatus(mMapStatusUpdate);

                        }
                        if (mypoint) {
                            try {
                                projection = mBaiduMap.getProjection();
                                point2 = projection.toScreenLocation(point);
                                mypoint = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;

                case 2:
                    if ("1".equals(a)) {
                        Log.e("aaa",
                                "(register_dingwei.java:106)" + latLng.latitude + "===" + latLng.longitude);
//                        new SPUtils("user").putString("latitude", latLng.latitude + "");
//                        new SPUtils("user").putString("longitude", latLng.longitude + "");
                    }
                    it.putExtra("Latitude", latLng.latitude + "");//纬度
                    it.putExtra("Longitude", latLng.longitude + "");//经度
                    it.putExtra("city", currentAddress);
                    it.putExtra("Address", currentAddress);
//                    System.out.println("----------currentAddress------------" + currentAddress);

//                    it.putExtra("Province", province);
//                    it.putExtra("City", city);

                    Log.e("aaa",
                            "(register_dingwei.java:127)<---->" + currentAddress);

                    Log.e("aaa",
                            "(register_dingwei.java:132)<--获取到的经纬度-->"+latLng.latitude+"    经度为==="+latLng.longitude);

                    etAddress.setText(currentAddress);


//                    register_dingwei.this.setResult(RESULT_OK, it);
//                    register_dingwei.this.finish();
                    break;
            }
        }
    };
    private String a;
    private LatLng point;
    private MyLocationData locData;
    private GeoCoder mGeoCoder;
    private TextView etAddress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        it = getIntent();
        a = "1";
        mMapView = (MapView) findViewById(R.id.register_dingwei_map);
        mycenter = (ImageView) findViewById(R.id.iv_register_dingwei_center);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(myListener);
        initLocation();//初始化LocationgClient
        locationClient.start();
        initMap();

        mGeoCoder = GeoCoder.newInstance();
        // 设置查询结果监听者
        mGeoCoder.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);

        etAddress = (TextView) findViewById(R.id.tv_register_dingwei_back);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("定位");
        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.tv_register_dingwei_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != latLng) {
//                    getAddress(latLng.latitude, latLng.longitude);

                    setResult(111, it);

                    finish();
                }
//                ToastUtils.showShortToast("aaaaaaaa");
            }
        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
//                Log.e("aaa",
//                        "(register_dingwei.java:176)<---->"+"地图状态改变时调用的");
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
//                Log.e("aaa",
//                        "(register_dingwei.java:181)<---->"+"地图位置变化时调用的");

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                latLng = mapStatus.target;
                Log.e("aaa",
                        "(register_dingwei.java:187)<---->" + "地图状态位置结束时调用的");

                Log.e("aaa",
                        "(register_dingwei.java:198)<--获取到的定位点的纬度-->" + latLng.latitude + "<-----获取到定位点的经度---->" + latLng.longitude);

                getAddress(latLng.latitude, latLng.longitude);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.register_dingwei;
    }

    private void initMap() {
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public class MyBdlocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

//            Logger.w("", location.getAddrStr() + "8888888888888");
            myAddress = location.getAddrStr();
            province = location.getProvince();
            city = location.getCity();
            district = location.getDistrict();
            Log.e("aaa",
                    "(MyBdlocationListener.java:247)<--省市区的区-->"+district);
            mylatitude = location.getLatitude();//获取纬度坐标
            mylongitude = location.getLongitude();//获取经度坐标

            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

//            MapStatus mMapStatus = new MapStatus.Builder()
//                    .target(new LatLng(mylatitude,mylongitude))
//                    .zoom(18)
//                    .build();
//            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//            //改变地图状态
//            mBaiduMap.setMapStatus(mMapStatusUpdate);

            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.dingweilan);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(new LatLng(mylatitude,mylongitude))
                    .icon(bitmap);

            mBaiduMap.addOverlay(option);
            mBaiduMap.setMyLocationEnabled(false);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationClient.setLocOption(option);
    }

    public void getAddress(double latitude, double lontitude) {

        Log.e("aaa",
                "(register_dingwei.java:236)<---->" + "获取地理位置信息");
        LatLng mLatLng = new LatLng(latitude, lontitude);
        // 反地理编码请求参数对象
        ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
        // 设置请求参数
        mReverseGeoCodeOption.location(mLatLng);
        // 发起反地理编码请求(经纬度->地址信息)
        mGeoCoder.reverseGeoCode(mReverseGeoCodeOption);

    }

    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            Log.e("aaa",
                    "(register_dingwei.java:213)" + result.toString());
//            System.out.println("---------address-------------" + result.getAddress());
            currentAddress = result.getAddress();
            city = result.getAddressDetail().city;
            province = result.getAddressDetail().province;
            String street = result.getAddressDetail().street;
            Log.e("aaa",
                    "(register_dingwei.java:310)<---->" + street);
            Message msg = new Message();
            msg.what = 2;
            mHandler.sendMessage(msg);
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            // 地理编码查询结果回调函数
            Log.e("aaa",
                    "(register_dingwei.java:308)<---->" + "详细地址转经纬度");

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        locationClient.stop();
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}