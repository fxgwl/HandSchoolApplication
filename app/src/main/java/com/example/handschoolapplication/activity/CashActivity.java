package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class CashActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_cash_money)
    EditText etCashMoney;
    @BindView(R.id.et_alipay)
    EditText etAlipay;
    @BindView(R.id.tv_account_money)
    TextView tvAccountMoney;
    @BindView(R.id.iv_cash_way)
    ImageView ivCashWay;
    @BindView(R.id.tv_cash_name)
    TextView tvCashName;
    private double moneys;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moneys = getIntent().getDoubleExtra("money",0);
        tvTitle.setText("提现");
        tvAccountMoney.setText(moneys+"");
        userId = (String) SPUtils.get(this, "userId", "");
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
//                startActivityForResult(new Intent(CashActivity.this,CashWayActivity.class),1);
                break;
            case R.id.btn_cash:
                String etMoney = etCashMoney.getText().toString().trim();
                double money = Double.parseDouble(etMoney);
                if (money>moneys){
                    Toast.makeText(this, "提现金额超过账户余额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String string = etAlipay.getText().toString();
                if (TextUtils.isEmpty(string)){
                    Toast.makeText(this, "请先输入支付宝账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                cashToAlipay(string,money);
                break;
        }
    }

    private void cashToAlipay(String string,double money) {
        OkHttpUtils.post()
                .url(Internet.ALIPAY_CASH)
                .addParams("user_id",userId)
                .addParams("t_money",money+"")
                .addParams("cash_num",string)
                .addParams("cash_way","支付宝")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                            "(CashActivity.java:96)"+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                            "(CashActivity.java:103)"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(CashActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (jsonObject.getInt("result")==0){
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
