package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.et_feedback_content)
    EditText etFeedbackContent;
    @BindView(R.id.tv_feedback_submit)
    TextView tvFeedbackSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("意见反馈");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_feedback_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.tv_feedback_submit:
                break;
        }
    }
}
