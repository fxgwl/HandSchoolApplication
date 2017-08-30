package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.MyAccountBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.lv_account)
    ListView lvAccount;

    private List<MyAccountBean> mList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(new MyAccountBean());
        mList.add(new MyAccountBean());
        mList.add(new MyAccountBean());
        mList.add(new MyAccountBean());
        mList.add(new MyAccountBean());

        myAdapter = new MyAdapter();
        lvAccount.setAdapter(myAdapter);
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
            case R.id.iv_menu:
                break;
            case R.id.tv_cash://提现
                startActivity(new Intent(MyAccountActivity.this,CashActivity.class));
                break;
            case R.id.ll_account:
                startActivity(new Intent(MyAccountActivity.this,IncomeRecordActivity.class));
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
