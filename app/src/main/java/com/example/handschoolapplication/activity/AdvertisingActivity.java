package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;

import butterknife.BindView;
import butterknife.OnClick;

public class AdvertisingActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ivMenu.setVisibility(View.GONE);
        tvTitle.setText("详情");

        String content1 = getIntent().getStringExtra("content");
        if (0 != getIntent().getIntExtra("id", 0)) {
            String content2 = getIntent().getStringExtra("content");
            int id = getIntent().getIntExtra("id", 0);
            content = Internet.BASE_URL + "Project-Backstage/Advertising-Management/adv_info.jsp?advertising_id=" + id;
        } else {
            content = Internet.BASE_URL + content1;
        }
        setWebView();


        Log.e("aaa",
                "(AdvertisingActivity.java:36)<---->" + content);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_advertising;
    }

    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setBlockNetworkImage(false);
        //去掉放大缩小按钮
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(content);
        //设置Web视图
        webView.setWebViewClient(new webViewClient());
        //clearWebViewCache();
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }


    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
