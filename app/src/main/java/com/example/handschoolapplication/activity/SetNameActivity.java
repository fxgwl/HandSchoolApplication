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

public class SetNameActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("会员昵称");

        String name = getIntent().getStringExtra("name");
        etName.setText(name);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_name;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.tv_save,R.id.iv_del_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.iv_del_name:
                etName.setText("");
                break;
            case R.id.tv_save:
                String name = etName.getText().toString().trim();
                setResult(44,new Intent().putExtra("name",name));
                finish();
                break;
        }
    }

}
