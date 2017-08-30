package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoveClassFragment extends BaseFragment {

    @BindView(R.id.lv_love_course)
    ListView lvLoveClass;
    private View view;
    private MyLoveClassAdapter myLoveClassAdapter;


    public LoveClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        Log.e("aaa",
                "(LoveClassFragment.java:41)" + "进入我收藏的学堂fragment");
        initView();
        return view;
    }

    private void initView() {
        myLoveClassAdapter = new MyLoveClassAdapter();
        lvLoveClass.setAdapter(myLoveClassAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_love_class;
    }

    class MyLoveClassAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_love_course_lv, null);
                holder = new ViewHolder();
                holder.tvCourse = (TextView) convertView.findViewById(R.id.tv_course);
                holder.ivCourse = (ImageView) convertView.findViewById(R.id.iv_course);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.ivShare = (ImageView) convertView.findViewById(R.id.iv_share);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }
            return convertView;
        }

        class ViewHolder {

            ImageView ivCourse;
            TextView tvCourse;
            TextView tvPrice;
            ImageView ivShare;

        }
    }

}
