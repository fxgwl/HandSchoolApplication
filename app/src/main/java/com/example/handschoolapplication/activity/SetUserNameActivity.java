package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SetUserNameActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("会员名称");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_user_name;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.tv_save:
                String username = etUsername.getText().toString().trim();
                setResult(22,new Intent().putExtra("username",username));
                finish();
                break;
        }
    }
}
