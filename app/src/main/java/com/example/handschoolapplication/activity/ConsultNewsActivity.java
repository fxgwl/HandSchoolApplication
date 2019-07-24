package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ConsultNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ConsultNewsBean;
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

public class ConsultNewsActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_consult_news)
    PullToRefreshListView lvConsultNews;

    private ConsultNewsAdapter mAdapter;
    private List<ConsultNewsBean> mList;
    private String userId;

    private int page = 0;
    private String limit = "10";
    private FinishRefresh onRefreshComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("咨询消息");
        userId = (String) SPUtils.get(this, "userId", "");
        mList = new ArrayList<>();
        mAdapter = new ConsultNewsAdapter(ConsultNewsActivity.this, mList);
        lvConsultNews.setAdapter(mAdapter);

        initView();

        initData();

        lvConsultNews.setOnItemClickListener(this);
    }

    private void initView() {

        // 1.设置刷新模式
        lvConsultNews.setMode(PullToRefreshBase.Mode.BOTH);
        // 上拉加载更多，分页加载
        lvConsultNews.getLoadingLayoutProxy(false, true).setPullLabel("加载更多");
        lvConsultNews.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中...");
        lvConsultNews.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载");
        // 下拉刷新
        lvConsultNews.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        lvConsultNews.getLoadingLayoutProxy(true, false).setRefreshingLabel("更新中...");
        lvConsultNews.getLoadingLayoutProxy(true, false).setReleaseLabel("松开更新");

        setRefresh();
    }

    private void setRefresh() {
        lvConsultNews.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

                lvConsultNews.getLoadingLayoutProxy(false, true)
                        .setPullLabel("上拉加载...");
                lvConsultNews.getLoadingLayoutProxy(false, true)
                        .setRefreshingLabel("正在为你加载更多内容...");
                lvConsultNews.getLoadingLayoutProxy(false, true)
                        .setReleaseLabel("松开自动加载...");
                page++;
                initData();


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
            if (lvConsultNews!=null)
            lvConsultNews.onRefreshComplete();
        }
    }

    private void initData() {

        Log.e("aaa",
                "(ConsultNewsActivity.java:138)<--page == -->"+page);
        OkHttpUtils.post()
                .url(InternetS.CONSULT_NEWS)
                .addParams("send_id", userId)
                .addParams("page", page+"")
                .addParams("limit", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ConsultNewsActivity.java:54)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ConsultNewsActivity.java:61)" + response);
                        if (response.contains("没有信息")|| TextUtils.isEmpty(response)){
                            Toast.makeText(ConsultNewsActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                            mAdapter.notifyDataSetChanged();
                            onRefreshComplete = new FinishRefresh();
                            onRefreshComplete.execute();
                        }else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray data = jsonObject.getJSONArray("data");
                                mList.addAll((Collection<? extends ConsultNewsBean>) new Gson().fromJson(data.toString(),new TypeToken<ArrayList<ConsultNewsBean>>(){}.getType()));
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

    @Override
    public int getContentViewId() {
        return R.layout.activity_consult_news;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ConsultNewsBean consultNewsBean = mList.get(position-1);
        String course_id = consultNewsBean.getCourse_id();
        String user_id = consultNewsBean.getUser_id();
        String consult_name = consultNewsBean.getConsult_name();

        Intent intent = new Intent(this,HumanServiceActivity.class);
        intent.putExtra("type", "1");
        intent.putExtra("course_id", course_id);
        intent.putExtra("send_id",user_id);
        intent.putExtra("name",consult_name);
        startActivity(intent);
    }
}
