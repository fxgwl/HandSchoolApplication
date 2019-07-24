package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.NowPayCourseTimeAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CourseTimeBean;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.demo.PayResult;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.MyPopupWindow;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.lv_coursetime)
    MyListView tvCoursetime;
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
    @BindView(R.id.tv_student_name)
    TextView tvStudentName;
    @BindView(R.id.tv_student_sex)
    TextView tvStudentSex;
    @BindView(R.id.iv_golddiscount)
    ImageView ivGolddiscount;
    @BindView(R.id.tv_user_discount)
    TextView tvUserDiscount;//取消使用优惠券
    @BindView(R.id.et_input_gold_num)
    EditText etInputGoldNum;
    @BindView(R.id.tv_gold_discount_money)
    TextView tvGoldMoney;
    @BindView(R.id.ll_is_golds)
    LinearLayout llIsGold;
    int tag = 1;
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
    private String user_phone;
    private double g;
    private double discount = 0;
    private String PayWay = "0"; //0支付宝1微信2线下支付
    private IWXAPI msgApi;

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
                startActivity(new Intent(NowApplyActivity.this, MyOrderActivity.class).putExtra("flag", "all"));
                finish();
//
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(NowApplyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NowApplyActivity.this, MyOrderActivity.class).putExtra("flag", "pay"));
                finish();
            }
        }
    };
    private int coupons_id;
    private String course_num;
    private double n;
    private double d = 0.0;
    private double rate;
    private String disMoney;
    private int goldNum;
    private String is_golds;
    private String string;
    private String student_name;
    private String student_sex;
    private String is_coup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp("wx433e119bb99f2075");
//        msgApi.registerApp("wxaa2eadfae2ee0a90");
        intent = getIntent();
        user_id = (String) SPUtils.get(this, "userId", "");
        user_phone = (String) SPUtils.get(this, "user_phone", "");
        school_name = intent.getStringExtra("school_name");
        school_id = intent.getStringExtra("school_id");
        course_name = intent.getStringExtra("course_name");
        course_time = intent.getStringExtra("course_time");
        enrol_num = intent.getStringExtra("enrol_num");
        course_capacity = intent.getStringExtra("course_capacity");
        age_range = intent.getStringExtra("age_range");
        course_teacher = intent.getStringExtra("course_teacher");
        original_price = intent.getStringExtra("original_price");
        preferential_price = intent.getStringExtra("preferential_price");
        class_money = intent.getStringExtra("class_money");
        course_id = intent.getStringExtra("course_id");
        order_id = intent.getStringExtra("order_id");
        course_num = intent.getStringExtra("course_num");
        is_golds = intent.getStringExtra("is_golds");
        is_coup = intent.getStringExtra("is_coup");
        student_sex = intent.getStringExtra("student_sex") == null ? "" : intent.getStringExtra("student_sex");
        student_name = intent.getStringExtra("student_name") == null ? "" : intent.getStringExtra("student_name");
        tvTitle.setText("立即报名");
        tvSchoolname.setText(school_name);
        tvCoursename.setText(course_name);
//        tvCoursetime.setText(course_time);
        tvPersonnum.setText(enrol_num + "【已报名】/" + course_capacity + "人【总人数】");
        tvStudentName.setText(student_name);
        tvStudentSex.setText(student_sex);
        tvAge.setText(age_range);
        tvTeacher.setText(course_teacher);
        tvCosttime.setText(class_money);
        String s = "";

        try {

            String s1 = class_money.split("/")[1];//XXX节
            String s2 = class_money.split("/")[0];//XXX元
            if (s1.equals("节")) {
                s = "1";
            } else {
                if (s1.contains("节")) {
                    s = s1.split("节")[0] == null ? "1" : s1.split("节")[0];
                } else {
                    s = s1;
                }
            }
            if (s2.contains("元")) {
                m = s2.split("元")[0];
            } else {
                m = s2;
            }

        } catch (Exception e) {
            Log.e("aaa",
                    "(NowApplyActivity.java:194)<--出现异常数据-->" + e.getMessage());
            s = "0";
            m = "0.00";
            Toast.makeText(this, "该课程费用异常", Toast.LENGTH_SHORT).show();
            finish();
        }
//        m = class_money.split("元")[0];
        int i = Integer.parseInt(course_num);
        int j = Integer.parseInt(s);
        double l = Double.parseDouble(m);
        money = i * l;
        tvCoursenum.setText((i * j) + "节");
        tvCoursemoney.setText("共" + (i * j) + "节课       小计: ¥" + new DecimalFormat("0.00").format(money));
        tvAllmoney.setText("¥" + (new DecimalFormat("0.00").format(money)));
        if ("1".equals(is_golds)) {
            llIsGold.setVisibility(View.VISIBLE);
            initGold();
        } else {
            llIsGold.setVisibility(View.GONE);
            //金币
        }

        etInputGoldNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (tag == 0) {
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    money = money + g;
                    tag = 1;
                }else{
                    money=money+d;
                }
                disMoney = etInputGoldNum.getText().toString().trim();
                if (TextUtils.isEmpty(disMoney)) {
                    disMoney = "0";
                }
                double v = Double.parseDouble(disMoney);
                if (v > n) {
                    Toast.makeText(NowApplyActivity.this, "您的金币数量不足", Toast.LENGTH_SHORT).show();
                    d=0;
                    etInputGoldNum.setText("");
                } else if (v == n) {
                    d = (v / rate);
                    if (money - d <= 0) {
                        Toast.makeText(NowApplyActivity.this, "抵扣超出金额上限", Toast.LENGTH_SHORT).show();
                        etInputGoldNum.setText("");
                    } else {
                        ivGolddiscount.setImageResource(R.drawable.hongquan);
                        tvGoldMoney.setText("¥" + new DecimalFormat("0.00").format(d));
                        money = money - d;
                        tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(money));
                        d=0;
                        tag = 0;
                    }
                } else {
                    d = (v / rate);
                    if (money - d <= 0) {
                        Toast.makeText(NowApplyActivity.this, "抵扣超出金额上限", Toast.LENGTH_SHORT).show();
                        etInputGoldNum.setText("");
                    } else {
                        money = money - d;
                        tvGoldMoney.setText("¥" + new DecimalFormat("0.00").format(d));
                        tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(money));
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        initClassTime();

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_now_apply;
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
                                rate = Double.parseDouble(dataBean.getRatios().getGoldnum());
                                n = Double.parseDouble(dataBean.getUser_gold());
                                g = n / rate;
                            } catch (Exception e) {

                            }
                            tvMoneydi.setText(dataBean.getUser_gold() + "金币");
//                            tvGoldMoney.setText("¥" + new DecimalFormat("0.00").format(g));
                        }

                    }
                });
    }

    //课程时间的选择
    private void initClassTime() {
        OkHttpUtils.post()
                .url(Internet.COURSETIME)
                .addParams("course_id", course_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:303)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:310)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(NowApplyActivity.this, "暂无课程时间", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            CourseTimeBean courseTime = gson.fromJson(response, CourseTimeBean.class);
                            List<CourseTimeBean.DataBean> data = courseTime.getData();
                            NowPayCourseTimeAdapter myCourseTimeAdapter = new NowPayCourseTimeAdapter(data, NowApplyActivity.this);
                            tvCoursetime.setAdapter(myCourseTimeAdapter);
                        }
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_nowapply_config, R.id.tv_discount, R.id.iv_golddiscount, R.id.tv_user_discount,
            R.id.ll_student_name, R.id.ll_student_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_nowapply_config:
                String allMoney = tvAllmoney.getText().toString().trim().split("¥")[1];
                user_phone = (String) SPUtils.get(this, "user_phone", "");
                if (TextUtils.isEmpty(user_phone)) {
                    showUnLoginDialog();
                    return;
                }
                String studentName = tvStudentName.getText().toString().trim();
                if (TextUtils.isEmpty(studentName)) {
                    Toast.makeText(this, "请填写上课学生姓名！", Toast.LENGTH_SHORT).show();
                    return;
                }
                initPayPop(allMoney);
                break;
            case R.id.tv_discount:
                user_phone = (String) SPUtils.get(this, "user_phone", "");
                if (TextUtils.isEmpty(user_phone)) {
                    showUnLoginDialog();
                    return;
                }
                if (is_coup != null && !is_coup.equals("0")) {
                    Intent intent1 = new Intent(this, MyDiscountActivity.class);
                    intent1.putExtra("school_id", school_id);
                    intent1.putExtra("money", (money) + "");
                    startActivityForResult(intent1, 1);
                } else {
                    Toast.makeText(this, "该课程不支持使用优惠券", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_user_discount://不使用优惠券
                money += discount;
                tvAllmoney.setText("¥" + (new DecimalFormat("0.00").format(money)));
                discount = 0;
                tvDiscount.setText("¥" + "0.00");
                tvUserDiscount.setVisibility(View.GONE);
                break;
            case R.id.iv_golddiscount:
                if (money - g <= 0) {
                    Toast.makeText(this, "不能全部选择", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tag == 1) {
                    ivGolddiscount.setImageResource(R.drawable.hongquan);
                    tag = 0;
                    money = money - g+d;
                    tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(money));
                    tvGoldMoney.setText("¥" + new DecimalFormat("0.00").format(g));
                } else {
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    tag = 1;
                    money = money + g-d;
                    tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(money));
                    tvGoldMoney.setText("¥0.00");
                }
                break;
            case R.id.ll_student_name:
                startActivityForResult(new Intent(NowApplyActivity.this, SetStudentNameActivity.class)
                        .putExtra("order_id", order_id), 2);
                break;
            case R.id.ll_student_sex:
                break;
        }
    }

    private void showUnLoginDialog() {
        final SelfDialog selfDialog = new SelfDialog(NowApplyActivity.this);

        selfDialog.setMessage("是否绑定手机号?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                startActivity(new Intent(NowApplyActivity.this, RegisterPersonActivity.class)
                        .putExtra("flag", "true")
                        .putExtra("type", "0"));
                finish();
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
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
        LinearLayout pop_pay_cash = (LinearLayout) view.findViewById(R.id.pop_pay_cash);
        final ImageView pop_state_ali = (ImageView) view.findViewById(R.id.pop_state_ali);
        final ImageView pop_state_wx = (ImageView) view.findViewById(R.id.pop_state_wx);
        final ImageView pop_state_cash = (ImageView) view.findViewById(R.id.pop_state_cash);
        final MyPopupWindow payPopWindow = new MyPopupWindow(this, view);
        payPopWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
        PayWay = "0";
        pop_pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayWay = "0";
                pop_state_ali.setImageResource(R.drawable.hongquan);
                pop_state_wx.setImageResource(R.drawable.baiquan);
                pop_state_cash.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayWay = "1";
                pop_state_wx.setImageResource(R.drawable.hongquan);
                pop_state_ali.setImageResource(R.drawable.baiquan);
                pop_state_cash.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayWay = "2";
                pop_state_cash.setImageResource(R.drawable.hongquan);
                pop_state_ali.setImageResource(R.drawable.baiquan);
                pop_state_wx.setImageResource(R.drawable.baiquan);
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
                switch (PayWay) {
                    case "0":
                        aliPay();
                        break;
                    case "1":
                        wechatPay();
                        break;
                    case "2":
                        cashPay();
                        break;
                }
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

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void aliPay() {
        String trim = etInputGoldNum.getText().toString().trim();
        String totalMoney = tvAllmoney.getText().toString().trim().split("¥")[1];
        if (TextUtils.isEmpty(trim)) {
            trim = "0";
        }
        String studentName = tvStudentName.getText().toString().trim();
        if (TextUtils.isEmpty(studentName)) {
            Toast.makeText(this, "请填写上课学生姓名！", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("aaa",
                "(NowApplyActivity.java:426)<--支付宝付款时传的订单 ID-->" + order_id);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_money", totalMoney);
//        params.put("order_money", "0.01");
        params.put("is_gold", trim + "");
        params.put("order_id", order_id);
//        params.put("order_id","1510647516852a1510647526728a1510647536703a1510647545388");
        params.put("pay_num", "1577");
        params.put("is_coupons", coupons_id + "");
        params.put("student_name", studentName);
        params.put("totle_pay", totalMoney + "");
//        params.put("totle_pay", 0.01 + "");

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
        String trim = etInputGoldNum.getText().toString().trim();
        String totalMoney = tvAllmoney.getText().toString().trim().split("¥")[1];
        if (TextUtils.isEmpty(trim)) {
            trim = "0";
        }
        String studentName = tvStudentName.getText().toString().trim();
        if (TextUtils.isEmpty(studentName)) {
            Toast.makeText(this, "请填写上课学生姓名！", Toast.LENGTH_SHORT).show();
            return;
        }

        String ipAddress = MyUtiles.getIPAddress(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("order_money", totalMoney);
        params.put("is_gold", trim + "");
        params.put("order_id", order_id);
//        params.put("order_id","1510647516852a1510647526728a1510647536703a1510647545388");
        params.put("pay_num", "1577");
        params.put("is_coupons", coupons_id + "");
        params.put("student_name", studentName);
//        params.put("totle_pay", "0.01");
        params.put("totle_pay", totalMoney);
        params.put("clientIp", ipAddress);
        OkHttpUtils.post()
                .url(Internet.WECHATPAY)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:374)" + e.getMessage());
//                        Toast.makeText(RechargeActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(NowApplyActivity.java:381)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("aaa",
                                    "(NowApplyActivity.java:391)jsonObject === " + jsonObject);
                            String appid = jsonObject.getString("appid");
                            String noncestr = jsonObject.getString("noncestr");
                            String signpay = jsonObject.getString("package");
                            String partnerid = jsonObject.getString("partnerid");
                            String prepayid = jsonObject.getString("prepayid");
                            String sign = jsonObject.getString("sign");
                            String timestamp = jsonObject.getString("timestamp");


                            PayReq request = new PayReq();
                            request.appId = appid;
                            request.partnerId = partnerid;
                            request.prepayId = prepayid;
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = noncestr;
                            request.timeStamp = timestamp;
                            request.sign = sign;

                            msgApi.sendReq(request);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void setFullScreenWindowLayout(Window window) {
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        //设置页面全屏显示
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        //设置页面延伸到刘海区显示
        window.setAttributes(lp);
    }

        private void cashPay() {
        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", order_id);
        params.put("order_state", "1");
        params.put("pay_type", "2");
        OkHttpUtils.post()
                .url(Internet.PAY_CASH)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(NowApplyActivity.java:724)<---->" + e.getMessage());
                        Toast.makeText(NowApplyActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", "(NowApplyActivity.java:729)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(NowApplyActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    startActivity(new Intent(NowApplyActivity.this, MyOrderActivity.class).putExtra("flag", "all"));
                                    finish();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(NowApplyActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11) {
            if (discount != 0) {
                money += discount;
            }
            discount = data.getDoubleExtra("discount", 0);
            coupons_id = data.getIntExtra("coupons_id", 0);
            tvDiscount.setText("-¥" + discount);
            money = money - discount;
            String trim = tvGoldMoney.getText().toString().trim();
            double v = 0.0;
            if (trim.contains("¥")) {
                String[] s = trim.split("¥");
                v = Double.parseDouble(s[1]);
            } else {
                v = Double.parseDouble(trim);
            }

            if (v > 0) {
                money -= v;
            }
            tvAllmoney.setText("¥" + (new DecimalFormat("0.00").format(money)));
            tvUserDiscount.setVisibility(View.VISIBLE);
        } else if (requestCode == 2 && resultCode == 22) {
            tvStudentName.setText(data.getStringExtra("student")+"("+data.getStringExtra("sex")+")");
            tvStudentSex.setText(data.getStringExtra("sex"));
        }
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
}
