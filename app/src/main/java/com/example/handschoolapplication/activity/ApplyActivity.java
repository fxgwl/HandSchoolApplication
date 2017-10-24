package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ApplyMessageAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ApplyMessage;
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
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ApplyActivity extends BaseActivity implements AdapterView.OnItemClickListener, ApplyMessageAdapter.StartListener,
        ApplyMessageAdapter.CancelListener, ApplyMessageAdapter.EndListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.v_all)
    View vAll;
    @BindView(R.id.tv_tostart)
    TextView tvTostart;
    @BindView(R.id.v_tostart)
    View vTostart;
    @BindView(R.id.tv_starting)
    TextView tvStarting;
    @BindView(R.id.v_starting)
    View vStarting;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.v_end)
    View vEnd;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.lv_course)
    ListView lvCourse;

    private List<ApplyMessage> mList;
    private ApplyMessageAdapter mAdapter;
    private String state = "";
    private String school_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("报名信息");
        school_id = (String) SPUtils.get(this, "school_id", "");
        mList = new ArrayList<>();
        mAdapter = new ApplyMessageAdapter(ApplyActivity.this, mList);
        lvCourse.setAdapter(mAdapter);
        initData("");
        lvCourse.setOnItemClickListener(this);
        mAdapter.setOnStartListener(this);
        mAdapter.setOnCancelListener(this);
        mAdapter.setOnEndListener(this);
    }

    private void initData(final String state) {
        mList.clear();
        HashMap<String, String> params = new HashMap<>();
        if (!"".equals(state)) params.put("State", state);
        params.put("school_id", school_id);
        OkHttpUtils.post()
                .url(InternetS.APPLYINFOR)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ApplyActivity.java:81)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyActivity.java:88)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends ApplyMessage>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ApplyMessage>>() {
                            }.getType()));
                            mAdapter.setState(state);
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_all, R.id.ll_tostart, R.id.ll_starting, R.id.ll_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.ll_all:
                tvAll.setTextColor(Color.parseColor("#27acf6"));
                vAll.setBackgroundColor(Color.parseColor("#27acf6"));
                tvTostart.setTextColor(Color.parseColor("#666666"));
                vTostart.setBackgroundColor(Color.parseColor("#ffffff"));
                tvStarting.setTextColor(Color.parseColor("#666666"));
                vStarting.setBackgroundColor(Color.parseColor("#ffffff"));
                tvEnd.setTextColor(Color.parseColor("#666666"));
                vEnd.setBackgroundColor(Color.parseColor("#ffffff"));
                state = "";
                initData(state);
                break;
            case R.id.ll_tostart:
                tvTostart.setTextColor(Color.parseColor("#27acf6"));
                vTostart.setBackgroundColor(Color.parseColor("#27acf6"));
                tvAll.setTextColor(Color.parseColor("#666666"));
                vAll.setBackgroundColor(Color.parseColor("#ffffff"));
                tvStarting.setTextColor(Color.parseColor("#666666"));
                vStarting.setBackgroundColor(Color.parseColor("#ffffff"));
                tvEnd.setTextColor(Color.parseColor("#666666"));
                vEnd.setBackgroundColor(Color.parseColor("#ffffff"));
                state = "0";
                initData(state);
                break;
            case R.id.ll_starting:
                tvStarting.setTextColor(Color.parseColor("#27acf6"));
                vStarting.setBackgroundColor(Color.parseColor("#27acf6"));
                tvTostart.setTextColor(Color.parseColor("#666666"));
                vTostart.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAll.setTextColor(Color.parseColor("#666666"));
                vAll.setBackgroundColor(Color.parseColor("#ffffff"));
                tvEnd.setTextColor(Color.parseColor("#666666"));
                vEnd.setBackgroundColor(Color.parseColor("#ffffff"));
                state = "1";
                initData(state);
                break;
            case R.id.ll_end:
                tvEnd.setTextColor(Color.parseColor("#27acf6"));
                vEnd.setBackgroundColor(Color.parseColor("#27acf6"));
                tvTostart.setTextColor(Color.parseColor("#666666"));
                vTostart.setBackgroundColor(Color.parseColor("#ffffff"));
                tvStarting.setTextColor(Color.parseColor("#666666"));
                vStarting.setBackgroundColor(Color.parseColor("#ffffff"));
                tvAll.setTextColor(Color.parseColor("#666666"));
                vAll.setBackgroundColor(Color.parseColor("#ffffff"));
                state = "2";
                initData(state);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, TimeChooseActivity.class));
    }

    //开课
    @Override
    public void onStart(int position) {
        String course_id = mList.get(position).getCourse_id();
        startCourse(course_id);
    }

    @Override
    public void onCancel(int position) {

    }

    @Override
    public void onEnd(int position) {
        String course_id = mList.get(position).getCourse_id();
        endCourse(course_id);
    }


    private void startCourse(String course_id) {
        OkHttpUtils.post()
                .url(InternetS.BEGINCOURSE)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ApplyActivity.java:212)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyActivity.java:218)" + response);
                    }
                });
    }

    private void endCourse(String course_id) {
        OkHttpUtils.post()
                .url(InternetS.ENDCOURSE)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ApplyActivity.java:237)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Log.e("aaa",
                                "(ApplyActivity.java:244)" + response);
                    }
                });
    }
}
