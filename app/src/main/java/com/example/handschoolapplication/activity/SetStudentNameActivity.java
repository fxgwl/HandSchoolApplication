package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SetStudentNameActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_student_name)
    EditText etStudentName;
    @BindView(R.id.iv_circle)
    ImageView ivCircle;
    @BindView(R.id.iv_circles)
    ImageView ivCircles;
    private String order_id;
    private String sex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvTitle.setText("设置学生姓名");

        etStudentName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                }
                return false;
            }
        });
        order_id = getIntent().getStringExtra("order_id");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_student_name;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_save_student_name, R.id.rl_nan, R.id.rl_nv,R.id.iv_del_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.iv_del_name:
                etStudentName.setText("");
                break;
            case R.id.rl_nan:
                ivCircle.setImageResource(R.drawable.hongquan);
                ivCircles.setImageResource(R.drawable.baiquan);
                sex = "男";
                break;
            case R.id.rl_nv:
                ivCircle.setImageResource(R.drawable.baiquan);
                ivCircles.setImageResource(R.drawable.hongquan);
                sex = "女";
                break;
            case R.id.tv_save_student_name:
                final String studentName = etStudentName.getText().toString().trim();
                if (!TextUtils.isEmpty(studentName)) {

                    OkHttpUtils.post()
                            .url(Internet.STUDENT_NAME)
                            .addParams("order_id", order_id)
                            .addParams("student_name", studentName)
                            .addParams("student_sex", sex)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("aaa",
                                            "(SetStudentNameActivity.java:74)<---->" + e.getMessage());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("aaa",
                                            "(SetStudentNameActivity.java:81)<---->" + response);
                                    if (TextUtils.isEmpty(response)) {
                                        Toast.makeText(SetStudentNameActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                                    } else {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            int result = jsonObject.getInt("result");
                                            if (result == 0) {
                                                setResult(22, new Intent()
                                                        .putExtra("student", studentName)
                                                        .putExtra("sex", sex));
                                                finish();
                                            }
                                            String msg = jsonObject.getString("msg");
                                            Toast.makeText(SetStudentNameActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(this, "请输入学生姓名！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

}
