package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.InteractionNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.InteractionNewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InteractionNewsActivity extends BaseActivity implements InteractionNewsAdapter.OnDetailClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_interaction_news)
    ListView lvInteractionNews;
    private List<InteractionNewsBean> mList;
    private InteractionNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("互动消息");
        initData();
    }

    private void initData() {

        mList = new ArrayList<>();
        mList.add(new InteractionNewsBean());
        mList.add(new InteractionNewsBean());
        mList.add(new InteractionNewsBean());
        mList.add(new InteractionNewsBean());
        mList.add(new InteractionNewsBean());

        mAdapter = new InteractionNewsAdapter(this, mList, this);
        lvInteractionNews.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_interaction_news;
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

    @Override
    public void onDetailClick(int position) {
        Toast.makeText(this, "查看评价详情", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,PJDetailActivity.class));
    }
}
