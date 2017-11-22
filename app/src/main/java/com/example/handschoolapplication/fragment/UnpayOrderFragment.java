package com.example.handschoolapplication.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.NowApplyActivity;
import com.example.handschoolapplication.activity.PublishEvaluateActivity;
import com.example.handschoolapplication.activity.ReturnMoneyActivity;
import com.example.handschoolapplication.adapter.OrderAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnpayOrderFragment extends BaseFragment {

    @BindView(R.id.lv_unpay)
    ListView lvUnpay;
    private View view;
    private List<OrderBean.DataBean> mOrderList = new ArrayList<>();
    private String user_id;
    private OrderAdapter orderAdapter;


    public UnpayOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        orderAdapter = new OrderAdapter(getActivity(), mOrderList);
        lvUnpay.setAdapter(orderAdapter);
        initDataView();

        orderAdapter.setOnMakeOrderListener(new OrderAdapter.OnMakeOrderListener() {
            @Override
            public void setOnCancelOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                showCashDialog(dataBean,position);
            }

            @Override
            public void setOnPayOrder(int position) {
//                /*school_name = intent.getStringExtra("school_name");
//                school_id = intent.getStringExtra("school_id");
//                course_name = intent.getStringExtra("course_name");
//                course_time = intent.getStringExtra("course_time");
//                enrol_num = intent.getStringExtra("enrol_num");
//                course_capacity = intent.getStringExtra("course_capacity");
//                age_range = intent.getStringExtra("age_range");
//                course_teacher = intent.getStringExtra("course_teacher");
//                original_price = intent.getStringExtra("original_price");
//                preferential_price = intent.getStringExtra("preferential_price");
//                class_money = intent.getStringExtra("class_money");
//                course_id = intent.getStringExtra("course_id");
//                order_id = intent.getStringExtra("order_id");*/
                OrderBean.DataBean dataBean = mOrderList.get(position);
                String school_id = dataBean.getSchool_id();
                String school_name = dataBean.getCourseInfo().getSchool_name();
                String course_name = dataBean.getCourseInfo().getCourse_name();
                String course_time = dataBean.getOrder_course_time();
                String enrol_num = dataBean.getCourseInfo().getEnrol_num();
                String course_capacity = dataBean.getCourseInfo().getCourse_capacity();
                String age_range = dataBean.getCourseInfo().getAge_range();
                String course_teacher = dataBean.getCourseInfo().getCourse_teacher();
                String original_price = dataBean.getCourseInfo().getOriginal_price();
                String preferential_price = dataBean.getCourseInfo().getPreferential_price();
                String class_money = dataBean.getClass_money();
                String course_id = dataBean.getCourseInfo().getCourse_id();
                String order_id = dataBean.getOrder_id();

                Intent intent1 = new Intent(getActivity(), NowApplyActivity.class);
                intent1.putExtra("school_id", school_id);
                intent1.putExtra("school_name", school_name);
                intent1.putExtra("course_name", course_name);
                intent1.putExtra("course_time", course_time);
                intent1.putExtra("enrol_num", enrol_num);
                intent1.putExtra("course_capacity", course_capacity);
                intent1.putExtra("age_range", age_range);
                intent1.putExtra("course_teacher", course_teacher);
                intent1.putExtra("original_price", original_price);
                intent1.putExtra("preferential_price", preferential_price);
                intent1.putExtra("class_money", class_money);
                intent1.putExtra("course_id", course_id);
                intent1.putExtra("order_id",order_id);
                startActivity(intent1);
            }

            @Override
            public void setOnRefund(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(getActivity(), ReturnMoneyActivity.class);
                intent.putExtra("ordernum", dataBean.getOrder_id());
                intent.putExtra("courseid", dataBean.getCourse_id());
                intent.putExtra("ivcourse", dataBean.getClass_photo());
                intent.putExtra("coursename", dataBean.getClass_name());
                intent.putExtra("money", dataBean.getOrder_money());
                intent.putExtra("coursenum", dataBean.getCourse_num());
                intent.putExtra("tuimoney", dataBean.getOrder_money());
                intent.putExtra("course_id",dataBean.getCourse_id());
                intent.putExtra("schooluid",dataBean.getUser_id());
                startActivity(intent);
            }

            @Override
            public void setOnVertify(int position) {

            }

            @Override
            public void setEvaluate(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(getActivity(), PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getCourse_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getClass_photo()
                        );
                startActivity(intent);
            }
        });
        return view;
    }

    private void initDataView() {
        OkHttpUtils.post()
                .url(Internet.ORDERSTATE)
                .addParams("user_id", user_id)
                .addParams("order_state", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(UnpayOrderFragment.java:126)" + response);
                        Gson gson = new Gson();
                        mOrderList.clear();
                        try {
                            mOrderList.addAll(gson.fromJson(response, OrderBean.class).getData());
                            orderAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_unpay_order;
    }

    private void showCashDialog(final OrderBean.DataBean dataBean, final int position) {
        final SelfDialog selfDialog = new SelfDialog(getActivity());

        selfDialog.setMessage("是否取消订单?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cancelOrder(dataBean, position);
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

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
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
                            mOrderList.remove(position);
                            orderAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}
