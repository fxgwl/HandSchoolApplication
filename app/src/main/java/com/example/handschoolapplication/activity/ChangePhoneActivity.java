package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.CountDownTimerUtils;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ChangePhoneActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.et_changephone_pwd)
    EditText etChangephonePwd;
    @BindView(R.id.et_changephone_newphone)
    EditText etChangephoneNewphone;
    @BindView(R.id.et_changephone_code)
    EditText etChangephoneCode;
    @BindView(R.id.tv_changephone_obtaincode)
    TextView tvChangephoneObtaincode;
    @BindView(R.id.tv_changephone_submit)
    TextView tvChangephoneSubmit;
    private String user_id;
    private CountDownTimerUtils countDownTimerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("修改手机号");
        user_id = (String) SPUtils.get(this, "userId", "");
        countDownTimerUtils = new CountDownTimerUtils(tvChangephoneObtaincode, 59000, 1000);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_phone;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_changephone_obtaincode, R.id.tv_changephone_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            //获取验证码
            case R.id.tv_changephone_obtaincode:
                getCode();
                break;
            //提交
            case R.id.tv_changephone_submit:
                changePhone();
                break;
        }
    }

    //修改手机号
    private void changePhone() {
        String pwd = etChangephonePwd.getText().toString();
        String user_phone = etChangephoneNewphone.getText().toString();
        String user_code = etChangephoneCode.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user_code)) {
            Toast.makeText(this, "请填写正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
//        user_id
//                phone
//        pwd
//                user_code
        OkHttpUtils.post()
                .url(Internet.CHANGEPHONE)
                .addParams("user_id", user_id)
                .addParams("phone", user_phone)
                .addParams("pwd", pwd)
                .addParams("user_code", user_code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChangePhoneActivity.java:114)" + response);
                        if (response.contains("成功")) {
                            Toast.makeText(ChangePhoneActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChangePhoneActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void getCode() {
        String user_phone = etChangephoneNewphone.getText().toString();
        if (TextUtils.isEmpty(user_phone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
//        user_phone
//                user_id
        OkHttpUtils.post()
                .url(Internet.CHANGEPHONECODE)
                .addParams("user_phone", user_phone)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ChangePhoneActivity.java:95)" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            Toast.makeText(ChangePhoneActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                            if (response.contains("成功")) {
                                countDownTimerUtils.start();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
