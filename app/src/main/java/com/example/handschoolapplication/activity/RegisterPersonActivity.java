package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.CountDownTimerUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPersonActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwds)
    EditText etPwds;
    @BindView(R.id.iv_checkbox)
    ImageView ivCheckbox;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private CountDownTimerUtils countDownTimerUtils;
    private int flag = 0;//默认没选用户协议
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countDownTimerUtils = new CountDownTimerUtils(tvGetCode, 59000, 1000);
        type = getIntent().getStringExtra("type");
        switch (type) {
            case "per":
                btnRegister.setText("注册");
                break;
            case "com":
                btnRegister.setText("下一步");
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register_person;
    }

    @OnClick({R.id.rl_back, R.id.tv_get_code, R.id.btn_register, R.id.iv_checkbox, R.id.tv_to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_get_code:
                countDownTimerUtils.start();
                break;
            case R.id.btn_register:
                if (flag == 1) {
                    initData();
                    switch (type){
                        case "per":
                            startActivity(new Intent(RegisterPersonActivity.this, LoginActivity.class).putExtra("type",type));
                            finish();
                            break;
                        case "com":
                            startActivity(new Intent(RegisterPersonActivity.this, AddDataActivity.class));
                            finish();
                            break;
                    }
                } else {
                    Toast.makeText(this, "请先阅读并同意《用户协议》", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_checkbox:
                if (flag == 0) {
                    flag = 1;
                    ivCheckbox.setImageResource(R.drawable.checkboxs);
                } else {
                    flag = 0;
                    ivCheckbox.setImageResource(R.drawable.checkbox);
                }
                break;
            case R.id.tv_to_login:
                startActivity(new Intent(RegisterPersonActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    //加载数据
    private void initData() {

    }
}
