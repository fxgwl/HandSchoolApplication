package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DealManagerActivity extends AppCompatActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.ll_dealmanager_daifukuang)
    LinearLayout llDealmanagerDaifukuang;
    @BindView(R.id.ll_dealmanager_daixuexiqueren)
    LinearLayout llDealmanagerDaixuexiqueren;
    @BindView(R.id.ll_dealmanager_evaluate)
    LinearLayout llDealmanagerEvaluate;
    @BindView(R.id.ll_dealmanager_refund)
    LinearLayout llDealmanagerRefund;
    @BindView(R.id.ll_dealmanager_successorder)
    LinearLayout llDealmanagerSuccessorder;
    @BindView(R.id.ll_dealmanager_closeorder)
    LinearLayout llDealmanagerCloseorder;
    @BindView(R.id.ll_dealmanager_allorder)
    LinearLayout llDealmanagerAllorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_deal_manager);
        ButterKnife.bind(this);
        tvTitle.setText("交易管理");

    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_dealmanager_daifukuang, R.id.ll_dealmanager_daixuexiqueren, R.id.ll_dealmanager_evaluate, R.id.ll_dealmanager_refund, R.id.ll_dealmanager_successorder, R.id.ll_dealmanager_closeorder, R.id.ll_dealmanager_allorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            //等待买家付款
            case R.id.ll_dealmanager_daifukuang:
                startActivity(new Intent(DealManagerActivity.this,DaifukuangActivity.class).putExtra("type","1"));
                break;
            //等待学习确认
            case R.id.ll_dealmanager_daixuexiqueren:
                startActivity(new Intent(DealManagerActivity.this,DaifukuangActivity.class).putExtra("type","2"));
                break;
            //需要评价
            case R.id.ll_dealmanager_evaluate:
                startActivity(new Intent(DealManagerActivity.this,DaifukuangActivity.class).putExtra("type","3"));
                break;
            //退款管理
            case R.id.ll_dealmanager_refund:
                startActivity(new Intent(DealManagerActivity.this,RefundManagerActivity.class));
                break;
            //成功的订单
            case R.id.ll_dealmanager_successorder:
                startActivity(new Intent(DealManagerActivity.this,DaifukuangActivity.class).putExtra("type","4"));
                break;
            //关闭的订单
            case R.id.ll_dealmanager_closeorder:
                startActivity(new Intent(DealManagerActivity.this,DaifukuangActivity.class).putExtra("type","5"));
                break;
            //全部订单
            case R.id.ll_dealmanager_allorder:
                startActivity(new Intent(DealManagerActivity.this,DaifukuangActivity.class).putExtra("type","6"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
