package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SetUserNameActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_id = (String) SPUtils.get(this, "userId", "");
        tvTitle.setText("会员名称");
        String userName = getIntent().getStringExtra("userName");
        etUsername.setText(userName);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_user_name;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_save,R.id.iv_del_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.iv_del_name:
                etUsername.setText("");
                break;
            case R.id.tv_save:
                final String username = etUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(this, "会员名称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpUtils.post()
                        .url(Internet.REALNAME)
                        .addParams("user_id", user_id)
                        .addParams("name", username)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa",
                                        "(SetUserNameActivity.java:66)<---->"+e.getMessage());
                                Toast.makeText(SetUserNameActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(SetUserNameActivity.java:64)" + response);
                                if (response.contains("成功")) {
                                    Toast.makeText(SetUserNameActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    setResult(22, new Intent().putExtra("username", username));
                                    finish();
                                }else {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("msg");
                                        Toast.makeText(SetUserNameActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                break;
        }
    }
}
