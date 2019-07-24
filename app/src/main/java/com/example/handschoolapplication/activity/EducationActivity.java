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
import com.example.handschoolapplication.adapter.EducationAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.TeachNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/27.
 */

public class EducationActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_education)
    ListView lvEducation;

    private ArrayList<TeachNewsBean.DataBean> mList = new ArrayList<>();
    private EducationAdapter educationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("教育资讯");
        educationAdapter = new EducationAdapter(mList, this);
        lvEducation.setAdapter(educationAdapter);
        lvEducation.setOnItemClickListener(this);
        initTeachNews();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_education;
    }


    private void initTeachNews() {
        OkHttpUtils.post()
                .url(Internet.TEACHNEWS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(EducationActivity.java:82)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(EducationActivity.java:87)" + response);

                        try {
                            if (response.contains("没有信息") || TextUtils.isEmpty(response)) {

                            } else {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    Gson gson = new Gson();
                                    mList.clear();
                                    mList.addAll(gson.fromJson(response, TeachNewsBean.class).getData());
                                    educationAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String newsId = mList.get(position).getNews_id() + "";
        String news_photo = mList.get(position).getNews_photo();
        String news_content = mList.get(position).getNews_title();

        Log.e("aaa",
                "(EducationActivity.java:71)" + newsId);
        startActivityForResult(new Intent(EducationActivity.this, EducationDetailActivity.class)
                .putExtra("newsId", newsId)
                .putExtra("imgUrl", news_photo)
                .putExtra("text", news_content), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            initTeachNews();
        }
    }
}
