package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.RefundDetailLvAdapter;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.OrderInfoBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.view.MyListView;
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

public class RefundDetailActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    RelativeLayout ivMenu;
    @BindView(R.id.mlv_refunddetail)
    MyListView mlvRefunddetail;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_state)
    TextView tvState;
    private RefundDetailLvAdapter mAdapter;
    private List<OrderInfoBean> mList = new ArrayList<>();
    private String order_id;
    private String ordernum;
    private String course_id;
    private String schooluid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        tvTitle.setText("退款详情");
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        mAdapter=new RefundDetailLvAdapter(RefundDetailActivity.this,mList);
        mlvRefunddetail.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        OkHttpUtils.post()
                .addParams("order_id",order_id)
                .url(Internet.ORDER_INFO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                            "(RefundDetailActivity.java:63)"+e.getMessage());
                        Toast.makeText(RefundDetailActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                            "(RefundDetailActivity.java:72)"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (response.contains("没有信息")){

                            }else {
                                OrderInfoBean orderInfoBean = new Gson().fromJson(response, OrderInfoBean.class);
                                mList.add(orderInfoBean);
                                tvOrderId.setText(order_id);
                                tvTime.setText("申请时间："+orderInfoBean.getData().getOrdre_time());
                                tvReason.setText(orderInfoBean.getData().getClass_people()+"");
                                tvState.setText("退款中");
                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_refund_detail;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }
}
