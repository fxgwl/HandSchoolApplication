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
import com.example.handschoolapplication.fragment.HasEvaFragment;
import com.example.handschoolapplication.fragment.UnEvaFragment;
import com.example.handschoolapplication.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyEvaluateActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_uneva)
    TextView tvUneva;
    @BindView(R.id.v_uneva)
    View vUneva;
    @BindView(R.id.tv_has_eva)
    TextView tvHasEva;
    @BindView(R.id.view_has_eva)
    View viewHasEva;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    private UnEvaFragment unEvaFragment;
    private HasEvaFragment hasEvaFragment;
    private Fragment currentFragment;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = (String) SPUtils.get(this, "userId", "");
        initFirstFragment();

        tvTitle.setText("我的评价");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_evaluate;
    }

    private void initFirstFragment() {
        currentFragment = new Fragment();
        if (unEvaFragment == null) {
            unEvaFragment = new UnEvaFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, unEvaFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(unEvaFragment).commit();
        }
        currentFragment = unEvaFragment;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_uneva, R.id.ll_has_eva})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.ll_uneva:
                tvUneva.setTextColor(Color.parseColor("#27acf6"));
                vUneva.setBackgroundColor(Color.parseColor("#27acf6"));
                tvHasEva.setTextColor(Color.parseColor("#666666"));
                viewHasEva.setBackgroundColor(Color.parseColor("#ffffff"));
                if (unEvaFragment == null) unEvaFragment = new UnEvaFragment();
                addFragmentShow(getSupportFragmentManager(), unEvaFragment);
                currentFragment = unEvaFragment;
                break;
            case R.id.ll_has_eva:
                tvUneva.setTextColor(Color.parseColor("#666666"));
                vUneva.setBackgroundColor(Color.parseColor("#ffffff"));
                tvHasEva.setTextColor(Color.parseColor("#27acf6"));
                viewHasEva.setBackgroundColor(Color.parseColor("#27acf6"));
                if (hasEvaFragment == null) hasEvaFragment = new HasEvaFragment();
                addFragmentShow(getSupportFragmentManager(), hasEvaFragment);
                currentFragment = hasEvaFragment;
                break;
        }
    }

    private void addFragmentShow(FragmentManager manager, Fragment fragment) {

        FragmentTransaction ft = manager.beginTransaction();
        if (currentFragment != fragment) {
            if (!fragment.isAdded()) {
                ft.hide(currentFragment).add(R.id.fl_fragment, fragment).commit();
            } else {
                ft.hide(currentFragment).show(fragment).commit();
            }
        } else {
            return;
        }

        currentFragment = fragment;
    }
}
