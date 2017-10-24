package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MyAccountBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.lv_account)
    ListView lvAccount;

    private List<MyAccountBean> mList;
    private MyAdapter myAdapter;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = (String) SPUtils.get(this, "userId", "");
        initView();
        initData();


        getAccuntList();//获取账户账单
    }

    private void getAccuntList() {
        mList = new ArrayList<>();
        OkHttpUtils.post()
                .addParams("user_id", userId)
                .url(InternetS.ACCOUNTLIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(MyAccountActivity.java:68)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyAccountActivity.java:75)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            mList.addAll((Collection<? extends MyAccountBean>) new Gson().fromJson(data.toString(),new TypeToken<ArrayList<MyAccountBean>>(){}.getType()));
                            myAdapter = new MyAdapter();
                            lvAccount.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
        OkHttpUtils.post()
                .url(InternetS.ACCOUNTINFO)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(MyAccountActivity.java:60)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyAccountActivity.java:67)" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            double account_money = data.getDouble("account_money");
                            tvMoney.setText(new DecimalFormat("0.00").format(account_money));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        tvTitle.setText("我的账户");

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_account;
    }

    @OnClick({R.id.rl_back, R.id.iv_menu, R.id.tv_cash, R.id.ll_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:                 show(view);
                break;
            case R.id.tv_cash://提现
                startActivity(new Intent(MyAccountActivity.this, CashActivity.class));
                break;
            case R.id.ll_account:
                startActivity(new Intent(MyAccountActivity.this, IncomeRecordActivity.class));
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        int size = 0;

        @Override
        public int getCount() {

            if (mList != null) {
                size = mList.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {


            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(MyAccountActivity.this, R.layout.item_account_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            MyAccountBean myAccountBean = mList.get(position);
            holder.tvHour.setText(myAccountBean.getRecord_time());
            holder.tvDay.setText(myAccountBean.getRecord_date());
            holder.tvNum.setText(myAccountBean.getMoney_info());
            holder.tvContent.setText(myAccountBean.getUser_name());
            Glide.with(MyAccountActivity.this).load(Internet.BASE_URL+myAccountBean.getUser_photo()).centerCrop().into(holder.civUsericon);
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_hour)
            TextView tvHour;
            @BindView(R.id.tv_day)
            TextView tvDay;
            @BindView(R.id.civ_usericon)
            CircleImageView civUsericon;
            @BindView(R.id.tv_num)
            TextView tvNum;
            @BindView(R.id.tv_content)
            TextView tvContent;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
