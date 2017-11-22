package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ModifyPwdActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.et_modifypwd_phone)
    EditText etModifypwdPhone;
    @BindView(R.id.et_modifypwd_code)
    EditText etModifypwdCode;
    @BindView(R.id.tv_modifypwd_sendcode)
    TextView tvModifypwdSendcode;
    @BindView(R.id.et_modifypwd_newpwd)
    EditText etModifypwdNewpwd;
    @BindView(R.id.et_modifypwd_surenewpwd)
    EditText etModifypwdSurenewpwd;
    @BindView(R.id.tv_modifypwd_submit)
    TextView tvModifypwdSubmit;
    private CountDownTimerUtils countDownTimerUtils;
    private String phoneNum;
    private String user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("修改密码");
        countDownTimerUtils = new CountDownTimerUtils(tvModifypwdSendcode, 59000, 1000);
        user_phone = (String) SPUtils.get(this, "user_phone", "");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_modify_pwd;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_modifypwd_sendcode, R.id.tv_modifypwd_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            //发送验证码
            case R.id.tv_modifypwd_sendcode:
                countDownTimerUtils.start();
                getCode();
                break;
            //提交
            case R.id.tv_modifypwd_submit:
                forgetPwd();
                break;
        }
    }

    private void getCode() {
        phoneNum = etModifypwdPhone.getText().toString().trim();
        if (!user_phone.equals(phoneNum)){
            Toast.makeText(this, "输入的手机号有误！", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ModifyPwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void forgetPwd() {
        String newPwds = etModifypwdNewpwd.getText().toString().trim();
        String newPwd = etModifypwdSurenewpwd.getText().toString().trim();
        String code = etModifypwdCode.getText().toString().trim();

        if (TextUtils.isEmpty(newPwds)){
            Toast.makeText(this, "密码设置不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPwd.equals(newPwds)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("user_phone", phoneNum);
        params.put("newpassword", newPwd);
        params.put("user_code", code);

        OkHttpUtils.post()
                .url(Internet.FORGOTPWD)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ForgetPwdToActivity.java:97)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ForgetPwdToActivity.java:104)" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(ModifyPwdActivity.this, msg, Toast.LENGTH_SHORT).show();

                            if (result == 0) {
                                finish();
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
