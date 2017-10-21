package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HPClassAdapter;
import com.example.handschoolapplication.adapter.HPCourseAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassBean;
import com.example.handschoolapplication.bean.CourseBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search_title)
    TextView tvSearchTitle;
    @BindView(R.id.rb_search_type)
    CheckBox rbSearchType;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    private String search;
    private List<CourseBean.DataBean> courseBeanList = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList = new ArrayList<>();
    private HPCourseAdapter courseAdapter;
    private HPClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        search = getIntent().getStringExtra("search");
        courseAdapter = new HPCourseAdapter(courseBeanList, this);
        classAdapter = new HPClassAdapter(this, classBeanList);
        initView();
    }

    private void initView() {
        etSearch.setText(search);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_result;
    }

    @OnClick({R.id.iv_back, R.id.tv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_search:
                tvSearchTitle.setText("当前搜索：  "+etSearch.getText().toString());
                if (rbSearchType.isChecked())setViewCourseData();//课堂
                else setViewSchoolData();//学堂
                break;
        }
    }

    private void setViewSchoolData() {
        OkHttpUtils.post()
                .url(Internet.SCHOOLSEARCH)
                .addParams("mechanism_name", etSearch.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SearchActivity.java:107)" + response);
                        Gson gson = new Gson();
                        classBeanList.clear();
                        lvSearch.setAdapter(classAdapter);
                        if (response.contains("没有信息")) {
                            Toast.makeText(SearchResultActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            classBeanList.addAll(gson.fromJson(response, ClassBean.class).getData());
                        }
                        classAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void setViewCourseData() {

        OkHttpUtils.post()
                .url(Internet.COURSESEARCH)
                .addParams("course_name", etSearch.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SearchActivity.java:107)" + response);
                        Gson gson = new Gson();
                        courseBeanList.clear();
                        lvSearch.setAdapter(courseAdapter);
                        if (response.contains("没有信息")) {
                            Toast.makeText(SearchResultActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            courseBeanList.addAll(gson.fromJson(response, CourseBean.class).getData());
                        }
                        courseAdapter.notifyDataSetChanged();
                    }
                });
    }


}
