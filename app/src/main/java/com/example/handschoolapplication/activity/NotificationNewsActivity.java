package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.NotificationAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.NotificationBean;
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

public class NotificationNewsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_notification_news)
    ListView lvNotificationNews;

    private List<NotificationBean> mList;
    private NotificationAdapter mAdapter;
    private String userId, userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("通知消息");
        userId = (String) SPUtils.get(this, "userId", "");
        userPhone = (String) SPUtils.get(this, "user_phone", "");
        mList = new ArrayList<>();
        mAdapter = new NotificationAdapter(NotificationNewsActivity.this, mList);
        lvNotificationNews.setAdapter(mAdapter);
        initData();

        onSetListener();
    }

    private void onSetListener() {

        mAdapter.setOnDiscountpagerListener(new NotificationAdapter.GetDiscountPagerListener() {
            @Override
            public void getDispager(int position) {
                int coupons_id = mList.get(position).getCoupons_id();

                getDiscountPager(coupons_id);
            }
        });
    }

    private void getDiscountPager(int coupons_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("coupons_id", coupons_id + "");
        params.put("user_id", userId);
        params.put("user_phone", userPhone);
        OkHttpUtils.post()
                .url(InternetS.GET_DISCOUNT_PAPER)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:82)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:88)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            int result = jsonObject.getInt("result");
                            Toast.makeText(NotificationNewsActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void initData() {
        mList.clear();
        OkHttpUtils.post()
                .url(InternetS.NOTIFATION_NEWS_INFORMINAGER)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:57)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NotificationNewsActivity.java:63)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends NotificationBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<NotificationBean>>() {
                            }.getType()));
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_notification_news;
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
}
