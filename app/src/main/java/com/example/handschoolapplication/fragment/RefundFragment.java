package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.RefundAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.bean.RefundBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RefundFragment extends BaseFragment {

    @BindView(R.id.lv_refund)
    ListView lvRefund;
    Unbinder unbinder;
    private View view;

    private List<RefundBean> mList;
    private List<OrderBean> mOrderList;
    private RefundAdapter mAdapter;


    public RefundFragment() {
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
        mList=new ArrayList<>();
        mOrderList = new ArrayList<>();

        mList.add(new RefundBean());
        mList.add(new RefundBean());
        mList.add(new RefundBean());


        mOrderList.add(new OrderBean());
        mOrderList.add(new OrderBean());


        mAdapter=new RefundAdapter(getActivity(),mList,mOrderList);
        lvRefund.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_refund_order;
    }

}
