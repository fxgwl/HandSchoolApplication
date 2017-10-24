package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.CourseSortBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FindCourseAdapter extends BaseAdapter {

    private List<CourseSortBean> mlist;
    private Context context;
    private LayoutInflater inflater;
    private double[] locations;
    private int size = 0;

    public FindCourseAdapter(List<CourseSortBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setLocations(double[] locations){
        this.locations = locations;
    }

    @Override
    public int getCount() {
        if (mlist!=null){
            size = mlist.size();
        }
        return size;
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
            convertView = inflater.inflate(R.layout.item_find_course_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        CourseSortBean courseSortBean = mlist.get(position);
        Glide.with(context).load(Internet.BASE_URL + courseSortBean.getCourse_photo()).centerCrop().into(holder.ivCourse);
        holder.tvCourse.setText(courseSortBean.getCourse_name());
        holder.tvPrice.setText("¥" + courseSortBean.getPreferential_price());//价格是放的优惠价
        holder.popularity.setText("（" + courseSortBean.getPopularity_num() + "人已报名）");

        double school_wei = Double.parseDouble(courseSortBean.getSchool_wei());
        double school_jing = Double.parseDouble(courseSortBean.getSchool_jing());

        if (locations != null) {
            double distance = DistanceUtil.getDistance(new LatLng(locations[0], locations[1]), new LatLng(school_wei, school_jing));
            Log.e("aaa",
                    "(ArtActivity.java:635)" + "纬度是=====" + locations[0] + "     经度为======" + locations[1]);
            Log.e("aaa",
                    "(ArtActivity.java:637)" + "学堂返回的纬度是=====" + school_wei + "     学堂返回的经度为======" + school_jing);
            holder.tvDistance.setText((int) distance + "m");
        } else {
            holder.tvDistance.setText("定位失败");
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course)
        TextView tvCourse;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.popularity)
        TextView popularity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
