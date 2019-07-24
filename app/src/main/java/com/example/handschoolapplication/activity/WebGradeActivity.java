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

public class WebGradeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.wb_grade)
    WebView wbGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        tvTitle.setText(title);

        setwbAgreement(url);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_web_grade;
    }

    private void setwbAgreement(String url) {
        wbGrade.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        wbGrade.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        wbGrade.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
//        wbAbout.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        wbGrade.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wbGrade.getSettings().setLoadWithOverviewMode(true);
        wbGrade.getSettings().setSupportZoom(true);
        wbGrade.getSettings().setDomStorageEnabled(true);
        wbGrade.getSettings().setUseWideViewPort(true);
        wbGrade.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wbGrade.getSettings().setBlockNetworkImage(false);
        //扩大比例的缩放
        wbGrade.getSettings().setUseWideViewPort(true);
        //去掉放大缩小按钮
        wbGrade.getSettings().setDisplayZoomControls(false);
        wbGrade.loadUrl(Internet.BASE_URL + "app/" + url);
        //设置Web视图
        wbGrade.setWebViewClient(new webViewClient());
        //clearWebViewCache();
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

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
