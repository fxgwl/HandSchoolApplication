package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.OrderDetailLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.OrderInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

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

    private OrderDetailLvAdapter mAdapter;
    private List<OrderInfoBean> mList = new ArrayList<>();
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        order_id = getIntent().getStringExtra("order_id");
        tvTitle.setText("订单详情");
        mAdapter = new OrderDetailLvAdapter(OrderDetailActivity.this, mList);
        mlvOrderdetail.setAdapter(mAdapter);

        initData();

        mlvOrderdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String course_id = mList.get(position).getCourse_id();
                String schooluid = mList.get(position).getUser_id();
                Intent intent = new Intent(OrderDetailActivity.this, CourseHomePagerActivity.class);
                intent.putExtra("course_id",course_id);
                intent.putExtra("schooluid",schooluid);
                startActivity(intent);
            }
        });
        llCancle.setOnClickListener(this);
    }

    private void initData() {

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
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject data = jsonObject.getJSONObject("data");
                                OrderInfoBean orderInfoBean = new Gson().fromJson(data.toString(), OrderInfoBean.class);
                                mList.add(orderInfoBean);
                                mAdapter.notifyDataSetChanged();
                                String school_name = orderInfoBean.getSchool_name();
                                //        0待付款 1待确认 2待评价 3评价后 4退款 5取消订单 6已退款

                                //        0待付款 1待确认 2待评价 3评价后 4退款中 5取消订单 6已退款
                                Log.e("aaa",
                                    "(OrderDetailActivity.java:133)order_state ==== "+orderInfoBean.getOrder_state());
                                switch (orderInfoBean.getOrder_state()) {
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
                                        break;
                                    case "6":
                                        llWaitpay.setVisibility(View.GONE);
                                        llPingjia.setVisibility(View.GONE);
                                        llTuikuan.setVisibility(View.GONE);
                                        llWaitconfig.setVisibility(View.GONE);
                                        llCancle.setVisibility(View.GONE);
                                        llYipingjia.setVisibility(View.GONE);
                                        llYituikuan.setVisibility(View.VISIBLE);
                                        break;
                                }
                                tvOrderId.setText(order_id);
                                tvLearnCode.setText(orderInfoBean.getClass_teacher());
                                tvCreateTime.setText(orderInfoBean.getClass_time());//用的是付款时间
                                tvPayTime.setText(orderInfoBean.getClass_time());
                                tvClassName.setText(school_name);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_detail;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.tv_make,R.id.tv_cancel,R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_make://评价
                OrderInfoBean dataBean = mList.get(0);
                Intent intent = new Intent(this, PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getCourse_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getClass_photo()
                        );
                startActivity(intent);
                break;
            case R.id.tv_cancel://取消订单
                showCashDialog();
                break;
            case R.id.tv_pay://付款
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cancle:
                showCashDialog();
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
