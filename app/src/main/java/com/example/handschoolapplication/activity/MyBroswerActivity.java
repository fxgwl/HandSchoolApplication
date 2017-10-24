package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.BroswerAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MyBroswerBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MyBroswerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_mybroswer)
    ListView lvMybroswer;
    ArrayList<MyBroswerBean.DataBean> mlist = new ArrayList<>();

    private BroswerAdapter broswerAdapter;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("我的足迹");
        user_id = (String) SPUtils.get(this, "userId", "");
        initData();


    }

    private void initData() {

        broswerAdapter = new BroswerAdapter(this, mlist);
        lvMybroswer.setAdapter(broswerAdapter);
        OkHttpUtils.post()
                .url(Internet.FOOTLIST)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyBroswerActivity.java:65)" + response);
                        Gson gson = new Gson();
                        mlist.clear();
                        if (response.contains("没有信息")) {

                        } else {
                            mlist.addAll(gson.fromJson(response, MyBroswerBean.class).getData());
                        }
                        broswerAdapter.notifyDataSetChanged();
                    }
                });
        lvMybroswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyBroswerActivity.this, CourseHomePagerActivity.class);
//                intent.putExtra("school_id", courseBeanList.get(position).getSchool_id());
                intent.putExtra("course_id", mlist.get(position).getCourse_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_broswer;
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
