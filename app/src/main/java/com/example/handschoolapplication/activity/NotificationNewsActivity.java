package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.NotificationAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.NotificationBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotificationNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_notification_news)
    ListView lvNotificationNews;

    private List<NotificationBean> mList;
    private NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("通知消息");

        initData();

    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new NotificationBean());
        mList.add(new NotificationBean());
        mList.add(new NotificationBean());
        mList.add(new NotificationBean());

        mAdapter = new NotificationAdapter(this, mList);
        lvNotificationNews.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_notification_news;
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
