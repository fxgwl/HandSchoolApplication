package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ApplyDetailAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ApplyDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_morning)
    TextView tvMorning;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.lv_aplly_det)
    ListView lvApllyDet;
    @BindView(R.id.iv_isall)
    ImageView ivIsall;
    @BindView(R.id.et_message)
    EditText etMessage;

    private ApplyDetailAdapter mAdapter;
    private List<ApplyDetail> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("报名信息详情");

        initData();
    }

    private void initData() {

        mList = new ArrayList<>();
        mList.add(new ApplyDetail());
        mList.add(new ApplyDetail());
        mList.add(new ApplyDetail());
        mList.add(new ApplyDetail());
        mList.add(new ApplyDetail());

        mAdapter = new ApplyDetailAdapter(this,mList);
        lvApllyDet.setAdapter(mAdapter);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_detail;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_all_sele, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_all_sele://是否全选
                break;
            case R.id.tv_send://发送消息
                break;
        }
    }
}
