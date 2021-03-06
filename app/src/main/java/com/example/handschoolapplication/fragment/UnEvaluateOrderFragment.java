package com.example.handschoolapplication.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
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
public class UnEvaluateOrderFragment extends BaseFragment {

    @BindView(R.id.lv_unevaluate_order)
    ListView lvUnevaluateOrder;
    private View view;
    private List<OrderBean.DataBean> mOrderList = new ArrayList<>();
    private String user_id;
    private OrderAdapter orderAdapter;


    public UnEvaluateOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        orderAdapter = new OrderAdapter(getActivity(), mOrderList);
        lvUnevaluateOrder.setAdapter(orderAdapter);
        initDataView();
        orderAdapter.setOnMakeOrderListener(new OrderAdapter.OnMakeOrderListener() {
            @Override
            public void setOnCancelOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                showCashDialog(dataBean, position);
            }

            @Override
            public void setOnPayOrder(int position) {
                Toast.makeText(getActivity(), "去支付", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("course_id", dataBean.getCourse_id());
                intent.putExtra("schooluid", dataBean.getUser_id());
                startActivity(intent);
            }

            @Override
            public void setOnVertify(int position) {

            }

            @Override
            public void setEvaluate(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
                Intent intent = new Intent(getActivity(), PublishEvaluateActivity.class)
                        .putExtra("order_id", dataBean.getOrder_id())
                        .putExtra("school_name", dataBean.getSchool_name())
                        .putExtra("class_photo", dataBean.getSchool_logo()
                        );
                startActivityForResult(intent, 0);
            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initDataView();
    }

    private void initDataView() {
        OkHttpUtils.post()
                .url(Internet.ORDERSTATE)
                .addParams("user_id", user_id)
                .addParams("order_state", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(UnEvaluateOrderFragment.java:127)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(UnEvaluateOrderFragment.java:131)<---->" + response);
                        if (response.contains("没有信息") || TextUtils.isEmpty(response)) {
                            mOrderList.clear();
                            orderAdapter.notifyDataSetChanged();
                        } else {
                            Gson gson = new Gson();
                            mOrderList.clear();
                            try {
                                mOrderList.addAll(gson.fromJson(response, OrderBean.class).getData());
                                orderAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                            }
                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_unevaluate_order;
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

    @Override
    public void onResume() {
        super.onResume();
//        initDataView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("aaa",
                "(UnEvaluateOrderFragment.java:231)<--待评价的界面的  activityforresult-->");
        initDataView();
    }
}
