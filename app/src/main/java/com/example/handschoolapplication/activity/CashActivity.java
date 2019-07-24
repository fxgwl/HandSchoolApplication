package com.example.handschoolapplication.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.MyApplication;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.WeChatConfig;
import com.example.handschoolapplication.utils.CountDownTimerUtils;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.example.handschoolapplication.utils.Utils;
import com.example.handschoolapplication.utils.XmlUtils;
import com.example.handschoolapplication.view.SelfDialog;
import com.example.handschoolapplication.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.weixin_cash)
    LinearLayout weixinCash;
    @BindView(R.id.radio_alipay)
    CheckBox radioAlipay;
    @BindView(R.id.radio_wachat)
    CheckBox radioWachat;
    @BindView(R.id.ali_choose)
    LinearLayout aliChoose;
    CountDownTimerUtils countDownTimerUtils;
    private double moneys;
    private String userId;
    private String data;
    private String user_phone;
    private String tip = "";
    private IWXAPI api;
    private int payway=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moneys = getIntent().getDoubleExtra("money", 0);
        tvTitle.setText("提现");
        String s = Utils.formatDouble4(moneys);
        tvAccountMoney.setText(s + "");
        userId = (String) SPUtils.get(this, "userId", "");
        user_phone = (String) SPUtils.get(this, "user_phone", "");
        if (user_phone.length() == 11) {
            StringBuffer sb = new StringBuffer(user_phone);
            StringBuffer replace = sb.replace(3, 7, "****");
            tvPhone.setText(replace.toString());
        } else {
            tvPhone.setText(user_phone);
        }
        countDownTimerUtils = new CountDownTimerUtils(tvGetCode, 59000, 1000);

        initCashTip();
        radiochange();
    }

    private void initCashTip() {

        HashMap<String, String> params = new HashMap<>();
        params.put("charge_id", "1");
        OkHttpUtils
                .post()
                .url(Internet.CASH_TIP)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa", "(CashActivity.java:91)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa", "(CashActivity.java:96)<---->" + response);
                        if (TextUtils.isEmpty(response)) {

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int code = jsonObject.getInt("code");
                                if (code == 0) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    tip = data.getString("charge_ratio");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_cash;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.btn_cash, R.id.tv_get_code,R.id.weixin_cash,R.id.ali_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
//            case R.id.ll_cash_way:
////                startActivityForResult(new Intent(CashActivity.this,CashWayActivity.class),1);
//                break;
            case R.id.btn_cash:
                String etMoney = etCashMoney.getText().toString().trim();
                if (TextUtils.isEmpty(etMoney)) {
                    Toast.makeText(this, "请输入提现金额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                double money = Double.parseDouble(etMoney);
                if (money > moneys) {
                    Toast.makeText(this, "提现金额超过账户余额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String string = etAlipay.getText().toString();
                if(payway==0){
                    if (TextUtils.isEmpty(string)) {
                        Toast.makeText(this, "请先输入支付宝账号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                String trim = etPhoneCode.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(this, "请先输入验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!trim.equals(data)) {
                    Toast.makeText(this, "验证码输入有误！", Toast.LENGTH_SHORT).show();
                    return;
                }

                showCashTipDialog(string, String.valueOf(money));

                break;
            case R.id.tv_get_code:
                countDownTimerUtils.start();
                getCode();
                break;
            case R.id.weixin_cash:
                MyApplication.open_id="";
                MyApplication.loginkg=1;
                WeixinPay();
                payway=1;
                radiochange();
                break;
            case R.id.ali_cash:
                payway=0;
                radiochange();
                break;
        }
    }

    private void radiochange(){
        switch (payway){
            case 0:
                radioAlipay.setChecked(true);
                radioWachat.setChecked(false);
                aliChoose.setVisibility(View.VISIBLE);
                break;
            case 1:
                radioAlipay.setChecked(false);
                radioWachat.setChecked(true);
                aliChoose.setVisibility(View.GONE);
                break;
        }
    }
    private void showCashTipDialog(final String string, final String money) {
        final SelfDialog selfDialog = new SelfDialog(CashActivity.this);

        selfDialog.setMessage("提现时扣除" + tip + "手续费?");
        selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                cashToAlipay(string, money);
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

    private void getCode() {
        OkHttpUtils.post()
                .url(Internet.PWD_GETCODE)
                .addParams("user_phone", user_phone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CashActivity.java:112)<---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CashActivity.java:118)<---->" + response);

                        if (TextUtils.isEmpty(response)) {

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(CashActivity.this, msg, Toast.LENGTH_SHORT).show();

                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    data = jsonObject.getString("data");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void cashToAlipay(String string, String money) {
        String cash_way="";
        Map<String,String> map=new HashMap<String, String>();
        map.put("user_id", userId);
        map.put("t_money", money + "");
        if(payway==0){
            cash_way="支付宝";
            map.put("cash_way", cash_way);
            map.put("cash_num", string);
        }else{
            cash_way="微信";
            if(!MyApplication.open_id.isEmpty()){
                map.put("cash_way", cash_way);
                map.put("openid",MyApplication.open_id);
                }else{
                Toast.makeText(getApplication(),"请重新获取微信授权",Toast.LENGTH_SHORT).show();
            }
        }
        Log.e("aaa", "(CashActivity.java:165)<--输入的金额是-->" + money);
        OkHttpUtils.post()
                .url(Internet.ALIPAY_CASH)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(CashActivity.java:96)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(CashActivity.java:103)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(CashActivity.this, msg, Toast.LENGTH_SHORT).show();
                            if (jsonObject.getInt("result") == 0) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 11) {
            String way = data.getStringExtra("way");
            switch (way) {
                case "zhifubao":
                    ivCashWay.setImageResource(R.drawable.zhifubao);
                    tvCashName.setText("支付宝");
                    break;
                case "weixin":
//                    ivCashWay.setImageResource(R.drawable.weixinzhifu);
//                    tvCashName.setText("微信");
                    break;
            }
        }
    }

    /**
     * 添加弹出的dialog关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements Dialog.OnDismissListener {


        @Override
        public void onDismiss(DialogInterface dialog) {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    public void WeixinPay() {
// 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WeChatConfig.APP_ID, true);
// 将该app注册到微信
        api.registerApp(WeChatConfig.APP_ID);

        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            api.sendReq(req);
        } else{
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
        }
    }
}
