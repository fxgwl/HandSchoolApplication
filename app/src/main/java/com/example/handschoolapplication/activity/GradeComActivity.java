package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.IntegralBean;
import com.example.handschoolapplication.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GradeComActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_class_grade)
    ImageView ivClassGrade;

    private List<IntegralBean.DataBean> mList = new ArrayList<>();
    private String user_id;
    private String flag;
    private String grade;
    private String integral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("等级详情");
        user_id = (String) SPUtils.get(this, "userId", "");
        flag = getIntent().getStringExtra("flag");
        grade = getIntent().getStringExtra("grade");
        integral = getIntent().getStringExtra("integral");
        if (flag.equals("com")) {
            switch (grade) {
                case "":
                case "0":
                case "1":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_tong);
                    break;
                case "2":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_yin);
                    break;
                case "3":
                    ivClassGrade.setImageResource(R.drawable.xuetangdengji_gold);
                    break;
            }
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_grade_com;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_grade_gold, R.id.ll_grade_silver, R.id.ll_grade_copper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_grade_gold:
                startActivity(new Intent(this, WebGradeActivity.class)
                        .putExtra("url", "merchant_level1.html")
                        .putExtra("title", "等级规则的介绍"));
                break;
            case R.id.ll_grade_silver:
                startActivity(new Intent(this, WebGradeActivity.class)
                        .putExtra("url", "merchant_level1.html")
                        .putExtra("title", "等级规则的介绍"));
                break;
            case R.id.ll_grade_copper:
                startActivity(new Intent(this, WebGradeActivity.class)
                        .putExtra("url", "merchant_level1.html")
                        .putExtra("title", "等级规则的介绍"));
                break;
        }
    }

}
