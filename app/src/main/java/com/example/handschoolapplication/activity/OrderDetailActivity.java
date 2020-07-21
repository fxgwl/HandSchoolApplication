package com.example.handschoolapplication.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.OrderDetailLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.OrderInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.mlv_orderdetail)
    MyListView mlvOrderdetail;
    @BindView(R.id.ll_pingjia)
    LinearLayout llPingjia;
    @BindView(R.id.ll_waitpay)
    LinearLayout llWaitpay;
    @BindView(R.id.ll_tuikuan)
    LinearLayout llTuikuan;
    @BindView(R.id.ll_waitconfig)
    LinearLayout llWaitconfig;
    @BindView(R.id.ll_cancle)
    LinearLayout llCancle;
    @BindView(R.id.ll_yipingjia)
    LinearLayout llYipingjia;
    @BindView(R.id.ll_pay_way)
    LinearLayout ll_pay_way;
    @BindView(R.id.ll_yituikuan)
    LinearLayout llYituikuan;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_learn_code)
    TextView tvLearnCode;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.ll_learn_code)
    LinearLayout llLearnCode;
    @BindView(R.id.tv_orderdetail_youhui)
    TextView tv_orderdetail_youhui;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.tv_orderdetail_sfk)
    TextView tv_orderdetail_sfk;
    @BindView(R.id.tv_orderdetail_student_name)
    TextView tv_orderdetail_stu_name;
    @BindView(R.id.tv_orderdetail_stu_sex)
    TextView tv_orderdetail_stu_sex;
    @BindView(R.id.tv_orderdetail_payway)
    TextView tv_orderdetail_payway;

    private OrderDetailLvAdapter mAdapter;
    private List<OrderInfoBean.DataBean> mList = new ArrayList<>();
    private String order_id;
    private OrderInfoBean orderInfoBean;
    private String course_id;
    private String classPhone;//学堂的电话号码
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE;
    private String payway="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        requestPermission();
        order_id = getIntent().getStringExtra("order_id");
        tvTitle.setText("订单详情");
        mAdapter = new OrderDetailLvAdapter(OrderDetailActivity.this, mList);
        mlvOrderdetail.setAdapter(mAdapter);

//        initData();

        mlvOrderdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String course_id = mList.get(position).getCourse_id();
                String schooluid = mList.get(position).getUser_id();
                Intent intent = new Intent(OrderDetailActivity.this, CourseHomePagerActivity.class);
                intent.putExtra("course_id", course_id);
                intent.putExtra("schooluid", schooluid);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initData() {

        mList.clear();

        OkHttpUtils.post()
                .url(Internet.ORDER_INFO)
                .addParams("order_id", order_id)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(OrderDetailActivity.java:63)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(OrderDetailActivity.java:70)" + response);
                        try {

                            if (response.contains("没有信息")) {

                            } else {
                                orderInfoBean = new Gson().fromJson(response, OrderInfoBean.class);
                                mList.add(orderInfoBean.getData());
                                mAdapter.notifyDataSetChanged();
                                String school_name = orderInfoBean.getData().getSchool_name();
                                payway=orderInfoBean.getData().getPay_type();
                                //        0待付款 1待确认 2待评价 3评价后 4退款 5取消订单 6已退款

                                //        0待付款 1待确认 2待评价 3评价后 4退款中 5取消订单 6已退款
                                Log.e("aaa",
                                        "(OrderDetailActivity.java:133)order_state ==== " + orderInfoBean.getData().getOrder_state());
                                switch (orderInfoBean.getData().getOrder_state()) {
                                    case "0":
                                        llWaitpay.setVisibility(View.VISIBLE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.GONE);
                                        break;
                                    case "1":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.VISIBLE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.VISIBLE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.VISIBLE);
                                        llYituikuan.setVisibility(View.GONE);
                                        break;
                                    case "4":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.VISIBLE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.GONE);
                                        break;
                                    case "5":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.VISIBLE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.GONE);
                                        ll_pay_way.setVisibility(View.GONE);
                                        break;
                                    case "6":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.VISIBLE);
                                        ll_pay_way.setVisibility(View.GONE);
                                        break;
                                }
                                String pay_type = orderInfoBean.getData().getPay_type();
                                if (!TextUtils.isEmpty(pay_type) && pay_type.equals("2")) {
                                    tvRefund.setVisibility(View.INVISIBLE);
                                }
                                switch (pay_type) {
                                    case "0":
                                        tv_orderdetail_payway.setText("支付宝支付");
                                        break;
                                    case "1":
                                        tv_orderdetail_payway.setText("微信支付");
                                        break;
                                    case "2":
                                        tv_orderdetail_payway.setText("线下支付");
                                        break;
                                    default:
                                        tv_orderdetail_payway.setText("待支付");
                                        break;
                                }

                                tvOrderId.setText(order_id);
                                tvLearnCode.setText(orderInfoBean.getData().getClass_teacher());
                                tvCreateTime.setText(orderInfoBean.getData().getOrdre_time());//用的是付款时间
                                if (!orderInfoBean.getData().getOrder_state().equals("0")) {
                                    tvPayTime.setText(orderInfoBean.getData().getClass_time());
                                }
                                tv_orderdetail_sfk.setText(orderInfoBean.getData().getClass_money().split("元")[0]);
                                tvClassName.setText(school_name);
                                tv_orderdetail_stu_name.setText(orderInfoBean.getData().getStudent_name());
                                tv_orderdetail_stu_sex.setText(orderInfoBean.getData().getStudent_sex());

                                classPhone = orderInfoBean.getData().getUserInfo().getUser_phone();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            } else {
//                Toast.makeText(this, "权限已申请", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_make, R.id.tv_cancel, R.id.tv_pay, R.id.ll_learn_code,
            R.id.ll_contact, R.id.ll_call, R.id.tv_refund, R.id.tv_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_make://评价
                OrderInfoBean.DataBean dataBean = mList.get(0);
                Intent intent = new Intent(this, PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getCourse_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getUserInfo().getHead_photo()
                        );
                startActivity(intent);
                break;
            case R.id.tv_cancel://取消订单
                showCashDialog();
                break;
            case R.id.tv_pay://付款
                payForOrder();
                break;
            case R.id.ll_learn_code:
                startActivity(new Intent(OrderDetailActivity.this, QRCodeActivity.class)
                        .putExtra("learnCode", "gr," + (orderInfoBean.getData().getClass_teacher() + "," + orderInfoBean.getData().getOrder_id() + "," + orderInfoBean.getData().getSchool_id()))
                        .putExtra("flag", "LC")
                        .putExtra("order_state", orderInfoBean.getData().getOrder_state()));
                break;
            case R.id.ll_contact:

                String course_id = orderInfoBean.getData().getCourseInfo().getCourse_id();
                String schooluid = orderInfoBean.getData().getCourseInfo().getUser_id();
                String school_name = orderInfoBean.getData().getCourseInfo().getSchool_name();

                Intent intent3 = new Intent(this, HumanServiceActivity.class);
                intent3.putExtra("type", "0");
                intent3.putExtra("course_id", course_id);
                intent3.putExtra("send_id", schooluid);
                intent3.putExtra("name", school_name);
                startActivity(intent3);
                break;
            case R.id.ll_call:
                // TODO: 2018/3/2 拨打电话
//                MyUtiles.requestPermission(this,11);
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_CALL);
                intent2.setData(Uri.parse("tel:" + classPhone));
                //开启系统拨号器
                startActivity(intent2);
                break;

            case R.id.tv_verify:

                String class_teacher = orderInfoBean.getData().getClass_teacher();//学习码
                String order_id = orderInfoBean.getData().getOrder_id();//订单Id
                String school_id = orderInfoBean.getData().getSchool_id();
                String order_state = orderInfoBean.getData().getOrder_state();
                showDialog(class_teacher, order_id, school_id, order_state);
                break;
            case R.id.tv_refund:
//                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent4 = new Intent(this, ReturnMoneyActivity.class);
                intent4.putExtra("ordernum", orderInfoBean.getData().getOrder_id());
                intent4.putExtra("courseid", orderInfoBean.getData().getCourse_id());
                intent4.putExtra("ivcourse", orderInfoBean.getData().getClass_photo());
                intent4.putExtra("coursename", orderInfoBean.getData().getClass_name());
                intent4.putExtra("money", orderInfoBean.getData().getOrder_money());
                intent4.putExtra("coursenum", orderInfoBean.getData().getCourse_num());
                intent4.putExtra("tuimoney", orderInfoBean.getData().getOrder_money());
                intent4.putExtra("course_id", orderInfoBean.getData().getCourse_id());
                intent4.putExtra("schooluid", orderInfoBean.getData().getUser_id());
                startActivity(intent4);
                break;
        }


    }

    private void showCashDialog() {
        final SelfDialog selfDialog = new SelfDialog(this);
        selfDialog.setMessage("是否取消订单?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                //假数据  逻辑没写
                cancelOrder();
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void payForOrder() {
        String school_name = orderInfoBean.getData().getSchool_name();
        String school_id = orderInfoBean.getData().getSchool_id();
        String course_name = orderInfoBean.getData().getClass_name();
        String course_time = orderInfoBean.getData().getCourseInfo().getCourse_time();
        String enrol_num = orderInfoBean.getData().getCourseInfo().getEnrol_num();
        String course_capacity = orderInfoBean.getData().getCourseInfo().getCourse_capacity();
        String age_range = orderInfoBean.getData().getCourseInfo().getAge_range();
        String course_teacher = orderInfoBean.getData().getCourseInfo().getCourse_teacher();
        String original_price = orderInfoBean.getData().getCourseInfo().getOriginal_price();
        String preferential_price = orderInfoBean.getData().getCourseInfo().getPreferential_price();
        String class_money = orderInfoBean.getData().getClass_money();
        String course_id = orderInfoBean.getData().getCourse_id();
        String order_id = orderInfoBean.getData().getOrder_id();
        String course_num = orderInfoBean.getData().getCourse_num();
        String is_golds = orderInfoBean.getData().getCourseInfo().getIs_golds();

        startActivity(new Intent(OrderDetailActivity.this, NowApplyActivity.class)
                .putExtra("school_name", school_name)
                .putExtra("school_id", school_id)
                .putExtra("course_name", course_name)
                .putExtra("course_time", course_time)
                .putExtra("enrol_num", enrol_num)
                .putExtra("course_capacity", course_capacity)
                .putExtra("age_range", age_range)
                .putExtra("course_teacher", course_teacher)
                .putExtra("original_price", original_price)
                .putExtra("preferential_price", preferential_price)
                .putExtra("class_money", class_money)
                .putExtra("order_id", order_id)
                .putExtra("course_id", course_id)
                .putExtra("course_num", course_num)
                .putExtra("is_golds", is_golds)
        );
    }

    private void showDialog(final String learnCode, final String orderId, final String school_id, final String order_state) {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("是否需要机构确认订单?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);
                startActivity(new Intent(OrderDetailActivity.this, QRCodeActivity.class)
                        .putExtra("learnCode", "gr," + learnCode + "," + orderId + "," + school_id)
                        .putExtra("flag", "LC")
                        .putExtra("order_state", order_state));
                selfDialog.dismiss();
            }
        });


        selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                if(payway.equals("2")){

                }else{
                    sign(learnCode, orderId, school_id);
                }
                selfDialog.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void cancelOrder() {

        OkHttpUtils.post()
                .url(Internet.CLOSEORDER)
                .addParams("order_id", order_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(OrderAdapter.java:164)" + response);
                        if (response.contains("成功")) {
                        }
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

    private void sign(String learnCode, String orderId, String school_id) {
        OkHttpUtils.post()
                .url(InternetS.SCAN_ORDER)
                .addParams("study_num", (learnCode + "," + orderId))
                .addParams("school_id", (school_id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(ScanQRCodeActivity.java:118)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(ScanQRCodeActivity.java:124)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");
                            if (result == 0) {
                                Toast.makeText(OrderDetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
//                                initDataView();
                                initData();
                            } else {
                                Toast.makeText(OrderDetailActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                showToast("权限已申请");
            } else {
//                showToast("权限已拒绝");
                boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(OrderDetailActivity.this, permissions[0]);
                if (showRequestPermission) {
//                        Toast.makeText(this, "权限未申请", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "权限未获得，不能拨打电话", Toast.LENGTH_SHORT).show();
                    //requestPermission();
                }else{
                    Toast.makeText(this, "手机系统设置->应用和通知->权限管理 进行设置", Toast.LENGTH_SHORT).show();
                    //showUnLoginDialog("点开手机系统设置->应用和通知->权限管理",0);
                }
            }
        }
    }

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            backgroundAlpha(1f);
        }
    }

}
