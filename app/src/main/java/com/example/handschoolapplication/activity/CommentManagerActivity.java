package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.CommentManagerAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.EvaluateManagerBean;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CommentManagerActivity extends BaseActivity implements CommentManagerAdapter.ReplyEvaluateListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.commentmanager_lv)
    ListView commentmanagerLv;

    private List<EvaluateManagerBean> mList = new ArrayList<>();
    private String userId;
    private CommentManagerAdapter cmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        setContentView();
        ButterKnife.bind(this);
        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        initData();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_comment_manager;
    }

    private void initView() {
        tvTitle.setText("评价管理");
        cmAdapter = new CommentManagerAdapter(this, mList);
        commentmanagerLv.setAdapter(cmAdapter);

        cmAdapter.setOnReplyEvaluateListener(this);

    }

    private void initData() {
        mList.clear();
        OkHttpUtils.post()
                .url(InternetS.CLASS_EVALUATE_MANAGER)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CommentManagerActivity.java:56)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CommentManagerActivity.java:63)" + response);

                        Log.e("aaa",
                                "(CommentManagerActivity.java:71)" + userId);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            Gson gson = new Gson();
                            mList.addAll((Collection<? extends EvaluateManagerBean>) gson.fromJson(data.toString(), new TypeToken<ArrayList<EvaluateManagerBean>>() {
                            }.getType()));
                            cmAdapter.notifyDataSetChanged();
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
    public void toReplyEvaluate(int position) {
        String interact_id = mList.get(position).getInteract_id();
        startActivity(new Intent(this, PJDetailActivity.class).putExtra("interact_id", interact_id));
    }
}
