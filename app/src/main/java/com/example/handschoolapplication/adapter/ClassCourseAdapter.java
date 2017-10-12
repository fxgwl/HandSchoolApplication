package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ClassCourse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ClassCourseAdapter extends BaseAdapter {

    private Context context;
    private List<ClassCourse.DataBean> mList;
    private int size = 0;

    public ClassCourseAdapter(Context context, List<ClassCourse.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            size = mList.size();
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_class_course_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ClassCourse.DataBean classCourse = mList.get(position);
        Glide.with(context)
                .load(classCourse.getCourse_photo().split(",")[0])
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(classCourse.getCourse_name());
        holder.tvDiscount.setText("金币抵" + classCourse.getPreferential_price() + "%");
//        holder.tvXianshi.setText();
        holder.tvPrice.setText("价格： ¥" + classCourse.getCourse_money());
        holder.popularity.setText("(" + classCourse.getPopularity_num() + "人已报名)");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course)
        TextView tvCourse;
        @BindView(R.id.tv_discount)
        TextView tvDiscount;
        @BindView(R.id.tv_xianshi)
        TextView tvXianshi;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.popularity)
        TextView popularity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
