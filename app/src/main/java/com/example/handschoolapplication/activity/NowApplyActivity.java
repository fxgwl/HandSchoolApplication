package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.view.MyPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NowApplyActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_schoolname)
    TextView tvSchoolname;
    @BindView(R.id.tv_coursename)
    TextView tvCoursename;
    @BindView(R.id.tv_coursetime)
    TextView tvCoursetime;
    @BindView(R.id.tv_personnum)
    TextView tvPersonnum;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_costtime)
    TextView tvCosttime;
    @BindView(R.id.tv_coursenum)
    TextView tvCoursenum;
    @BindView(R.id.tv_coursemoney)
    TextView tvCoursemoney;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_moneydi)
    TextView tvMoneydi;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.tv_nowapply_config)
    TextView tvNowapplyConfig;
    private Intent intent;
    private String school_name;
    private String course_name;
    private String course_time;
    private String enrol_num;
    private String course_capacity;
    private String age_range;
    private String course_teacher;
    private String original_price;
    private String preferential_price;
    private String class_money;
    private String course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        intent = getIntent();
        school_name = intent.getStringExtra("school_name");
        course_name = intent.getStringExtra("course_name");
//        course_time = intent.getStringExtra("course_time");
        enrol_num = intent.getStringExtra("enrol_num");
        course_capacity = intent.getStringExtra("course_capacity");
        age_range = intent.getStringExtra("age_range");
        course_teacher = intent.getStringExtra("course_teacher");
        original_price = intent.getStringExtra("original_price");
        preferential_price = intent.getStringExtra("preferential_price");
        class_money = intent.getStringExtra("class_money");
        course_id = intent.getStringExtra("course_id");
        tvTitle.setText("立即报名");
        tvSchoolname.setText(school_name);
        tvCoursename.setText(course_name);
        tvPersonnum.setText(enrol_num + "【已报名】 /" + course_capacity + "人【总人数】");
        tvAge.setText(age_range);
        tvTeacher.setText(course_teacher);
        tvCosttime.setText(class_money);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_now_apply;
    }

    @OnClick({R.id.rl_back, R.id.tv_nowapply_config})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_nowapply_config:
                initPayPop();
                break;
        }
    }

    //初始化支付
    private void initPayPop() {
        View view = View.inflate(NowApplyActivity.this, R.layout.nowapply_pay_pop, null);
        TextView close = (TextView) view.findViewById(R.id.pop_close);
        TextView pop_pay_config = (TextView) view.findViewById(R.id.pop_pay_config);
        LinearLayout pop_pay_ali = (LinearLayout) view.findViewById(R.id.pop_pay_ali);
        LinearLayout pop_pay_weixin = (LinearLayout) view.findViewById(R.id.pop_pay_weixin);
        final ImageView pop_state_ali = (ImageView) view.findViewById(R.id.pop_state_ali);
        final ImageView pop_state_wx = (ImageView) view.findViewById(R.id.pop_state_wx);
        final MyPopupWindow payPopWindow = new MyPopupWindow(this, view);
        payPopWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
        pop_pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_state_ali.setImageResource(R.drawable.hongquan);
                pop_state_wx.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_state_wx.setImageResource(R.drawable.hongquan);
                pop_state_ali.setImageResource(R.drawable.baiquan);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPopWindow.dismiss();
            }
        });
        pop_pay_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPopWindow.dismiss();
            }
        });
        payPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }
}
