package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.CourseNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CourseNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Axehome_Mr.Z on 2019/1/29
 */
public class SchoolNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_learn_news)
    PullToRefreshListView lvLearnNews;
    private String course_id;
    private int page;
    private List<CourseNewsBean.DataBean> mList;
    private CourseNewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        course_id = getIntent().getStringExtra("course_id");
        initView();

        initData("0");
    }

    private void initView() {

        tvTitle.setText("通知消息");

        mList = new ArrayList<>();
        mAdapter = new CourseNewsAdapter(this, mList);
        lvLearnNews.setAdapter(mAdapter);

        // 1.设置刷新模式
        lvLearnNews.setMode(PullToRefreshBase.Mode.BOTH);
        // 上拉加载更多，分页加载
        lvLearnNews.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        lvLearnNews.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        lvLearnNews.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        lvLearnNews.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        lvLearnNews.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        lvLearnNews.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        setRefresh();
    }

    private void initData(String page) {

        HashMap<String, String> params = new HashMap<>();
        params.put("course_id", course_id);
        params.put("page", page);
        params.put("limit", "10");

        OkHttpUtils.post()
                .url(Internet.COURSE_NEWS)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(SchoolNewsActivity.java:55)<---->" + e.getMessage());
                        lvLearnNews.onRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", "(SchoolNewsActivity.java:62)<---->" + response);
                        lvLearnNews.onRefreshComplete();
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(SchoolNewsActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    CourseNewsBean courseNewsBean = new Gson().fromJson(response, CourseNewsBean.class);
                                    mList.addAll(courseNewsBean.getData());
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(SchoolNewsActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void setRefresh() {
        lvLearnNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                page = 0;
                if (mList != null)
                    mList.clear();
                initData(String.valueOf(page));
//                isMore = true;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                // 自定义上拉header内容

                lvLearnNews.getLoadingLayoutProxy(false, true)
                        .setPullLabel("上拉加载...");
                lvLearnNews.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("正在为你加载更多内容...");
                lvLearnNews.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("松开自动加载...");
                page++;
                initData(String.valueOf(page));


            }
        });
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_learn_news;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }
}
