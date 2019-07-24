package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.PublishEvaluateActivity;
import com.example.handschoolapplication.adapter.AllOrderAdapter;
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
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnEvaFragment extends BaseFragment implements AllOrderAdapter.EvaluateListener, OrderAdapter.OnMakeOrderListener {

    @BindView(R.id.lv_all_order)
    ListView lvAllOrder;
    Unbinder unbinder;
    private View view;
    private List<OrderBean.DataBean> mlist = new ArrayList<>();

    private String user_id;
    private OrderAdapter orderAdapter;


    public UnEvaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        orderAdapter = new OrderAdapter(getActivity(), mlist);
        lvAllOrder.setAdapter(orderAdapter);
        initDataView();

        orderAdapter.setOnMakeOrderListener(this);
        return view;
    }

    private void initDataView() {
        mlist.clear();
        OkHttpUtils.post()
                .url(Internet.ORDERSTATE)
                .addParams("user_id", user_id)
                .addParams("order_state", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(UnEvaFragment.java:73)" + response);
                        Gson gson = new Gson();
                        mlist.clear();
                        try {
                            mlist.addAll(gson.fromJson(response, OrderBean.class).getData());
                            orderAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                        }

                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_all_order;
    }

    //去评价
    @Override
    public void onEvaluate(int position) {
        OrderBean.DataBean dataBean = mlist.get(position);
        Intent intent = new Intent(getActivity(), PublishEvaluateActivity.class)
                .putExtra("order_id", dataBean.getOrder_id())
                .putExtra("school_name", dataBean.getSchool_name())
                .putExtra("class_photo", dataBean.getSchool_logo()
                );
        startActivity(intent);
    }

    @Override
    public void setOnCancelOrder(int position) {

    }

    @Override
    public void setOnPayOrder(int position) {

    }

    @Override
    public void setOnRefund(int position) {

    }

    @Override
    public void setOnVertify(int position) {

    }

    @Override
    public void setEvaluate(int position) {
        OrderBean.DataBean dataBean = mlist.get(position);
        Intent intent = new Intent(getActivity(), PublishEvaluateActivity.class)
                .putExtra("order_id", dataBean.getOrder_id())
                .putExtra("school_name", dataBean.getSchool_name())
                .putExtra("class_photo", dataBean.getSchool_logo()
                );
        startActivity(intent);
    }
}
