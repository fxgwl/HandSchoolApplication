package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.AllCourseAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearnStateBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AllCourseActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_all_course)
    ListView lvAllCourse;

    private List<LearnStateBean> mList = new ArrayList<>();
    private AllCourseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        tvTitle.setText("全部课程");
    }

    private void initData() {
        mList.add(new LearnStateBean());
        mList.add(new LearnStateBean());
        mList.add(new LearnStateBean());
        mList.add(new LearnStateBean());
        mList.add(new LearnStateBean());
        mList.add(new LearnStateBean());

        mAdapter = new AllCourseAdapter(mList, this);
        lvAllCourse.setAdapter(mAdapter);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_all_course;
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
