package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.UnpayOrderAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.bean.UnpayOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnpayOrderFragment extends BaseFragment {

    @BindView(R.id.lv_unpay)
    ListView lvUnpay;
    private View view;
    private UnpayOrderAdapter mAdapter;
    private List<UnpayOrderBean> mlist;
    private List<OrderBean> mOrderList;


    public UnpayOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        initDataView();
        return view;
    }

    private void initDataView() {

        mlist = new ArrayList<>();
        mOrderList = new ArrayList<>();

        mlist.add(new UnpayOrderBean());
        mlist.add(new UnpayOrderBean());
        mlist.add(new UnpayOrderBean());


        mOrderList.add(new OrderBean());
        mOrderList.add(new OrderBean());


        mAdapter = new UnpayOrderAdapter(getActivity(), mlist, mOrderList);
        lvUnpay.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_unpay_order;
    }

}
