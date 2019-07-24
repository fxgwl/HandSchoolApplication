package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private double[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        search = getIntent().getStringExtra("search");
        locations = getIntent().getDoubleArrayExtra("location");
        courseAdapter = new HPCourseAdapter(courseBeanList, this);
        classAdapter = new HPClassAdapter(this, classBeanList);

        initView();

        rbSearchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setViewCourseData();
                }else {
                    setViewSchoolData();
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    tvSearchTitle.setText("当前搜索：  "+etSearch.getText().toString().trim());
                    if (rbSearchType.isChecked())setViewCourseData();//课堂
                    else setViewSchoolData();//机构
                }
                return false;
            }
        });
    }

    private void initView() {
        etSearch.setText(search);
        tvSearchTitle.setText("当前搜索：  "+etSearch.getText().toString().trim());
        setViewCourseData();
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
                tvSearchTitle.setText("当前搜索：  "+etSearch.getText().toString().trim());
                if (rbSearchType.isChecked())setViewCourseData();//课堂
                else setViewSchoolData();//机构
                break;
        }
    }

    private void setViewSchoolData() {
        String search = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)){
            Toast.makeText(this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post()
                .url(Internet.SCHOOLSEARCH)
                .addParams("mechanism_name", search)
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
                            Toast.makeText(SearchResultActivity.this, "暂无搜索结果", Toast.LENGTH_SHORT).show();
                        } else {
                            classBeanList.addAll(gson.fromJson(response, ClassBean.class).getData());

                        }
                        classAdapter.setLocations(locations);
                        classAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void setViewCourseData() {

        String search = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)){
            Toast.makeText(this, "搜索内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.post()
                .url(Internet.COURSESEARCH)
                .addParams("course_name", search)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa","(SearchResultActivity.java:162)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SearchActivity.java:107)" + response);
                        Gson gson = new Gson();
                        courseBeanList.clear();
                        lvSearch.setAdapter(courseAdapter);
                        if (response.contains("没有信息")) {
                            Toast.makeText(SearchResultActivity.this, "暂无搜索结果", Toast.LENGTH_SHORT).show();
                        } else {
                            courseBeanList.addAll(gson.fromJson(response, CourseBean.class).getData());

                        }
                        courseAdapter.setLocation(locations);
                        courseAdapter.notifyDataSetChanged();
                    }
                });
    }


}
