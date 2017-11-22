package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgetPwdActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    private CountDownTimerUtils countDownTimerUtils;
    private String phoneNum;
    private String user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("忘记密码");
        countDownTimerUtils = new CountDownTimerUtils(tvGetCode, 59000, 1000);
        mBtnNext.setEnabled(false);
        user_phone = (String) SPUtils.get(this, "user_phone", "");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_pwd;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_get_code, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.tv_get_code:
                countDownTimerUtils.start();
                getCode();
                break;
            case R.id.btn_next:
                String code = etCode.getText().toString().trim();
                startActivity(new Intent(this,ForgetPwdToActivity.class)
                        .putExtra("phone",phoneNum)
                        .putExtra("code",code));
                finish();
                break;
        }
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==6){
                    mBtnNext.setBackgroundColor(Color.parseColor("#27acf6"));
                    mBtnNext.setEnabled(true);
                }else {
                    mBtnNext.setBackgroundColor(Color.parseColor("#e6e6e6"));
                    mBtnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getCode() {
        phoneNum = etPhone.getText().toString().trim();
        if (!phoneNum.equals(user_phone)){
            Toast.makeText(this, "输入的手机号有误！", Toast.LENGTH_SHORT).show();
            etPhone.setText("");
            return;
        }
        OkHttpUtils.post()
                .url(Internet.PWD_GETCODE)
                .addParams("user_phone", phoneNum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ForgetPwdActivity.java:71)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ForgetPwdActivity.java:78)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(ForgetPwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
