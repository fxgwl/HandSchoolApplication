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
import com.example.handschoolapplication.adapter.LearnNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearnNewsBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class LearnNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_learn_news)
    PullToRefreshListView lvLearnNews;
    private List<LearnNewsBean> mList;
    private LearnNewsAdapter mAdapter;
    private String userId;

    private int page = 0;
    private String limit = "10";
    FinishRefresh onRefreshComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(this, "userId", "");
        tvTitle.setText("学习消息");
        mList = new ArrayList<>();
        mAdapter = new LearnNewsAdapter(LearnNewsActivity.this, mList);
        lvLearnNews.setAdapter(mAdapter);

        initView();
//        initData();

//        lvLearnNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

        mAdapter.setListener(new LearnNewsAdapter.OnItemClickListener() {
            @Override
            public void setIconClick(int position) {
//                LearnNewsBean learnNewsBean = mList.get(position-1);
//                startActivity(new Intent(LearnNewsActivity.this, LearnNewsDetailActivity.class).putExtra("learnNewsBean", learnNewsBean));
                String school_id = mList.get(position).getSchool_id();
                startActivity(new Intent(LearnNewsActivity.this,ClassActivity.class).putExtra("school_id",school_id));
            }

            @Override
            public void setLearnClick(int position) {
                LearnNewsBean learnNewsBean = mList.get(position);
                startActivity(new Intent(LearnNewsActivity.this, LearnNewsDetailActivity.class).putExtra("learnNewsBean", learnNewsBean));
            }
        });

    }

    private void initView() {

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
                mList.clear();
                initData();
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
                initData();


            }
        });
    }

    private void initData() {
        OkHttpUtils.post()
                .url(InternetS.LEARNNEWS)
                .addParams("user_id", userId)
                .addParams("page", page+"")
                .addParams("limit", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearnNewsActivity.java:53)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearnNewsActivity.java:60)" + response);

                        if (response.contains("暂无信息")|| TextUtils.isEmpty(response)){
                            Toast.makeText(LearnNewsActivity.this, "暂无信息", Toast.LENGTH_SHORT).show();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                        }else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mList.addAll((Collection<? extends LearnNewsBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<LearnNewsBean>>() {
                                }.getType()));
                                mAdapter.notifyDataSetChanged();
                                onRefreshComplete = new FinishRefresh();
                                onRefreshComplete.execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
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
            if (lvLearnNews!=null)
            lvLearnNews.onRefreshComplete();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        mList.clear();
        initData();
    }
}
