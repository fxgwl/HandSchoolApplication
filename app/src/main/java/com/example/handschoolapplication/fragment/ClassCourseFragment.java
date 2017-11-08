package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.CourseHomePagerActivity;
import com.example.handschoolapplication.adapter.ClassCourseAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.ClassCourse;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassCourseFragment extends BaseFragment implements AdapterView.OnItemClickListener {

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
        EventBus.getDefault().register(ClassCourseFragment.this);
        school_id = (String) SPUtils.get(getActivity(), "school_id", "");
        mAdapter = new ClassCourseAdapter(getActivity(), mList);
        lvClassCourse.setAdapter(mAdapter);
        initViewData();
        lvClassCourse.setOnItemClickListener(this);
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
                        if (response.contains("没有信息")){}else {
                            Gson gson = new Gson();
                            mList.clear();
                            mList.addAll(gson.fromJson(response, ClassCourse.class).getData());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String event) {
        initCourseType(event);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_course;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(ClassCourseFragment.this);
    }

    //课程类型
    private void initCourseType(String s) {
        OkHttpUtils.post()
                .url(Internet.COURSESTATE)
                .addParams("school_id", school_id)
                .addParams("course_state", s)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassActivity.java:236)" + response);
                        Gson gson = new Gson();
                        mList.clear();
                        if (response.contains("没有信息")) {

                        } else {
                            mList.addAll(gson.fromJson(response, ClassCourse.class).getData());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ClassCourse.DataBean dataBean = mList.get(position);
        String school_id = dataBean.getSchool_id();
        String course_id = dataBean.getCourse_id();
        String schooluid = dataBean.getUser_id();

        Intent intent = new Intent(getActivity(), CourseHomePagerActivity.class);
        intent.putExtra("school_id",school_id);
        intent.putExtra("course_id",course_id);
        intent.putExtra("schooluid",schooluid);
        getActivity().startActivity(intent);
    }
}
