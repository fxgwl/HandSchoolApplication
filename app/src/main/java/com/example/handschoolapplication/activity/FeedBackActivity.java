package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
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

        etFeedbackContent.setInputType(TYPE_TEXT_FLAG_MULTI_LINE);
        etFeedbackContent.setSingleLine(false);

        etFeedbackContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    submitFeedBack();
                }
                return false;

            }
        });
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
                show(view);
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
                            if (response.contains("成功")) {
                                Toast toast = Toast.makeText(FeedBackActivity.this, "您好，您的建议和意见非常重要，我们会认真处理您的反馈。感谢您对掌上私塾的支持！为了孩子我们一直在努力！", 2000);
                                toast.setDuration(2000);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();

                                finish();
                            }else {
                                Toast.makeText(FeedBackActivity.this, json.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
