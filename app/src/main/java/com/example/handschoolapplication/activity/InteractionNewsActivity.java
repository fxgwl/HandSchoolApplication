package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.InteractionNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.HasEvaBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class InteractionNewsActivity extends BaseActivity implements InteractionNewsAdapter.OnDetailClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_interaction_news)
    PullToRefreshListView lvInteractionNews;
    private InteractionNewsAdapter mAdapter;
    private List<HasEvaBean.DataBean> mList = new ArrayList<>();
    private String user_id;
    private String user_type;

    private int page = 0;
    private String limit = "10";
    private FinishRefresh onRefreshComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("互动消息");
        user_id = (String) SPUtils.get(this, "userId", "");
        mAdapter = new InteractionNewsAdapter(this, mList, this);
        lvInteractionNews.setAdapter(mAdapter);
        user_type = (String) SPUtils.get(this, "user_type", "");
        page = 0;
        mList.clear();
        if ("1".equals(user_type)) {
            initDataSchool();
        } else {
            initData();
        }

        initView();

    }

    private void initDataSchool() {
        Log.e("aaa",
                "(InteractionNewsActivity.java:148)<--user_id-->" + user_id);
        OkHttpUtils.post()
                .url(Internet.INTERACT_MESSAGE_SCHOOL)
                .addParams("user_id", user_id)
                .addParams("page", page + "")
                .addParams("limit", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(InteractionNewsActivity.java:157)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(InteractionNewsActivity.java:62)" + response);
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(InteractionNewsActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Gson gson = new Gson();
                            try {
                                mList.addAll(gson.fromJson(response, HasEvaBean.class).getData());
                            } catch (Exception e) {

                            }
                            mAdapter.notifyDataSetChanged();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                        }

                    }
                });
    }

    private void initData() {

        Log.e("aaa",
                "(InteractionNewsActivity.java:86)<--page == -->" + page);
        OkHttpUtils.post()
                .url(Internet.HASCOMMENT_s)
                .addParams("user_id", user_id)
                .addParams("page", page + "")
                .addParams("limit", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(InteractionNewsActivity.java:200)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(InteractionNewsActivity.java:62)" + response);

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            Toast.makeText(InteractionNewsActivity.this, "暂无互动信息", Toast.LENGTH_SHORT).show();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                        } else {
                            Gson gson = new Gson();
                            try {
                                mList.addAll(gson.fromJson(response, HasEvaBean.class).getData());
                            } catch (Exception e) {

                            }
                            mAdapter.notifyDataSetChanged();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                        }

                    }
                });


    }

    private void initView() {

        // 1.设置刷新模式
        lvInteractionNews.setMode(PullToRefreshBase.Mode.BOTH);
        // 上拉加载更多，分页加载
        lvInteractionNews.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        lvInteractionNews.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        lvInteractionNews.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        lvInteractionNews.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        lvInteractionNews.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        lvInteractionNews.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        setRefresh();
    }

    private void setRefresh() {
        lvInteractionNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                page = 0;
                mList.clear();

                if ("1".equals(user_type)) {
                    initDataSchool();
                } else {
                    initData();
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                // 自定义上拉header内容

                lvInteractionNews.getLoadingLayoutProxy(false, true)
                        .setPullLabel("上拉加载...");
                lvInteractionNews.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("正在为你加载更多内容...");
                lvInteractionNews.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("松开自动加载...");
                page++;
                if ("1".equals(user_type)) {
                    initDataSchool();
                } else {
                    initData();
                }


            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_interaction_news;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mList != null) mList.clear();
        page = 0;
        if ("1".equals(user_type)) {
            initDataSchool();
        } else {
            initData();
        }

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

    @Override
    public void onDetailClick(int position) {
        Intent intent = new Intent(this, PJDetailActivity.class);
        intent.putExtra("interact_id", mList.get(position).getInteract_id());
        startActivityForResult(intent, 1);
    }

    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//          adapter.notifyDataSetChanged();
            if (lvInteractionNews != null)
                lvInteractionNews.onRefreshComplete();
        }
    }
}
