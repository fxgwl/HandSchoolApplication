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

public class EducationDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;
    private String newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("教育咨询");
        newsId = getIntent().getStringExtra("newsId");
        setWebView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_education_detail;
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

    private void setWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        webview.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        webview.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setDomStorageEnabled(true);
//        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setBlockNetworkImage(false);
        //去掉放大缩小按钮
        webview.getSettings().setDisplayZoomControls(false);
        webview.loadUrl(Internet.BASE_URL + "Project-Backstage/News-Management/show.jsp?news_id=" + newsId);
        //设置Web视图
        webview.setWebViewClient(new webViewClient());
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
