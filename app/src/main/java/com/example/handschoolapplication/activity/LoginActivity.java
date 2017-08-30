package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.rl_back, R.id.ll_phone_num, R.id.ll_password, R.id.btn_login, R.id.iv_wechat_login, R.id.iv_weibo_login, R.id.iv_qq_login,R.id.tv_forget_pwd, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                break;
            case R.id.ll_phone_num:
                break;
            case R.id.ll_password:
                break;
            case R.id.btn_login:
                startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("flag","com"));
//                startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("flag","per"));
                break;
            case R.id.iv_wechat_login:
                break;
            case R.id.iv_weibo_login:
                break;
            case R.id.iv_qq_login:
                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(LoginActivity.this,ForgetPwdActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegisterToActivity.class));
                break;
        }
    }
}
