package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.LearnNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearnNewsBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    ListView lvLearnNews;
    private List<LearnNewsBean> mList;
    private LearnNewsAdapter mAdapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(this, "userId", "");
        tvTitle.setText("学习消息");
        initData();

        lvLearnNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LearnNewsBean learnNewsBean = mList.get(position);
                startActivity(new Intent(LearnNewsActivity.this,LearnNewsDetailActivity.class).putExtra("learnNewsBean",learnNewsBean));
            }
        });

    }

    private void initData() {
        mList = new ArrayList<>();
        OkHttpUtils.post()
                .url(InternetS.LEARNNEWS)
                .addParams("user_id",userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                            "(LearnNewsActivity.java:53)"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                            "(LearnNewsActivity.java:60)"+response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends LearnNewsBean>) new Gson().fromJson(data.toString(),new TypeToken<ArrayList<LearnNewsBean>>(){}.getType()));
                            mAdapter = new LearnNewsAdapter(LearnNewsActivity.this, mList);
                            lvLearnNews.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
