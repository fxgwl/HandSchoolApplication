package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class ForgetPwdToActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwds)
    EditText etNewPwds;
    @BindView(R.id.iv_is_see)
    ImageView ivIsSee;
    @BindView(R.id.iv_is_sees)
    ImageView ivIsSees;
    private String phone;
    private String code;

    private boolean pwdVisible = false;
    private boolean pwdsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("忘记密码");
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_pwd_to;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.btn_ok,R.id.iv_is_see,R.id.iv_is_sees})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.btn_ok:
                forgetPwd();
                break;
            case R.id.iv_is_see:
                if (pwdVisible){
                    pwdVisible = false;
                    etNewPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivIsSee.setImageResource(R.drawable.mimabukejian);
                }else {
                    pwdVisible = true;
                    etNewPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivIsSee.setImageResource(R.drawable.mimakejian);
                }
                break;
            case R.id.iv_is_sees:
                if (pwdsVisible){
                    pwdsVisible = false;
                    etNewPwds.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivIsSees.setImageResource(R.drawable.mimabukejian);
                }else {
                    pwdsVisible = true;
                    etNewPwds.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivIsSees.setImageResource(R.drawable.mimakejian);
                }
                break;
        }

        etNewPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String newPwd = etNewPwd.getText().toString().trim();
                    if (newPwd.length() < 1) {
                        Toast.makeText(ForgetPwdToActivity.this, "密码设置不能为空", Toast.LENGTH_SHORT).show();
                    }

                    if (newPwd.length()<6||!Utils.ispsd(newPwd)){
                        Toast.makeText(ForgetPwdToActivity.this, "密码必须包含字母和数字且长度不能小于6位！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void forgetPwd() {
        String newPwds = etNewPwds.getText().toString().trim();
        String newPwd = etNewPwd.getText().toString().trim();

        if (!newPwd.equals(newPwds)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPwd.length()<6||!Utils.ispsd(newPwd)){
            Toast.makeText(ForgetPwdToActivity.this, "密码设置不符合条件！", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("user_phone", phone);
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
                            Toast.makeText(ForgetPwdToActivity.this, msg, Toast.LENGTH_SHORT).show();

                            if (result == 0) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
