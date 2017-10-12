package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ClassCourseAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.ClassCourse;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassCourseFragment extends BaseFragment {

    private View view;

    private ListView lvClassCourse;
    private List<ClassCourse.DataBean> mList = new ArrayList<>();
    ;
    private ClassCourseAdapter mAdapter;
    private String school_id;

    public ClassCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        lvClassCourse = (ListView) view.findViewById(R.id.lv_class_course);
        school_id = (String) SPUtils.get(getActivity(), "school_id", "");
        mAdapter = new ClassCourseAdapter(getActivity(), mList);
        lvClassCourse.setAdapter(mAdapter);
        initViewData();
        return view;
    }

    private void initViewData() {
        OkHttpUtils.post()
                .url(Internet.COURSEINFO)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassCourseFragment.java:70)" + response);
                        Gson gson = new Gson();
                        mList.clear();
                        mList.addAll(gson.fromJson(response, ClassCourse.class).getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_course;
    }

}
