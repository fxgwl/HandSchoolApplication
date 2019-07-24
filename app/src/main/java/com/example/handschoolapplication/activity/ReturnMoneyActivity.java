package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class ReturnMoneyActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_ordernum)
    TextView tvOrdernum;
    @BindView(R.id.tv_coursenum)
    TextView tvCoursenum;
    @BindView(R.id.iv_course)
    ImageView ivCourse;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll_course)
    LinearLayout llCourse;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_tuimoney)
    TextView tvTuimoney;
    @BindView(R.id.tv_tuireason)
    EditText tvTuireason;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rl_course)
    RelativeLayout rlCourse;
    @BindView(R.id.et_account)
    EditText etAccount;
    private String ordernum;
    private String course_id;
    private String schooluid;
    private String user_phone;
    private String account;
    private double v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        user_phone = (String) SPUtils.get(this, "user_phone", "");
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
        String courseid = intent.getStringExtra("courseid");
        String ivcourse = intent.getStringExtra("ivcourse");
        String coursename = intent.getStringExtra("coursename");
        String money = intent.getStringExtra("money");
        String coursenum = intent.getStringExtra("coursenum");
        String tuimoney = intent.getStringExtra("tuimoney");
        course_id = intent.getStringExtra("course_id");
        schooluid = intent.getStringExtra("schooluid");
        tvTitle.setText("订单详情");
        tvOrdernum.setText(ordernum);

        String img = TextUtils.isEmpty(ivcourse) ? "" : ivcourse;
        with(this)
                .load(Internet.BASE_URL + img)
                .centerCrop()
                .into(ivCourse);
        tvCourse.setText(coursename);
        tvPrice.setText("价格：¥" + money);
        tvCoursenum.setText(courseid);
        tvNum.setText("x" + coursenum);
        v = Double.parseDouble(tuimoney);
        tvTuimoney.setText(v + "元");

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_return_money;
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.rl_back, R.id.iv_menu, R.id.rl_course})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.rl_course:
                Intent intent = new Intent(this, CourseHomePagerActivity.class);
                intent.putExtra("course_id", course_id);
                intent.putExtra("schooluid", schooluid);
                startActivity(intent);
                break;
            case R.id.tv_submit:
                String class_people = tvTuireason.getText().toString();
                account = etAccount.getText().toString().trim();//用户填写的退款账号
                if (TextUtils.isEmpty(class_people)) {
                    Toast.makeText(this, "请填写退款原因", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入退款账号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpUtils.post()
                        .url(Internet.RETURNMONEY)
                        .addParams("order_id", ordernum)
                        .addParams("class_people", class_people)
                        .addParams("alipay_num", account)
                        .addParams("order_money", String.valueOf(v))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.e("aaa", "(ReturnMoneyActivity.java:146)<---->" + e.getMessage());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(ReturnMoneyActivity.java:108)" + response);
                                if (response.contains("成功")) {
                                    EventBus.getDefault().post("refund");
                                    finish();
                                    Toast.makeText(ReturnMoneyActivity.this, "提交退款成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }


}
