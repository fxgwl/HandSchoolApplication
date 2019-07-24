package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.OnClick;

public class RegisterToActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register_to;
    }

    @OnClick({R.id.rl_back, R.id.ll_person, R.id.ll_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_person:
                //个人注册
                startActivity(new Intent(RegisterToActivity.this,RegisterPersonActivity.class)
                        .putExtra("flag","no")
                        .putExtra("type","0"));
                finish();
                break;
            case R.id.ll_company:
                //企业注册
                startActivity(new Intent(RegisterToActivity.this,RegisterPersonActivity.class)
                        .putExtra("type","1"));
                finish();
                break;
        }
    }
}
