package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.VertifyOrderAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.OrderBean;
import com.example.handschoolapplication.bean.VertifyOrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class VertifyOrderFragment extends BaseFragment {

    @BindView(R.id.lv_vertify_order)
    ListView lvVertifyOrder;
    Unbinder unbinder;
    private View view;
    private List<VertifyOrderBean> mList;
    private List<OrderBean> mOrderList;

    private VertifyOrderAdapter mAdapter;


    public VertifyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);

        initDataView();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initDataView() {
        mList = new ArrayList<>();
        mOrderList = new ArrayList<>();

        mList.add(new VertifyOrderBean());
        mList.add(new VertifyOrderBean());
        mList.add(new VertifyOrderBean());


        mOrderList.add(new OrderBean());
        mOrderList.add(new OrderBean());


        mAdapter = new VertifyOrderAdapter(getActivity(), mList, mOrderList);
        lvVertifyOrder.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_vertify_order;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
