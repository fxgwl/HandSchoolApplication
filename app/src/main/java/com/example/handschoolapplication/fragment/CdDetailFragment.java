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
public class CdDetailFragment extends BaseFragment {

    private View view;
    @BindView(R.id.web_view)
    WebView wvCourseInfo;
    private String courseId;


    public CdDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater,container,savedInstanceState);
        courseId = getArguments().getString("courseId");

        setWebView();
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_cd_detail;
    }


    private void setWebView() {
        wvCourseInfo.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        wvCourseInfo.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        wvCourseInfo.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        wvCourseInfo.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wvCourseInfo.getSettings().setLoadWithOverviewMode(true);
        wvCourseInfo.getSettings().setSupportZoom(true);
        wvCourseInfo.getSettings().setDomStorageEnabled(true);
//        wvCourseInfo.getSettings().setUseWideViewPort(true);
        wvCourseInfo.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvCourseInfo.getSettings().setBlockNetworkImage(false);
        //去掉放大缩小按钮
        wvCourseInfo.getSettings().setDisplayZoomControls(false);
        wvCourseInfo.loadUrl(Internet.BASE_URL+"School-Backstage/Course-Information/Course-Detailss.jsp?course_id="+courseId);
        //设置Web视图
        wvCourseInfo.setWebViewClient(new webViewClient());
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
