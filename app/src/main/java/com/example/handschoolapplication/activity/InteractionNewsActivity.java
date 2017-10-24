package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.InteractionNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.HasEvaBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
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
    ListView lvInteractionNews;
    private InteractionNewsAdapter mAdapter;
    private List<HasEvaBean.DataBean> mList = new ArrayList<>();
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("互动消息");
        user_id = (String) SPUtils.get(this, "userId", "");
        mAdapter = new InteractionNewsAdapter(this, mList, this);
        lvInteractionNews.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        OkHttpUtils.post()
                .url(Internet.HASCOMMENT)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HasEvaFragment.java:71)" + response);
                        Gson gson = new Gson();
                        mList.clear();
                        try {
                            mList.addAll(gson.fromJson(response, HasEvaBean.class).getData());
                        } catch (Exception e) {

                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_interaction_news;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
        }
    }

    @Override
    public void onDetailClick(int position) {
        Intent intent = new Intent(this, PJDetailActivity.class);
        intent.putExtra("interact_id", mList.get(position).getInteract_id());
        startActivity(intent);
    }
}
