package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.PingJiaAdapter;
import com.example.handschoolapplication.view.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CdPingJiaFragment extends Fragment {


    @BindView(R.id.mlv_cdpingjia)
    MyListView mlvCdpingjia;
    Unbinder unbinder;

    public CdPingJiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cd_ping_jia, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        PingJiaAdapter pingjiaAdapter = new PingJiaAdapter(getActivity(), new ArrayList<String>());
        mlvCdpingjia.setAdapter(pingjiaAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
