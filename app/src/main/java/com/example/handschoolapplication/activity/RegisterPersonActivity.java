package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.UserBean;
import com.example.handschoolapplication.utils.CountDownTimerUtils;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPersonActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwds)
    EditText etPwds;
    @BindView(R.id.iv_checkbox)
    ImageView ivCheckbox;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private CountDownTimerUtils countDownTimerUtils;
    private int flag = 0;//默认没选用户协议
    private String type;
    private boolean isThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countDownTimerUtils = new CountDownTimerUtils(tvGetCode, 59000, 1000);
        type = getIntent().getStringExtra("type");
        switch (type) {
            case "0":
                btnRegister.setText("注册");
                break;
            case "1":
                btnRegister.setText("下一步");
                break;
        }
        if ("three".equals(getIntent().getStringExtra("flag"))){
            isThree = true;
        }else {
            isThree = false;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register_person;
    }

    @OnClick({R.id.rl_back, R.id.tv_get_code, R.id.btn_register, R.id.iv_checkbox, R.id.tv_to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_get_code:
                countDownTimerUtils.start();
                if (isThree){
                    getThreeCode();
                }else {
                    getRegCode();
                }
                break;
            case R.id.btn_register:
                if (flag == 1) {
                    register(type);
                } else {
                    Toast.makeText(this, "请先阅读并同意《用户协议》", Toast.LENGTH_SHORT).show();
                }

//                startActivity(new Intent(RegisterPersonActivity.this, AddDataActivity.class));
                break;
            case R.id.iv_checkbox:
                if (flag == 0) {
                    flag = 1;
                    ivCheckbox.setImageResource(R.drawable.checkboxs);
                } else {
                    flag = 0;
                    ivCheckbox.setImageResource(R.drawable.checkbox);
                }
                break;
            case R.id.tv_to_login:
                startActivity(new Intent(RegisterPersonActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void getThreeCode() {
        String phonenum = etPhoneNum.getText().toString().trim();
        String user_id = getIntent().getStringExtra("user_id");
        OkHttpUtils.post()
                .url(Internet.GET_CODE_FOR_THREE)
                .addParams("user_phone", phonenum)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:137)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(RegisterPersonActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getRegCode() {
        String phonenum = etPhoneNum.getText().toString().trim();
        OkHttpUtils.post()
                .url(Internet.RE_GETCODE)
                .addParams("user_phone", phonenum)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:166)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(RegisterPersonActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //注册
    private void register(String type) {

        final String phone = etPhoneNum.getText().toString().trim();
        String code = etPhoneCode.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String pwds = etPwds.getText().toString().trim();

        if (!pwd.equals(pwds)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("user_phone", phone);
        params.put("user_password", pwd);
        params.put("user_code", code);
        params.put("user_type", type);

        OkHttpUtils.post()
                .url(Internet.REGISTER)
                .params(params)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:185)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(RegisterPersonActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (result == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                String user_id = data.getString("user_id");
                                MyUtiles.saveBeanByFastJson(RegisterPersonActivity.this, "userId", user_id);
                                switch (RegisterPersonActivity.this.type) {
                                    case "0":
                                        if (jsonObject.getInt("result")==0){
                                            UserBean userBean = new Gson().fromJson(data.toString(), UserBean.class);
                                            SPUtils.put(RegisterPersonActivity.this, "userId", userBean.getUser_id());
                                            SPUtils.put(RegisterPersonActivity.this, "user_type", userBean.getUser_type());
                                            SPUtils.put(RegisterPersonActivity.this, "user_phone", phone);
                                            SPUtils.put(RegisterPersonActivity.this, "isLogin", true);
                                            SPUtils.put(RegisterPersonActivity.this, "flag", "0");
                                            startActivity(new Intent(RegisterPersonActivity.this, MainActivity.class));
                                            finish();
                                        }
                                        break;
                                    case "1":
                                        startActivity(new Intent(RegisterPersonActivity.this, AddDataActivity.class));
                                        finish();
                                        break;
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
