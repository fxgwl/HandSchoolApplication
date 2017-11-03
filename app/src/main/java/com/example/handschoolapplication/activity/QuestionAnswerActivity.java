package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class QuestionAnswerActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_questionanswer_question)
    TextView tvQuestionanswerQuestion;
    @BindView(R.id.tv_questionanswer_answer)
    TextView tvQuestionanswerAnswer;
    private String help_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("问题解答");
        help_id = getIntent().getStringExtra("help_id");
        initView();
    }

    //初始化详情
    private void initView() {
        OkHttpUtils.post()
                .url(Internet.INFODETAIL)
                .addParams("help_id", help_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(QuestionAnswerActivity.java:56)" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String help_title = json.getJSONObject("data").getString("help_title");
                            String help_content = json.getJSONObject("data").getString("help_content");
                            tvQuestionanswerQuestion.setText(help_title);
                            tvQuestionanswerAnswer.setText(help_content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_question_answer;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
