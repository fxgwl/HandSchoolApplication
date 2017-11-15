package com.example.handschoolapplication.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = ".wxapi.WXPayEntryActivity";

    private IWXAPI api;
    private int recLen=3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.item_paysuccess);
        api = WXAPIFactory.createWXAPI(this, "wx0d9ec08cde28f7b6");
        api.handleIntent(getIntent(), this);
//        final TextView mTvTime = (TextView) findViewById(R.id.tv_paysuccess_time);
//        TextView mTvMyOrder = (TextView) findViewById(R.id.tv_paysuccess_myorder);
//        TextView mTvSHouye = (TextView) findViewById(R.id.tv_paysuccess_shouye);
//        mTvMyOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(WXPayEntryActivity.this,MainTabActivity.class));
//                EventBus.getDefault().post(new SecondEvent("aaaaaaaaaa"));
//            }
//        });
//        mTvSHouye.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(WXPayEntryActivity.this,MainTabActivity.class));
//                EventBus.getDefault().post(new SecondEvent("aaaaaaaaaa"));
//            }
//        });
//
//        final Timer timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recLen--;
//                        mTvTime.setText(""+recLen);
//                        if (recLen<=0){
//                            timer.cancel();
//                            startActivity(new Intent(WXPayEntryActivity.this,MainTabActivity.class));
//                            EventBus.getDefault().post(new SecondEvent("aaaaaaaaaa"));
//                        }
//                    }
//                });
//            }
//        },1000,1000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        Log.e("aaa",
            "(WXPayEntryActivity.java:46)"+resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == -2) {
                Toast.makeText(this, "取消付款！", Toast.LENGTH_LONG).show();
            }
            if (resp.errCode == -1) {
                Toast.makeText(this, "支付错误！", Toast.LENGTH_LONG).show();
            }
            if (resp.errCode == 0) {
                Toast.makeText(this, "支付成功！", Toast.LENGTH_LONG).show();
//                queryOrder();//查询接口调用后台服务器查询是否成功
            }
        }
    }
}