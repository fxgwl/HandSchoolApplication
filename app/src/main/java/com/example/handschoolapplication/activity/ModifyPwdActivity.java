package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.InputType;
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
import com.example.handschoolapplication.utils.Utils;
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
    TextView etModifypwdPhone;
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
    @BindView(R.id.iv_is_see)
    ImageView ivIsSee;
    @BindView(R.id.iv_is_sees)
    ImageView ivIsSees;
    private CountDownTimerUtils countDownTimerUtils;
    private String phoneNum;
    private String user_phone;

    private boolean pwdVisible = false;
    private boolean pwdsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("修改密码");
        countDownTimerUtils = new CountDownTimerUtils(tvModifypwdSendcode, 59000, 1000);
        user_phone = (String) SPUtils.get(this, "user_phone", "");
        if (user_phone.length() == 11) {
            StringBuffer stringBuffer = new StringBuffer(user_phone);
            StringBuffer replace = stringBuffer.replace(3, 9, "******");
            etModifypwdPhone.setText(replace.toString());
        }else {
            etModifypwdPhone.setText(user_phone);
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_modify_pwd;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_modifypwd_sendcode, R.id.tv_modifypwd_submit,R.id.iv_is_see,R.id.iv_is_sees})
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
                getCode();
                break;
            //提交
            case R.id.tv_modifypwd_submit:
                forgetPwd();
                break;
            case R.id.iv_is_see:
                if (pwdVisible){
                   pwdVisible = false;
                    etModifypwdNewpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivIsSee.setImageResource(R.drawable.mimabukejian);
                }else {
                    pwdVisible = true;
                    etModifypwdNewpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivIsSee.setImageResource(R.drawable.mimakejian);
                }
                break;
            case R.id.iv_is_sees:
                if (pwdsVisible){
                    pwdsVisible = false;
                    etModifypwdSurenewpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivIsSees.setImageResource(R.drawable.mimabukejian);
                }else {
                    pwdsVisible = true;
                    etModifypwdSurenewpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivIsSees.setImageResource(R.drawable.mimakejian);
                }
                break;
        }
    }

    private void getCode() {
//        phoneNum = etModifypwdPhone.getText().toString().trim();
//        if (!user_phone.equals(phoneNum)){
//            Toast.makeText(this, "输入的手机号有误！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        countDownTimerUtils.start();
        OkHttpUtils.post()
                .url(Internet.PWD_GETCODE)
                .addParams("user_phone", user_phone)
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

        if (TextUtils.isEmpty(newPwds)) {
            Toast.makeText(this, "密码设置不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newPwd.equals(newPwds)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPwds.length() < 6 || !Utils.ispsd(newPwds)) {
            Toast.makeText(this, "输入的密码不符合！", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("user_phone", user_phone);
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
