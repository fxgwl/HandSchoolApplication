package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.PrimarySchoolAdapter;
import com.example.handschoolapplication.adapter.PrimarySchoolsAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.PrimaryBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class NearLittleSchoolActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_near_school)
    MyListView lvNearSchool;
    private String school_id;

    private PrimarySchoolsAdapter mAdapter;
    private List<PrimaryBean.DataBean> mList = new ArrayList<>();
    private LoadingDailog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        school_id = (String) SPUtils.get(this, "school_id", "");
        initView();

        getSchoolList();
    }

    private void initView() {

        tvTitle.setText("附近学校");
        mAdapter = new PrimarySchoolsAdapter(mList, this);
        lvNearSchool.setAdapter(mAdapter);

        mAdapter.setListener(new PrimarySchoolsAdapter.OnTVClickListener() {
            @Override
            public void setOnClickListener(int position) {
                deleteSchool(position);
            }
        });

        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();
    }

    private void getSchoolList() {
        mList.clear();
        OkHttpUtils.post()
                .url(Internet.GET_SCHOOL_BY_CLASS)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:48)<---->" + e.getMessage());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:55)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(NearLittleSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    PrimaryBean classSchoolBean = new Gson().fromJson(response, PrimaryBean.class);
                                    mList.addAll(classSchoolBean.getData());
//                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(NearLittleSchoolActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void deleteSchool(int position) {

        Log.e("aaa", "(NearLittleSchoolActivity.java:117)<---->" + mList.get(position).getGakuen_id());

        OkHttpUtils.post()
                .url(Internet.DELECT_CLASS_SCHOOL)
                .addParams("gakuen_id", mList.get(position).getGakuen_id() + "")
                .addParams("school_id", school_id + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:116)<---->" + e.getMessage());
                        Toast.makeText(NearLittleSchoolActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NearLittleSchoolActivity.java:122)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(NearLittleSchoolActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(NearLittleSchoolActivity.this, msg, Toast.LENGTH_SHORT).show();
                                getSchoolList();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_near_little_school;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_add_school})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_add_school:
                startActivityForResult(new Intent(this, AddClassSchoolActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSchoolList();
    }
}
