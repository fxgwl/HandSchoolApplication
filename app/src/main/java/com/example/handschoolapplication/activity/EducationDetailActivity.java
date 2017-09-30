package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EducationDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("教育咨询");
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
                break;
        }
    }
}
