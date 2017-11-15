package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class VertifyOrderFragment extends BaseFragment {

    @BindView(R.id.lv_vertify_order)
    ListView lvVertifyOrder;
    private View view;
    private List<OrderBean.DataBean> mOrderList = new ArrayList<>();
    private String user_id;
    private OrderAdapter orderAdapter;


    public VertifyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        orderAdapter = new OrderAdapter(getActivity(), mOrderList);
        lvVertifyOrder.setAdapter(orderAdapter);
        initDataView();

        orderAdapter.setOnMakeOrderListener(new OrderAdapter.OnMakeOrderListener() {
            @Override
            public void setOnCancelOrder(int position) {
                OrderBean.DataBean dataBean = mOrderList.get(position);
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
                .addParams("order_state", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(AllOrderFragment.java:7373待确认)" + response);
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
        return R.layout.fragment_vertify_order;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initDataView();
    }
}
