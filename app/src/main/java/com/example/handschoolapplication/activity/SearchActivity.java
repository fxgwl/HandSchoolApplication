package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.handschoolapplication.bean.HistorySearch;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search_title)
    TextView tvSearchTitle;
    @BindView(R.id.lv_history)
    ListView lvHistory;
    @BindView(R.id.rb_search_type)
    CheckBox rbSearchType;

    private List<HistorySearch> mlist;
    private MySearchAdapter mySearchAdapter;

    private List<CourseBean.DataBean> courseBeanList = new ArrayList<>();
    private List<ClassBean.DataBean> classBeanList = new ArrayList<>();
    private HPCourseAdapter courseAdapter;
    private HPClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseAdapter = new HPCourseAdapter(courseBeanList, this);
        classAdapter = new HPClassAdapter(this, classBeanList);
        initViewData();
    }

    private void initViewData() {

        mlist = new ArrayList<>();
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mlist.add(new HistorySearch());
        mySearchAdapter = new MySearchAdapter();
        lvHistory.setAdapter(mySearchAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.iv_back, R.id.tv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_search:
                tvSearchTitle.setText("搜索结果");
//                Internet.COURSESEARCH
                //搜索成功调用的方法
                if (TextUtils.isEmpty(etSearch.getText().toString())) {
                    Toast.makeText(this, "搜索不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rbSearchType.isChecked()) {
                    //初始化课堂
                    setViewCourseData();
                } else {
                    //初始化学堂
                    setViewSchoolData();
                }
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
                        lvHistory.setAdapter(classAdapter);
                        if (response.contains("没有信息")) {
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
                        lvHistory.setAdapter(courseAdapter);
                        if (response.contains("没有信息")) {
                        } else {
                            courseBeanList.addAll(gson.fromJson(response, CourseBean.class).getData());
                        }
                        courseAdapter.notifyDataSetChanged();
                    }
                });
    }

    class MySearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(SearchActivity.this, R.layout.item_history_lv, null);
                holder.textView = (TextView) view.findViewById(R.id.tv_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            return view;
        }

        class ViewHolder {
            TextView textView;
        }
    }
}
