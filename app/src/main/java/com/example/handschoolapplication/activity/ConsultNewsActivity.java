package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ConsultNewsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ConsultNewsBean;
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

public class ConsultNewsActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_consult_news)
    ListView lvConsultNews;

    private ConsultNewsAdapter mAdapter;
    private List<ConsultNewsBean> mList;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("咨询消息");
        userId = (String) SPUtils.get(this, "userId", "");
        mList = new ArrayList<>();
        mAdapter = new ConsultNewsAdapter(ConsultNewsActivity.this, mList);
        lvConsultNews.setAdapter(mAdapter);
        initData();

        lvConsultNews.setOnItemClickListener(this);
    }

    private void initData() {
        mList.clear();
        OkHttpUtils.post()
                .url(InternetS.CONSULT_NEWS)
                .addParams("send_id", userId)
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends ConsultNewsBean>) new Gson().fromJson(data.toString(),new TypeToken<ArrayList<ConsultNewsBean>>(){}.getType()));
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        ConsultNewsBean consultNewsBean = mList.get(position);
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
