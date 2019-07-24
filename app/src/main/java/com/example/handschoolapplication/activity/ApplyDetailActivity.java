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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ApplyDetailAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.ApplyDetail;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SystemUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ApplyDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    //    @BindView(R.id.tv_week)
//    TextView tvWeek;
//    @BindView(R.id.tv_morning)
//    TextView tvMorning;
//    @BindView(R.id.tv_date)
//    TextView tvDate;
    @BindView(R.id.lv_aplly_det)
    ListView lvApllyDet;
    @BindView(R.id.iv_isall)
    CheckBox ivIsall;
    @BindView(R.id.et_message)
    EditText etMessage;
    int i = 2;
    boolean isChoose = false;
    private ApplyDetailAdapter mAdapter;
    private List<ApplyDetail.DataBean> mList;
    private String course_id;
    private String course_id1;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        course_id = getIntent().getStringExtra("course_id");
        tvTitle.setText("报名信息详情");
        mList = new ArrayList<>();
        mAdapter = new ApplyDetailAdapter(this, mList);
        lvApllyDet.setAdapter(mAdapter);

        mAdapter.setOnSendMessageListener(new ApplyDetailAdapter.SendMessageListener() {
            @Override
            public void sendMessage(int position) {
                i = 0;
                course_id1 = mList.get(position).getCourse_id();
                user_id = mList.get(position).getUser_id();
                etMessage.setFocusable(true);
                etMessage.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    if (TextUtils.isEmpty(etMessage.getText().toString())) {
                        Toast.makeText(ApplyDetailActivity.this, "请填写消息内容", Toast.LENGTH_SHORT).show();
                    } else {
                        if (i == 0) {
                            sendToSingle();
                        } else if (i == 1) {
                            sendToAll();
                        } else {
                            sendToBatch();
                        }
                    }
                }
                return false;
            }
        });


        initData();

        ivIsall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Log.e("aaa",
                        "(ApplyDetailActivity.java:167)<--全选点击-->" + b);
                if (b) {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).isCheck = true;
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).isCheck = false;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        String systemModel = SystemUtil.getSystemModel();
        if (systemModel.contains("AL")) {
            findViewById(R.id.view_20dp).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.view_20dp).setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_detail;
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

    private void sendToSingle() {

        Log.e("aaa", "(ApplyDetailActivity.java:167)<---->" + "单独发送");

        if (TextUtils.isEmpty(course_id1)) {
            Toast.makeText(this, "请选择要发送的学生", Toast.LENGTH_SHORT).show();
        }
        OkHttpUtils.post()
                .url(Internet.SINGLENEWS)
                .addParams("course_id", course_id1)
                .addParams("user_id", user_id)
                .addParams("message_content", etMessage.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:304)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:175)单发消息" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(ApplyDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            etMessage.setText("");
                        }
                    }
                });
    }

    private void sendToAll() {
        Log.e("aaa", "(ApplyDetailActivity.java:196)<---->" + "全部发送消息");

        HashMap<Integer, Boolean> deleteMap = mAdapter.getSeleteMap();
        ArrayList<ApplyDetail.DataBean> tempList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (deleteMap.get(i)) {
                tempList.add(mList.get(i));
            }

        }

        String orderId = "";
        String userId = "";
        for (int i = 0; i < tempList.size(); i++) {
            if (i != 0) {
//                orderId = orderId + "," + (tempList.get(i).getCourse_id());
                userId = userId + "," + (tempList.get(i).getUser_id());
            } else {
                orderId = (tempList.get(i).getCourse_id()) + "";
                userId = (tempList.get(i).getUser_id()) + "";
            }
        }


        Log.e("aaa",
                "(ApplyDetailActivity.java:177)" + orderId);
        OkHttpUtils.post()
                .url(Internet.ALLNEWS)
                .addParams("course_id", orderId)
                .addParams("message_content", etMessage.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(ApplyDetailActivity.java:232)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:150)群发消息" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(ApplyDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            etMessage.setText("");
                        }
                    }
                });

    }

    private void sendToBatch() {

        Log.e("aaa", "(ApplyDetailActivity.java:246)<---->" + "组队发送消息");
        HashMap<Integer, Boolean> deleteMap = mAdapter.getSeleteMap();
        ArrayList<ApplyDetail.DataBean> tempList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (deleteMap.get(i)) {
                tempList.add(mList.get(i));
            }

        }

        String courderId = "";
        String userId = "";
        for (int i = 0; i < tempList.size(); i++) {
            if (i != 0) {
//                courderId = courderId + "," + (tempList.get(i).getCourse_id());
                userId = userId + "," + (tempList.get(i).getUser_id());
            } else {
                courderId = (tempList.get(i).getCourse_id()) + "";
                userId = (tempList.get(i).getUser_id()) + "";
            }
        }


        Log.e("aaa", "(ApplyDetailActivity.java:272)<---->" + courderId);
        Log.e("aaa", "(ApplyDetailActivity.java:273)<---->" + userId);
        Log.e("aaa", "(ApplyDetailActivity.java:274)<---->" + etMessage.getText().toString());

        OkHttpUtils.post()
                .url(Internet.SINGLENEWS)
                .addParams("course_id", courderId)
                .addParams("user_id", userId)
                .addParams("message_content", etMessage.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(ApplyDetailActivity.java:235)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:175)单发消息" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(ApplyDetailActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            etMessage.setText("");
                        }
                    }
                });

    }

    private void initData() {
        OkHttpUtils.post()
                .url(Internet.BMINFO)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailActivity.java:66)报名信息" + response);
                        mList.clear();
                        if (TextUtils.isEmpty(response) || response.contains("没有信息")) {
                            Toast.makeText(ApplyDetailActivity.this, "没有信息", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            ArrayList<ApplyDetail.DataBean> dataBeens = (ArrayList<ApplyDetail.DataBean>) gson.fromJson(response, ApplyDetail.class).getData();
                            mList.addAll(dataBeens);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_all_sele, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_all_sele://是否全选
                if (isChoose)
                    i = 1;
                else
                    i = 2;
//                ivIsall.setChecked(true);
                break;
            case R.id.tv_send://发送消息
                if (TextUtils.isEmpty(etMessage.getText().toString())) {
                    Toast.makeText(this, "请填写消息内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (i == 0) {
                    sendToSingle();
                } else if (i == 1) {
                    sendToAll();
                } else {
                    sendToBatch();
                }
                break;
        }
    }
}
