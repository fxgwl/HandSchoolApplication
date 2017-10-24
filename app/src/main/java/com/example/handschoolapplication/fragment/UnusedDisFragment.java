package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.DiscountBean;
import com.example.handschoolapplication.bean.UnusedDisBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnusedDisFragment extends BaseFragment {

    @BindView(R.id.lv_discountcoupon)
    ListView lvDiscountcoupon;
    private View view;
    private List<UnusedDisBean> mList;
    private String userId;
    ArrayList<DiscountBean.DataBean> mlist = new ArrayList<>();

    public UnusedDisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        userId = (String) SPUtils.get(getActivity(), "userId", "");
        initDataView();
        return view;
    }

    private void initDataView() {
        mList = new ArrayList<>();

        OkHttpUtils.post()
                .url(Internet.DISCOUNTLIST)
                .addParams("user_id", userId)
                .addParams("coupons_state", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("aaa",
                                "(UnusedDisFragment.java:66)" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(UnusedDisFragment.java:73)" + response);
                        Gson gson = new Gson();
                        mlist.clear();
                        try {
                            mlist.addAll(gson.fromJson(response, DiscountBean.class).getData());
                        } catch (Exception e) {

                        }
                        MyAdapter madapter = new MyAdapter();
                        lvDiscountcoupon.setAdapter(madapter);
                        lvDiscountcoupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        });
                    }
                });

    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_unused_dis;
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
                view = View.inflate(getActivity(), R.layout.item_unused_dis_lv, null);
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
