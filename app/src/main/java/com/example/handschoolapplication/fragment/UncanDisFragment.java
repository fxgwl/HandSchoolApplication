package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.UncanDisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UncanDisFragment extends BaseFragment {

    @BindView(R.id.lv_discountcoupon)
    ListView lvDiscountcoupon;
    private View view;
    private List<UncanDisBean> mList;
    private MyAdapter myAdapter;


    public UncanDisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        initDataView();
        // Inflate the layout for this fragment
        return view;
    }

    private void initDataView() {
        mList=new ArrayList<>();
        mList.add(new UncanDisBean());
        mList.add(new UncanDisBean());
        mList.add(new UncanDisBean());
        mList.add(new UncanDisBean());
        mList.add(new UncanDisBean());

        myAdapter= new MyAdapter();
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
                view = View.inflate(getActivity(), R.layout.item_uncan_dis_lv, null);
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
