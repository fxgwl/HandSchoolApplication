package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.EducationAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.TeachNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/27.
 */

public class EducationActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_education)
    ListView lvEducation;

    private ArrayList<TeachNewsBean.DataBean> mList = new ArrayList<>();
    private EducationAdapter educationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("教育资讯");
        educationAdapter = new EducationAdapter(mList, this);
        lvEducation.setAdapter(educationAdapter);
        lvEducation.setOnItemClickListener(this);
        initTeachNews();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_education;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(EducationActivity.this, EducationDetailActivity.class));
    }

    private void initTeachNews() {
        OkHttpUtils.post()
                .url(Internet.TEACHNEWS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:185)" + response);
                        Gson gson = new Gson();
                        mList.clear();
                        mList.addAll(gson.fromJson(response, TeachNewsBean.class).getData());
                        educationAdapter.notifyDataSetChanged();
                    }
                });
    }
}
