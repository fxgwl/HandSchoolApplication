package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassCourseAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.ClassCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassCourseFragment extends BaseFragment {

    private View view;

    private ListView lvClassCourse;
    private List<ClassCourse> mList;
    private ClassCourseAdapter mAdapter;

    public ClassCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        lvClassCourse = (ListView) view.findViewById(R.id.lv_class_course);
        mAdapter = new ClassCourseAdapter(getActivity());
        lvClassCourse.setAdapter(mAdapter);
        initViewData();
        return view;
    }

    private void initViewData() {
        mList = new ArrayList<>();
        mList.add(new ClassCourse());
        mList.add(new ClassCourse());
        mList.add(new ClassCourse());
        mList.add(new ClassCourse());

        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_course;
    }

}
