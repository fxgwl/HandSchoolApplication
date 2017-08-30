package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.CourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/5.
 */

public class BroswerCourseAdapter extends BaseAdapter {

    private List<CourseBean> mList;
    private Context context;

    public BroswerCourseAdapter(List<CourseBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

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
    public View getView(int position, View view, ViewGroup parent) {


        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_bro_course_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }

    static class ViewHolder {
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
