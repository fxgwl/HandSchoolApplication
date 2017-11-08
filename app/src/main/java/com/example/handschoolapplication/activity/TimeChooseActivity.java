package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.TimeAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.TimeChooseBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class TimeChooseActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.gv_time)
    GridView gvTime;
    @BindView(R.id.tv_Mon)
    TextView tvMon;
    @BindView(R.id.tv_Tue)
    TextView tvTue;
    @BindView(R.id.tv_Wed)
    TextView tvWed;
    @BindView(R.id.tv_Thu)
    TextView tvThu;
    @BindView(R.id.tv_Fri)
    TextView tvFri;
    @BindView(R.id.tv_Sat)
    TextView tvSat;
    @BindView(R.id.tv_Sun)
    TextView tvSun;

    //    private ChooseTimeAdapter mAdapter;
//    private List<TimeBean> mList;
    private ArrayList<TimeHourBean> mHourList;
    private ArrayList<TimeHourBean> mHourList2 = new ArrayList<>();
    private TimeAdapter mAdapter;
    private String course_id;
    ArrayList<ArrayList<TimeHourBean>> timeList = new ArrayList<>();
    ArrayList<TimeChooseBean.DataBean> dataBeanList = new ArrayList<>();
    private TextView[] tvs;
    String week = "1";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvs = new TextView[]{tvMon, tvTue, tvWed, tvThu, tvFri, tvSat, tvSun};
        tvTitle.setText("报名信息");
        course_id = getIntent().getStringExtra("course_id");
        mHourList = new ArrayList<>();
        mAdapter = new TimeAdapter(this, mHourList);
        gvTime.setAdapter(mAdapter);
        gvTime.setOnItemClickListener(this);
        initData();
    }

    private void initData() {
        OkHttpUtils.post()
                .url(Internet.COURSETIME)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(TimeChooseActivity.java:81)时间选择" + response);
                        dataBeanList.clear();
                        timeList.clear();
                        Gson gson = new Gson();
                        TimeChooseBean tcBean = gson.fromJson(response, TimeChooseBean.class);
                        dataBeanList.addAll(tcBean.getData());
                        Log.e("aaa",
                                "(TimeChooseActivity.java:95)" + dataBeanList.get(0).getCtime_times());
                        for (int i = 0; i < dataBeanList.size(); i++) {
                            mHourList2.clear();
                            tvs[i].setText(dataBeanList.get(i).getCtime_week());
                            tvs[i].setVisibility(View.VISIBLE);
                            for (int m = 0; m < dataBeanList.get(i).getCtime_times().split(",").length; m++) {
                                TimeHourBean time = new TimeHourBean();
                                time.setTime(dataBeanList.get(i).getCtime_times().split(",")[m]);
                                time.setChecked(false);
                                mHourList2.add(time);
                            }
                            timeList.add(mHourList2);
                        }
                        mHourList.clear();
                        mHourList.addAll(timeList.get(0));
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_time_choose;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_Mon, R.id.tv_Tue, R.id.tv_Wed, R.id.tv_Thu, R.id.tv_Fri, R.id.tv_Sat, R.id.tv_Sun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_Mon:
                week = "1";
                tvMon.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                mHourList.clear();
                mHourList.addAll(timeList.get(0));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_Tue:
                week = "2";
                tvTue.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                mHourList.clear();
                mHourList.addAll(timeList.get(1));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_Wed:
                week = "3";
                tvWed.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                mHourList.clear();
                mHourList.addAll(timeList.get(2));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_Thu:
                week = "4";
                tvThu.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                mHourList.clear();
                mHourList.addAll(timeList.get(3));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_Fri:
                week = "5";
                mHourList.clear();
                mHourList.addAll(timeList.get(4));
                mAdapter.notifyDataSetChanged();
                tvFri.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Sat:
                week = "6";
                mHourList.clear();
                mHourList.addAll(timeList.get(5));
                mAdapter.notifyDataSetChanged();
                tvSat.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSun.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
            case R.id.tv_Sun:
                week = "7";
                mHourList.clear();
                mHourList.addAll(timeList.get(6));
                mAdapter.notifyDataSetChanged();
                tvSun.setBackgroundColor(Color.parseColor("#e6e6e6"));
                tvMon.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvTue.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvWed.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvThu.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvFri.setBackgroundColor(Color.parseColor("#f2f2f2"));
                tvSat.setBackgroundColor(Color.parseColor("#f2f2f2"));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(this, ApplyDetailActivity.class).putExtra("course_id", course_id));
    }
}
