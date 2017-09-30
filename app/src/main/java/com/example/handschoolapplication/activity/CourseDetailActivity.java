package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.fragment.CdDetailFragment;
import com.example.handschoolapplication.fragment.CdPingJiaFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDetailActivity extends BaseActivity {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.content)
    LinearLayout content;
    private int mIndex;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_course_detail;
    }

    private void initView() {
        CdDetailFragment cdDetailFragment = new CdDetailFragment();
        CdPingJiaFragment cdPingJiaFragment = new CdPingJiaFragment();
        fragments = new Fragment[]{cdDetailFragment, cdPingJiaFragment};

        //开启事务

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.content, cdDetailFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }

    @OnClick({R.id.rl_back, R.id.tv_cd_detail, R.id.tv_cd_pingjia, R.id.iv_cd_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_cd_detail:
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                setIndexSelected(0);
                break;
            case R.id.tv_cd_pingjia:
                tv1.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.VISIBLE);
                setIndexSelected(1);
                break;
            case R.id.iv_cd_more:
                break;
        }
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(fragments[mIndex]);
        //判断是否添加
        if (!fragments[index].isAdded()) {
            ft.add(R.id.content, fragments[index]).show(fragments[index]);
        } else {
            ft.show(fragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex = index;

    }
}
