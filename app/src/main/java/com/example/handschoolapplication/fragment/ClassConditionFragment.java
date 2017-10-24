package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.adapter.ImageAdapter;
import com.example.handschoolapplication.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassConditionFragment extends BaseFragment {

    private View view;
    private ListView lvCondition;
    private List<String> mList;
    private ImageAdapter mAdapter;
    public ClassConditionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater,container,savedInstanceState);
        lvCondition= (ListView) view.findViewById(R.id.lv_condition);
        mList=new ArrayList<>();
        mAdapter=new ImageAdapter(getActivity());
        lvCondition.setAdapter(mAdapter);
        initViewData();
        return view;
    }

    private void initViewData() {
        mList.clear();
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504162097378&di=7d048131edc0fa6bdac1097a9a0b82dd&imgtype=0&src=http%3A%2F%2Fs.114study.com%2Fimages%2F201512%2F20151216162650512281.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504161348888&di=7d1d2983d4e63eedda55f4e38ea48372&imgtype=0&src=http%3A%2F%2Fsh.pxto.com.cn%2FUserFiles%2Fa9eb344270e12b8d%2FDSCF3157.jpg");
        mList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504162097378&di=7d048131edc0fa6bdac1097a9a0b82dd&imgtype=0&src=http%3A%2F%2Fs.114study.com%2Fimages%2F201512%2F20151216162650512281.jpg");
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_class_condition;
    }

}
