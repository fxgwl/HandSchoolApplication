package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.SearchActivity;
import com.example.handschoolapplication.adapter.FindCourseAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.FindCourse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {

    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_current_location)
    TextView tvCurrentLocation;
    @BindView(R.id.lv_ff_course)
    ListView lvFfCourse;
    private View view;

    private List<FindCourse> findCourseList;
    private FindCourseAdapter findCourseAdapter;


    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater,container,savedInstanceState);
//        unbinder = ButterKnife.bind(this, view);
        initLvData();
        return view;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_find;
    }


    private void initLvData() {

        findCourseList = new ArrayList<>();
        findCourseList.add(new FindCourse());
        findCourseList.add(new FindCourse());
        findCourseList.add(new FindCourse());
        findCourseAdapter = new FindCourseAdapter(findCourseList, getActivity());

        lvFfCourse.setAdapter(findCourseAdapter);

    }
    //    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    @OnClick({R.id.iv_search,R.id.tv_search})
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }
}
