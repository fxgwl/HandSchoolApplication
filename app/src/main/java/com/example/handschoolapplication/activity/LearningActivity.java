package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.LearningAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearningCourseBean;
import com.example.handschoolapplication.utils.Internet;
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

public class LearningActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.lv_learning_course)
    ListView lvLearningCourse;
    private LearningAdapter mAdapter;
    private List<LearningCourseBean> mList = new ArrayList<>();
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();//实例化视图
        initData();//获取数据

    }

    private void initView() {
        tvTitle.setText("学习中");
        mAdapter = new LearningAdapter(mList, this);
        lvLearningCourse.setAdapter(mAdapter);
    }

    private void initData() {
        mList.clear();
        OkHttpUtils.post()
                .url(Internet.ALLORDER)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearningActivity.java:61)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearningActivity.java:68)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            ArrayList<LearningCourseBean> learningCourseBeen = new ArrayList<>();
                            learningCourseBeen.addAll((Collection<? extends LearningCourseBean>) new Gson().fromJson(data.toString(), new TypeToken<ArrayList<LearningCourseBean>>() {
                            }.getType()));
                            for (int i = 0; i < learningCourseBeen.size(); i++) {
                                if ("2".equals(learningCourseBeen.get(i).getOrder_state())) {
                                    mList.add(learningCourseBeen.get(i));
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_learning;
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
