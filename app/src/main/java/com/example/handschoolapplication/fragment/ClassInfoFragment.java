package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.utils.Internet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassInfoFragment extends BaseFragment {


    @BindView(R.id.wv_class_info)
    WebView wvClassInfo;
    @BindView(R.id.tm_map)
    TextureMapView tmMap;
    Unbinder unbinder;
    private View view;
    private String school_id;
    private BaiduMap baiduMap;
    private double latitude;
    private double longitude;

    public ClassInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            school_id = getArguments().getString("school_id");
            latitude = getArguments().getDouble("latitude");
            longitude = getArguments().getDouble("longitude");
        }
        setWebView();
        initMap();
        return view;
    }

    private void initMap() {
        baiduMap = tmMap.getMap();
        LatLng point = new LatLng(latitude, longitude);

//构建Marker图标
        Log.e("aaa",
                "(ClassInfoFragment.java:72)" + latitude + "====" + longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.dingweis);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
        MapStatusUpdate mMapStatus = MapStatusUpdateFactory.newLatLng(point);
        //改变地图状态将这个坐标设为中心点
        baiduMap.animateMapStatus(mMapStatus);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_info;
    }

    private void setWebView() {
        wvClassInfo.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        wvClassInfo.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        wvClassInfo.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        wvClassInfo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wvClassInfo.getSettings().setLoadWithOverviewMode(true);
        wvClassInfo.getSettings().setSupportZoom(true);
        wvClassInfo.getSettings().setDomStorageEnabled(true);
//        wvClassInfo.getSettings().setUseWideViewPort(true);
        wvClassInfo.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvClassInfo.getSettings().setBlockNetworkImage(false);
        //去掉放大缩小按钮
        wvClassInfo.getSettings().setDisplayZoomControls(false);
        wvClassInfo.loadUrl(Internet.BASE_URL + "School-Backstage/School-Situation/School-Profiles.jsp?school_id=" + school_id);
        //设置Web视图
        wvClassInfo.setWebViewClient(new webViewClient());
        //clearWebViewCache();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
