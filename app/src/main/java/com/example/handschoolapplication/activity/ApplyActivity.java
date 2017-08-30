package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ApplyMessageAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ApplyMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.v_all)
    View vAll;
    @BindView(R.id.tv_tostart)
    TextView tvTostart;
    @BindView(R.id.v_tostart)
    View vTostart;
    @BindView(R.id.tv_starting)
    TextView tvStarting;
    @BindView(R.id.v_starting)
    View vStarting;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.v_end)
    View vEnd;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.lv_course)
    ListView lvCourse;

    private List<ApplyMessage> mList;
    private ApplyMessageAdapter mAdapter;
    private int state=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("报名信息");

        initData();

        lvCourse.setOnItemClickListener(this);
    }

    private void initData() {
        mList=new ArrayList<>();
        mList.add(new ApplyMessage());
        mList.add(new ApplyMessage());
        mList.add(new ApplyMessage());
        mList.add(new ApplyMessage());
        mList.add(new ApplyMessage());
        mList.add(new ApplyMessage());
        mAdapter=new ApplyMessageAdapter(this,mList);
        lvCourse.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_all, R.id.ll_tostart, R.id.ll_starting, R.id.ll_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.ll_all:
                tvAll.setTextColor(Color.parseColor("#27acf6"));
                vAll.setBackgroundColor(Color.parseColor("#27acf6"));
                tvTostart.setTextColor(Color.parseColor("#666666"));
                vTostart.setBackgroundColor(Color.parseColor("#ffffff"));
                tvStarting.setTextColor(Color.parseColor("#666666"));
                vStarting.setBackgroundColor(Color.parseColor("#ffffff"));
                tvEnd.setTextColor(Color.parseColor("#666666"));
                vEnd.setBackgroundColor(Color.parseColor("#ffffff"));
                state=1;
                mAdapter.setState(state);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_tostart:
                tvTostart.setTextColor(Color.parseColor("#27acf6"));
                vTostart.setBackgroundColor(Color.parseColor("#27acf6"));
                tvAll.setTextColor(Color.parseColor("#666666"));
                vAll.setBackgroundColor(Color.parseColor("#ffffff"));
                tvStarting.setTextColor(Color.parseColor("#666666"));
                vStarting.setBackgroundColor(Color.parseColor("#ffffff"));
                tvEnd.setTextColor(Color.parseColor("#666666"));
                vEnd.setBackgroundColor(Color.parseColor("#ffffff"));
                state=2;
                mAdapter.setState(state);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_starting:
                tvStarting.setTextColor(Color.parseColor("#27acf6"));
                vStarting.setBackgroundColor(Color.parseColor("#27acf6"));
                tvTostart.setTextColor(Color.parseColor("#666666"));
                vTostart.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAll.setTextColor(Color.parseColor("#666666"));
                vAll.setBackgroundColor(Color.parseColor("#ffffff"));
                tvEnd.setTextColor(Color.parseColor("#666666"));
                vEnd.setBackgroundColor(Color.parseColor("#ffffff"));
                state=3;
                mAdapter.setState(state);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_end:
                tvEnd.setTextColor(Color.parseColor("#27acf6"));
                vEnd.setBackgroundColor(Color.parseColor("#27acf6"));
                tvTostart.setTextColor(Color.parseColor("#666666"));
                vTostart.setBackgroundColor(Color.parseColor("#ffffff"));
                tvStarting.setTextColor(Color.parseColor("#666666"));
                vStarting.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAll.setTextColor(Color.parseColor("#666666"));
                vAll.setBackgroundColor(Color.parseColor("#ffffff"));
                state=4;
                mAdapter.setState(state);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this,TimeChooseActivity.class));
    }
}
