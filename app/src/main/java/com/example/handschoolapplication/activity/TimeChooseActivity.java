package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.TimeAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.TimeHourBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TimeChooseActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.gv_time)
    GridView gvTime;
    @BindView(R.id.tv_Mon)
    TextView tvMon;
    @BindView(R.id.tv_Tue)
    TextView tvTue;
    @BindView(R.id.tv_Wed)
    TextView tvWed;
    @BindView(R.id.tv_Thu)
    TextView tvThu;
    @BindView(R.id.tv_Fri)
    TextView tvFri;
    @BindView(R.id.tv_Sat)
    TextView tvSat;
    @BindView(R.id.tv_Sun)
    TextView tvSun;

    //    private ChooseTimeAdapter mAdapter;
//    private List<TimeBean> mList;
    private List<TimeHourBean> mHourList;

    private TimeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("报名信息");

        initData();
    }

    private void initData() {

        mHourList=new ArrayList<>();
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());
        mHourList.add(new TimeHourBean());

        mAdapter=new TimeAdapter(this,mHourList);
        gvTime.setAdapter(mAdapter);

        gvTime.setOnItemClickListener(this);
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_time_choose;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_Mon, R.id.tv_Tue, R.id.tv_Wed, R.id.tv_Thu, R.id.tv_Fri, R.id.tv_Sat, R.id.tv_Sun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_Mon:
                tvMon.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Tue:
                tvTue.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Wed:
                tvWed.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Thu:
                tvThu.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Fri:
                tvFri.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Sat:
                tvSat.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Sun:
                tvSun.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(this,ApplyDetailActivity.class));
    }
}
