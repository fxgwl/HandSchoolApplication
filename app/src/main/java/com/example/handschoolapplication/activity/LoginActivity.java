package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolBean;
import com.example.handschoolapplication.bean.UserBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

public class LoginActivity extends BaseActivity implements PlatformActionListener {

    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String user_id = (String) SPUtils.get(this, "userId", "");
        String user_type = (String) SPUtils.get(this, "user_type", "");
        if (!TextUtils.isEmpty(user_id)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("flag", user_type);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.rl_back, R.id.ll_phone_num, R.id.ll_password, R.id.btn_login, R.id.iv_wechat_login, R.id.iv_weibo_login, R.id.iv_qq_login, R.id.tv_forget_pwd, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                break;
            case R.id.ll_phone_num:
                break;
            case R.id.ll_password:
                break;
            case R.id.btn_login:
                String phone = etPhoneNum.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();
                login(phone, pwd);
                break;
            case R.id.iv_wechat_login:
                wechatLogin();
//                showShare();
                break;
            case R.id.iv_weibo_login:
                break;
            case R.id.iv_qq_login:

                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterToActivity.class));
                break;
        }
    }

    private void wechatLogin() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
        if (!wechat.isClientValid()) {
            Toast.makeText(LoginActivity.this, "微信未安装,请先安装微信", Toast.LENGTH_LONG).show();
        }
        wechat.setPlatformActionListener(this); // 设置分享事件回调
//        wechat.showUser(null);//授权并获取用户信息
        wechat.authorize();
    }


    private void login(String phone, String pwd) {

//        if (!MyUtiles.isPhone(phone)) {
//            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
//           return;
//        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入有误！", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("user_phone", phone);
        params.put("user_pwd", pwd);

        OkHttpUtils.post()
                .url(Internet.LOGIN)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:94)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(LoginActivity.java:100)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (data.getString("user_type").equals("0")) {
                                UserBean userBean = new Gson().fromJson(data.toString(), UserBean.class);
                                SPUtils.put(LoginActivity.this, "userId", userBean.getUser_id());
                                SPUtils.put(LoginActivity.this, "user_type", userBean.getUser_type());
                                SPUtils.put(LoginActivity.this, "user_phone", userBean.getUser_phone());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("flag", "0"));
                            }
                            if (data.getString("user_type").equals("1")) {
                                SchoolBean schoolBean = new Gson().fromJson(data.toString(), SchoolBean.class);
                                SPUtils.put(LoginActivity.this, "userId", schoolBean.getUser_id());
                                SPUtils.put(LoginActivity.this, "school_id", schoolBean.getSchool_id());
                                SPUtils.put(LoginActivity.this, "user_type", schoolBean.getUser_type());
                                SPUtils.put(LoginActivity.this, "user_phone", schoolBean.getUser_phone());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("flag", "1"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //三方登录的回调

    //微信登录的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    /**
     * 三方登录
     * */


}
