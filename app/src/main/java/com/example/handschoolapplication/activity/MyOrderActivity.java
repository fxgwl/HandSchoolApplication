package com.example.handschoolapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.fragment.AllOrderFragment;
import com.example.handschoolapplication.fragment.RefundFragment;
import com.example.handschoolapplication.fragment.UnEvaluateOrderFragment;
import com.example.handschoolapplication.fragment.UnpayOrderFragment;
import com.example.handschoolapplication.fragment.VertifyOrderFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getIntent().getStringExtra("flag");
        Log.e("aaa",
                "(MyOrderActivity.java:47)" + flag);
        initView();

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

        tvTitle.setText("订单中心");

        initFirstFragment(flag);
    }

    private void initFirstFragment(String flag) {
        currentFragment = new Fragment();
        switch (flag) {
            case "all":
                if (allOrderFragment == null) {
                    allOrderFragment = new AllOrderFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, allOrderFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(allOrderFragment).commit();
                }
                selectAllorder();
                currentFragment = allOrderFragment;
                break;
            case "pay":
                if (unpayOrderFragment == null) {
                    unpayOrderFragment = new UnpayOrderFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, unpayOrderFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(unpayOrderFragment).commit();
                }
                selectUnpay();
                currentFragment = unpayOrderFragment;
                break;
            case "eva":
                if (unEvaluateOrderFragment == null) {
                    unEvaluateOrderFragment = new UnEvaluateOrderFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_fragment, unEvaluateOrderFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().show(unEvaluateOrderFragment).commit();
                }
                selectUnevaluta();
                currentFragment = unEvaluateOrderFragment;
                break;
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(v);

                break;
            case R.id.ll_all_order:
                selectAllorder();
                if (allOrderFragment == null) allOrderFragment = new AllOrderFragment();
                addFragmentShow(getSupportFragmentManager(), allOrderFragment);
                break;
            case R.id.ll_unpay_order:
                selectUnpay();
                if (unpayOrderFragment == null) unpayOrderFragment = new UnpayOrderFragment();
                addFragmentShow(getSupportFragmentManager(), unpayOrderFragment);
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
                if (vertifyOrderFragment == null) vertifyOrderFragment = new VertifyOrderFragment();
                addFragmentShow(getSupportFragmentManager(), vertifyOrderFragment);
                break;
            case R.id.ll_unevalutae_order:
                selectUnevaluta();
                if (unEvaluateOrderFragment == null)
                    unEvaluateOrderFragment = new UnEvaluateOrderFragment();
                addFragmentShow(getSupportFragmentManager(), unEvaluateOrderFragment);
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
                if (refundFragment == null) refundFragment = new RefundFragment();
                addFragmentShow(getSupportFragmentManager(), refundFragment);
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
