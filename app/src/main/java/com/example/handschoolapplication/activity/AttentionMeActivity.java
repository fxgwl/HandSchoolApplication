package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.SchoolFansAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolFans;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AttentionMeActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_fans)
    ListView lvFans;

    private List<SchoolFans.DataBean> mList = new ArrayList<>();
    private SchoolFansAdapter mAdapter;
    private String userId;
    private String consult_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        initData();

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_attention_me;
    }

    private void initView() {

        tvTitle.setText("机构粉丝");
        mAdapter = new SchoolFansAdapter(this, mList);
        lvFans.setAdapter(mAdapter);

        lvFans.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: 2018/3/17 点击按钮
                String school_id = mList.get(position).getSchool_id();
                SchoolFans.DataBean.UserInfoBean userInfo = mList.get(position).getUserInfo();
                switch (userInfo.getUser_type()){
                    case "0":
                        consult_name = TextUtils.isEmpty(userInfo.getUser_name())?"":userInfo.getUser_name();
                        break;
                    case "1":
//                        holder.tvUsername.setText(dataBean.getSchool_name());
                        consult_name = mList.get(position).getSchool_name();
                        break;
                }

                String user_id = userInfo.getUser_id();

                getCourseIdBySchoolId(school_id,user_id);

            }
        });
    }

    private void getCourseIdBySchoolId(String school_id, final String userId) {


        OkHttpUtils.post()
                .url(Internet.GET_COURSE_ID)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AttentionMeActivity.java:95)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AttentionMeActivity.java:101)<---->" + response);
                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result != -1) {
                                    JSONArray data = jsonObject.getJSONArray("data");
                                    if (data.length() > 0) {
                                        JSONObject jsonObject1 = data.getJSONObject(0);
                                        String course_id = jsonObject1.getString("course_id");


                                        Intent intent = new Intent(AttentionMeActivity.this, HumanServiceActivity.class);
                                        intent.putExtra("type", "1");
                                        intent.putExtra("course_id", course_id);
                                        intent.putExtra("send_id", userId);
                                        intent.putExtra("name", consult_name);
                                        startActivity(intent);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    private void initData() {
        mList.clear();
        OkHttpUtils.post()
                .url(Internet.GET_SCHOOL_FAN)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(AttentionMeActivity.java:68)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AttentionMeActivity.java:75)<---->" + response);

                        if (!TextUtils.isEmpty(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result != -1) {
                                    SchoolFans schoolFans = new Gson().fromJson(response, SchoolFans.class);
                                    mList.addAll(schoolFans.getData());
                                    mAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

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
