package com.example.handschoolapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseActivity;
import com.example.handschoolapplication.bean.DiscountBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MyDiscountActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.lv)
    ListView lv;
    private String user_id;
    private String money;
    private String school_id;
    ArrayList<DiscountBean.DataBean> mlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tvTitle.setText("优惠券");
        user_id = (String) SPUtils.get(this, "userId", "");
        money = intent.getStringExtra("money");
        school_id = intent.getStringExtra("school_id");
        initView();


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_discount;
    }

    private void initView() {
        OkHttpUtils.post()
                .url(Internet.DISCOUNT)
                .addParams("user_id", user_id)
                .addParams("money", money)
                .addParams("school_id", school_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(MyDiscountActivity.java:66)" + response);
                        Gson gson = new Gson();
                        mlist.clear();
                        try {
                            mlist.addAll(gson.fromJson(response, DiscountBean.class).getData());
                        } catch (Exception e) {

                        }
                        MyAdapter madapter = new MyAdapter();
                        lv.setAdapter(madapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent mIntent = new Intent();
                                String discount_amount = mlist.get(position).getDiscount_amount();
                                mIntent.putExtra("discount", Double.parseDouble(discount_amount));
                                // 设置结果，并进行传送
                                setResult(11, mIntent);
                                finish();
                            }
                        });
                    }
                });
    }

    @OnClick({R.id.rl_back, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_menu:
                show(view);
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        private int size = 0;

        @Override
        public int getCount() {

            if (mlist != null) {
                size = mlist.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(MyDiscountActivity.this, R.layout.item_unused_dis_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvClassname.setText(mlist.get(position).getCoupons_name());
            holder.tvCondition.setText("满" + mlist.get(position).getMax_money() + "元使用");
            holder.tvMoney.setText(mlist.get(position).getDiscount_amount() + "元");
            holder.tvTime.setText(mlist.get(position).getStart_time() + "-" + mlist.get(position).getEnd_time());
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tv_classname)
            TextView tvClassname;
            @BindView(R.id.tv_condition)
            TextView tvCondition;
            @BindView(R.id.tv_money)
            TextView tvMoney;
            @BindView(R.id.tv_time)
            TextView tvTime;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
