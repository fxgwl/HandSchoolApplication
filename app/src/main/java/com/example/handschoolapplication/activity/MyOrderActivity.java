package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.OrderAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.fragment.AllOrderFragment;
import com.example.handschoolapplication.fragment.RefundFragment;
import com.example.handschoolapplication.fragment.UnEvaluateOrderFragment;
import com.example.handschoolapplication.fragment.UnpayOrderFragment;
import com.example.handschoolapplication.fragment.VertifyOrderFragment;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llAllOrder, llUnpayOrder, llVertifyOrder, llUnEvaluateOrder, llRefundOrder;
    private TextView tvAllOrder, tvUnpayOrder, tvVertifyOrder, tvUnEvaluateOrder, tvRefundOrder;
    private View vAllOrder, vUnpayOrder, vVertifyOrder, vUnEvaluateOrder, vRefundOrder;
    private FrameLayout frameLayout;

    private RelativeLayout rlBack;
    private TextView tvTitle;
    private RelativeLayout ivMenu;

    private Fragment currentFragment;
    private AllOrderFragment allOrderFragment;
    private UnpayOrderFragment unpayOrderFragment;
    private UnEvaluateOrderFragment unEvaluateOrderFragment;
    private VertifyOrderFragment vertifyOrderFragment;
    private RefundFragment refundFragment;
    private String flag;
    private LoadingDailog dialog;
    private ListView lvOrder;
    private String user_id;

    private List<OrderBean.DataBean> mOrderList = new ArrayList<>();
    private OrderAdapter mAdapter;
    private String state = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getIntent().getStringExtra("flag");
        Log.e("aaa",
                "(MyOrderActivity.java:47)" + flag);

        user_id = (String) SPUtils.get(this, "userId", "");

        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(false);
        dialog = loadBuilder.create();

        initView();
        mAdapter = new OrderAdapter(this, mOrderList);
        lvOrder.setAdapter(mAdapter);

        initListener();
        switch (flag){
            case "pay":
                state = "0";
                selectUnpay();
                break;
            case "eva":
                state = "2";
                selectUnevaluta();
                break;
            case "all":
                state = "";
                selectAllorder();
                break;
        }
        getData(state);
    }

    private void initListener() {

        mAdapter.setOnMakeOrderListener(new OrderAdapter.OnMakeOrderListener() {
            @Override
            public void setOnCancelOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                showCashDialog(dataBean, position);
            }

            @Override
            public void setOnPayOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                String school_name = dataBean.getSchool_name();
                String school_id = dataBean.getSchool_id();
                String course_name = dataBean.getClass_name();
                String course_time = dataBean.getCourseInfo().getCourse_time();
                String course_state = dataBean.getCourseInfo().getCourse_state();
                int enrol_num = dataBean.getCourseInfo().getEnrol_num();
                int course_capacity = dataBean.getCourseInfo().getCourse_capacity();
                String age_range = dataBean.getCourseInfo().getAge_range();
                String course_teacher = dataBean.getCourseInfo().getCourse_teacher();
                String original_price = dataBean.getCourseInfo().getOriginal_price();
                String preferential_price = dataBean.getCourseInfo().getPreferential_price();
                String class_money = dataBean.getClass_money();
                String course_id = dataBean.getCourse_id();
                String order_id = dataBean.getOrder_id();
                String course_num = dataBean.getCourse_num();
                String is_golds = dataBean.getCourseInfo().getIs_golds();
                String student_name = dataBean.getStudent_name();
                String student_sex = dataBean.getStudent_sex();
                String is_coups = dataBean.getCourseInfo().getIs_coups();

                if ("2".equals(course_state) || enrol_num >= course_capacity) {
                    Toast.makeText(MyOrderActivity.this, course_name + "课程已停止报名", Toast.LENGTH_SHORT).show();
                } else {
                    startActivityForResult(new Intent(MyOrderActivity.this, NowApplyActivity.class)
                                    .putExtra("school_name", school_name)
                                    .putExtra("school_id", school_id)
                                    .putExtra("course_name", course_name)
                                    .putExtra("course_time", course_time)
                                    .putExtra("enrol_num", enrol_num + "")
                                    .putExtra("course_capacity", course_capacity + "")
                                    .putExtra("age_range", age_range)
                                    .putExtra("course_teacher", course_teacher)
                                    .putExtra("original_price", original_price)
                                    .putExtra("preferential_price", preferential_price)
                                    .putExtra("class_money", class_money)
                                    .putExtra("order_id", order_id)
                                    .putExtra("course_id", course_id)
                                    .putExtra("is_coup", is_coups)
                                    .putExtra("course_num", course_num)
                                    .putExtra("is_golds", is_golds)
                                    .putExtra("student_name", student_name)
                                    .putExtra("student_sex", student_sex)
                            , 1);
                }
            }

            @Override
            public void setOnRefund(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(MyOrderActivity.this, ReturnMoneyActivity.class);
                intent.putExtra("ordernum", dataBean.getOrder_id());
                intent.putExtra("courseid", dataBean.getCourse_id());
                intent.putExtra("ivcourse", dataBean.getCourseInfo().getPicture_one());
                intent.putExtra("coursename", dataBean.getClass_name());
                intent.putExtra("money", dataBean.getOrder_money());
                intent.putExtra("coursenum", dataBean.getCourse_num());
                intent.putExtra("tuimoney", dataBean.getOrder_money());
                intent.putExtra("course_id", dataBean.getCourse_id());
                intent.putExtra("schooluid", dataBean.getUser_id());
                startActivityForResult(intent, 1);
            }

            @Override
            public void setOnVertify(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                String class_teacher = dataBean.getClass_teacher();//学习码
                String order_id = dataBean.getOrder_id();//订单Id
                String school_id = dataBean.getSchool_id();
                String order_state = dataBean.getOrder_state();
                if(dataBean.getPay_type().equals("2")){
                    showDialog2(class_teacher, order_id,school_id,order_state);
                }else{
                    showDialog(class_teacher, order_id,school_id,order_state);
                }
            }

            @Override
            public void setEvaluate(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(MyOrderActivity.this, PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getOrder_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getSchool_logo()
                        );
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initView() {
        llAllOrder = (LinearLayout) findViewById(R.id.ll_all_order);
        llUnpayOrder = (LinearLayout) findViewById(R.id.ll_unpay_order);
        llVertifyOrder = (LinearLayout) findViewById(R.id.ll_verify_order);
        llUnEvaluateOrder = (LinearLayout) findViewById(R.id.ll_unevalutae_order);
        llRefundOrder = (LinearLayout) findViewById(R.id.ll_refund);

        tvAllOrder = (TextView) findViewById(R.id.tv_all_order);
        tvUnpayOrder = (TextView) findViewById(R.id.tv_unpay_order);
        tvVertifyOrder = (TextView) findViewById(R.id.tv_verify_order);
        tvUnEvaluateOrder = (TextView) findViewById(R.id.tv_unevalutae_order);
        tvRefundOrder = (TextView) findViewById(R.id.tv_refund);

        vAllOrder = findViewById(R.id.view_all_order);
        vUnpayOrder = findViewById(R.id.view_unpay_order);
        vVertifyOrder = findViewById(R.id.view_verify_order);
        vUnEvaluateOrder = findViewById(R.id.view_unevalutae_order);
        vRefundOrder = findViewById(R.id.view_refund);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);
        ivMenu = (RelativeLayout) findViewById(R.id.iv_menu);

        llAllOrder.setOnClickListener(this);
        llUnpayOrder.setOnClickListener(this);
        llVertifyOrder.setOnClickListener(this);
        llUnEvaluateOrder.setOnClickListener(this);
        llRefundOrder.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        lvOrder = (ListView) findViewById(R.id.lv_order);

        tvTitle.setText("订单中心");

//        initFirstFragment(flag);
    }


    private void selectAllorder() {
        tvAllOrder.setTextColor(getResources().getColor(R.color.blue));
        vAllOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        tvUnpayOrder.setTextColor(Color.parseColor("#666666"));
        vUnpayOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvVertifyOrder.setTextColor(Color.parseColor("#666666"));
        vVertifyOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvUnEvaluateOrder.setTextColor(Color.parseColor("#666666"));
        vUnEvaluateOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvRefundOrder.setTextColor(Color.parseColor("#666666"));
        vRefundOrder.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void selectUnpay() {
        tvUnpayOrder.setTextColor(getResources().getColor(R.color.blue));
        vUnpayOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        tvAllOrder.setTextColor(Color.parseColor("#666666"));
        vAllOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvVertifyOrder.setTextColor(Color.parseColor("#666666"));
        vVertifyOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvUnEvaluateOrder.setTextColor(Color.parseColor("#666666"));
        vUnEvaluateOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvRefundOrder.setTextColor(Color.parseColor("#666666"));
        vRefundOrder.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void selectUnevaluta() {
        tvUnEvaluateOrder.setTextColor(getResources().getColor(R.color.blue));
        vUnEvaluateOrder.setBackgroundColor(getResources().getColor(R.color.blue));
        tvAllOrder.setTextColor(Color.parseColor("#666666"));
        vAllOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvUnpayOrder.setTextColor(Color.parseColor("#666666"));
        vUnpayOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvVertifyOrder.setTextColor(Color.parseColor("#666666"));
        vVertifyOrder.setBackgroundColor(Color.parseColor("#ffffff"));
        tvRefundOrder.setTextColor(Color.parseColor("#666666"));
        vRefundOrder.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(v);
                break;
            case R.id.ll_all_order:
                selectAllorder();
                state = "";
                getData("");
//                if (allOrderFragment == null) allOrderFragment = new AllOrderFragment();
//                addFragmentShow(getSupportFragmentManager(), allOrderFragment);
                break;
            case R.id.ll_unpay_order:
                selectUnpay();
                state = "0";
                getData(state);
//                if (unpayOrderFragment == null) unpayOrderFragment = new UnpayOrderFragment();
//                addFragmentShow(getSupportFragmentManager(), unpayOrderFragment);
                break;
            case R.id.ll_verify_order:
                tvVertifyOrder.setTextColor(getResources().getColor(R.color.blue));
                vVertifyOrder.setBackgroundColor(getResources().getColor(R.color.blue));
                tvAllOrder.setTextColor(Color.parseColor("#666666"));
                vAllOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                tvUnpayOrder.setTextColor(Color.parseColor("#666666"));
                vUnpayOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                tvUnEvaluateOrder.setTextColor(Color.parseColor("#666666"));
                vUnEvaluateOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                tvRefundOrder.setTextColor(Color.parseColor("#666666"));
                vRefundOrder.setBackgroundColor(Color.parseColor("#ffffff"));

                state = "1";
                getData(state);
//                if (vertifyOrderFragment == null) vertifyOrderFragment = new VertifyOrderFragment();
//                addFragmentShow(getSupportFragmentManager(), vertifyOrderFragment);
                break;
            case R.id.ll_unevalutae_order:
                selectUnevaluta();
                state = "2";
                getData(state);
//                if (unEvaluateOrderFragment == null)
//                    unEvaluateOrderFragment = new UnEvaluateOrderFragment();
//                addFragmentShow(getSupportFragmentManager(), unEvaluateOrderFragment);
                break;
            case R.id.ll_refund:
                tvRefundOrder.setTextColor(getResources().getColor(R.color.blue));
                vRefundOrder.setBackgroundColor(getResources().getColor(R.color.blue));
                tvAllOrder.setTextColor(Color.parseColor("#666666"));
                vAllOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                tvUnpayOrder.setTextColor(Color.parseColor("#666666"));
                vUnpayOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                tvVertifyOrder.setTextColor(Color.parseColor("#666666"));
                vVertifyOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                tvUnEvaluateOrder.setTextColor(Color.parseColor("#666666"));
                vUnEvaluateOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                state = "4";
                getData(state);
//                if (refundFragment == null) refundFragment = new RefundFragment();
//                addFragmentShow(getSupportFragmentManager(), refundFragment);
                break;
        }
    }

    private void addFragmentShow(FragmentManager manager, Fragment fragment) {

        FragmentTransaction ft = manager.beginTransaction();
        if (currentFragment != fragment) {
            if (!fragment.isAdded()) {
                ft.hide(currentFragment).add(R.id.fl_fragment, fragment).commit();
            } else {
                ft.hide(currentFragment).show(fragment).commit();
            }
        } else {
            return;
        }

        currentFragment = fragment;
    }

    //获取订单详情
    private void getData(String state) {
        dialog.isShowing();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", user_id);
        String url = "";
        if (state.isEmpty()) {
            params.put("type", "order");
            url = Internet.ALLORDER;
        } else {
            //state  0待付款，1待确认，2待评价，4退款中
            params.put("order_state", state);
            url = Internet.ORDERSTATE;
        }

        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(MyOrderActivity.java:290)<---->" + e.getMessage());
                        dialog.isShowing();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", "(MyOrderActivity.java:295)<---->" + response);
                        dialog.isShowing();

                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            mOrderList.clear();
                            mAdapter.notifyDataSetChanged();
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    Gson gson = new Gson();
                                    mOrderList.clear();
                                    try {
                                        mOrderList.addAll(gson.fromJson(response, OrderBean.class).getData());
                                        mAdapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });


    }

    private void showCashDialog(final OrderBean.DataBean dataBean, final int position) {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("是否取消订单?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cancelOrder(dataBean, position);
                selfDialog.dismiss();
                backgroundAlpha(1f);
            }
        });


        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.6f);
        //selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }

    private void cancelOrder(OrderBean.DataBean dataBean, final int position) {
        OkHttpUtils.post()
                .url(Internet.CLOSEORDER)
                .addParams("order_id", dataBean.getOrder_id())
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
                            getData(state);
                        }
                    }
                });
    }


    private void showDialog(final String learnCode, final String orderId, final String school_id, final String order_state) {
        final SelfDialog selfDialog = new SelfDialog(this);

        selfDialog.setMessage("是否需要到学习机构进行确认?");
        selfDialog.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
////                cancelOrder(dataBean, position);
//                startActivity(new Intent(getActivity(), QRCodeActivity.class)
//                        .putExtra("learnCode", "gr,"+learnCode + "," + orderId)
//                        .putExtra("flag", "LC"));
                selfDialog.dismiss();
                backgroundAlpha(1f);
                showDialog2(learnCode, orderId,school_id,order_state);

            }
        });


        selfDialog.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
//                sign(learnCode,orderId);
                selfDialog.dismiss();
                backgroundAlpha(1f);
                showDialog3(learnCode, orderId,school_id,order_state);
            }
        });
        backgroundAlpha(0.6f);
        //selfDialog.setOnDismissListener(new poponDismissListener());
        selfDialog.show();
    }


    private void showDialog2(final String learnCode, final String orderId,final String school_id,final String order_state) {
        final SelfDialog selfDialog2 = new SelfDialog(this);

        selfDialog2.setMessage("学费将支付到机构账户，如发生退款请直接与机构联系");
        selfDialog2.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);
                startActivityForResult(new Intent(MyOrderActivity.this, QRCodeActivity.class)
                        .putExtra("learnCode", "gr," + learnCode + "," + orderId+","+school_id)
                        .putExtra("flag", "LC")
                        .putExtra("order_state", order_state), 0);
                selfDialog2.dismiss();
                backgroundAlpha(1f);
            }
        });


        selfDialog2.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfDialog2.dismiss();
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.6f);
        //selfDialog2.setOnDismissListener(new poponDismissListener());
        selfDialog2.show();
    }


    private void showDialog3(final String learnCode, final String orderId,final String school_id,final String order_state) {
        final SelfDialog selfDialog3 = new SelfDialog(this);

        selfDialog3.setMessage("学费将支付到机构账户，如发生退款请直接与机构联系");
        selfDialog3.setYesOnclickListener("是", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
//                cancelOrder(dataBean, position);
                selfDialog3.dismiss();
                sign(learnCode, orderId,school_id);
                backgroundAlpha(1f);
            }
        });


        selfDialog3.setNoOnclickListener("否", new SelfDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                getData(state);
                selfDialog3.dismiss();
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.6f);
        //selfDialog3.setOnDismissListener(new poponDismissListener());
        selfDialog3.show();
    }


    private void sign(String learnCode, String orderId,String school_id) {
        OkHttpUtils.post()
                .url(InternetS.SCAN_ORDER)
                .addParams("study_num", (learnCode + "," + orderId))
                .addParams("school_id", school_id)
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
                                Toast.makeText(MyOrderActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
                                getData(state);
                            } else {
                                Toast.makeText(MyOrderActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            getData(state);
        }
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

    /**
     * 添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
   /* class poponDismissListener implements Dialog.OnDismissListener {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            backgroundAlpha(1f);
        }
    }*/
}
