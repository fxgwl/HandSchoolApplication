package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;

import butterknife.BindView;
import butterknife.OnClick;

public class AgreementActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wb_agreement)
    WebView wbAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("url");
        initView();

        setwbAgreement(url);
    }

    private void initView() {
        tvTitle.setText("用户协议");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_agreement;
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }

    private void setwbAgreement(String url) {
        wbAgreement.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        wbAgreement.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        wbAgreement.getSettings().setBuiltInZoomControls(false);
        //自适应屏幕
//        wbAgreement.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wbAgreement.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wbAgreement.getSettings().setLoadWithOverviewMode(true);
        wbAgreement.getSettings().setSupportZoom(true);
        wbAgreement.getSettings().setDomStorageEnabled(true);
        wbAgreement.getSettings().setUseWideViewPort(true);
        wbAgreement.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wbAgreement.getSettings().setBlockNetworkImage(false);
        //扩大比例的缩放
        wbAgreement.getSettings().setUseWideViewPort(true);
        //去掉放大缩小按钮
        wbAgreement.getSettings().setDisplayZoomControls(false);
        wbAgreement.loadUrl(Internet.BASE_URL + "app/"+url);
        //设置Web视图
        wbAgreement.setWebViewClient(new webViewClient());
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
