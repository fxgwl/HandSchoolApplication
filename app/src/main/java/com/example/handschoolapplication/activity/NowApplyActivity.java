package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.demo.PayResult;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class NowApplyActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_schoolname)
    TextView tvSchoolname;
    @BindView(R.id.tv_coursename)
    TextView tvCoursename;
    @BindView(R.id.tv_coursetime)
    TextView tvCoursetime;
    @BindView(R.id.tv_personnum)
    TextView tvPersonnum;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_costtime)
    TextView tvCosttime;
    @BindView(R.id.tv_coursenum)
    TextView tvCoursenum;
    @BindView(R.id.tv_coursemoney)
    TextView tvCoursemoney;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_moneydi)
    TextView tvMoneydi;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.tv_nowapply_config)
    TextView tvNowapplyConfig;
    @BindView(R.id.iv_golddiscount)
    ImageView ivGolddiscount;
    @BindView(R.id.tv_user_discount)
    TextView tvUserDiscount;//取消使用优惠券
    private Intent intent;
    private String school_name;
    private String course_name;
    private String course_time;
    private String enrol_num;
    private String course_capacity;
    private String age_range;
    private String course_teacher;
    private String original_price;
    private String preferential_price;
    private String class_money;
    private String course_id;
    private String order_id;
    private String school_id;
    private String m;//课程费用
    private double money;
    private String user_id;
    int tag = 1;
    private double g;
    private double discount = 0;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            Log.e("aaa",
                    "(NowApplyActivity.java:109)  resultStatus====" + resultStatus);
            Log.e("aaa",
                    "(NowApplyActivity.java:111)  resultInfo====" + resultInfo);
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(NowApplyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(NowApplyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        intent = getIntent();
        user_id = (String) SPUtils.get(this, "userId", "");
        school_name = intent.getStringExtra("school_name");
        school_id = intent.getStringExtra("school_id");
        course_name = intent.getStringExtra("course_name");
//        course_time = intent.getStringExtra("course_time");
        enrol_num = intent.getStringExtra("enrol_num");
        course_capacity = intent.getStringExtra("course_capacity");
        age_range = intent.getStringExtra("age_range");
        course_teacher = intent.getStringExtra("course_teacher");
        original_price = intent.getStringExtra("original_price");
        preferential_price = intent.getStringExtra("preferential_price");
        class_money = intent.getStringExtra("class_money");
        course_id = intent.getStringExtra("course_id");
        order_id = intent.getStringExtra("order_id");
        tvTitle.setText("立即报名");
        tvSchoolname.setText(school_name);
        tvCoursename.setText(course_name);
        tvPersonnum.setText(enrol_num + "【已报名】 /" + course_capacity + "人【总人数】");
        tvAge.setText(age_range);
        tvTeacher.setText(course_teacher);
        tvCosttime.setText(class_money);
        String s = class_money.split("/")[1].split("节")[0];
        m = class_money.split("元")[0];
        money = Double.parseDouble(m);//转换的钱数
        tvCoursenum.setText(s + "课");
        tvCoursemoney.setText("共" + s + "节课       小计: ¥" + m);
        tvAllmoney.setText("¥" + m);
        //金币
        initGold();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_now_apply;
    }

    @OnClick({R.id.rl_back, R.id.tv_nowapply_config, R.id.tv_discount, R.id.iv_golddiscount, R.id.tv_user_discount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_nowapply_config:
                String allMoney = tvAllmoney.getText().toString().trim().split("¥")[1];
                initPayPop(allMoney);
                break;
            case R.id.tv_discount:
                Intent intent1 = new Intent(this, MyDiscountActivity.class);
                intent1.putExtra("school_id", school_id);
                intent1.putExtra("money", m);
                startActivityForResult(intent1, 1);
                break;
            case R.id.tv_user_discount://不使用优惠券
                money+=discount;
                tvAllmoney.setText("¥" + (money));
                discount = 0;
                tvDiscount.setText("¥" + "0.00");
                tvUserDiscount.setVisibility(View.GONE);
                break;
            case R.id.iv_golddiscount:
                if (tag == 1) {
                    ivGolddiscount.setImageResource(R.drawable.hongquan);
                    tag = 0;
                    money = money - g;
                    tvAllmoney.setText("¥" + money);
                } else {
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    tag = 1;
                    money = money + g;
                    tvAllmoney.setText("¥" + money);
                }
                break;
        }
    }

    //
    private void initGold() {
        OkHttpUtils.post()
                .url(Internet.USERINFO)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {

                    private SchoolInfoBean.DataBean dataBean;

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:148)" + response);
                        if (response.contains("没有信息")) {
                            ivGolddiscount.setVisibility(View.INVISIBLE);
                            tvMoneydi.setText("没有金币可以抵扣");
                        } else {
                            Gson gson = new Gson();
                            try {
                                dataBean = gson.fromJson(response, SchoolInfoBean.class).getData();
                                g = Double.parseDouble(dataBean.getUser_gold());
                            } catch (Exception e) {

                            }
                            tvMoneydi.setText(dataBean.getUser_gold() + "金币            -¥" + dataBean.getUser_gold());
                        }

                    }
                });
    }

    //初始化支付
    private void initPayPop(final String allMoney) {
        View view = View.inflate(NowApplyActivity.this, R.layout.nowapply_pay_pop, null);
        TextView close = (TextView) view.findViewById(R.id.pop_close);
        TextView pop_pay_config = (TextView) view.findViewById(R.id.pop_pay_config);
        TextView tvMoney = (TextView) view.findViewById(R.id.tv_money);
        tvMoney.setText(allMoney + "元");
        LinearLayout pop_pay_ali = (LinearLayout) view.findViewById(R.id.pop_pay_ali);
        LinearLayout pop_pay_weixin = (LinearLayout) view.findViewById(R.id.pop_pay_weixin);
        final ImageView pop_state_ali = (ImageView) view.findViewById(R.id.pop_state_ali);
        final ImageView pop_state_wx = (ImageView) view.findViewById(R.id.pop_state_wx);
        final MyPopupWindow payPopWindow = new MyPopupWindow(this, view);
        payPopWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
        pop_pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_state_ali.setImageResource(R.drawable.hongquan);
                pop_state_wx.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_state_wx.setImageResource(R.drawable.hongquan);
                pop_state_ali.setImageResource(R.drawable.baiquan);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payPopWindow.dismiss();
            }
        });
        pop_pay_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliPay();
                payPopWindow.dismiss();
            }
        });
        payPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void aliPay() {
//        String money = etRecharge.getText().toString().trim();
//        if (TextUtils.isEmpty(money)) {
//            Toast.makeText(this, "还未输入提现金额", Toast.LENGTH_SHORT).show();
//            return;
//        }
        HashMap<String, String> params = new HashMap<>();
        params.put("order_money", "0.01");
        params.put("is_gold", "0");
//        params.put("order_id",order_id);
        params.put("order_id","1510647516852a1510647526728a1510647536703a1510647545388");
        params.put("pay_num","15773263911");
        OkHttpUtils.post()
                .url(Internet.ALIPAY)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:309)" + e.getMessage());
                        Toast.makeText(NowApplyActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:316)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("orderInfo");
                            final String orderInfo = data;
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(NowApplyActivity.this);
                                    Map<String, String> stringStringMap = alipay.payV2(orderInfo, true);
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = stringStringMap;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 订单信息


                    }
                });

    }

    private void wechatPay() {

        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", order_id);
        params.put("pay_num", "0.01");
//        params.put("is_coupons", mon + "");
        params.put("is_gold","0");
        params.put("order_money","0.01");

        OkHttpUtils.post()
                .url(Internet.WECHATPAY)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(RechargeActivity.java:125)" + e.getMessage());
//                        Toast.makeText(RechargeActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(RechargeActivity.java:131)" + response);
//{"appid":"wx36ea87e510cf7c88","noncestr":"253boi8GHBMxRuft","package":"Sign=WXPay",
// "partnerid":"1485613042","prepayid":"wx2017082513344988a91b94290062893355",
// "sign":"E2D76ED2B2B46ED0D560D30F262C25B3","timestamp":"1503639289"}
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            String appid = data.getString("appid");
                            String noncestr = data.getString("noncestr");
                            String signpay = data.getString("package");
                            String partnerid = data.getString("partnerid");
                            String prepayid = data.getString("prepayid");
                            String sign = data.getString("sign");
                            String timestamp = data.getString("timestamp");
                            PayReq request = new PayReq();
                            request.appId = appid;
                            request.partnerId = partnerid;
                            request.prepayId = prepayid;
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = noncestr;
                            request.timeStamp = timestamp;
                            request.sign = sign;
//                            msgApi.sendReq(request);


//                            EventBus.getDefault().post(this);
//                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11) {
            if (discount != 0) {
                Toast.makeText(this, "discount === " + discount, Toast.LENGTH_SHORT).show();
                money += discount;
            }
            discount = data.getDoubleExtra("discount", 0);
            tvDiscount.setText("-¥" + discount);
            money = money - discount;
            tvAllmoney.setText("¥" + (money));
            tvUserDiscount.setVisibility(View.VISIBLE);
        }
    }
}
