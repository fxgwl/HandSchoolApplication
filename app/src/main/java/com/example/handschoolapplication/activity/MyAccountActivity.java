package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.bumptech.glide.Glide.with;

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.lv_account)
    ListView lvAccount;

    private List<MyAccountBean.DataBean> mList;
    private MyAdapter myAdapter;
    private String userId;
    private double account_money;

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
                        Toast.makeText(MyAccountActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyAccountActivity.java:75)" + response);
                        if (TextUtils.isEmpty(response)) {
                            Toast.makeText(MyAccountActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();

                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt("result");
                                if (result == 0) {
                                    MyAccountBean myAccountBean = new Gson().fromJson(response, MyAccountBean.class);
                                    mList.addAll(myAccountBean.getData());
                                    myAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MyAccountActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MyAccountActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                            }

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
                            account_money = data.getDouble("account_money");
                            tvMoney.setText(new DecimalFormat("0.00").format(account_money));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        tvTitle.setText("我的账户");
        mList = new ArrayList<>();
        myAdapter = new MyAdapter();
        lvAccount.setAdapter(myAdapter);

        lvAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MyAccountActivity.this, DealDetailActivity.class).putExtra("myAccountBean", mList.get(position)));

            }
        });

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
            case R.id.iv_menu:
                show(view);
                break;
            case R.id.tv_cash://提现
                startActivity(new Intent(MyAccountActivity.this, CashActivity.class).putExtra("money", account_money));
                break;
            case R.id.ll_account:
                startActivity(new Intent(MyAccountActivity.this, IncomeRecordActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public class MyAdapter extends BaseAdapter {

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

            MyAccountBean.DataBean myAccountBean = mList.get(position);
            holder.tvHour.setText(myAccountBean.getRecord_time());
            holder.tvDay.setText(myAccountBean.getRecord_date());
            holder.tvNum.setText(myAccountBean.getMoney_info());
            holder.tvContent.setText(myAccountBean.getUserInfo().getUser_name());
            with(MyAccountActivity.this).load(Internet.BASE_URL + myAccountBean.getUserInfo().getHead_photo()).centerCrop().into(holder.civUsericon);
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
