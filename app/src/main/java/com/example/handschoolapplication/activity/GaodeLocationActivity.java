package com.example.handschoolapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.amap.api.services.geocoder.StreetNumber;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ChoutiRecyclerViewAdapter;
import com.example.handschoolapplication.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GaodeLocationActivity extends BaseActivity implements LocationSource, AMapLocationListener, AdapterView.OnItemClickListener, PoiSearch.OnPoiSearchListener, View.OnClickListener, AMap.OnMapLoadedListener {


    ProgressDialog progDialog;
    private MapView mapView;
    private AMap aMap;
    private LatLng point;
    private Marker marker;
    private List<PoiItem> pois = new ArrayList<>();
    private ChoutiRecyclerViewAdapter adapter;
    private ListView lvPoi;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private EditText etSearch;
    private String keyWord;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    private PoiItem poiItem;//临时的缓存的点击周边
    private TextView tvTitle;
    private EditText tvCity;
    private boolean isFirst = true;
    private boolean canReplace = true;
    private Double weidu;
    private Double jingdu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();

        mapView = (MapView) findViewById(R.id.mapview);
        lvPoi = (ListView) findViewById(R.id.lv_search_poi);
        etSearch = (EditText) findViewById(R.id.et_search);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCity = (EditText) findViewById(R.id.tv_city);

        RelativeLayout rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        RelativeLayout ivMenu = (RelativeLayout) findViewById(R.id.iv_menu);


//        if (!TextUtils.isEmpty(getIntent().getStringExtra("city"))) {
//            tvCity.setText(getIntent().getStringExtra("city"));
//        }

        rlBack.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        tvTitle.setText("定位");
        initLocation();//初始化地位
        lvPoi.setOnItemClickListener(this);
        adapter = new ChoutiRecyclerViewAdapter(pois, GaodeLocationActivity.this);
        lvPoi.setAdapter(adapter);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    keyWord = etSearch.getText().toString().trim();
                    if ("".equals(keyWord)) {
//                        ToastUtil.show(PoiKeywordSearchActivity.this, "请输入搜索关键字");
                        Toast.makeText(GaodeLocationActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        // 隐藏软键盘
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                        doSearchQuery();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_gaode_location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /***
     * 初始化定位
     */
    private void initLocation() {
        Log.e("aaa", "(GaodeLocationActivity.java:167)<---->" + "初始化定位");
        if (aMap != null) {
            Log.e("aaa", "(GaodeLocationActivity.java:167)<---->" + "当aMap不为空   初始化定位");
            MyLocationStyle locationStyle = new MyLocationStyle();
            locationStyle.myLocationIcon(null);
            locationStyle.strokeColor(Color.argb(0, 0, 0, 0));
            locationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
            locationStyle.strokeWidth(1.0f);
            aMap.setMyLocationStyle(locationStyle);
            aMap.setLocationSource(this);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16f));
            aMap.setOnMapLoadedListener(this);
            //监听地图发生变化之后
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {

                    //发生变化时获取到经纬度传递逆地理编码获取周边数据
                    regeocodeSearch(cameraPosition.target.latitude, cameraPosition.target.longitude, 2000);
                    point = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    marker.remove();//将上一次描绘的mark清除

                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    //地图发生变化之后描绘mark
                    marker = aMap.addMarker(new MarkerOptions()
                            .position(point)
                            .title("")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.dingwei_xiao)));
                }
            });
        } else {

        }

    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        String city = "";
        if (!TextUtils.isEmpty(tvCity.getText().toString().trim()))
            city = tvCity.getText().toString().trim();
        Log.e("aaa",
                "(GaodeLocationActivity.java:167)<--keyWord-->" + keyWord + "city=====" + city);
        showProgressDialog();
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        query.setCityLimit(true);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /***
     * 逆地理编码获取定位后的附近地址
     * @param weidu
     * @param jingdu
     * @param distances 设置查找范围
     */
    private void regeocodeSearch(Double weidu, Double jingdu, int distances) {
        final LatLonPoint point = new LatLonPoint(weidu, jingdu);
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, distances, geocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);

        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                String preAdd = "";//地址前缀
                if (1000 == rCode) {
                    final RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                    StringBuffer stringBuffer = new StringBuffer();
                    String area = address.getProvince();//省或直辖市
                    String loc = address.getCity();//地级市或直辖市
                    String subLoc = address.getDistrict();//区或县或县级市
                    String ts = address.getTownship();//乡镇
                    String thf = null;//道路
                    List<RegeocodeRoad> regeocodeRoads = address.getRoads();//道路列表
                    if (regeocodeRoads != null && regeocodeRoads.size() > 0) {
                        RegeocodeRoad regeocodeRoad = regeocodeRoads.get(0);
                        if (regeocodeRoad != null) {
                            thf = regeocodeRoad.getName();
                        }
                    }
                    String subthf = null;//门牌号
                    StreetNumber streetNumber = address.getStreetNumber();
                    if (streetNumber != null) {
                        subthf = streetNumber.getNumber();
                    }
                    String fn = address.getBuilding();//标志性建筑,当道路为null时显示
                    if (area != null) {
                        stringBuffer.append(area);
                        preAdd += area;
                    }
                    if (loc != null && !area.equals(loc)) {
                        stringBuffer.append(loc);
                        preAdd += loc;
                    }
                    if (subLoc != null) {
                        stringBuffer.append(subLoc);
                        preAdd += subLoc;
                    }
                    if (ts != null)
                        stringBuffer.append(ts);
                    if (thf != null)
                        stringBuffer.append(thf);
                    if (subthf != null)
                        stringBuffer.append(subthf);
                    if ((thf == null && subthf == null) && fn != null && !subLoc.equals(fn))
                        stringBuffer.append(fn + "附近");
                    //  String ps = "poi";
//                    pois = address.getPois();//获取周围兴趣点

                    String building = address.getBuilding();
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        tvCity.setText(loc);
                    }
                    Log.e("aaa",
                            "(GaodeLocationActivity.java:227)<--这个是我也不知道怎么说的  周边的兴趣点的获取-->" + building);
                    pois.clear();

                    if (poiItem != null) {
                        if (canReplace) {
                            poiItem = address.getPois().get(0);
                            etSearch.setText(poiItem.getTitle());
                        } else {
                            pois.add(poiItem);
                            etSearch.setText(poiItem.getTitle());
                        }

                    } else {
                        poiItem = address.getPois().get(0);
                        etSearch.setText(address.getPois().get(0).getTitle());
                    }
                    pois.addAll(address.getPois());
                    adapter.notifyDataSetChanged();
//                    lvPoi.setItemAnimator(new DefaultItemAnimator());
                }

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(GaodeLocationActivity.this.getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);

            mLocationOption.setOnceLocation(true); //只定位一次
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && mListener != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                weidu = aMapLocation.getLatitude();
                jingdu = aMapLocation.getLongitude();
                Log.e("aaa",
                        "(GaodeLocationActivity.java:303)<--定位改变获取周边POI-->");
                regeocodeSearch(weidu, jingdu, 500);
            } else {
                String errorText = "faild to located" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo();
                Log.d("ceshi", errorText);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        double latitude = pois.get(i).getLatLonPoint().getLatitude();//点击的纬度
        double longitude = pois.get(i).getLatLonPoint().getLongitude();//点击的经度
        poiItem = pois.get(i);
        adapter.setClick(true);
        canReplace = false;
        LatLng position = new LatLng(latitude, longitude);
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                position, 16, 0, 0)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dingwei_xiao)));
        etSearch.setText(poiItem.getTitle());
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        progDialog.dismiss();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            Log.e("aaa",
                    "(GaodeLocationActivity.java:329)<--获取周边的成功-->");
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        pois.clear();
                        if (poiItem != null) {
                            Log.e("aaa",
                                    "(GaodeLocationActivity.java:339)<--临时缓存兴趣点不为空-->");
                            poiItems.add(poiItem);
                        } else {
                            poiItem = poiItems.get(0);
                            etSearch.setText(poiItem.getTitle());
                        }
                        pois.addAll(poiItems);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "暂无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "暂无搜索结果", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this, "请先选择定位点", Toast.LENGTH_SHORT).show();
            return;
        }

        if (poiItem == null) {
            Toast.makeText(this, "请先选择定位点", Toast.LENGTH_SHORT).show();
            return;
        }

        String cityName = poiItem.getCityName();
        Log.e("aaa",
                "(GaodeLocationActivity.java:128)<--cityName-->" + cityName);

        String snippet = poiItem.getSnippet();//详细地址
        String title = poiItem.getTitle();

        Intent it = new Intent();
        it.putExtra("Latitude", (poiItem.getLatLonPoint().getLatitude() + 0.006) + "");//纬度  高德向百度纬度转换
        it.putExtra("Longitude", (poiItem.getLatLonPoint().getLongitude() + 0.0065) + "");//经度  高德向百度经度转换
//        it.putExtra("city", currentAddress);
        it.putExtra("Address", snippet + title);
        setResult(111, it);
        finish();
    }

    @Override
    public void onMapLoaded() {
        Log.e("aaa", "(GaodeLocationActivity.java:489)<---->" + "地图加载完成");
        LatLng marker1 = new LatLng(weidu, jingdu);
        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }
}
