package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DealDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_usericon)
    CircleImageView civUsericon;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_course_detail)
    TextView tvCourseDetail;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("交易详情");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_deal_detail;
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
}
