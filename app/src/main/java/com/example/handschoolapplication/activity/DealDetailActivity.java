package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MyAccountBean;
import com.example.handschoolapplication.utils.Internet;

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
    private MyAccountBean myAccountBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("交易详情");
        myAccountBean = (MyAccountBean) getIntent().getSerializableExtra("myAccountBean");

        initData();
    }

    private void initData() {

        Glide.with(this).load(Internet.BASE_URL+myAccountBean.getUser_photo()).centerCrop().into(civUsericon);
        tvUsername.setText(myAccountBean.getUser_name());
        tvMoney.setText(myAccountBean.getMoney_info());
        tvCourseDetail.setText(myAccountBean.getCourse_name());
        tvCreateTime.setText(myAccountBean.getRecord_date()+"\t\t"+myAccountBean.getRecord_time());
        tvCode.setText(myAccountBean.getRecord_id()+"");
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
            case R.id.iv_menu:                 show(view);
                break;
        }
    }
}
