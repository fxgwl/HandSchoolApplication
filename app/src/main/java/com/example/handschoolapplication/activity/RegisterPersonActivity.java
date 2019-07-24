package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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
import com.example.handschoolapplication.utils.Utils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

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
    @BindView(R.id.iv_is_see)
    ImageView ivPwdVisible;
    @BindView(R.id.iv_is_sees)
    ImageView ivPwdsVisible;
    @BindView(R.id.tv_xieyi)
    TextView tvAgreement;

    private CountDownTimerUtils countDownTimerUtils;
    private int flag = 1;//默认没选用户协议
    private String type;
    private boolean isThree;
    private String user_id;

    private boolean pwdVisible = false;
    private boolean pwdsVisible = false;
    private String code;

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
        if (("true").equals(getIntent().getStringExtra("flag"))) {
            isThree = true;
            user_id = (String) SPUtils.get(this, "userId", "");
            btnRegister.setText("绑定手机号");
        } else {
            Log.e("aaa",
                    "(RegisterPersonActivity.java:77)<--asdsdsdsdsa-->");
            isThree = false;
        }

        setListener();
    }

    private void setListener() {
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pwd = etPwd.getText().toString().trim();
                    if (pwd.length() < 6 || !Utils.ispsd(pwd)) {
                        Toast.makeText(RegisterPersonActivity.this, "密码必须包含数字和字母且长度不能小于6位！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register_person;
    }

    @OnClick({R.id.rl_back, R.id.tv_get_code, R.id.btn_register, R.id.iv_checkbox, R.id.tv_to_login, R.id.iv_is_see,
            R.id.iv_is_sees, R.id.tv_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_get_code:
                String phonenum = etPhoneNum.getText().toString().trim();
                if (!MyUtiles.isPhone(phonenum)) {
                    Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                countDownTimerUtils.start();
                if (isThree) {
                    getThreeCode(phonenum);
                } else {
                    getRegCode(phonenum);
                }
                break;
            case R.id.tv_xieyi:
                String url = "";
                if (type.equals("1")) {
                    url = "merchant_service1.html";
                } else {
                    url = "user_service1.html";
                }
                startActivity(new Intent(this, AgreementActivity.class).putExtra("url", url));
                break;
            case R.id.btn_register:
                if (flag == 1) {
                    register(type);
                } else {
                    Toast.makeText(this, "请先阅读并同意《用户协议》", Toast.LENGTH_SHORT).show();
                }
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
            case R.id.iv_is_see:
                if (pwdVisible) {
                    pwdVisible = false;
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivPwdVisible.setImageResource(R.drawable.mimabukejian);
                } else {
                    pwdVisible = true;
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivPwdVisible.setImageResource(R.drawable.mimakejian);
                }
                break;
            case R.id.iv_is_sees:
                if (pwdsVisible) {
                    pwdsVisible = false;
                    etPwds.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    ivPwdsVisible.setImageResource(R.drawable.mimabukejian);
                } else {
                    pwdsVisible = true;
                    etPwds.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    ivPwdsVisible.setImageResource(R.drawable.mimakejian);
                }
                break;
        }
    }

    private void getThreeCode(String phonenum) {
        OkHttpUtils.post()
                .url(Internet.GET_CODE_FOR_THREE)
                .addParams("user_phone", phonenum)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:153)<---->" + e.getMessage());
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

    private void getRegCode(final String phonenum) {
        Log.e("aaa",
                "(RegisterPersonActivity.java:178)phonenum ==== " + phonenum);
        OkHttpUtils.post()
                .url(Internet.RE_GETCODE)
                .addParams("user_phone", phonenum)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(okhttp3.Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:218)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:166)" + response);
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:178)phonenum ==== " + phonenum);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(RegisterPersonActivity.this, msg, Toast.LENGTH_SHORT).show();
                            code = jsonObject.getString("data");
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
        final String pwd = etPwd.getText().toString().trim();
        String pwds = etPwds.getText().toString().trim();

        if (!(Utils.ispsd(pwd) && pwd.length() > 5)) {
            Toast.makeText(this, "密码必须包含数字和字母且长度不能小于6位！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pwd.equals(pwds)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("user_phone", phone);
        params.put("user_password", pwd);
        params.put("user_code", code);
        params.put("user_type", type);
        if (!TextUtils.isEmpty(user_id)) {
            params.put("user_id", user_id);
        }

        if ("1".equals(type)) {
            if (code.equals(this.code)){
                startActivity(new Intent(RegisterPersonActivity.this, AddDataActivity.class)
                        .putExtra("user_phone", phone)
                        .putExtra("user_password", pwd)
                        .putExtra("user_code", code)
                        .putExtra("user_type", type));
                finish();
            }else {
                Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        OkHttpUtils.post()
                .url(Internet.REGISTER)
                .params(params)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(RegisterPersonActivity.java:280)<---->" + e.getMessage());
                        Toast.makeText(RegisterPersonActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
                                UserBean userBean = new Gson().fromJson(data.toString(), UserBean.class);
                                SPUtils.put(RegisterPersonActivity.this, "user_phone", userBean.getUser_phone());
                                switch (RegisterPersonActivity.this.type) {
                                    case "0":
                                        if (jsonObject.getInt("result") == 0) {
                                            SPUtils.put(RegisterPersonActivity.this, "userId", userBean.getUser_id());
                                            SPUtils.put(RegisterPersonActivity.this, "user_type", userBean.getUser_type());
                                            SPUtils.put(RegisterPersonActivity.this, "user_phone", phone);
                                            SPUtils.put(RegisterPersonActivity.this, "isLogin", true);
                                            SPUtils.put(RegisterPersonActivity.this, "flag", "0");
                                            setTagAndAlias(userBean.getUser_id());
                                            if (isThree) {
                                                finish();
                                            } else {
                                                startActivity(new Intent(RegisterPersonActivity.this, LoginActivity.class)
                                                        .putExtra("from", "register")
                                                        .putExtra("phone", phone)
                                                        .putExtra("password", pwd)
                                                );
                                                finish();
                                            }

                                        }
                                        break;
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias(String alias) {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!TextUtils.isEmpty(alias)) {
            tags.add(alias);//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(this, alias, tags,
                mAliasCallback);
        // }
    }

    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };
}
