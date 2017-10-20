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
import com.example.handschoolapplication.bean.UnusedDisBean;
import com.example.handschoolapplication.utils.InternetS;
import com.example.handschoolapplication.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

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
    private MyAdapter myAdapter;
    private String userId;

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
                .url(InternetS.DISCOUNT_PAPER_LIST)
                .addParams("user_id", userId)
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        mList.add(new UnusedDisBean());
        mList.add(new UnusedDisBean());
        mList.add(new UnusedDisBean());
        mList.add(new UnusedDisBean());
        mList.add(new UnusedDisBean());

        myAdapter = new MyAdapter();
        lvDiscountcoupon.setAdapter(myAdapter);

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
                view = View.inflate(getActivity(), R.layout.item_unused_dis_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
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
