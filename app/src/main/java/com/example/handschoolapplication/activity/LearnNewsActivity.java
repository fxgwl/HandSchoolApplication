package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.LearnNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearnNewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LearnNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_learn_news)
    ListView lvLearnNews;
    private List<LearnNewsBean> mList;
    private LearnNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("学习消息");
        initData();

    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new LearnNewsBean());
        mList.add(new LearnNewsBean());
        mList.add(new LearnNewsBean());
        mList.add(new LearnNewsBean());
        mList.add(new LearnNewsBean());
        mAdapter = new LearnNewsAdapter(this, mList);
        lvLearnNews.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_learn_news;
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
