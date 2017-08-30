package com.example.handschoolapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.DaifukuangLvAdapter;
import com.example.handschoolapplication.adapter.RefundManagerLvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefundManagerActivity extends AppCompatActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_refundmanager_all)
    TextView tvRefundmanagerAll;
    @BindView(R.id.tv_refundmanager_allline)
    TextView tvRefundmanagerAllline;
    @BindView(R.id.tv_refundmanager_tkz)
    TextView tvRefundmanagerTkz;
    @BindView(R.id.tv_refundmanager_tkzline)
    TextView tvRefundmanagerTkzline;
    @BindView(R.id.tv_refundmanager_complete)
    TextView tvRefundmanagerComplete;
    @BindView(R.id.tv_refundmanager_completeline)
    TextView tvRefundmanagerCompleteline;
    @BindView(R.id.lv_refundmanager)
    ListView lvRefundmanager;
    @BindView(R.id.ll_refundmanager_all)
    LinearLayout llRefundmanagerAll;
    @BindView(R.id.ll_refundmanager_tkz)
    LinearLayout llRefundmanagerTkz;
    @BindView(R.id.ll_refundmanager_complete)
    LinearLayout llRefundmanagerComplete;
    private RefundManagerLvAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_refund_manager);
        ButterKnife.bind(this);
        tvTitle.setText("退款管理");
        mAdapter=new RefundManagerLvAdapter(RefundManagerActivity.this);
        lvRefundmanager.setAdapter(mAdapter);
    }

    @OnClick({R.id.rl_back, R.id.iv_menu,R.id.ll_refundmanager_all, R.id.ll_refundmanager_tkz, R.id.ll_refundmanager_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                break;
            case R.id.ll_refundmanager_all:

                tvRefundmanagerAll.setTextColor(getResources().getColor(R.color.blue));
                tvRefundmanagerAllline.setBackgroundColor(getResources().getColor(R.color.blue));
                tvRefundmanagerTkz.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerTkzline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerComplete.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerCompleteline.setBackgroundColor(getResources().getColor(R.color.white));

                break;

            case R.id.ll_refundmanager_tkz:

                tvRefundmanagerAll.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerAllline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerTkz.setTextColor(getResources().getColor(R.color.blue));
                tvRefundmanagerTkzline.setBackgroundColor(getResources().getColor(R.color.blue));
                tvRefundmanagerComplete.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerCompleteline.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case R.id.ll_refundmanager_complete:

                tvRefundmanagerAll.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerAllline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerTkz.setTextColor(getResources().getColor(R.color.t666666));
                tvRefundmanagerTkzline.setBackgroundColor(getResources().getColor(R.color.white));
                tvRefundmanagerComplete.setTextColor(getResources().getColor(R.color.blue));
                tvRefundmanagerCompleteline.setBackgroundColor(getResources().getColor(R.color.blue));
                break;


        }
    }


}
