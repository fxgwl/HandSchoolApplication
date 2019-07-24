package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.CarListBean;
import com.example.handschoolapplication.bean.SchoolInfoBean;
import com.example.handschoolapplication.demo.PayResult;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.MyUtiles;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.MyPopupWindow;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.bumptech.glide.Glide.with;

public class ApplyDetailPayActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_apply_detail)
    ListView lvApplyDetail;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.tv_nowapply_config)
    TextView tvNowapplyConfig;
    @BindView(R.id.iv_golddiscount)
    ImageView ivGolddiscount;
    @BindView(R.id.tv_moneydi)
    TextView tvMoneydi;
    @BindView(R.id.tv_gold_discount_money)
    TextView tvMoneyNum;
    @BindView(R.id.et_input_gold_num)
    EditText etInputGoldNum;
    @BindView(R.id.ll_gold)
    LinearLayout llGolds;

    private IWXAPI msgApi;
    private String payWay = "0";
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
                    "(ApplyDetailPayActivity.java:109)  resultStatus====" + resultStatus);
            Log.e("aaa",
                    "(ApplyDetailPayActivity.java:111)  resultInfo====" + resultInfo);
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(ApplyDetailPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ApplyDetailPayActivity.this, MyOrderActivity.class).putExtra("flag", "all"));
                finish();
//
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(ApplyDetailPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ApplyDetailPayActivity.this, MyOrderActivity.class).putExtra("flag", "pay"));
                finish();
            }
        }
    };
    private Intent intent;
    private String user_id;
    private String school_name;
    private String school_id;
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
    private ArrayList<CarListBean.DataBean> orderBean;

    private MyApplyDetailAdapter mAdapter;

    private int position;
    private double discount;

    private String[] studentsName;//学生姓名集合
    private String[] orderIds;//学生姓名集合
    private int couponsIds[];
    private double[] unitPrice;
    private double totalPrice;
    private int tag = 1;
    private double g = 0.0;
    private double rate;
    private double n;
    private String disMoney;
    private double d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp("wx433e119bb99f2075");

        intent = getIntent();
        user_id = (String) SPUtils.get(this, "userId", "");
        orderBean = (ArrayList<CarListBean.DataBean>) getIntent().getSerializableExtra("orderBean");
        studentsName = new String[orderBean.size()];
        couponsIds = new int[orderBean.size()];
        orderIds = new String[orderBean.size()];
        unitPrice = new double[orderBean.size()];
        calculate();
        initView();

        String isGolds = "0";
        for (int i = 0; i < orderBean.size(); i++) {
            String is_golds = orderBean.get(i).getCourseInfo().getIs_golds();
            if ("1".equals(is_golds)) {
                isGolds = "1";
                break;
            }
        }

        if (isGolds.equals("1")) {
            llGolds.setVisibility(View.VISIBLE);
            initGold();
        } else {
            llGolds.setVisibility(View.GONE);
        }


        etInputGoldNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (tag == 0) {
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    totalPrice = totalPrice + g;
                    tag = 1;
                }else{
                    totalPrice=totalPrice+d;
                }
                disMoney = etInputGoldNum.getText().toString().trim();
                if (TextUtils.isEmpty(disMoney)) {
                    disMoney = "0";
                }
                double v = Double.parseDouble(disMoney);
                if (v > n) {
                    Toast.makeText(ApplyDetailPayActivity.this, "您的金币数量不足", Toast.LENGTH_SHORT).show();
                    d=0;
                    etInputGoldNum.setText("");
                } else if (v == n) {
                    d = (v / rate);

                    if (totalPrice - d <= 0) {
                        Toast.makeText(ApplyDetailPayActivity.this, "抵扣超出金额上限", Toast.LENGTH_SHORT).show();
                        etInputGoldNum.setText("");
                    } else {
                        ivGolddiscount.setImageResource(R.drawable.hongquan);
                        tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(d));
                        totalPrice = totalPrice - d;
                        tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(totalPrice));
                        d=0;
                        tag = 0;
                    }
                    /*if (totalPrice-d>0){
                        ivGolddiscount.setImageResource(R.drawable.hongquan);
                        tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(d));
                        totalPrice = totalPrice - d;
                        tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(totalPrice));
                        tag = 0;
                    }else {
                        Toast.makeText(ApplyDetailPayActivity.this, "抵扣超出金额上限", Toast.LENGTH_SHORT).show();
                        etInputGoldNum.setText("");
                    }*/
                    
                } else {
                    d = (v / rate);
                    if (totalPrice - d <= 0) {
                        Toast.makeText(ApplyDetailPayActivity.this, "抵扣超出金额上限", Toast.LENGTH_SHORT).show();
                        etInputGoldNum.setText("");
                    } else {
                        totalPrice = totalPrice - d;
                        tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(d));
                        tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(totalPrice));
                    }
                    /*if (totalPrice-d>0){
                        tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(d));
                        tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(totalPrice - d));
                    }else {
                        Toast.makeText(ApplyDetailPayActivity.this, "抵扣超出金额上限", Toast.LENGTH_SHORT).show();
                        etInputGoldNum.setText("");
                    }*/

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_detail_pay;
    }

    private void initView() {
        tvTitle.setText("立即报名");
        mAdapter = new MyApplyDetailAdapter();
        lvApplyDetail.setAdapter(mAdapter);
    }

    private void calculate() {
        totalPrice = 0.00;
        for (int i = 0; i < orderBean.size(); i++) {
            String class_money = orderBean.get(i).getClass_money();
            double money = 0;
            if (class_money.contains("元")) {
                money = Double.parseDouble(class_money.split("元")[0]);
            } else {
                money = Double.parseDouble(class_money.split("/")[0]);
            }

            int courseNum = Integer.parseInt(orderBean.get(i).getCourse_num());
            totalPrice = totalPrice + (money * courseNum);
            orderIds[i] = orderBean.get(i).getOrder_id();
            couponsIds[i] = 0;
            unitPrice[i] = (money * courseNum);
        }
        tvAllmoney.setText("¥" + totalPrice);
    }

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
                            //tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(g));
                        }

                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_nowapply_config, R.id.iv_golddiscount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_nowapply_config:
                Log.e("aaa",
                        "(ApplyDetailPayActivity.java:272)----------" + tvAllmoney.getText().toString().trim());
                String totalMoney = tvAllmoney.getText().toString().trim().split("¥")[1];
                initPayPop(totalMoney);
                break;
            case R.id.iv_golddiscount:
                if (totalPrice-g<=0){
                    Toast.makeText(this, "不能全部抵扣", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tag == 1) {
                    ivGolddiscount.setImageResource(R.drawable.hongquan);
                    tag = 0;
                    totalPrice = totalPrice - g+d;
                    tvAllmoney.setText("¥" + totalPrice);
                    tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(g));
                } else {
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    tag = 1;
                    totalPrice = totalPrice + g-d;
                    tvAllmoney.setText("¥" + new DecimalFormat("0.00").format(totalPrice));
                    tvMoneyNum.setText("¥0.00");
                }
                /*else if (tag == 0) {
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    tag = 1;
                    totalPrice = totalPrice + g;
                    tvAllmoney.setText("¥" + totalPrice);
                    tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(0));
                } else {
                    ivGolddiscount.setImageResource(R.drawable.hongquan);
                    tag = 0;
                    totalPrice = totalPrice - g;
                    tvAllmoney.setText("¥" + totalPrice);
                    tvMoneyNum.setText("¥" + new DecimalFormat("0.00").format(g));
                }*/
                break;
        }
    }


    //初始化支付
    private void initPayPop(final String allMoney) {
        View view = View.inflate(ApplyDetailPayActivity.this, R.layout.nowapply_pay_pop, null);
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
        payWay ="0";
        pop_pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payWay = "0";
                pop_state_ali.setImageResource(R.drawable.hongquan);
                pop_state_wx.setImageResource(R.drawable.baiquan);
                pop_state_cash.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payWay = "1";
                pop_state_wx.setImageResource(R.drawable.hongquan);
                pop_state_ali.setImageResource(R.drawable.baiquan);
                pop_state_cash.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payWay = "2";
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
                switch (payWay){
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

    private void aliPay() {
        String trim = etInputGoldNum.getText().toString().trim();
        String totalMoney = tvAllmoney.getText().toString().trim().split("¥")[1];
        if (TextUtils.isEmpty(trim)) {
            trim = "0";
        }
        String orderIdItem = "";
        String studentItem = "";
        String couponsIdItem = "";
        String unitPriceItem = "";
        String payNumItem = "";
        for (int i = 0; i < orderBean.size(); i++) {
            if (TextUtils.isEmpty(studentsName[i])) {
                Toast.makeText(this, "请确认所有订单都填写是上课学生姓名！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (i == 0) {
                orderIdItem = orderIdItem + orderIds[i];
                studentItem = studentItem + studentsName[i];
                couponsIdItem = couponsIdItem + couponsIds[i];
                unitPriceItem = unitPriceItem + (unitPrice[i] + "");
                payNumItem = payNumItem + "1";
            } else {
                orderIdItem = orderIdItem + "a" + orderIds[i];
                studentItem = studentItem + "," + studentsName[i];
                couponsIdItem = couponsIdItem + "," + couponsIds[i];
                unitPriceItem = unitPriceItem + "," + (unitPrice[i] + "");
                payNumItem = payNumItem + ",1";
            }
        }

        Log.e("aaa",
                "(ApplyDetailPayActivity.java:307)订单的id-----" + orderIdItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:309)上课学生的姓名-----" + studentItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:311)优惠券的id-------" + couponsIdItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:318)每个列表的价格-------" + unitPriceItem);

        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderIdItem);
        params.put("pay_num", payNumItem);
        params.put("is_coupons", couponsIdItem);
        params.put("is_gold", trim + "");
        params.put("student_name", studentItem);
        params.put("order_money", unitPriceItem);
        params.put("totle_pay", totalMoney);

        OkHttpUtils.post()
                .url(Internet.ALIPAY)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ApplyDetailPayActivity.java:309)" + e.getMessage());
                        Toast.makeText(ApplyDetailPayActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailPayActivity.java:316)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("orderInfo");
                            final String orderInfo = data;
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(ApplyDetailPayActivity.this);
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
        String orderIdItem = "";
        String studentItem = "";
        String couponsIdItem = "";
        String unitPriceItem = "";
        String payNumItem = "";
        for (int i = 0; i < orderBean.size(); i++) {
            if (TextUtils.isEmpty(studentsName[i])) {
                Toast.makeText(this, "请确认所有订单都填写是上课学生姓名！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (i == 0) {
                orderIdItem = orderIdItem + orderIds[i];
                studentItem = studentItem + studentsName[i];
                couponsIdItem = couponsIdItem + couponsIds[i];
                unitPriceItem = unitPriceItem + (unitPrice[i] + "");
                payNumItem = payNumItem + "1";
            } else {
                orderIdItem = orderIdItem + "a" + orderIds[i];
                studentItem = studentItem + "," + studentsName[i];
                couponsIdItem = couponsIdItem + "," + couponsIds[i];
                unitPriceItem = unitPriceItem + "," + (unitPrice[i] + "");
                payNumItem = payNumItem + ",1";
            }
        }

        Log.e("aaa",
                "(ApplyDetailPayActivity.java:307)订单的id-----" + orderIdItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:309)上课学生的姓名-----" + studentItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:311)优惠券的id-------" + couponsIdItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:318)每个列表的价格-------" + unitPriceItem);

        String ipAddress = MyUtiles.getIPAddress(this);

        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderIdItem);
        params.put("pay_num", payNumItem);
        params.put("is_coupons", couponsIdItem);
        params.put("is_gold", trim + "");
        params.put("student_name", studentItem);
        params.put("order_money", unitPriceItem);
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
                                "(ApplyDetailPayActivity.java:374)" + e.getMessage());
//                        Toast.makeText(RechargeActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ApplyDetailPayActivity.java:381)" + response);
//{"appid":"wx36ea87e510cf7c88","noncestr":"253boi8GHBMxRuft","package":"Sign=WXPay",
// "partnerid":"1485613042","prepayid":"wx2017082513344988a91b94290062893355",
// "sign":"E2D76ED2B2B46ED0D560D30F262C25B3","timestamp":"1503639289"}
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("aaa",
                                    "(ApplyDetailPayActivity.java:391)jsonObject === " + jsonObject);
                            String appid = jsonObject.getString("appid");
                            String noncestr = jsonObject.getString("noncestr");
                            String signpay = jsonObject.getString("package");
                            String partnerid = jsonObject.getString("partnerid");
                            String prepayid = jsonObject.getString("prepayid");
                            String sign = jsonObject.getString("sign");
                            String timestamp = jsonObject.getString("timestamp");

                            Log.e("aaa",
                                    "(ApplyDetailPayActivity.java:402)prepayid ===" + prepayid);
                            PayReq request = new PayReq();
                            request.appId = appid;
                            request.partnerId = partnerid;
                            request.prepayId = prepayid;
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr = noncestr;
                            request.timeStamp = timestamp;
                            request.sign = sign;


                            msgApi.sendReq(request);


//                            EventBus.getDefault().post(this);
//                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void cashPay() {

        String orderIdItem = "";
        String studentItem = "";
        String couponsIdItem = "";
        String unitPriceItem = "";
        String payNumItem = "";
        for (int i = 0; i < orderBean.size(); i++) {
            if (TextUtils.isEmpty(studentsName[i])) {
                Toast.makeText(this, "请确认所有订单都填写是上课学生姓名！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (i == 0) {
                orderIdItem = orderIdItem + orderIds[i];
                studentItem = studentItem + studentsName[i];
                couponsIdItem = couponsIdItem + couponsIds[i];
                unitPriceItem = unitPriceItem + (unitPrice[i] + "");
                payNumItem = payNumItem + "1";
            } else {
                orderIdItem = orderIdItem + "a" + orderIds[i];
                studentItem = studentItem + "," + studentsName[i];
                couponsIdItem = couponsIdItem + "," + couponsIds[i];
                unitPriceItem = unitPriceItem + "," + (unitPrice[i] + "");
                payNumItem = payNumItem + ",1";
            }
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderIdItem);
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
                        Toast.makeText(ApplyDetailPayActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", "(NowApplyActivity.java:729)<---->" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(ApplyDetailPayActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("result");
                                if (code == 0) {
                                    startActivity(new Intent(ApplyDetailPayActivity.this, MyOrderActivity.class).putExtra("flag", "all"));
                                    finish();
                                } else {
                                    String msg = jsonObject.getString("msg");
                                    Toast.makeText(ApplyDetailPayActivity.this, msg, Toast.LENGTH_SHORT).show();
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
        if (requestCode == 1 && resultCode == 22) {
            if (discount != 0) {
                totalPrice += discount;
            }

            String student = data.getStringExtra("student");
            int coupons_id = data.getIntExtra("coupons_id", 0);
            Log.e("aaa",
                    "(ApplyDetailPayActivity.java:588)<--coupons_id-->"+coupons_id);
            discount = data.getDoubleExtra("discount", 0);
            Log.e("aaa",
                    "(ApplyDetailPayActivity.java:593)<--discount-->"+discount);
            double hasDiscount = data.getDoubleExtra("hasDiscount", 0);
            Log.e("aaa",
                    "(ApplyDetailPayActivity.java:596)<--hasDiscount-->"+hasDiscount);

            studentsName[position] = student;
            couponsIds[position] = coupons_id;
            unitPrice[position] = hasDiscount;
            mAdapter.setPositon(position);
            mAdapter.notifyDataSetChanged();

            totalPrice = 0;
            for (int i = 0; i < unitPrice.length; i++) {
                totalPrice += unitPrice[i];
            }

            if (tag==0){
                double nowManey = Double.parseDouble(tvMoneyNum.getText().toString().trim().split("¥")[1]);
                if (nowManey > 0) {
                    totalPrice -= nowManey;
                }
            }else {

            }
            tvAllmoney.setText("¥" + (totalPrice));
        }
    }

    class MyApplyDetailAdapter extends BaseAdapter {
        int size = 0;
        int selectPosition = 0;


        @Override
        public int getCount() {
            if (null != orderBean)
                size = orderBean.size();
            return size;
        }

        private void setPositon(int positon) {
            this.selectPosition = positon;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ApplyDetailPayActivity.this, R.layout.item_apply_detail_pay, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final CarListBean.DataBean dataBean = orderBean.get(position);

            with(ApplyDetailPayActivity.this).load(Internet.BASE_URL + dataBean.getSchool_logo()).centerCrop().into(holder.ivSchoolIcon);
            holder.tvSchoolName.setText(dataBean.getSchool_name());
//            String photo = "";
//            if (dataBean.getClass_photo().contains(",")) {
//                photo = dataBean.getClass_photo().split(",")[0];
//            } else {
//                photo = dataBean.getClass_photo();
//            }
            String picture_one = dataBean.getCourseInfo().getPicture_one();
            with(ApplyDetailPayActivity.this).load(Internet.BASE_URL + picture_one).centerCrop().into(holder.ivCourseIcon);
            holder.tvCourse.setText(dataBean.getClass_name());
            String string = dataBean.getClass_money().split("/")[1].split("节")[0];
            String money = dataBean.getClass_money().split("元")[0];
            holder.tvCourseNum.setText("x" + dataBean.getCourse_num());
            double uCourseNum = Double.parseDouble(string);
            int tCourseNum = Integer.parseInt(dataBean.getCourse_num());
            double mon = Double.parseDouble(money);
            double tCourse = uCourseNum * tCourseNum;
            holder.tvCourseNumAll.setText((int)tCourse+ "");
            double anInt = mon * tCourseNum;
            holder.tvAllMoney.setText("¥" + unitPrice[position] + "");

            holder.llCourseDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(ApplyDetailPayActivity.this, ApplyDetailsActivity.class)
                            .putExtra("order", orderBean.get(position))
                            .putExtra("student", studentsName[position]), 1);
                    ApplyDetailPayActivity.this.position = position;
                }
            });

            holder.llSchool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(ApplyDetailPayActivity.this, "跳转到学堂主页", Toast.LENGTH_SHORT).show();

                    Intent intent2 = new Intent(ApplyDetailPayActivity.this, ClassActivity.class);
                    intent2.putExtra("school_id", dataBean.getSchool_id());
                    ApplyDetailPayActivity.this.startActivity(intent2);
                }
            });

            holder.llCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(ApplyDetailPayActivity.this, "跳转到课程主页", Toast.LENGTH_SHORT).show();

                    String course_id = dataBean.getCourse_id();
                    String schooluid = dataBean.getUser_id();
                    Intent intent = new Intent(ApplyDetailPayActivity.this, CourseHomePagerActivity.class);
                    intent.putExtra("course_id",course_id);
                    intent.putExtra("schooluid",schooluid);
                    startActivity(intent);
                }
            });

            if (position == selectPosition) {
                double v = anInt - discount;
                holder.tvAllMoney.setText("¥" + v + "");
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_school_icon)
            ImageView ivSchoolIcon;
            @BindView(R.id.tv_school_name)
            TextView tvSchoolName;
            @BindView(R.id.iv_course_icon)
            ImageView ivCourseIcon;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.ll_course_detail)
            LinearLayout llCourseDetail;
            @BindView(R.id.tv_course_num)
            TextView tvCourseNum;
            @BindView(R.id.tv_course_num_all)
            TextView tvCourseNumAll;
            @BindView(R.id.tv_all_money)
            TextView tvAllMoney;
            @BindView(R.id.ll_school)
            LinearLayout llSchool;
            @BindView(R.id.ll_course)
            LinearLayout llCourse;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
