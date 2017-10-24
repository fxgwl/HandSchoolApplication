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
import com.example.handschoolapplication.fragment.UncanDisFragment;
import com.example.handschoolapplication.fragment.UnusedDisFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MyDiscountcouponActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_unused)
    TextView tvUnused;
    @BindView(R.id.view_unused)
    View viewUnused;
    @BindView(R.id.tv_uncan)
    TextView tvUncan;
    @BindView(R.id.view_uncan)
    View viewUncan;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;

    private Fragment currentFragment;
    private UnusedDisFragment unusedDisFragment;
    private UncanDisFragment uncanDisFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("我的优惠券");

        initFirstFragment();
    }

    private void initFirstFragment() {
        if (unusedDisFragment==null){
            unusedDisFragment=new UnusedDisFragment();
        }
        if (!unusedDisFragment.isAdded()) {

            /** 如果第一个未被添加，则添加到管理器中*/
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, unusedDisFragment).commit();

            /** 记录当前Fragment*/
            currentFragment = unusedDisFragment;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_discountcoupon;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_unused, R.id.ll_uncan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_unused:
                tvUnused.setTextColor(Color.parseColor("#27acf6"));
                tvUncan.setTextColor(Color.parseColor("#666666"));
                viewUnused.setBackgroundColor(Color.parseColor("#27acf6"));
                viewUncan.setBackgroundColor(Color.parseColor("#ffffff"));
                if (unusedDisFragment == null) unusedDisFragment = new UnusedDisFragment();
                addFragment(getSupportFragmentManager(), unusedDisFragment);
                break;
            case R.id.ll_uncan:
                tvUnused.setTextColor(Color.parseColor("#666666"));
                tvUncan.setTextColor(Color.parseColor("#27acf6"));
                viewUnused.setBackgroundColor(Color.parseColor("#ffffff"));
                viewUncan.setBackgroundColor(Color.parseColor("#27acf6"));
                if (uncanDisFragment==null)uncanDisFragment=new UncanDisFragment();
                addFragment(getSupportFragmentManager(),uncanDisFragment);
                break;
        }
    }

    private void addFragment(FragmentManager manager, Fragment fragment) {

        FragmentTransaction ft = manager.beginTransaction();

        if (fragment == currentFragment) {
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
