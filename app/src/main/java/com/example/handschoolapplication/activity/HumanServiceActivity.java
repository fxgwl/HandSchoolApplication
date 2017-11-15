package com.example.handschoolapplication.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ConsultAdapter;
import com.example.handschoolapplication.adapter.ConsultCTPAdapter;
import com.example.handschoolapplication.adapter.ConsultServiceAdapter;
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
    private ConsultAdapter consultAdapter;
    ArrayList<ConsultBean.DataBean> consultList = new ArrayList<ConsultBean.DataBean>();
    private ConsultServiceAdapter contactAdapter;//人工客服
    private String type;
    private String content;
    private String course_id;
    private String schooluid;
    private ConsultCTPAdapter consultCTPAdapter;
    private String send_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (null != getIntent().getStringExtra("name"))
            tvTitle.setText(getIntent().getStringExtra("name"));
        else tvTitle.setText("人工客服");
        type = getIntent().getStringExtra("type");
        user_id = (String) SPUtils.get(this, "userId", "");
        if (type.equals("0")) {//个人跟学堂对话
            course_id = getIntent().getStringExtra("course_id");
            send_id = getIntent().getStringExtra("send_id");
            consultAdapter = new ConsultAdapter(contactList, this);
            initView();
        } else if (type.equals("1")) {
            //学堂跟个人对话
            course_id = getIntent().getStringExtra("course_id");
            send_id = getIntent().getStringExtra("send_id");
            consultCTPAdapter = new ConsultCTPAdapter(contactList, this);
            initView2();
        } else {
            //人工客服对话
            contactAdapter = new ConsultServiceAdapter(consultList, this);
            initView3();
        }
    }

    private void initView3() {
        lvHumanservice.setAdapter(contactAdapter);
        consultList.clear();
        OkHttpUtils.post()
                .url(Internet.SERVICELIST)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:98)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:104)" + response);
                        if (response.contains("没有信息")) {
                        } else {
                            consultList.addAll(new Gson().fromJson(response, ConsultBean.class).getData());
                            contactAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    //

    private void initView2() {

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
                                "(HumanServiceActivity.java:104)" + response);
                        Log.e("aaa",
                                "(HumanServiceActivity.java:106)user_id===" + user_id + "  send_id=== " + send_id + "   course_id === " + course_id);
                        lvHumanservice.setAdapter(consultCTPAdapter);
                        if (response.contains("没有信息")) {
                            contactList.clear();
                            consultCTPAdapter.notifyDataSetChanged();
                        } else {
                            Gson gson = new Gson();
                            contactList.clear();
                            contactList.addAll(gson.fromJson(response, ContactServiceBean.class).getData());
                            consultCTPAdapter.notifyDataSetChanged();
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
                                "(HumanServiceActivity.java:136)" + response);
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
                if (type.equals("0")) {
                    //给学堂发消息
//                    sendToRobot();
                    sendToPerson();
                } else {
                    //给个人发消息
                    sendToClass();
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

    private void sendToClass() {
        OkHttpUtils.post()
                .url(Internet.HOMECONTACT)
                .addParams("user_id", send_id)
                .addParams("send_id", user_id)
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
                                initView2();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;

                if (diff != 0) {
                    if (contentView.getPaddingBottom() != diff) {
                        contentView.setPadding(0, 0, 0, diff);
                    }
                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };
    }
}
