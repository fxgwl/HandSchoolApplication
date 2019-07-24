package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.DiscountBean;
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
public class UncanDisFragment extends BaseFragment {

    @BindView(R.id.lv_discountcoupon)
    ListView lvDiscountcoupon;
    private View view;
    private List<DiscountBean.DataBean> mList;
    private MyAdapter myAdapter;
    private String user_id;


    public UncanDisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        initDataView();
        // Inflate the layout for this fragment
        return view;
    }

    private void initDataView() {
        mList = new ArrayList<>();
        OkHttpUtils.post()
                .url(Internet.DISCOUNTLIST)
                .addParams("user_id", user_id)
                .addParams("coupons_state", "1")
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
                        mList.clear();
                        try {
                            mList.addAll(gson.fromJson(response, DiscountBean.class).getData());
                        } catch (Exception e) {

                        }
                        MyAdapter madapter = new MyAdapter();
                        lvDiscountcoupon.setAdapter(madapter);
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

            if (mList != null) {
                size = mList.size();
            }
            return size;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(getActivity(), R.layout.item_uncan_dis_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvClassname.setText(mList.get(position).getCoupons_name());
            holder.tvCondition.setText("满" + mList.get(position).getMax_money() + "元使用");
            holder.tvMoney.setText(mList.get(position).getDiscount_amount() + "元");
            holder.tvTime.setText(mList.get(position).getStart_time() + "-" + mList.get(position).getEnd_time());
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
