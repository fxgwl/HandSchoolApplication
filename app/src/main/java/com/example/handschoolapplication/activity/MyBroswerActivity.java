package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.BroswerAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.BroCourseBean;
import com.example.handschoolapplication.bean.BroswerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBroswerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_mybroswer)
    ListView lvMybroswer;

    private BroswerAdapter broswerAdapter;
    private List<BroswerBean> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("我的足迹");

        initData();


    }

    private void initData() {
        ArrayList<BroswerBean> mlist = new ArrayList<>();
        ArrayList<BroCourseBean> mmlist = new ArrayList<>();
        broswerAdapter = new BroswerAdapter(this,mlist,mmlist);
        lvMybroswer.setAdapter(broswerAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_broswer;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
        }
    }

}
