package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class CashWayActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("提现到");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_cash_way;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.ll_zhifubao, R.id.ll_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.ll_zhifubao:
                ivZhifubao.setImageResource(R.drawable.hongquan);
                ivWeixin.setImageResource(R.drawable.baiquan);
                setResult(11,new Intent().putExtra("way","zhifubao"));
                finish();
                break;
            case R.id.ll_weixin:
                ivWeixin.setImageResource(R.drawable.hongquan);
                ivZhifubao.setImageResource(R.drawable.baiquan);
                setResult(11,new Intent().putExtra("way","weixin"));
                finish();
                break;
        }
    }
}
