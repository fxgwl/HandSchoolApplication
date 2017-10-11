package com.example.handschoolapplication.activity;

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
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.et_feedback_content)
    EditText etFeedbackContent;
    @BindView(R.id.tv_feedback_submit)
    TextView tvFeedbackSubmit;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("意见反馈");
        user_id = (String) SPUtils.get(this, "userId", "");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_feed_back;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_feedback_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.tv_feedback_submit:
                //意见反馈
                submitFeedBack();
                break;
        }
    }

    private void submitFeedBack() {
        String content = etFeedbackContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入意见和建议", Toast.LENGTH_SHORT).show();
            return;
        }
//        user_id   feedback_content
        OkHttpUtils.post()
                .url(Internet.BACKINFO)
                .addParams("user_id", user_id)
                .addParams("feedback_content", content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(FeedBackActivity.java:87)" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            Toast.makeText(FeedBackActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                            if (response.contains("成功")) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
