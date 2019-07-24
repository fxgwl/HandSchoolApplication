package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.wb_about)
//    WebView wbAbout;
    @BindView(R.id.tv_apk_version)
    TextView tvApkVersion;
    private String versionName;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("关于掌上私塾");
        versionName = MyUtiles.getVersionName(this);
        tvApkVersion.setText("当前版本号："+versionName);
        type = getIntent().getStringExtra("type");
//        setwbAgreement(url);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.ll_geiwopingjia, R.id.ll_banquanxinxi,
            R.id.ll_ruanjianxvke, R.id.ll_tebieshuoming, R.id.ll_fuwuxieyi,
            R.id.ll_yinsizhengce, R.id.iv_imag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_geiwopingjia:
                startActivity(new Intent(this,AgreementWebActivity.class)
                        .putExtra("url","")
                        .putExtra("title",""));
                break;
            case R.id.ll_banquanxinxi:
                startActivity(new Intent(this,AgreementWebSActivity.class)
                        .putExtra("title","版权信息"));
                break;
            case R.id.ll_ruanjianxvke:
                startActivity(new Intent(this,AgreementWebActivity.class)
                        .putExtra("url","legal_notices1.html")
                        .putExtra("title","软件许可使用协议"));
                break;
            case R.id.ll_tebieshuoming:
                startActivity(new Intent(this,AgreementWebActivity.class)
                        .putExtra("url","")
                        .putExtra("title","特别说明"));
                break;
            case R.id.ll_fuwuxieyi:
                if (type.equals("com")){
                    startActivity(new Intent(this,AgreementWebActivity.class)
                            .putExtra("url","merchant _service.html")
                            .putExtra("title","商户服务协议"));
                }else {
                    startActivity(new Intent(this,AgreementWebActivity.class)
                            .putExtra("url","user_service1.html")
                            .putExtra("title","用户服务协议"));
                }

                break;
            case R.id.ll_yinsizhengce:
                startActivity(new Intent(this,AgreementWebActivity.class)
                        .putExtra("url","privacy_policy1.html")
                        .putExtra("title","隐私权政策"));
                break;
            case R.id.iv_imag:
                showShare("掌上私塾","更多精彩尽在掌上私塾", Internet.BASE_URL+"head/logo.png","http://m.xczsss.com/PrivateSchool/head/tel_zsss/load_page.html");
                break;
        }
    }

//    private void setwbAgreement(String url) {
//        wbAbout.getSettings().setJavaScriptEnabled(true);
//        //设置可以访问文件
//        wbAbout.getSettings().setAllowFileAccess(true);
//        //设置支持缩放
//        wbAbout.getSettings().setBuiltInZoomControls(true);
//        //自适应屏幕
////        wbAbout.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        wbAbout.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        wbAbout.getSettings().setLoadWithOverviewMode(true);
//        wbAbout.getSettings().setSupportZoom(true);
//        wbAbout.getSettings().setDomStorageEnabled(true);
//        wbAbout.getSettings().setUseWideViewPort(true);
//        wbAbout.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        wbAbout.getSettings().setBlockNetworkImage(false);
//        //扩大比例的缩放
//        wbAbout.getSettings().setUseWideViewPort(true);
//        //去掉放大缩小按钮
//        wbAbout.getSettings().setDisplayZoomControls(false);
//        wbAbout.loadUrl(Internet.BASE_URL + "app/" + url);
//        //设置Web视图
//        wbAbout.setWebViewClient(new webViewClient());
//        //clearWebViewCache();
//    }

//    //Web视图
//    private class webViewClient extends WebViewClient {
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }

    //asdfasdsdaf
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
