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

public class SetStudentNameActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_student_name)
    EditText etStudentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("设置学生姓名");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_student_name;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_save:
                String studentName = etStudentName.getText().toString().trim();
                setResult(22,new Intent().putExtra("student",studentName));
                finish();
                break;
        }
    }
}
