package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationActivity extends BaseActivity implements OnGetPoiSearchResultListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.lv_location)
    ListView lvLocation;
    @BindView(R.id.et_search)
    EditText etSearch;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private int locType;
    private float radius;
    private String addrStr;
    private float direction;
    private String province;
    private String city;
    private String district;
    private PoiSearch mPoiSearch;

    private List<PoiInfo> dataList = new ArrayList();
    private Marker mCurrentMarker;
    private double longitude;
    private double latitude;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                etSearch.setText(currentAddress+"");
        }
    };
    private MyAdapter mAdapter;
    private LatLng latLng;
    private GeoCoder geoCoder;
    private String province1 = "天津市";
    private String currentAddress;
    private boolean isFirst;
    private String currentCity;
    private String currentProvince;
    private String currentStreet;
    private LatLng currentLocation;
    private String currentDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        province1 = (String) SPUtils.get(this, "province", "");

        mAdapter = new MyAdapter();
        lvLocation.setAdapter(mAdapter);

        initMapView();

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

                currentLocation = latLng;
                getAddress(latLng.latitude, latLng.longitude);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Looper.prepare();
                        searchNeayBy(latLng.latitude, latLng.longitude);
                        Looper.loop();
                    }
                }).start();
            }
        });

        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);

        // POI初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String address = dataList.get(position).address;
                // TODO: 2018/6/15
                currentLocation = dataList.get(position).location;
                initMap(currentLocation.latitude,currentLocation.longitude);
                getAddress(currentLocation.latitude,currentLocation.longitude);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Looper.prepare();
                        searchNeayBy(currentLocation.latitude,currentLocation.longitude);
                        Looper.loop();
                    }
                }).start();

            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    //先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    //其次再做相应操作
                    String inputContent = etSearch.getText().toString();
                    if (TextUtils.isEmpty(inputContent)) {
                        Toast.makeText(LocationActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        //做相应的操作
                        mPoiSearch.searchInCity(new PoiCitySearchOption()
                                .keyword(inputContent));
                    }
                }
                return false;
            }
        });

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
        geoCoder.reverseGeoCode(mReverseGeoCodeOption);

    }

    //初始化地图相关
    private void initMapView() {
        mBaiduMap = mapView.getMap();
        mBaiduMap.clear();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));   // 设置级别

        // 定位初始化
        mLocationClient = new LocationClient(this); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener);// 注册定位监听接口

        /**
         * 设置定位参数
         */
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
//      option.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
        option.setOpenGps(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        mLocationClient.setLocOption(option);
        mLocationClient.start(); // 调用此方法开始定位
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_location;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                commit();
                break;
        }
    }

    private void commit() {

        String trim = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            Toast.makeText(this, "请先选择定位点", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent it = new Intent();
        it.putExtra("Latitude", currentLocation.latitude + "");//纬度
        it.putExtra("Longitude", currentLocation.longitude + "");//经度
        it.putExtra("city", currentAddress);
        it.putExtra("Address", currentAddress);
        setResult(111, it);
        finish();
    }

    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            Log.e("aaa",
                    "(register_dingwei.java:213)" + result.toString());
//            System.out.println("---------address-------------" + result.getAddress());
            currentAddress = result.getAddress();
            currentCity = result.getAddressDetail().city;
            currentProvince = result.getAddressDetail().province;
            currentDistrict = result.getAddressDetail().district;
            currentStreet = result.getAddressDetail().street;
            mHandler.sendEmptyMessage(1);

            Log.e("aaa",
                    "(LocationActivity.java:283)<---->"+"获取到的当前位置信息 == "+currentAddress);
            Log.e("aaa",
                    "(LocationActivity.java:285)<---->"+"获取到的当前位置城市 == "+ currentCity);
            Log.e("aaa",
                    "(LocationActivity.java:287)<---->"+"获取当前位置省份 == "+ currentProvince);
            Log.e("aaa",
                    "(LocationActivity.java:289)<---->"+"获取当前位置街道信息 == "+ currentStreet);
            Log.e("aaa",
                    "(LocationActivity.java:306)<---->"+"获取当前位置的区 == "+district);
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            // 地理编码查询结果回调函数
            Log.e("aaa",
                    "(register_dingwei.java:308)<---->" + "详细地址转经纬度");

        }
    };

    private void initMap(double latitude,double longitude) {

        LatLng cenpt = new LatLng(latitude,longitude); //设定中心点坐标

        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(18)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(LocationActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
            Log.e("aaa",
                    "(LocationActivity.java:245)<---->"+"dadsadsadsadasdsadsadsadasdsadsadsad");
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            Log.e("aaa",
                    "(LocationActivity.java:249)<---->"+"dadsadasdsa");
            //mBaiduMap.clear();
            if (result != null) {
                if (!isFirst){
                    dataList.clear();
                    isFirst = false;
                }
                if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                    Log.e("aaa",
                            "(LocationActivity.java:256)<---->"+"fdsfdsfdsfsdf");
                    String address = result.getAllPoi().get(0).address;
                    String name = result.getAllPoi().get(0).name;
                    String city = result.getAllPoi().get(0).city;
                    Log.e("aaa",
                            "(LocationActivity.java:293)<---->"+city);
                    Log.e("aaa",
                            "(LocationActivity.java:295)<---->"+name);
                    dataList.addAll(result.getAllPoi());
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mapView == null) {
                return;
            }

            locType = location.getLocType();

            longitude = location.getLongitude();
            latitude = location.getLatitude();
            if (location.hasRadius()) {// 判断是否有定位精度半径
                radius = location.getRadius();
            }

            if (locType == BDLocation.TypeNetWorkLocation) {
                addrStr = location.getAddrStr();// 获取反地理编码(文字描述的地址)
            }

            direction = location.getDirection();// 获取手机方向，【0~360°】,手机上面正面朝北为0°
            province = location.getProvince();// 省份
            city = location.getCity();// 城市
            district = location.getDistrict();// 区县

            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

            //将当前位置加入List里面
            PoiInfo info = new PoiInfo();
            info.address = location.getAddrStr();
            info.city = location.getCity();
            info.location = ll;
            info.name = location.getAddrStr();
            dataList.add(info);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
            Log.e("mybaidumap", "province是：" + province + " city是" + city + " 区县是: " + district);


            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

//            //画标志
            CoordinateConverter converter = new CoordinateConverter();
            converter.coord(ll);
            converter.from(CoordinateConverter.CoordType.COMMON);
            LatLng convertLatLng = converter.convert();

//            OverlayOptions ooA = new MarkerOptions().position(ll);
//            mCurrentMarker = (Marker) mBaiduMap.addOverlay(ooA);


            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
            mBaiduMap.animateMapStatus(u);

            //画当前定位标志
            MapStatusUpdate uc = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(uc);
            isFirst = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mapView.showZoomControls(false);
                }
            });
//            poi 搜索周边
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Looper.prepare();
                    searchNeayBy(latitude, longitude);
                    Looper.loop();
                }
            }).start();


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void searchNeayBy(double latitude, double longitude) {
        if (isFirst){
            isFirst = false;
        }else {
            dataList.clear();
        }
        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
        poiNearbySearchOption.keyword("公司");
        poiNearbySearchOption.location(new LatLng(latitude, longitude));
        poiNearbySearchOption.radius(500);  // 检索半径，单位是米
        poiNearbySearchOption.pageCapacity(10);  // 默认每页10条
        mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
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
                convertView = View.inflate(LocationActivity.this, R.layout.item_location_lv, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvLocation.setText(dataList.get(position).address);
            return convertView;

        }

        class ViewHolder {
            @BindView(R.id.tv_location)
            TextView tvLocation;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (geoCoder != null) geoCoder.destroy();
        if (mapView!=null)mapView.onDestroy();
        if (mBaiduMap!=null)mBaiduMap.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView!=null){
            mapView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView!=null)mapView.onResume();
    }

}
