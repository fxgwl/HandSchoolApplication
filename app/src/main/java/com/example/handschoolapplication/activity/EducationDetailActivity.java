package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class EducationDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.iv_img_right)
    ImageView ivImgRight;
    private String newsId;
    private String imgUrl;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("教育资讯");
        newsId = getIntent().getStringExtra("newsId");
        imgUrl = getIntent().getStringExtra("imgUrl");
        text = getIntent().getStringExtra("text");
        Log.e("aaa",
                "(EducationDetailActivity.java:31)" + newsId);

        ivImgRight.setImageResource(R.drawable.fenxiangbai);
        read();
        setWebView();
    }

    private void read() {

        OkHttpUtils.post()
                .url(Internet.READ_EDU)
                .addParams("news_id", newsId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(EducationDetailActivity.java:50)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(EducationDetailActivity.java:56)" + response);

                    }
                });
    }

    private void setWebView() {
        webview.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        webview.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        webview.getSettings().setBuiltInZoomControls(true);
        //自适应屏幕
//        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setBlockNetworkImage(false);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //去掉放大缩小按钮
        webview.getSettings().setDisplayZoomControls(false);
        webview.loadUrl(Internet.BASE_URL + "Project-Backstage/News-Management/show.jsp?news_id=" + newsId);
        //设置Web视图
        webview.setWebViewClient(new webViewClient());
        //clearWebViewCache();
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
                showShare("教育咨讯", text, Internet.BASE_URL + imgUrl, Internet.BASE_URL + "Project-Backstage/News-Management/show.jsp?news_id=" + newsId);
                break;
        }
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("aaa", "(webViewClient.java:120)<--拦截到的url-->" + url);
            if (url.contains("Android")) {
                Toast.makeText(EducationDetailActivity.this, "已转到浏览器下载，请注意查看", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("aaa", "(webViewClient.java:138)<--拦截到的url-->" + url);
            if (url.contains("Android")) {
                Toast.makeText(EducationDetailActivity.this, "已转到浏览器下载，请注意查看", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }

}
