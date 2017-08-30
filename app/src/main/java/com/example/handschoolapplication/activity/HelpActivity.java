package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HelpLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_help)
    ListView lvHelp;
    private HelpLvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tvTitle.setText("帮助");

        mAdapter = new HelpLvAdapter(HelpActivity.this);
        lvHelp.setAdapter(mAdapter);
        setListener();

    }

    private void setListener() {
        lvHelp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(HelpActivity.this,QuestionAnswerActivity.class));
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_help;
    }



    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.ll_help_service, R.id.ll_help_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //联系客服
            case R.id.ll_help_service:
                startActivity(new Intent(HelpActivity.this,HumanServiceActivity.class));
                break;
            //意见反馈
            case R.id.ll_help_feedback:
                startActivity(new Intent(HelpActivity.this,FeedBackActivity.class));
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
        }
    }
}
