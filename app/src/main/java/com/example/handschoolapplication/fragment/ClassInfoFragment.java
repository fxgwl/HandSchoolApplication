package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.utils.Internet;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassInfoFragment extends BaseFragment {


    @BindView(R.id.wv_class_info)
    WebView wvClassInfo;
    private View view;
    private String school_id;

    public ClassInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater,container,savedInstanceState);
        if (getArguments()!=null){
            school_id = getArguments().getString("school_id");
        }
        setWebView();
        return view;
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
        wvClassInfo.loadUrl(Internet.BASE_URL+"School-Backstage/School-Situation/School-Profiles.jsp?school_id="+school_id);
        //设置Web视图
        wvClassInfo.setWebViewClient(new webViewClient());
        //clearWebViewCache();


    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
