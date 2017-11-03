package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.DaifukuangLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ClassDealManagerBean;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DaifukuangActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.lv_daifukuang)
    ListView lvDaifukuang;
    private DaifukuangLvAdapter mAdapter;
    private List<ClassDealManagerBean> mList;
    private String school_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        school_id = (String) SPUtils.get(this, "school_id", "");
        ButterKnife.bind(this);
        String type = getIntent().getStringExtra("type");
        mList = new ArrayList<>();
        switch (type) {
            case "0":
                tvTitle.setText("等待买家付款");
                mAdapter = new DaifukuangLvAdapter(DaifukuangActivity.this, mList, "等待买家付款");
                break;
            case "1":
                tvTitle.setText("等待学习确认");
                mAdapter = new DaifukuangLvAdapter(DaifukuangActivity.this, mList, "等待学习确认");
                break;
            case "2":
                mAdapter = new DaifukuangLvAdapter(DaifukuangActivity.this, mList, "等待买家评价");
                tvTitle.setText("等待买家评价");
                break;
            case "3":
                mAdapter = new DaifukuangLvAdapter(DaifukuangActivity.this, mList, "成功的订单");
                tvTitle.setText("成功的订单");
                break;
            case "5":
                mAdapter = new DaifukuangLvAdapter(DaifukuangActivity.this, mList, "取消的订单");
                tvTitle.setText("关闭的订单");
                break;
            case "":
                mAdapter = new DaifukuangLvAdapter(DaifukuangActivity.this, mList, "");
                tvTitle.setText("全部订单");
                break;
        }

        lvDaifukuang.setAdapter(mAdapter);

        lvDaifukuang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order_id = mList.get(position).getOrder_id();
                startActivity(new Intent(DaifukuangActivity.this, OrderDetailActivity.class).putExtra("order_id",order_id));
            }
        });

        getData(type);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_daifukuang;
    }

    private void getData(String type) {
        mList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("school_id", school_id);
        if (!TextUtils.isEmpty(type)) {
            params.put("order_state", type);
        }

        Log.e("aaa",
                "(DaifukuangActivity.java:119)" + "params ==== " + params);
        OkHttpUtils.post()
                .url(InternetS.CLASS_ORDER_INFOR)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(DaifukuangActivity.java:108)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(DaifukuangActivity.java:115)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends ClassDealManagerBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<ClassDealManagerBean>>() {
                            }.getType()));
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
