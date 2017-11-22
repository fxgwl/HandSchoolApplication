package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

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
    @BindView(R.id.tv_money_num)
    TextView tvMoneyNum;

    private IWXAPI msgApi;
    private boolean isWechat;
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
//
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(ApplyDetailPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
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
    private int tag = 3;
    private double g = 0.0;


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
        initGold();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_apply_detail_pay;
    }

    private void initView() {
        tvTitle.setText("立即报名");
        mAdapter =new MyApplyDetailAdapter();
        lvApplyDetail.setAdapter(mAdapter);
    }

    private void calculate() {
        totalPrice = 0.00;
        for (int i = 0; i < orderBean.size(); i++) {
            String class_money = orderBean.get(i).getClass_money();
            int money = Integer.parseInt(class_money.split("元")[0]);
            int courseNum = Integer.parseInt(orderBean.get(i).getCourse_num());
            totalPrice = totalPrice + (money*courseNum);
            orderIds[i] = orderBean.get(i).getOrder_id();
            couponsIds[i] = 0;
            unitPrice[i] = (money*courseNum);
        }
        tvAllmoney.setText("￥"+totalPrice);
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
                                g = Double.parseDouble(dataBean.getUser_gold());
                            } catch (Exception e) {

                            }
                            tvMoneydi.setText(dataBean.getUser_gold() + "金币");
                            tvMoneyNum.setText("-¥" +dataBean.getUser_gold());
                        }

                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.tv_nowapply_config,R.id.iv_golddiscount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_nowapply_config:
                initPayPop(totalPrice+"");
                break;
            case R.id.iv_golddiscount:
                if (tag == 1) {
                    ivGolddiscount.setImageResource(R.drawable.hongquan);
                    tag = 0;
                    totalPrice = totalPrice - g;
                    tvAllmoney.setText("¥" + totalPrice);
                } else if (tag==0){
                    ivGolddiscount.setImageResource(R.drawable.baiquan);
                    tag = 1;
                    totalPrice = totalPrice + g;
                    tvAllmoney.setText("¥" + totalPrice);
                }else {
                    ivGolddiscount.setImageResource(R.drawable.hongquan);
                    tag = 0;
                    totalPrice = totalPrice - g;
                    tvAllmoney.setText("¥" + totalPrice);
                }
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
        final ImageView pop_state_ali = (ImageView) view.findViewById(R.id.pop_state_ali);
        final ImageView pop_state_wx = (ImageView) view.findViewById(R.id.pop_state_wx);
        final MyPopupWindow payPopWindow = new MyPopupWindow(this, view);
        payPopWindow.showAtLocation(tvTitle, Gravity.BOTTOM, 0, 0);
        pop_pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWechat = false;
                pop_state_ali.setImageResource(R.drawable.hongquan);
                pop_state_wx.setImageResource(R.drawable.baiquan);
            }
        });
        pop_pay_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWechat = true;
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
                if (isWechat) {
                    wechatPay();
                } else aliPay();
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
        String orderIdItem = "";
        String studentItem = "";
        String couponsIdItem = "";
        String unitPriceItem = "";
        for (int i = 0; i < orderBean.size(); i++) {
            if (TextUtils.isEmpty(studentsName[i])){
                Toast.makeText(this, "请确认所有订单都填写是上课学生姓名！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (i==0){
                orderIdItem = orderIdItem + orderIds[i];
                studentItem = studentItem + studentsName[i];
                couponsIdItem = couponsIdItem + couponsIds[i];
                unitPriceItem = unitPriceItem + (unitPrice[i]+"");
            }else {
                orderIdItem = orderIdItem+"a"+orderIds[i];
                studentItem = studentItem+","+studentsName[i];
                couponsIdItem = couponsIdItem +","+ couponsIds[i];
                unitPriceItem = unitPriceItem +","+ (unitPrice[i]+"");
            }
        }

        Log.e("aaa",
                "(ApplyDetailPayActivity.java:307)订单的id-----"+orderIdItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:309)上课学生的姓名-----"+studentItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:311)优惠券的id-------"+couponsIdItem);
        Log.e("aaa",
                "(ApplyDetailPayActivity.java:318)每个列表的价格-------"+unitPriceItem);

        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderIdItem);
        params.put("pay_num", "1");
        params.put("is_coupons",couponsIdItem);
        params.put("is_gold", tag+"");
        params.put("student_name", studentItem);
        params.put("order_money",unitPriceItem);
        params.put("totle_pay", "0.01");

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
        String orderIdItem = "";
        String studentItem = "";
        String couponsIdItem = "";
        String unitPriceItem = "";
        for (int i = 0; i < orderBean.size(); i++) {
            if (TextUtils.isEmpty(studentsName[i])){
                Toast.makeText(this, "请确认所有订单都填写是上课学生姓名！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (i==0){
                orderIdItem = orderIdItem + orderIds[i];
                studentItem = studentItem + studentsName[i];
                couponsIdItem = couponsIdItem + couponsIds[i];
                unitPriceItem = unitPriceItem + (unitPrice[i]+"");
            }else {
                orderIdItem = orderIdItem+"a"+orderIds[i];
                studentItem = studentItem+","+studentsName[i];
                couponsIdItem = couponsIdItem +","+ couponsIds[i];
                unitPriceItem = unitPriceItem +","+ (unitPrice[i]+"");
            }
        }

        Log.e("aaa",
            "(ApplyDetailPayActivity.java:307)订单的id-----"+orderIdItem);
        Log.e("aaa",
            "(ApplyDetailPayActivity.java:309)上课学生的姓名-----"+studentItem);
        Log.e("aaa",
            "(ApplyDetailPayActivity.java:311)优惠券的id-------"+couponsIdItem);
        Log.e("aaa",
            "(ApplyDetailPayActivity.java:318)每个列表的价格-------"+unitPriceItem);

        String ipAddress = MyUtiles.getIPAddress(this);

        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", orderIdItem);
        params.put("pay_num", "1");
        params.put("is_coupons",couponsIdItem);
        params.put("is_gold", tag+"");
        params.put("student_name", studentItem);
        params.put("order_money",unitPriceItem);
        params.put("totle_pay", "0.01");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==22){
            if (discount != 0) {
                totalPrice += discount;
            }
            String student = data.getStringExtra("student");
            int coupons_id = data.getIntExtra("coupons_id", 0);
            discount = data.getDoubleExtra("discount", 0);
            double hasDiscount = data.getDoubleExtra("hasDiscount", 0);
            totalPrice = totalPrice - discount;
            tvAllmoney.setText("¥" + (totalPrice));
            studentsName[position] = student;
            couponsIds[position] = coupons_id;
            unitPrice[position] = hasDiscount;
            mAdapter.setPositon(position);
            mAdapter.notifyDataSetChanged();
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

        private void setPositon(int positon){
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
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            CarListBean.DataBean dataBean = orderBean.get(position);

            Glide.with(ApplyDetailPayActivity.this).load(Internet.BASE_URL+dataBean.getSchool_logo()).centerCrop().into(holder.ivSchoolIcon);
            holder.tvSchoolName.setText(dataBean.getSchool_name());
            String photo = "";
            if (dataBean.getClass_photo().contains(",")){
                photo = dataBean.getClass_photo().split(",")[0];
            }else {
                photo = dataBean.getClass_photo();
            }
            Glide.with(ApplyDetailPayActivity.this).load(Internet.BASE_URL+photo).centerCrop().into(holder.ivCourseIcon);
            holder.tvCourse.setText(dataBean.getClass_name());
            String string = dataBean.getClass_money().split("/")[1].split("节")[0];
            String money = dataBean.getClass_money().split("元")[0];
            holder.tvCourseNum.setText("x"+dataBean.getCourse_num());
            int uCourseNum = Integer.parseInt(string);
            int tCourseNum = Integer.parseInt(dataBean.getCourse_num());
            int mon = Integer.parseInt(money);
            int tCourse = uCourseNum*tCourseNum;
            holder.tvCourseNumAll.setText(tCourse+"");
            double anInt = mon * tCourseNum;
            holder.tvAllMoney.setText("¥"+ unitPrice[position] +"");

            holder.llCourseDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(ApplyDetailPayActivity.this,ApplyDetailsActivity.class).putExtra("order",orderBean.get(position)),1);
                    ApplyDetailPayActivity.this.position = position;
                }
            });

            if (position==selectPosition){
                double v = anInt - discount;
                holder.tvAllMoney.setText("¥"+ v +"");
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

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
