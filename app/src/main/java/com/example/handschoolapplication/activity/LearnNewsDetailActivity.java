package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearnNewsBean;

import butterknife.BindView;
import butterknife.OnClick;

public class LearnNewsDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private LearnNewsBean learnNewsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("学习消息");
        learnNewsBean = (LearnNewsBean) getIntent().getSerializableExtra("learnNewsBean");
        String school_name = learnNewsBean.getSchool_name();
        tvClassName.setText(school_name);
        String message_content = learnNewsBean.getMessage_content();
        tvContent.setText(message_content);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_learn_news_detail;
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
