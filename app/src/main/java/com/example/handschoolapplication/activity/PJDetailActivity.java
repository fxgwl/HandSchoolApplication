package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.PJDAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PJDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pjd_mlv)
    MyListView pjdMlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvTitle.setText("评价详情");
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pjdetail;
    }

    //初始化
    private void initView() {
        PJDAdapter pjdAdapter = new PJDAdapter(this, new ArrayList<String>());
        pjdMlv.setAdapter(pjdAdapter);
    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
}
