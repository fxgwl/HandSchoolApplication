package com.example.handschoolapplication.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.example.handschoolapplication.bean.NewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.KeyboardChangeListener;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.SystemUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class HumanServiceActivity extends BaseActivity implements TextView.OnEditorActionListener {

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
    @BindView(R.id.view_20dp)
    View view20dp;
    ArrayList<ContactServiceBean.DataBean> contactList = new ArrayList<>();
    ArrayList<ConsultBean.DataBean> consultList = new ArrayList<ConsultBean.DataBean>();
    private String user_id;
    private ConsultAdapter consultAdapter;
    private ConsultServiceAdapter contactAdapter;//人工客服
    private String type;
    private String content;
    private String course_id;
    private String schooluid;
    private ConsultCTPAdapter consultCTPAdapter;
    private String send_id;
    private String systemModel;//手机型号

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
            tvContactContent.setHint("说点什么吧...");
            initView();
        } else if (type.equals("1")) {
            //学堂跟个人对话
            course_id = getIntent().getStringExtra("course_id");
            send_id = getIntent().getStringExtra("send_id");
            consultCTPAdapter = new ConsultCTPAdapter(contactList, this);
            tvContactContent.setHint("说点什么吧...");
            initView2();
        } else {
            //人工客服对话
            contactAdapter = new ConsultServiceAdapter(consultList, this);
            tvContactContent.setHint("请输入您想咨询的问题");
            initView3();
        }

        tvContactContent.setOnEditorActionListener(this);

        systemModel = SystemUtil.getSystemModel();

        if (systemModel.contains("AL")) {
            view20dp.setVisibility(View.VISIBLE);
        } else {
            view20dp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_human_service;
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

    //

    //刷新列表
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.CONTACTLIST)
                .addParams("user_id", user_id)
                .addParams("send_id", send_id)
                .addParams("course_id", course_id)
                .addParams("user_type", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:177)" + e.getMessage());
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

    private void initView2() {

        OkHttpUtils.post()
                .url(Internet.CONTACTLIST)
                .addParams("user_id", send_id)
                .addParams("send_id", user_id)
                .addParams("course_id", course_id)
                .addParams("user_type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:142)" + e.getMessage());
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

    private void initView3() {

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
                            consultList.clear();
                            lvHumanservice.setAdapter(contactAdapter);
                        } else {
                            consultList.clear();
                            lvHumanservice.setAdapter(contactAdapter);
                            consultList.addAll(new Gson().fromJson(response, ConsultBean.class).getData());
                            contactAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_contact_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                KeyBoardCancle();
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
                } else if (type.equals("1")) {
                    //给个人发消息
                    sendToClass();
                } else {
                    sendPlatform();
                }
                break;
        }
    }

    //强制隐藏软键盘
    public void KeyBoardCancle() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private void sendPlatform() {

        OkHttpUtils.post()
                .url(Internet.CONTACTSERVICE)
                .addParams("user_id", user_id)
                .addParams("message_content", content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:246)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HumanServiceActivity.java:252)<---->" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (response.contains("成功")) {
                                tvContactContent.setText("");
                                initView3();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEND) {
            content = tvContactContent.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            } else {
                if (type.equals("0")) {
                    //给学堂发消息
//                    sendToRobot();
                    sendToPerson();
                } else if (type.equals("1")) {
                    //给个人发消息
                    sendToClass();
                } else {
                    sendPlatform();
                }
            }
        }
        return false;
    }
}
