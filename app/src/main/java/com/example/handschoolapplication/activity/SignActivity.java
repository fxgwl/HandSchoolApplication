package com.example.handschoolapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sign_days)
    TextView tvSignDays;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_date_1)
    TextView tvDate1;
    @BindView(R.id.tv_date_2)
    TextView tvDate2;
    @BindView(R.id.tv_date_3)
    TextView tvDate3;
    @BindView(R.id.tv_date_4)
    TextView tvDate4;
    @BindView(R.id.tv_date_5)
    TextView tvDate5;
    @BindView(R.id.tv_date_6)
    TextView tvDate6;
    @BindView(R.id.tv_date_7)
    TextView tvDate7;
    @BindView(R.id.tv_date_8)
    TextView tvDate8;
    @BindView(R.id.tv_date_9)
    TextView tvDate9;
    @BindView(R.id.tv_date_10)
    TextView tvDate10;
    @BindView(R.id.tv_date_11)
    TextView tvDate11;
    @BindView(R.id.tv_date_12)
    TextView tvDate12;
    @BindView(R.id.tv_date_13)
    TextView tvDate13;
    @BindView(R.id.tv_date_14)
    TextView tvDate14;
    @BindView(R.id.tv_date_15)
    TextView tvDate15;
    @BindView(R.id.tv_date_16)
    TextView tvDate16;
    @BindView(R.id.tv_date_17)
    TextView tvDate17;
    @BindView(R.id.tv_date_18)
    TextView tvDate18;
    @BindView(R.id.tv_date_19)
    TextView tvDate19;
    @BindView(R.id.tv_date_20)
    TextView tvDate20;
    @BindView(R.id.tv_date_21)
    TextView tvDate21;
    @BindView(R.id.tv_date_22)
    TextView tvDate22;
    @BindView(R.id.tv_date_23)
    TextView tvDate23;
    @BindView(R.id.tv_date_24)
    TextView tvDate24;
    @BindView(R.id.tv_date_25)
    TextView tvDate25;
    @BindView(R.id.tv_date_26)
    TextView tvDate26;
    @BindView(R.id.tv_date_27)
    TextView tvDate27;
    @BindView(R.id.tv_date_28)
    TextView tvDate28;
    @BindView(R.id.tv_date_29)
    TextView tvDate29;
    @BindView(R.id.tv_date_30)
    TextView tvDate30;
    @BindView(R.id.tv_date_31)
    TextView tvDate31;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;
    @BindView(R.id.view_end)
    View viewEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        tvTitle.setText("签到");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sign;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_sign:
                //签到
                setTvBg(tvDate1);
                break;
        }
    }

    private void setTvBg(TextView currentTextView){
        currentTextView.setBackgroundResource(R.drawable.tv_date_bg);
        currentTextView.setTextColor(Color.parseColor("#ffffff"));
    }
}
