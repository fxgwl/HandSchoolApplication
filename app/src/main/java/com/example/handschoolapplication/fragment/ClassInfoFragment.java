package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassInfoFragment extends BaseFragment {

    private View view;

    public ClassInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_info;
    }

}
