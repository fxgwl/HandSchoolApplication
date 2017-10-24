package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ConsultNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ConsultNewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ConsultNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_consult_news)
    ListView lvConsultNews;

    private ConsultNewsAdapter mAdapter;
    private List<ConsultNewsBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("咨询消息");

        initData();
    }

    private void initData() {
        mList = new ArrayList<>();

        mList.add(new ConsultNewsBean());
        mList.add(new ConsultNewsBean());
        mList.add(new ConsultNewsBean());
        mList.add(new ConsultNewsBean());
        mList.add(new ConsultNewsBean());

        mAdapter = new ConsultNewsAdapter(this, mList);
        lvConsultNews.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_consult_news;
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
