package com.example.handschoolapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SignBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.example.handschoolapplication.R.id.tv_sign_days;

public class SignActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(tv_sign_days)
    TextView tvSignDays;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_date_1)
    TextView tvDate1;
    @BindView(R.id.tv_date_2)
    TextView tvDate2;
    @BindView(R.id.tv_date_3)
    TextView tvDate3;
    @BindView(R.id.tv_date_4)
    TextView tvDate4;
    @BindView(R.id.tv_date_5)
    TextView tvDate5;
    @BindView(R.id.tv_date_6)
    TextView tvDate6;
    @BindView(R.id.tv_date_7)
    TextView tvDate7;
    @BindView(R.id.tv_date_8)
    TextView tvDate8;
    @BindView(R.id.tv_date_9)
    TextView tvDate9;
    @BindView(R.id.tv_date_10)
    TextView tvDate10;
    @BindView(R.id.tv_date_11)
    TextView tvDate11;
    @BindView(R.id.tv_date_12)
    TextView tvDate12;
    @BindView(R.id.tv_date_13)
    TextView tvDate13;
    @BindView(R.id.tv_date_14)
    TextView tvDate14;
    @BindView(R.id.tv_date_15)
    TextView tvDate15;
    @BindView(R.id.tv_date_16)
    TextView tvDate16;
    @BindView(R.id.tv_date_17)
    TextView tvDate17;
    @BindView(R.id.tv_date_18)
    TextView tvDate18;
    @BindView(R.id.tv_date_19)
    TextView tvDate19;
    @BindView(R.id.tv_date_20)
    TextView tvDate20;
    @BindView(R.id.tv_date_21)
    TextView tvDate21;
    @BindView(R.id.tv_date_22)
    TextView tvDate22;
    @BindView(R.id.tv_date_23)
    TextView tvDate23;
    @BindView(R.id.tv_date_24)
    TextView tvDate24;
    @BindView(R.id.tv_date_25)
    TextView tvDate25;
    @BindView(R.id.tv_date_26)
    TextView tvDate26;
    @BindView(R.id.tv_date_27)
    TextView tvDate27;
    @BindView(R.id.tv_date_28)
    TextView tvDate28;
    @BindView(R.id.tv_date_29)
    TextView tvDate29;
    @BindView(R.id.tv_date_30)
    TextView tvDate30;
    @BindView(R.id.tv_date_31)
    TextView tvDate31;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;
    @BindView(R.id.view_end)
    View viewEnd;
    private String userId;
    private List<SignBean.DataBean> mList = new ArrayList<>();
    private List<String> mData = new ArrayList<>();
    private String city;
    private String[] split;
    private String signed_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = (String) SPUtils.get(this, "userId", "");
        city = (String) SPUtils.get(this, "city", "");
        signed_num = getIntent().getStringExtra("signed_num");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        //签到
        split = str.split("-");
        initView();
        initData();
    }

    private void initData() {

        OkHttpUtils
                .post()
                .url(InternetS.SIGN_LIST_HP)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(SignActivity.java:116)" + e.getMessage()
                        );
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(SignActivity.java:124)" + response);
                        if (response.contains("没有信息")) {
                        } else {
                            SignBean signBean = new Gson().fromJson(response, SignBean.class);
                            mList.addAll(signBean.getData());

                            for (int i = 0; i < mList.size(); i++) {
                                String sign_time = mList.get(i).getSign_time();
                                String[] split1 = sign_time.split("-");
                                if (split1[2].equals(split[2])) tvSign.setText("已签到");
                                initView2(split1[2]);
                            }

                        }
                    }
                });
    }

    private void initView2(String date) {
        switch (date) {
            case "01":
                setTvBg(tvDate1);
                break;
            case "02":
                setTvBg(tvDate2);
                break;
            case "03":
                setTvBg(tvDate3);
                break;
            case "04":
                setTvBg(tvDate4);
                break;
            case "05":
                setTvBg(tvDate5);
                break;
            case "06":
                setTvBg(tvDate6);
                break;
            case "07":
                setTvBg(tvDate7);
                break;
            case "08":
                setTvBg(tvDate8);
                break;
            case "09":
                setTvBg(tvDate9);
                break;
            case "10":
                setTvBg(tvDate10);
                break;
            case "11":
                setTvBg(tvDate11);
                break;
            case "12":
                setTvBg(tvDate12);
                break;
            case "13":
                setTvBg(tvDate13);
                break;
            case "14":
                setTvBg(tvDate14);
                break;
            case "15":
                setTvBg(tvDate15);
                break;
            case "16":
                setTvBg(tvDate16);
                break;
            case "17":
                setTvBg(tvDate17);
                break;
            case "18":
                setTvBg(tvDate18);
                break;
            case "19":
                setTvBg(tvDate19);
                break;
            case "20":
                setTvBg(tvDate20);
                break;
            case "21":
                setTvBg(tvDate21);
                break;
            case "22":
                setTvBg(tvDate22);
                break;
            case "23":
                setTvBg(tvDate23);
                break;
            case "24":
                setTvBg(tvDate24);
                break;
            case "25":
                setTvBg(tvDate25);
                break;
            case "26":
                setTvBg(tvDate26);
                break;
            case "27":
                setTvBg(tvDate27);
                break;
            case "28":
                setTvBg(tvDate28);
                break;
            case "29":
                setTvBg(tvDate29);
                break;
            case "30":
                setTvBg(tvDate30);
                break;
            case "31":
                setTvBg(tvDate31);
                break;
        }
    }

    private void initView() {

        tvTitle.setText("签到");
        tvSignDays.setText(signed_num);
        int currentMonthDay = getCurrentMonthDay();//当月天数
        if (currentMonthDay == 28) {
            llEnd.setVisibility(View.GONE);
            viewEnd.setVisibility(View.GONE);
            tvDate30.setVisibility(View.INVISIBLE);
            tvDate29.setVisibility(View.INVISIBLE);
        } else if (currentMonthDay == 29) {
            llEnd.setVisibility(View.GONE);
            viewEnd.setVisibility(View.GONE);
            tvDate30.setVisibility(View.INVISIBLE);
        } else if (currentMonthDay == 30) {
            llEnd.setVisibility(View.GONE);
            viewEnd.setVisibility(View.GONE);
        }
    }

    private void sign() {
        OkHttpUtils.post()
                .url(Internet.SIGN)
                .addParams("user_id", userId)
                .addParams("sign_city", city)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HomeFragment.java:603)" + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String msg = json.getString("msg");
                            if (msg.contains("成功")) {
//                                            llSignIn.setVisibility(View.GONE);
//                                            llSignIns.setVisibility(View.VISIBLE);
                                initView2(split[2]);
//                setTvBg(tvDate1);
                            }
                            Toast.makeText(SignActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sign;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_sign:
                sign();
                break;
        }
    }

    private void setTvBg(TextView currentTextView) {
        currentTextView.setBackgroundResource(R.drawable.tv_date_bg);
        currentTextView.setTextColor(Color.parseColor("#ffffff"));
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
