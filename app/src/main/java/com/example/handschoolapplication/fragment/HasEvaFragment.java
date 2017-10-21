package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.HasEvaAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.example.handschoolapplication.bean.HasEvaBean;
import com.example.handschoolapplication.utils.Internet;
import com.example.handschoolapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HasEvaFragment extends BaseFragment {

    @BindView(R.id.lv_has_evaluate)
    ListView lvHasEva;
    private View view;
    private List<HasEvaBean.DataBean> mList = new ArrayList<>();

    private HasEvaAdapter mAdapter;
    private String user_id;


    public HasEvaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        user_id = (String) SPUtils.get(getActivity(), "userId", "");
        mAdapter = new HasEvaAdapter(getActivity(), mList);
        lvHasEva.setAdapter(mAdapter);
        initDataView();
        return view;
    }

    private void initDataView() {
        OkHttpUtils.post()
                .url(Internet.HASCOMMENT)
                .addParams("user_id", user_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aaa",
                                "(HasEvaFragment.java:71)" + response);
                        Gson gson = new Gson();
                        mList.clear();
                        try {
                            mList.addAll(gson.fromJson(response, HasEvaBean.class).getData());
                        } catch (Exception e) {

                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_has_evaluate;
    }

}
