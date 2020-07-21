package com.example.handschoolapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.LearnNewsBean;
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class LearnNewsDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_class_phone)
    TextView tvClassPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private LearnNewsBean learnNewsBean;
    private int smessage_id;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private String userPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("学习消息");
        learnNewsBean = (LearnNewsBean) getIntent().getSerializableExtra("learnNewsBean");
        String school_name = learnNewsBean.getSchool_name();
        tvClassName.setText(school_name);
        String message_content = learnNewsBean.getMessage_content();
        tvContent.setText(message_content);
        smessage_id = learnNewsBean.getSmessage_id();
        tvTime.setText(learnNewsBean.getMessage_time());
        readNews();
    }

    private void readNews() {

        OkHttpUtils.post()
                .url(Internet.READ_NEWS)
                .addParams("smessage_id", smessage_id + "")
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LearnNewsDetailActivity.java:54)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LearnNewsDetailActivity.java:61)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            userPhone = data.has("user_phone") ? data.getString("user_phone") : "";
                            tvClassPhone.setText(String.valueOf(userPhone));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LearnNewsDetailActivity.this, "阅读失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_learn_news_detail;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_class_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_class_phone:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        //拨打电话
                        if (!TextUtils.isEmpty(userPhone))
                            callClass(userPhone);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                showToast("权限已申请");
                //拨打电话
                if (!TextUtils.isEmpty(userPhone))
                    callClass(userPhone);
            } else {
                boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(LearnNewsDetailActivity.this, permissions[0]);
                if (showRequestPermission) {
//                        Toast.makeText(this, "权限未申请", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "未获得打电话权限", Toast.LENGTH_SHORT).show();
                    //requestPermission();
                }else{
                    Toast.makeText(this, "手机系统设置->应用和通知->权限管理 进行设置", Toast.LENGTH_SHORT).show();
                    //showUnLoginDialog("点开手机系统设置->应用和通知->权限管理",0);
                }
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void callClass(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }
}
