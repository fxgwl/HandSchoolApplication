package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CashActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_cash_money)
    EditText etCashMoney;
    @BindView(R.id.tv_account_money)
    TextView tvAccountMoney;
    @BindView(R.id.iv_cash_way)
    ImageView ivCashWay;
    @BindView(R.id.tv_cash_name)
    TextView tvCashName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("提现");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_cash;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_cash_way, R.id.btn_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.ll_cash_way:
                startActivityForResult(new Intent(CashActivity.this,CashWayActivity.class),1);
                break;
            case R.id.btn_cash:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==11){
            String way = data.getStringExtra("way");
            switch (way){
                case "zhifubao":
                    ivCashWay.setImageResource(R.drawable.zhifubao);
                    tvCashName.setText("支付宝");
                    break;
                case "weixin":
                    ivCashWay.setImageResource(R.drawable.weixinzhifu);
                    tvCashName.setText("微信");
                    break;
            }
        }
    }
}
