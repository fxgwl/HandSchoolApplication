package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ClassCourse;
import com.example.handschoolapplication.utils.Internet;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private double[] locations;

    public ClassCourseAdapter(Context context, List<ClassCourse.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setLocation(double[] location){
        this.locations = location;
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
        String course_photo = classCourse.getCourse_photo();
        String photo = "";
        if (course_photo.contains(",")){
            String[] split = course_photo.split(",");
            photo = split[0];
        }else {
            photo = course_photo;
        }

        double school_wei = Double.parseDouble(classCourse.getSchool_wei());
        double school_jing = Double.parseDouble(classCourse.getSchool_jing());

        if (locations != null) {
            double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
            distance = (distance / 1000);
            double v = new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
            holder.tvDiscount.setText("距离：" + v + "km");
        } else {
            holder.tvDiscount.setText("定位失败");
        }
        Glide.with(context)
                .load(Internet.BASE_URL+photo)
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(classCourse.getCourse_name());
//        holder.tvXianshi.setText();
        holder.tvPrice.setText("价格： ¥" + classCourse.getPreferential_price());
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
