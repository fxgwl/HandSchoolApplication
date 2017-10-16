package com.example.handschoolapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.PublishEvaluateActivity;
import com.example.handschoolapplication.adapter.AllOrderAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.AllOrderBean;
import com.example.handschoolapplication.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnEvaFragment extends BaseFragment implements AllOrderAdapter.EvaluateListener {

    @BindView(R.id.lv_all_order)
    ListView lvAllOrder;
    Unbinder unbinder;
    private View view;
    private List<AllOrderBean> mList;
    private List<OrderBean> mOrderList;

    private AllOrderAdapter mAdapter;


    public UnEvaFragment() {
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

        mList.add(new AllOrderBean());
        mList.add(new AllOrderBean());
        mList.add(new AllOrderBean());


        mOrderList.add(new OrderBean());
        mOrderList.add(new OrderBean());


        mAdapter=new AllOrderAdapter(getActivity(),mList,mOrderList);
        lvAllOrder.setAdapter(mAdapter);
        mAdapter.setEvaluateListener(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_all_order;
    }

    //去评价
    @Override
    public void onEvaluate(int position) {
        startActivity(new Intent(getActivity(), PublishEvaluateActivity.class));
    }
}
