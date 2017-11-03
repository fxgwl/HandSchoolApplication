package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.handschoolapplication.utils.Internet;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ReturnMoneyActivity extends AppCompatActivity {

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
    private String ordernum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_money);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
        String courseid = intent.getStringExtra("courseid");
        String ivcourse = intent.getStringExtra("ivcourse");
        String coursename = intent.getStringExtra("coursename");
        String money = intent.getStringExtra("money");
        String coursenum = intent.getStringExtra("coursenum");
        String tuimoney = intent.getStringExtra("tuimoney");
        tvTitle.setText("订单详情");
        tvOrdernum.setText(ordernum);
        Glide.with(this)
                .load(Internet.BASE_URL + ivcourse)
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(ivCourse);
        tvCourse.setText(coursename);
        tvPrice.setText("价格：¥" + money);
        tvCoursenum.setText(courseid);
        tvNum.setText("x" + coursenum);
        tvTuimoney.setText(tuimoney);
    }

    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                String class_people = tvTuireason.getText().toString();
                if (TextUtils.isEmpty(class_people)) {
                    Toast.makeText(this, "请填写退款原因", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpUtils.post()
                        .url(Internet.RETURNMONEY)
                        .addParams("order_id", ordernum)
                        .addParams("class_people", class_people)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("aaa",
                                        "(ReturnMoneyActivity.java:108)" + response);
                                if (response.contains("成功")) {
                                    finish();
                                    Toast.makeText(ReturnMoneyActivity.this, "提交退款成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}
