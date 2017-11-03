package com.example.handschoolapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.fragment.LoveClassFragment;
import com.example.handschoolapplication.fragment.LoveCourseFragment;
import com.example.handschoolapplication.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyLoveActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_love_course)
    TextView tvLoveCourse;
    @BindView(R.id.v_line_course)
    View vLineCourse;
    @BindView(R.id.tv_love_class)
    TextView tvLoveClass;
    @BindView(R.id.v_line_class)
    View vLineClass;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    LoveClassFragment loveClassFragment;
    LoveCourseFragment loveCourseFragment;

    private Fragment currentFragment;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = (String) SPUtils.get(this, "userId", "");
        tvTitle.setText("我的收藏");
        tvEdit.setVisibility(View.VISIBLE);
        initFragment();
    }

    private void initFragment() {

        loveCourseFragment = new LoveCourseFragment();
        currentFragment = new Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, loveCourseFragment).commit();
        currentFragment = loveCourseFragment;

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_love;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_love_course, R.id.ll_love_class})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.ll_love_course:
                vLineClass.setBackgroundColor(Color.parseColor("#ffffff"));
                tvLoveCourse.setTextColor(Color.parseColor("#27acf6"));
                vLineCourse.setBackgroundColor(Color.parseColor("#27acf6"));
                tvLoveClass.setTextColor(Color.parseColor("#222222"));
                if (loveCourseFragment == null) loveCourseFragment = new LoveCourseFragment();
                selectIndex(getSupportFragmentManager(), loveCourseFragment);
                break;
            case R.id.ll_love_class:
                vLineClass.setBackgroundColor(Color.parseColor("#27acf6"));
                tvLoveCourse.setTextColor(Color.parseColor("#222222"));
                vLineCourse.setBackgroundColor(Color.parseColor("#ffffff"));
                tvLoveClass.setTextColor(Color.parseColor("#27acf6"));
                if (loveClassFragment == null) loveClassFragment = new LoveClassFragment();
                selectIndex(getSupportFragmentManager(), loveClassFragment);
                break;
        }
    }

    private void selectIndex(FragmentManager manager, Fragment fragment) {

        FragmentTransaction ft = manager.beginTransaction();
        if (currentFragment == fragment) {

            return;
        } else {
            if (!fragment.isAdded()) {
                ft.hide(currentFragment).add(R.id.fl_fragment, fragment).commit();
            } else {
                ft.hide(currentFragment).show(fragment).commit();
            }
        }

        currentFragment = fragment;
    }
}
