package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PublishEvaluateActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.ratb_classflee)
    RatingBar ratbClassflee;
    @BindView(R.id.tv_classscore)
    TextView tvClassscore;
    @BindView(R.id.ratb_schooladjust)
    RatingBar ratbSchooladjust;
    @BindView(R.id.tv_shcoolscore)
    TextView tvShcoolscore;
    @BindView(R.id.et_classcomment)
    EditText etClasscomment;
    @BindView(R.id.cb_idni)
    CheckBox cbIdni;
    private String user_id;
    private String order_id;
    private String classscore;
    private String schoolscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        user_id = (String) SPUtils.get(this, "userId", "");
        order_id = getIntent().getStringExtra("order_id");
        ratbClassflee.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("aaa",
                        "(PublishEvaluateActivity.java:42)" + rating);
                classscore = rating + "";
                tvClassscore.setText(rating + "分");
            }
        });
        ratbSchooladjust.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("aaa",
                        "(PublishEvaluateActivity.java:42)" + rating);
                schoolscore = rating + "";
                tvShcoolscore.setText(rating + "分");
            }
        });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_publish_evaluate;
    }

    @OnClick({R.id.rl_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_save:
                String schoolComment = etClasscomment.getText().toString();

                if (TextUtils.isEmpty(schoolComment)) {
                    Toast.makeText(this, "请输入评价", Toast.LENGTH_SHORT).show();
                    return;
                }
//                send_uid
//                        order_id
//                class_score
//                        school_score
//                contents
//                        anonymous
                HashMap<String, String> params = new HashMap<>();
                params.put("send_uid", user_id);
                params.put("order_id", order_id);
                params.put("class_score", classscore);
                params.put("school_score", schoolscore);
                params.put("contents", schoolComment);
                if (cbIdni.isChecked()) {
                    params.put("anonymous", "1");
                }
                OkHttpUtils.post()
                        .url(Internet.COMMENT)
                        .params(params)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(PublishEvaluateActivity.java:127)" + response);
                                if (response.contains("添加成功")) {
                                    finish();
                                }
                            }
                        });
                break;
        }
    }
}
