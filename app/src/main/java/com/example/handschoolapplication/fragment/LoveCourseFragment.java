package com.example.handschoolapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoveCourseFragment extends BaseFragment {

    private View view;
    private ListView lvCourseLove;

    private MyLoveCourseAdapter myLoveCourseAdapter;


    public LoveCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = super.onCreateView(inflater, container, savedInstanceState);
        lvCourseLove = (ListView) view.findViewById(R.id.lv_love_course);

        initView();
        return view;
    }

    private void initView() {
        myLoveCourseAdapter = new MyLoveCourseAdapter();
        lvCourseLove.setAdapter(myLoveCourseAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_love_class;
    }


    class MyLoveCourseAdapter extends BaseAdapter {

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
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder= (ViewHolder) convertView.getTag();
            }

            holder.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/8/15 fenxiang
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_price)
            TextView tvPrice;
            @BindView(R.id.iv_share)
            ImageView ivShare;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
