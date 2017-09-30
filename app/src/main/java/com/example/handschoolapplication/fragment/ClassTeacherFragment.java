package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.TeacherAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassTeacherFragment extends BaseFragment {

    private View view;
    private ListView lvTeacher;
    private List<Teacher> mList;
    private TeacherAdapter mAdapter;

    public ClassTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        lvTeacher = (ListView) view.findViewById(R.id.lv_teacher);
        mAdapter = new TeacherAdapter(getActivity());
        lvTeacher.setAdapter(mAdapter);
        initViewData();
        return view;
    }

    private void initViewData() {
        mList = new ArrayList<>();
        mList.add(new Teacher());
        mList.add(new Teacher());
        mList.add(new Teacher());
        mList.add(new Teacher());

        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_teacher;
    }

}
