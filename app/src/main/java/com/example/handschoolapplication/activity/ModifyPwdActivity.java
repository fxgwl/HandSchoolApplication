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

public class ModifyPwdActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.et_modifypwd_phone)
    EditText etModifypwdPhone;
    @BindView(R.id.et_modifypwd_code)
    EditText etModifypwdCode;
    @BindView(R.id.tv_modifypwd_sendcode)
    TextView tvModifypwdSendcode;
    @BindView(R.id.et_modifypwd_newpwd)
    EditText etModifypwdNewpwd;
    @BindView(R.id.et_modifypwd_surenewpwd)
    EditText etModifypwdSurenewpwd;
    @BindView(R.id.tv_modifypwd_submit)
    TextView tvModifypwdSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("修改密码");

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_modify_pwd;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_modifypwd_sendcode, R.id.tv_modifypwd_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            //发送验证码
            case R.id.tv_modifypwd_sendcode:
                break;
            //提交
            case R.id.tv_modifypwd_submit:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
