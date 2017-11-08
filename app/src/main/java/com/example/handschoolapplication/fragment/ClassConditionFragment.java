package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ImageAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.SchoolIntroBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassConditionFragment extends BaseFragment {

    private View view;
    private ListView lvCondition;
    private List<String> mList;
    private ImageAdapter mAdapter;
    private String school_id;

    public ClassConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater,container,savedInstanceState);
        school_id = getArguments().getString("school_id");
        lvCondition= (ListView) view.findViewById(R.id.lv_condition);
        mList=new ArrayList<>();
        mAdapter=new ImageAdapter(getActivity());
        lvCondition.setAdapter(mAdapter);
        initView();
        return view;
    }
    //初始化学堂简介
    private void initView() {
        mList.clear();
        OkHttpUtils.post()
                .url(Internet.SCHOOLINTO)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassActivity.java:79)" + response);
                        Gson gson = new Gson();
                        SchoolIntroBean school = gson.fromJson(response, SchoolIntroBean.class);
                        String school_environment = school.getData().getSchoolData().getSchool_environment();
                        mList.add(Internet.BASE_URL+school_environment);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_condition;
    }

}
