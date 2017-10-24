package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectSexActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_circle)
    ImageView ivCircle;
    @BindView(R.id.iv_circles)
    ImageView ivCircles;

    private String sex="男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("性别");

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_select_sex;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_save, R.id.rl_nan, R.id.rl_nv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.tv_save:
                setResult(11, new Intent().putExtra("sex",sex));
                finish();
                break;
            case R.id.rl_nan:
                ivCircle.setImageResource(R.drawable.hongquan);
                ivCircles.setImageResource(R.drawable.baiquan);
                sex="男";
                break;
            case R.id.rl_nv:
                ivCircle.setImageResource(R.drawable.baiquan);
                ivCircles.setImageResource(R.drawable.hongquan);
                sex="女";
                break;
        }
    }
}
