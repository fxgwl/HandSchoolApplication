package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {
    //昝宏伟10.25  12:27
    //zhshjiwo tianjiade
    //这是昝宏伟新版的教育项目11122323232

    //xianzai
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("关于我们");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_about_us;
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

    //asdfasdsdaf
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
