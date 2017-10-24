package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.LearningAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearningCourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LearningActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_learning_course)
    ListView lvLearningCourse;
    private LearningAdapter mAdapter;
    private List<LearningCourseBean> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();//实例化视图
        initData();//获取数据

    }

    private void initView() {
        tvTitle.setText("学习中");
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new LearningCourseBean());
        mList.add(new LearningCourseBean());
        mList.add(new LearningCourseBean());
        mList.add(new LearningCourseBean());
        mList.add(new LearningCourseBean());

        mAdapter = new LearningAdapter(mList, this);
        lvLearningCourse.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_learning;
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
