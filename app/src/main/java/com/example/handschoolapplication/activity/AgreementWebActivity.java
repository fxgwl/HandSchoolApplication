package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;

import butterknife.BindView;
import butterknife.OnClick;

public class AgreementWebActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wb_about)
    WebView wbAbout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        setwbAgreement(url);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_agreement_web;
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

    private void setwbAgreement(String url) {
        wbAbout.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        wbAbout.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        wbAbout.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
//        wbAbout.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wbAbout.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wbAbout.getSettings().setLoadWithOverviewMode(true);
        wbAbout.getSettings().setSupportZoom(true);
        wbAbout.getSettings().setDomStorageEnabled(true);
        wbAbout.getSettings().setUseWideViewPort(true);
        wbAbout.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wbAbout.getSettings().setBlockNetworkImage(false);
        //扩大比例的缩放
        wbAbout.getSettings().setUseWideViewPort(true);
        //去掉放大缩小按钮
        wbAbout.getSettings().setDisplayZoomControls(false);
        wbAbout.loadUrl(Internet.BASE_URL + "app/" + url);
        //设置Web视图
        wbAbout.setWebViewClient(new webViewClient());
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
