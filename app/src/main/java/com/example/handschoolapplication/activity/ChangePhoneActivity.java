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

public class ChangePhoneActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.et_changephone_pwd)
    EditText etChangephonePwd;
    @BindView(R.id.et_changephone_newphone)
    EditText etChangephoneNewphone;
    @BindView(R.id.et_changephone_code)
    EditText etChangephoneCode;
    @BindView(R.id.tv_changephone_obtaincode)
    TextView tvChangephoneObtaincode;
    @BindView(R.id.tv_changephone_submit)
    TextView tvChangephoneSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("修改手机号");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_phone;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_changephone_obtaincode, R.id.tv_changephone_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            //获取验证码
            case R.id.tv_changephone_obtaincode:
                break;
            //提交
            case R.id.tv_changephone_submit:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
