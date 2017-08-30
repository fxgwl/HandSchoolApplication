package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.UnevaluateOrderAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.bean.UnevaluateOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnEvaluateOrderFragment extends BaseFragment {

    @BindView(R.id.lv_unevaluate_order)
    ListView lvUnevaluateOrder;
    private View view;
    private List<UnevaluateOrderBean> mList;
    private List<OrderBean> mOrderList;
    private UnevaluateOrderAdapter mAdapter;


    public UnEvaluateOrderFragment() {
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

        mList.add(new UnevaluateOrderBean());
        mList.add(new UnevaluateOrderBean());
        mList.add(new UnevaluateOrderBean());


        mOrderList.add(new OrderBean());
        mOrderList.add(new OrderBean());


        mAdapter=new UnevaluateOrderAdapter(getActivity(),mList,mOrderList);
        lvUnevaluateOrder.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_unevaluate_order;
    }

}
