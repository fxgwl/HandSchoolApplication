//package com.example.handschoolapplication.activity.location;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.animation.DecelerateInterpolator;
//import android.view.inputmethod.EditorInfo;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.CameraUpdate;
//import com.amap.api.maps2d.CameraUpdateFactory;
//import com.amap.api.maps2d.LocationSource;
//import com.amap.api.maps2d.MapView;
//import com.amap.api.maps2d.model.BitmapDescriptor;
//import com.amap.api.maps2d.model.BitmapDescriptorFactory;
//import com.amap.api.maps2d.model.CameraPosition;
//import com.amap.api.maps2d.model.LatLng;
//import com.amap.api.maps2d.model.Marker;
//import com.amap.api.maps2d.model.MarkerOptions;
//import com.amap.api.maps2d.model.MyLocationStyle;
//import com.amap.api.services.core.LatLonPoint;
//import com.amap.api.services.core.PoiItem;
//import com.amap.api.services.core.SuggestionCity;
//import com.amap.api.services.geocoder.GeocodeResult;
//import com.amap.api.services.geocoder.GeocodeSearch;
//import com.amap.api.services.geocoder.RegeocodeAddress;
//import com.amap.api.services.geocoder.RegeocodeQuery;
//import com.amap.api.services.geocoder.RegeocodeResult;
//import com.amap.api.services.poisearch.PoiResult;
//import com.amap.api.services.poisearch.PoiSearch;
//import com.blankj.utilcode.utils.KeyboardUtils;
//import com.example.handschoolapplication.MyApplication;
//import com.example.handschoolapplication.R;
//import com.example.handschoolapplication.adapter.LocationAdapter;
//import com.example.handschoolapplication.base.BaseActivity;
//import com.example.handschoolapplication.bean.AddressSearchTextEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//
//public class LocationActivity extends BaseActivity implements LocationSource, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {
//
//
//    @BindView(R.id.rc_ext_amap)
//    MapView rcExtAmap;
//    @BindView(R.id.rc_ext_location_marker)
//    EditText rcExtLocationMarker;
//    @BindView(R.id.rc_ext_my_location)
//    ImageView rcExtMyLocation;
//    @BindView(R.id.lv_location)
//    ListView lvLocation;
//    @BindView(R.id.rl_back)
//    RelativeLayout rlBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    private AMap mAMap;
//    private GeocodeSearch mGeocodeSearch;
//    private OnLocationChangedListener mLocationChangedListener;
//    private double mMyLat;
//    private double mMyLng;
//    private String mMyPoi;
//    private double mLatResult;
//    private double mLngResult;
//    private String mPoiResult;
//    ValueAnimator animator;
//    private Handler mHandler;
//    private Marker mMarker;
//    private BitmapDescriptor mBitmapDescriptor;
//    private PoiSearch poiSearch;
//    private String addressName;
//    private PoiResult poiResult; // poi返回的结果
//    private int currentPage = 0;// 当前页面，从0开始计数
//    private PoiSearch.Query query;// Poi查询条件类
//    private LatLonPoint lp;//
//    private List<PoiItem> poiItems;// poi数据
//    private LatLng mFinalChoosePosition; //最终选择的点
////    private String city = "天津市";
//    private boolean isHandDrag = true;
//    private boolean isFirstLoadList = true;
//    private boolean isBackFromSearchChoose = false;
//    private String mSearchText;
//    private ArrayList<AddressSearchTextEntity> mDatas = new ArrayList<>();
//    private AddressSearchTextEntity mAddressEntityFirst = null;
//    private LocationAdapter mAdapter;
//    private String targetid;
//    private boolean isItem=false;
//    private String mPoiResultItem;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
////        View listEmptyView = View.inflate(this, R.layout.empty_lv_location, (ViewGroup) lvLocation.getParent());
////        lvLocation.setEmptyView(listEmptyView);
//        tvTitle.setText("位置");
//        rlBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               onBackPressed();
//            }
//        });
//        targetid = getIntent().getStringExtra("targetid");
//        rcExtAmap.onCreate(savedInstanceState);
//        mAdapter = new LocationAdapter(LocationActivity.this, mDatas);
//        lvLocation.setAdapter(mAdapter);
////        tvSearch.setVisibility(View.VISIBLE);
//
//        this.mHandler = new Handler();
//        initMap();
//        rcExtMyLocation.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                if (LocationActivity.this.mMyPoi != null) {
//
//                    LocationActivity.this.mAMap.setOnCameraChangeListener((AMap.OnCameraChangeListener) null);
//                    CameraUpdate update = CameraUpdateFactory.changeLatLng(new LatLng(LocationActivity.this.mMyLat, LocationActivity.this.mMyLng));
//                    LocationActivity.this.mAMap.animateCamera(update, new AMap.CancelableCallback() {
//                        public void onFinish() {
//                            LocationActivity.this.mAMap.setOnCameraChangeListener(LocationActivity.this);
//                        }
//
//                        public void onCancel() {
//                        }
//                    });
//                    LocationActivity.this.rcExtLocationMarker.setText(LocationActivity.this.mMyPoi);
//
//                    LocationActivity.this.mLatResult = LocationActivity.this.mMyLat;
//                    LocationActivity.this.mLngResult = LocationActivity.this.mMyLng;
//                    LocationActivity.this.mPoiResult = LocationActivity.this.mMyPoi;
//                } else {
//
////                    LocationManager.getInstance().updateMyLocation();
//                }
//
//            }
//        });
//        rcExtLocationMarker.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    mSearchText = rcExtLocationMarker.getText().toString().trim();
//                    if (TextUtils.isEmpty(mSearchText)) {
//                        Toast.makeText(LocationActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
//                    } else {
//                        doSearchQueryWithKeyWord(mSearchText);
//                        KeyboardUtils.hideSoftInput(LocationActivity.this, rcExtLocationMarker);
//                    }
//                }
//                return false;
//            }
//        });
//
//        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                isItem=true;
//                MyApplication.selectPosition = position;
//                mAdapter.notifyDataSetChanged();
//                mFinalChoosePosition = convertToLatLng(mDatas.get(position).getLatLonPoint());
//                mPoiResultItem = mDatas.get(position).getTitle()+mDatas.get(position).getSnippet();
//                Log.e("aaa","点击后的最终经纬度：  纬度" + mFinalChoosePosition.latitude + " 经度 " + mFinalChoosePosition.longitude);
//                isHandDrag = false;
//                // 点击之后，我利用代码指定的方式改变了地图中心位置，所以也会调用 onCameraChangeFinish
//                // 只要地图发生改变，就会调用 onCameraChangeFinish ，不是说非要手动拖动屏幕才会调用该方法
//                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mFinalChoosePosition.latitude, mFinalChoosePosition.longitude), 20));
//
//            }
//        });
//
//        //点击发送按钮
////        tvSearch.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                LocationMessage locationMessage = LocationMessage.obtain(mLatResult, mLngResult, mPoiResult, Uri.parse(getMapUrl(mLatResult, mLngResult)));
////                Log.e("aaa", "==dddd====>" + Uri.parse(getMapUrl(mLatResult, mLngResult))+mPoiResult);
////                Message message = Message.obtain(targetid, Conversation.ConversationType.PRIVATE, locationMessage);
////                RongIM.getInstance().sendLocationMessage(message, null, null, new IRongCallback.ISendMediaMessageCallback() {
////                    @Override
////                    public void onProgress(Message message, int i) {
////
////                    }
////
////                    @Override
////                    public void onCanceled(Message message) {
////
////                    }
////
////                    @Override
////                    public void onAttached(Message message) {
////
////                    }
////
////                    @Override
////                    public void onSuccess(Message message) {
////                        Log.e("aaa", "---发送位置成功-->");
////                        finish();
////                    }
////
////                    @Override
////                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
////                        Log.e("aaa", "---发送位置error-->");
////                    }
////                });
//////                if(LocationActivity.this.mLatResult == 0.0D && LocationActivity.this.mLngResult == 0.0D && TextUtils.isEmpty(LocationActivity.this.mPoiResult)) {
//////                    Toast.makeText(LocationActivity.this, LocationActivity.this.getString(string.rc_location_temp_failed), 0).show();
//////                } else {
//////                    Intent intent = new Intent();
//////                    intent.putExtra("thumb", LocationActivity.access$800(LocationActivity.this, LocationActivity.this.mLatResult, LocationActivity.this.mLngResult));
//////                    intent.putExtra("lat", LocationActivity.this.mLatResult);
//////                    intent.putExtra("lng", LocationActivity.this.mLngResult);
//////                    intent.putExtra("poi", LocationActivity.this.mPoiResult);
//////                    LocationActivity.this.setResult(-1, intent);
//////                    LocationActivity.this.finish();
//////                }
////            }
////        });
//
//    }
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_location_gaode;
//    }
//
//    /**
//     * 把LatLonPoint对象转化为LatLon对象
//     */
//    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
//        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
//    }
//
//    private void initMap() {
//        mAMap = rcExtAmap.getMap();
//        this.mAMap.setLocationSource(this);
//        this.mAMap.setMyLocationEnabled(true);
//        this.mAMap.getUiSettings().setZoomControlsEnabled(false);
//        this.mAMap.getUiSettings().setMyLocationButtonEnabled(false);
//        this.mAMap.setMapType(1);
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.dingwei_hong_xiao));
//        myLocationStyle.strokeWidth(0.0F);
//        myLocationStyle.strokeColor(R.color.a97f5ef);
//
//        myLocationStyle.radiusFillColor(0);
//        this.mAMap.setMyLocationStyle(myLocationStyle);
//        this.mGeocodeSearch = new GeocodeSearch(this);
//        this.mGeocodeSearch.setOnGeocodeSearchListener(this);
////        LocationManager.getInstance().setMyLocationChangedListener(this);
//
//    }
//
//    @Override
//    protected void onDestroy() {
//
//        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        rcExtAmap.onDestroy();
////        LocationManager.getInstance().setMyLocationChangedListener((IMyLocationChangedListener) null);
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        rcExtAmap.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        rcExtAmap.onPause();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        rcExtAmap.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void activate(OnLocationChangedListener onLocationChangedListener) {
//        this.mLocationChangedListener = onLocationChangedListener;
//    }
//
//    @Override
//    public void deactivate() {
//
//    }
//
//    private void addLocatedMarker(LatLng latLng, String poi) {
//        this.mBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.dingwei_hong_xiao);
//        MarkerOptions markerOptions = (new MarkerOptions()).position(latLng).icon(this.mBitmapDescriptor);
//        this.mMarker = this.mAMap.addMarker(markerOptions);
//        this.mMarker.setPositionByPixels(this.rcExtAmap.getWidth() / 2, this.rcExtAmap.getHeight() / 2);
//        this.rcExtLocationMarker.setText(String.format("%s", new Object[]{poi}));
//        mFinalChoosePosition = latLng;
//        Log.e("aaa", "=ss==fff=ggg=" + mFinalChoosePosition.latitude);
//        doSearchQuery();
//    }
//
//    @TargetApi(11)
//    private void animMarker() {
//        if (Build.VERSION.SDK_INT > 11) {
//            if (this.animator != null) {
//                this.animator.start();
//                return;
//            }
//
//            this.animator = ValueAnimator.ofFloat(new float[]{(float) (this.rcExtAmap.getHeight() / 2), (float) (this.rcExtAmap.getHeight() / 2 - 30)});
//            this.animator.setInterpolator(new DecelerateInterpolator());
//            this.animator.setDuration(150L);
//            this.animator.setRepeatCount(1);
//            this.animator.setRepeatMode(ValueAnimator.REVERSE);
//
//            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                public void onAnimationUpdate(ValueAnimator animation) {
//
//                    Float value = (Float) animation.getAnimatedValue();
//                    LocationActivity.this.mMarker.setPositionByPixels(LocationActivity.this.rcExtAmap.getWidth() / 2, Math.round(value.floatValue()));
//                }
//            });
//            this.animator.addListener(new AnimatorListenerAdapter() {
//                public void onAnimationEnd(Animator animation) {
//                    LocationActivity.this.mMarker.setIcon(LocationActivity.this.mBitmapDescriptor);
//                }
//            });
//            this.animator.start();
//        }
//
//    }
//
////    @Override
////    public void onMyLocationChanged(final AMapLocationInfo locationInfo) {
////        if (this.mLocationChangedListener != null) {
////            this.mHandler.post(new Runnable() {
////                public void run() {
////                    if (locationInfo != null) {
////                        LocationActivity.this.mMyLat = LocationActivity.this.mLatResult = locationInfo.getLat();
////                        LocationActivity.this.mMyLng = LocationActivity.this.mLngResult = locationInfo.getLng();
////                        LocationActivity.this.mMyPoi = LocationActivity.this.mPoiResult = locationInfo.getStreet() + locationInfo.getPoiname();
////                        Log.e("aaa","--locationInfo.toString()---->"+locationInfo.toString());
////                        lp = new LatLonPoint(mMyLat, mMyLng);
////                        Location location = new Location("AMap");
////                        location.setLatitude(locationInfo.getLat());
////                        location.setLongitude(locationInfo.getLng());
////                        location.setTime(locationInfo.getTime());
////                        location.setAccuracy(locationInfo.getAccuracy());
////                        LocationActivity.this.mLocationChangedListener.onLocationChanged(location);
////                        LocationActivity.this.addLocatedMarker(new LatLng(LocationActivity.this.mLatResult, LocationActivity.this.mLngResult), LocationActivity.this.mPoiResult);
////                        CameraUpdate update = CameraUpdateFactory.zoomTo(17.0F);
////                        LocationActivity.this.mAMap.animateCamera(update, new AMap.CancelableCallback() {
////                            @Override
////                            public void onFinish() {
////                                LocationActivity.this.mAMap.setOnCameraChangeListener(new LocationActivity());
////                            }
////
////                            @Override
////                            public void onCancel() {
////
////                            }
////                        });
////                        LocationActivity.this.mAMap.animateCamera(update, new AMap.CancelableCallback() {
////                            public void onFinish() {
////                                LocationActivity.this.mAMap.setOnCameraChangeListener(LocationActivity.this);
////                            }
////
////                            public void onCancel() {
////                            }
////                        });
////                    } else {
////                        Toast.makeText(LocationActivity.this, LocationActivity.this.getString(R.string.rc_location_fail), Toast.LENGTH_SHORT).show();
////                    }
////
////                }
////            });
////        }
////    }
//
////    @Override
////    public void onPointerCaptureChanged(boolean hasCapture) {
////
////    }
//
//    @SuppressLint("ShowToast")
//    @Override
//    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//        Log.e("aaa", "onRegeocodeSearched");
//        if (regeocodeResult != null) {
//            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
//            this.mLatResult = regeocodeResult.getRegeocodeQuery().getPoint().getLatitude();
//            this.mLngResult = regeocodeResult.getRegeocodeQuery().getPoint().getLongitude();
//            String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
//            Log.e("aaa", "==this.mPoiResult=aaaa==" + regeocodeAddress.getBuilding()+regeocodeAddress.getRoads());
//            if (isItem){
//                this.mPoiResult=mPoiResultItem;
////                isItem=!isItem;
//            }else{
//
//                this.mPoiResult = formatAddress.replace(regeocodeAddress.getProvince(), "").replace(regeocodeAddress.getCity(), "").replace(regeocodeAddress.getDistrict(), "");
//            }
//
//            this.rcExtLocationMarker.setText(this.mPoiResult);
//            Log.e("aaa", "==this.mPoiResult==bbbbb=" + this.mPoiResult);
//            LatLng latLng = new LatLng(this.mLatResult, this.mLngResult);
//            if (this.mMarker != null) {
//                this.mMarker.setPosition(latLng);
//            }
//
//            isItem = false;
//        } else {
//            Toast.makeText(this, this.getString(R.string.rc_location_fail), Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    private String getMapUrl(double latitude, double longitude) {
//        return "http://restapi.amap.com/v3/staticmap?location=" + longitude + "," + latitude + "&zoom=16&scale=2&size=408*240&markers=mid,,A:" + longitude + "," + latitude + "&key=e09af6a2b26c02086e9216bd07c960ae";
//    }
//
//    @Override
//    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//
//    }
//
//
//    public void onCameraChange(CameraPosition cameraPosition) {
//        if (Build.VERSION.SDK_INT < 11) {
//            this.mMarker.setPosition(cameraPosition.target);
//        }
//
//    }
//
//    public void onCameraChangeFinish(CameraPosition cameraPosition) {
//        mFinalChoosePosition = cameraPosition.target;
//        LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
//        RegeocodeQuery query = new RegeocodeQuery(point, 50.0F, "autonavi");
//        this.mGeocodeSearch.getFromLocationAsyn(query);
//        if (this.mMarker != null) {
//            this.animMarker();
//        }
//        if (isHandDrag || isFirstLoadList) {//手动去拖动地图
//            getAddress(cameraPosition.target);
//            doSearchQuery();
//        } else if (isBackFromSearchChoose) {
//            doSearchQuery();
//        } else {
////            mRvAddressAdapter.notifyDataSetChanged();
//        }
//        isHandDrag = true;
//        isFirstLoadList = false;
//
//
//    }
//
//
//    @Override
//    public void onPoiItemSearched(PoiItem poiItem, int i) {
//        Log.e("aaa",
//                "(LocationActivity.java:329)<--poiItem-->");
//    }
//
//    /**
//     * 根据经纬度得到地址
//     */
//    public void getAddress(final LatLng latLonPoint) {
//        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
//        RegeocodeQuery query = new RegeocodeQuery(convertToLatLonPoint(latLonPoint), 200, GeocodeSearch.AMAP);
//        this.mGeocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
//    }
//
//    /**
//     * 把LatLng对象转化为LatLonPoint对象
//     */
//    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
//        return new LatLonPoint(latlon.latitude, latlon.longitude);
//    }
//
//
////    /**
////     * 逆地理编码回调
////     */
////    @Override
////    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
////        if (rCode == 1000) {
////            if (result != null && result.getRegeocodeAddress() != null
////                    && result.getRegeocodeAddress().getFormatAddress() != null) {
////                addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
////                Log.e("aaa", "---gggggggggg----->" + addressName);
////                mAddressEntityFirst = new AddressSearchTextEntity(addressName, addressName, true, convertToLatLonPoint(mFinalChoosePosition));
////
////            } else {
////                Toast.makeText(this, "ssss", Toast.LENGTH_SHORT).show();
////
////            }
////        } else if (rCode == 27) {
////
////        } else if (rCode == 32) {
////
////        } else {
////
////        }
////    }
//
//    /**
//     * 开始进行poi搜索   重点
//     * 通过经纬度获取附近的poi信息
//     * <p>
//     * 1、keyword 传 ""
//     * 2、poiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 5000, true)); 根据
//     */
//    protected void doSearchQuery() {
//
//        currentPage = 0;
//        query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
//        query.setPageSize(20);// 设置每页最多返回多少条poiitem
//        query.setPageNum(currentPage);// 设置查第一页
//
//        LatLonPoint lpTemp = convertToLatLonPoint(mFinalChoosePosition);
//
//        if (lpTemp != null) {
//            poiSearch = new PoiSearch(this, query);
//            poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
//            poiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 5000, true));//
//            // 设置搜索区域为以lp点为圆心，其周围5000米范围
//            poiSearch.searchPOIAsyn();// 异步搜索
//        }
//    }
//
//    /**
//     * 按照关键字搜索附近的poi信息
//     *
//     * @param key
//     */
//    protected void doSearchQueryWithKeyWord(String key) {
//        currentPage = 0;
//        query = new PoiSearch.Query(key, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
//        query.setPageSize(20);// 设置每页最多返回多少条poiitem
//        query.setPageNum(currentPage);// 设置查第一页
//        Log.e("aaa", "--ffffffhhhhh----->" + lp.getLatitude() + "jjjjj" + lp.getLongitude());
//        if (lp != null) {
//            poiSearch = new PoiSearch(this, query);
//            poiSearch.setOnPoiSearchListener(this);   // 实现  onPoiSearched  和  onPoiItemSearched
//            poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
//            // 设置搜索区域为以lp点为圆心，其周围5000米范围
//            poiSearch.searchPOIAsyn();// 异步搜索
//        }
//    }
//
//
//    /**
//     * poi 附近数据搜索
//     *
//     * @param result
//     * @param rcode
//     */
//    @Override
//    public void onPoiSearched(PoiResult result, int rcode) {
//        if (rcode == 1000) {
//            if (result != null && result.getQuery() != null) {// 搜索poi的结果
//                if (result.getQuery().equals(query)) {// 是否是同一条
//                    poiResult = result;
//                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
//                    Log.e("aaa", "--hhhhhh-poiResult->" + poiResult.getSearchSuggestionCitys());
//
//                    List<SuggestionCity> suggestionCities = poiResult
//                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
//
//                    Log.e("aaa", "--hhhhhh-->" + suggestionCities.toString() + suggestionCities.size());
//                    Log.e("aaa", "--hhhhhh-poiItems->" + poiItems.size());
//                    Log.e("aaa", "--hhhhhh-result->" + result.getSearchSuggestionCitys());
//                    //if(isFirstLoadList || isBackFromSearchChoose){
////                    mDatas.add(mAddressEntityFirst);// 第一个元素
//
//                    mDatas.clear();
//                    AddressSearchTextEntity addressEntity = null;
//                        for (PoiItem poiItem : poiItems) {
//                            addressEntity = new AddressSearchTextEntity(poiItem.getTitle(), poiItem.getSnippet(), false, poiItem.getLatLonPoint());
//                            mDatas.add(addressEntity);
//                        }
//                        mAdapter.notifyDataSetChanged();
//
//
//                    if (isHandDrag) {
//                        mDatas.get(0).setChoose(true);
//                    }
//
//                }
//            } else {
////                ToastUtil
////                        .show(ShareLocActivity.this, "对不起，没有搜索到相关数据！");
//            }
//        }
//    }
//
//
//}
