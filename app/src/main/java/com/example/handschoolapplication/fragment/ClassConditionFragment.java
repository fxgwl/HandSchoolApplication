package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ImagePagerActivity;
import com.example.handschoolapplication.adapter.ImageAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.SchoolEnviBean;
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
    private String school_environment;


    public ClassConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        school_id = getArguments().getString("school_id");
        lvCondition = (ListView) view.findViewById(R.id.lv_condition);
        mList = new ArrayList<>();
        mAdapter = new ImageAdapter(getActivity());
        lvCondition.setAdapter(mAdapter);
        initView();

        lvCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
//                Log.e("bbb", String.valueOf(position));
//                Log.e("bbb", "mContext" + "" + "==" + "photos" +listImage.toString() + "==" + "position" + a + "==" + "imageSize" + listImage.size());
//                if (school_environment.contains(",")){
//                    final String[] split = school_environment.split(",");
//                    ArrayList<String> aaa = new ArrayList<>();
//                    for (int i = 0; i < split.length; i++) {
//                        aaa.add(Internet.BASE_URL + split[i]);
//                    }
//                }
                ImagePagerActivity.startImagePagerActivity(getActivity(), mList, position, imageSize);

            }
        });
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
                        Log.e("aaa",
                                "(ClassConditionFragment.java:91)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ClassActivity.java:79)" + response);
                        if (TextUtils.isEmpty(response)){
                            return;
                        }
                        Gson gson = new Gson();
//                        SchoolIntroBean school = gson.fromJson(response, SchoolIntroBean.class);
                        SchoolEnviBean schoolEnviBean = gson.fromJson(response, SchoolEnviBean.class);
                        SchoolEnviBean.DataBean.SchoolDataBean schoolData = schoolEnviBean.getData().getSchoolData();
                        String picture_one = schoolData.getPicture_one();
                        String picture_two = schoolData.getPicture_two();
                        String picture_three = schoolData.getPicture_three();
                        if (!TextUtils.isEmpty(picture_one)) {
                            mList.add(Internet.BASE_URL + picture_one);
                        }
                        if (!TextUtils.isEmpty(picture_two)) {
                            mList.add(Internet.BASE_URL + picture_two);
                        }
                        if (!TextUtils.isEmpty(picture_three)) {
                            mList.add(Internet.BASE_URL + picture_three);
                        }

//                            school_environment = schoolEnviBean.getData().getSchoolData();
//                            if (school_environment.contains(",")){
//                                String[] split = school_environment.split(",");
//                                for (int i = 0; i < split.length; i++) {
//                                    mList.add(Internet.BASE_URL +split[i]);
//                                }
//                            }else {
//                                mList.add(Internet.BASE_URL + school_environment);
//                            }

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
