package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.utils.EmojiFilter;
import com.example.handschoolapplication.utils.FilterEmojiTextWatcher;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

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
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.civ_class_icon)
    CircleImageView civClassIcon;
    private String user_id;
    private String order_id;
    private String classscore = "5";
    private String schoolscore = "5";
    private OrderBean.DataBean dataBean;
    private String school_name;
    private String class_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        user_id = (String) SPUtils.get(this, "userId", "");
//        dataBean = (OrderBean.DataBean) getIntent().getSerializableExtra("dataBean");
        order_id = getIntent().getStringExtra("order_id");
        Log.e("aaa", "(PublishEvaluateActivity.java:72)<--传过来的OrderId-->" + order_id);
        school_name = getIntent().getStringExtra("school_name");
        class_photo = getIntent().getStringExtra("class_photo");
        initView();
        ratbClassflee.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("aaa",
                        "(PublishEvaluateActivity.java:42)" + rating);
                classscore = (int) rating + "";
                tvClassscore.setText(rating + "分");
            }
        });
        ratbSchooladjust.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("aaa",
                        "(PublishEvaluateActivity.java:42)" + rating);
                schoolscore = (int) rating + "";
                tvShcoolscore.setText(rating + "分");
            }
        });

        etClasscomment.addTextChangedListener(new FilterEmojiTextWatcher(PublishEvaluateActivity.this){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                super.beforeTextChanged(charSequence, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                super.onTextChanged(charSequence, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
            }
        });

    }

    private void initView() {
        tvClassName.setText(school_name);

        with(this)
                .load(Internet.BASE_URL + class_photo)
                .centerCrop()
                .into(civClassIcon);
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
//                etClasscomment.setFilters(new InputFilter[]{new EmojiFilter()});

                Log.e("aaa",
                        "(PublishEvaluateActivity.java:132)<--classscore-->" + classscore);
                Log.e("aaa",
                        "(PublishEvaluateActivity.java:132)<--schoolscore-->" + schoolscore);
                HashMap<String, String> params = new HashMap<>();
                params.put("send_uid", user_id);
                params.put("order_id", order_id);
                params.put("class_score", classscore);
                params.put("school_score", schoolscore);
                params.put("contents", schoolComment);
                if (cbIdni.isChecked()) {
                    params.put("anonymous", "1");
                }

                Log.e("aaa",
                        "(PublishEvaluateActivity.java:146)<--params.toString()-->" + params.toString());

                OkHttpUtils.post()
                        .url(Internet.COMMENT)
                        .params(params)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa",
                                        "(PublishEvaluateActivity.java:147)<---->" + e.getMessage());
                                Toast.makeText(PublishEvaluateActivity.this, "网络不给力！", Toast.LENGTH_SHORT).show();
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
