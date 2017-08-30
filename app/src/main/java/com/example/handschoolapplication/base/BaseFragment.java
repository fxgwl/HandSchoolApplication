package com.example.handschoolapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/22.
 */

public abstract class BaseFragment extends Fragment {

    protected FragmentActivity activity;
    protected View mRootView;
    private Unbinder bind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getContentViewId() != 0) {
            mRootView = inflater.inflate(getContentViewId(), null);
        } else {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        bind = ButterKnife.bind(this, mRootView);
        Log.e(this.getClass().getName(), "--->onCreateView");
        initData(getArguments());
        return mRootView;
    }

    public abstract int getContentViewId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    /**
     * 初始化数据
     *
     * @param arguments 接收到的从其他地方传递过来的参数
     */
    public void initData(Bundle arguments){};

    @Override
    public void onResume() {
        super.onResume();
        Log.e(this.getClass().getName(),"--->onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(this.getClass().getName(),"--->onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        Log.e(this.getClass().getName(),"--->onDestroy");
    }
}
