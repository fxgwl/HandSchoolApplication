package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.EducationAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.Education;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/27.
 */

public class EducationActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_education)
    ListView lvEducation;

    private List<Education> mList;
    private EducationAdapter educationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("教育资讯");
        ivMenu.setVisibility(View.VISIBLE);

        initViewData();

        initListener();

    }

    private void initListener() {

        lvEducation.setOnItemClickListener(this);
    }

    private void initViewData() {
        mList=new ArrayList<>();
        mList.add(new Education());
        mList.add(new Education());
        mList.add(new Education());
        mList.add(new Education());

        educationAdapter=new EducationAdapter(mList,this);
        lvEducation.setAdapter(educationAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_education;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                break;
            case R.id.iv_menu:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(EducationActivity.this,EducationDetailActivity.class));
    }
}
