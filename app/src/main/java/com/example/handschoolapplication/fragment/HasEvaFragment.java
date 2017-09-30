package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HasEvaAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.HasEvaBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HasEvaFragment extends BaseFragment {

    @BindView(R.id.lv_has_evaluate)
    ListView lvHasEva;
    private View view;
    private List<HasEvaBean> mList;

    private HasEvaAdapter mAdapter;


    public HasEvaFragment() {
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
        mList.add(new HasEvaBean());
        mList.add(new HasEvaBean());
        mList.add(new HasEvaBean());
        mList.add(new HasEvaBean());

        mAdapter=new HasEvaAdapter(getActivity(),mList);
        lvHasEva.setAdapter(mAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_has_evaluate;
    }

}
