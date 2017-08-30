package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import mapapi.overlayutil.PoiOverlay;

public class LocationSelectActivity extends BaseActivity {

    @BindView(R.id.mp_location)
    MapView mpLocation;
    BaiduMap baiduMap;
    @BindView(R.id.et_search)
    EditText etSearch;
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private boolean ifFrist = true;
    private PoiSearch poiSearch;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        baiduMap = mpLocation.getMap();
        baiduMap.setMyLocationEnabled(true);


        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        getLocation();

        poiSearch = PoiSearch.newInstance();//创建POI实例

//        poiSearch.setOnGetPoiSearchResultListener(poiListener);
        poiSearch.setOnGetPoiSearchResultListener(resultListener);


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_location_select;
    }

    /**
     * 获得所在位置经纬度及详细地址
     */
    public void getLocation() {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();

    }

    //POI检索的监听对象
    OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener() {
        //获得POI的检索结果，一般检索数据都是在这里获取
        MyOverLay overlay = null;

        @Override
        public void onGetPoiResult(PoiResult poiResult) {

            //如果搜索到的结果不为空，并且没有错误
            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
                if (overlay == null) {
                    overlay = new MyOverLay(baiduMap, poiSearch);//这传入search对象，因为一般搜索到后，点击时方便发出详细搜索
                }else {
                    overlay.removeFromMap();
                }

                //设置数据,这里只需要一步，
                overlay.setData(poiResult);
                //添加到地图
                overlay.addToMap();
                //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                overlay.zoomToSpan();//计算工具
                //设置标记物的点击监听事件
                baiduMap.setOnMarkerClickListener(overlay);
            } else {

                Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
            }
        }

        //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
        //详细检索一般用于单个地点的搜索，比如搜索一大堆信息后，选择其中一个地点再使用详细检索
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getApplication(), "抱歉，未找到结果",
                        Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                Toast.makeText(getApplication(), poiDetailResult.getName() + ": "
                                + poiDetailResult.getAddress(),
                        Toast.LENGTH_LONG).show();
            }
        }

        //获得POI室内检索结果
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    };


    @OnClick({R.id.tv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                break;
            case R.id.iv_search:

                String string = etSearch.getText().toString().trim();

                PoiCitySearchOption poiCity = new PoiCitySearchOption();
                poiCity.keyword(string).city(city);//这里还能设置显示的个数，默认显示10个
                poiSearch.searchInCity(poiCity);//执行搜索，搜索结束后，在搜索监听对象里面的方法会被回调
                break;
        }
    }


    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();
                city = location.getCity();
                Log.e("aaa", "address:" + address + " latitude:" + latitude
                        + " longitude:" + longitude + "---");
                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
                if (ifFrist) {
                    ifFrist = false;
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder(); //设置缩放中心点；缩放比例；
                    builder.target(ll).zoom(18.0f);


                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.dingwei);
                    baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, bitmap));
//                    //构建MarkerOption，用于在地图上添加Marker
//                    OverlayOptions option = new MarkerOptions()
//                            .position(ll)
//                            .icon(bitmap);
//                    //在地图上添加Marker，并显示
//                    baiduMap.addOverlay(option);

                    MarkerOptions markerOptions = new MarkerOptions();//参数设置类
                    markerOptions.position(ll);//marker坐标位置
                    markerOptions.icon(bitmap);//marker图标
                    markerOptions.draggable(false);//是否可以拖拽
                    markerOptions.anchor(0.5f, 1.0f);//设置 marker覆盖物与位置点的位置关系，默认（0.5f, 1.0f）水平居中，垂直下对齐
                    markerOptions.alpha(1.0f);//marker图标透明度，0~1.0，默认为1.0
                    markerOptions.animateType(MarkerOptions.MarkerAnimateType.none);//marker出现的方式，从天上掉下
                    baiduMap.addOverlay(markerOptions);
                    // 给地图设置状态
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }


            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        poiSearch.destroy();
    }

    public class MyOverLay extends PoiOverlay {
        /**
         * 构造函数
         */
        PoiSearch poiSearch;

        public MyOverLay(BaiduMap baiduMap, PoiSearch poiSearch) {
            super(baiduMap);
            this.poiSearch = poiSearch;
        }

        /**
         * 覆盖物被点击时
         */
        @Override
        public boolean onPoiClick(int i) {
            //获取点击的标记物的数据
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            Log.e("TAG", poiInfo.name + "   " + poiInfo.address + "   " + poiInfo.phoneNum);
            //  发起一个详细检索,要使用uid
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
            return true;
        }
    }
}
