package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ContactServiceAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ContactServiceBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class HumanServiceActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.lv_humanservice)
    ListView lvHumanservice;
    @BindView(R.id.tv_contact_content)
    EditText tvContactContent;
    private String user_id;
    ArrayList<ContactServiceBean.DataBean> contactList = new ArrayList<>();
    private ContactServiceAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tvTitle.setText("人工客服");
        user_id = (String) SPUtils.get(this, "userId", "");
        contactAdapter = new ContactServiceAdapter(contactList, HumanServiceActivity.this);
        lvHumanservice.setAdapter(contactAdapter);
        initView();
    }

    //刷新列表
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.SERVICELIST)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:67)" + response);
                        Gson gson = new Gson();
                        contactList.clear();
                        contactList.addAll(gson.fromJson(response, ContactServiceBean.class).getData());
                        contactAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_human_service;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_contact_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.tv_contact_send:
                String content = tvContactContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
//                user_id
//                        message_content
                OkHttpUtils.post()
                        .url(Internet.CONTACTSERVICE)
                        .addParams("user_id", user_id)
                        .addParams("message_content", content)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(HumanServiceActivity.java:82)" + response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    Toast.makeText(HumanServiceActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                                    if (response.contains("成功")) {
                                        tvContactContent.setText("");
                                        initView();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                break;
        }
    }
}
