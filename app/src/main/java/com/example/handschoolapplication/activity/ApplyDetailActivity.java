package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ApplyDetailAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ApplyDetail;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ApplyDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    //    @BindView(R.id.tv_week)
//    TextView tvWeek;
//    @BindView(R.id.tv_morning)
//    TextView tvMorning;
//    @BindView(R.id.tv_date)
//    TextView tvDate;
    @BindView(R.id.lv_aplly_det)
    ListView lvApllyDet;
    @BindView(R.id.iv_isall)
    ImageView ivIsall;
    @BindView(R.id.et_message)
    EditText etMessage;

    private ApplyDetailAdapter mAdapter;
    private List<ApplyDetail.DataBean> mList;
    private String course_id;
    int i = 0;
    int choose = 0;
    private String course_id1;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course_id = getIntent().getStringExtra("course_id");
        tvTitle.setText("报名信息详情");
        mList = new ArrayList<>();
        mAdapter = new ApplyDetailAdapter(this, mList);
        lvApllyDet.setAdapter(mAdapter);
        lvApllyDet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choose = position;
                course_id1 = mList.get(position).getCourse_id();
                user_id = mList.get(position).getUser_id();
            }
        });
        initData();
    }

    private void initData() {
        OkHttpUtils.post()
                .url(Internet.BMINFO)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:66)包名信息" + response);
                        mList.clear();
                        Gson gson = new Gson();
                        ArrayList<ApplyDetail.DataBean> dataBeens = (ArrayList<ApplyDetail.DataBean>) gson.fromJson(response, ApplyDetail.class).getData();
                        mList.addAll(dataBeens);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_detail;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_all_sele, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_all_sele://是否全选
                if (i == 0) {
                    ivIsall.setImageResource(R.drawable.hongquan);
                    i = 1;
                } else {
                    ivIsall.setImageResource(R.drawable.baiquan);
                    i = 0;
                }
                break;
            case R.id.tv_send://发送消息
                if (TextUtils.isEmpty(etMessage.getText().toString())) {
                    Toast.makeText(this, "请填写消息内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == 0) {
                    sendToSingle();
                } else {
                    sendToAll();
                }

                break;
        }
    }

    private void sendToAll() {
        OkHttpUtils.post()
                .url(Internet.ALLNEWS)
                .addParams("course_id", course_id)
                .addParams("message_content", etMessage.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:150)群发消息" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(ApplyDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            etMessage.setText("");
                        }
                    }
                });

    }

    private void sendToSingle() {
        OkHttpUtils.post()
                .url(Internet.SINGLENEWS)
                .addParams("course_id", course_id)
                .addParams("user_id", user_id)
                .addParams("message_content", etMessage.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:175)单发消息" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(ApplyDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            etMessage.setText("");
                        }
                    }
                });

    }
}
