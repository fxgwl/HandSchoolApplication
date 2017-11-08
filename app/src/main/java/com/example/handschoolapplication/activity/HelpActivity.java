package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HelpLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.InfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.lv_help)
    ListView lvHelp;
    @BindView(R.id.et_question)
    EditText etQuestion;
    private HelpLvAdapter mAdapter;
    ArrayList<InfoBean.DataBean> infoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tvTitle.setText("帮助");
        mAdapter = new HelpLvAdapter(HelpActivity.this, infoList);
        lvHelp.setAdapter(mAdapter);
        lvHelp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelpActivity.this, QuestionAnswerActivity.class);
                intent.putExtra("help_id", infoList.get(position).getHelp_id() + "");
                startActivity(intent);
            }
        });
        initList();

    }

    //全部问题列表
    private void initList() {
        OkHttpUtils.post()
                .url(Internet.ALLINFO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HelpActivity.java:61)" + response);
                        Gson gson = new Gson();
                        infoList.clear();
                        infoList.addAll(gson.fromJson(response, InfoBean.class).getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_help;
    }


    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_help_service, R.id.ll_help_feedback, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //联系客服
            case R.id.ll_help_service:
                Intent intent = new Intent(HelpActivity.this, HumanServiceActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            //意见反馈
            case R.id.ll_help_feedback:
                startActivity(new Intent(HelpActivity.this, FeedBackActivity.class));
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.iv_search:
                //筛选问题列表
                searchQuestion();
                break;
        }
    }

    private void searchQuestion() {
        if (TextUtils.isEmpty(etQuestion.getText().toString())) {
            Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils.post()
                .url(Internet.INFOSEARCH)
                .addParams("title", etQuestion.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HelpActivity.java:137)" + response);
                        Gson gson = new Gson();
                        infoList.clear();
                        infoList.addAll(gson.fromJson(response, InfoBean.class).getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
