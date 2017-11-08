package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ConsultAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ConsultBean;
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
    RelativeLayout ivMenu;
    @BindView(R.id.lv_humanservice)
    ListView lvHumanservice;
    @BindView(R.id.tv_contact_content)
    EditText tvContactContent;
    private String user_id;
    ArrayList<ContactServiceBean.DataBean> contactList = new ArrayList<>();
    ArrayList<ConsultBean.DataBean> consultList = new ArrayList<ConsultBean.DataBean>();
//    private ContactServiceAdapter contactAdapter;
    private String type;
    private String content;
    private String user_type;
    private String course_id;
    private String schooluid;
    private ConsultAdapter consultAdapter;
    private String send_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (null != getIntent().getStringExtra("name"))
            tvTitle.setText(getIntent().getStringExtra("name"));
        else tvTitle.setText("人工客服");
        type = getIntent().getStringExtra("type");
        course_id = getIntent().getStringExtra("course_id");
        send_id = getIntent().getStringExtra("send_id");

        user_type = (String) SPUtils.get(this, "user_type", "");
//        contactAdapter = new ContactServiceAdapter(contactList, HumanServiceActivity.this);
        consultAdapter = new ConsultAdapter(contactList, this);
        if (type.equals("0")) {//个人跟学堂对话
            user_id = (String) SPUtils.get(this, "userId", "");
            initView();
        } else if (type.equals("1")){
            //学堂跟个人对话
            user_id = (String) SPUtils.get(this, "school_id", "");
            initView2();
        }
    }

    //

    private void initView2() {

        Log.e("aaa",
            "(HumanServiceActivity.java:90)user_id=="+user_id+"  send_id=="+send_id+ "   course_id=="+course_id);
        OkHttpUtils.post()
                .url(Internet.CONTACTLIST)
                .addParams("user_id", send_id)
                .addParams("send_id", user_id)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:67)" + response);
                        lvHumanservice.setAdapter(consultAdapter);
                        if (response.contains("没有信息")) {
                            contactList.clear();
                            consultAdapter.notifyDataSetChanged();
                        } else {
                            Gson gson = new Gson();
                            contactList.clear();
                            contactList.addAll(gson.fromJson(response, ContactServiceBean.class).getData());
                            consultAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    //刷新列表
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.CONTACTLIST)
                .addParams("user_id", user_id)
                .addParams("send_id", send_id)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:67)" + response);
                        lvHumanservice.setAdapter(consultAdapter);
                        if (response.contains("没有信息")) {
                            contactList.clear();
                            consultAdapter.notifyDataSetChanged();
                        } else {
                            Gson gson = new Gson();
                            contactList.clear();
                            contactList.addAll(gson.fromJson(response, ContactServiceBean.class).getData());
                            consultAdapter.notifyDataSetChanged();
                        }
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
                show(view);
                break;
            case R.id.tv_contact_send:
                content = tvContactContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type.equals("1")) {
                    //给学堂发消息
//                    sendToRobot();
                    sendToPerson();
                } else {
                    //给人发消息
                    sendToPerson();
                }
                break;
        }
    }


    private void sendToPerson() {
        OkHttpUtils.post()
                .url(Internet.HOMECONTACT)
                .addParams("user_id", user_id)
                .addParams("send_id", send_id)
                .addParams("consult_type", type)
                .addParams("consult_content", content)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:188)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:184)" + response);
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


    }

    private void sendToRobot() {
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
    }
}
