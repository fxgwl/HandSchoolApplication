package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.CourseHomePagerActivity;
import com.example.handschoolapplication.bean.RecommendBean;
import com.example.handschoolapplication.utils.Internet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/20.
 */

public class RecommendAdapter extends BaseAdapter{

    private Context context;
    private List<RecommendBean> mList;
    private double[] locations;
    private int size = 0;

    public RecommendAdapter(Context context, List<RecommendBean> mList) {
        this.context = context;
        this.mList = mList;
    }
    public void setLocation(double[] locations){
        this.locations = locations;
    }

    @Override
    public int getCount() {
        if (mList!=null){
            if (mList.size()>3){
                size = 3;
            }else {
                size=mList.size();
            }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_hf_course_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        String course_photo = mList.get(position).getCourse_photo();
        String picture_one = mList.get(position).getPicture_one();
//        String photo = "";
//        if (course_photo.contains(",")){
//            String[] split = course_photo.split(",");
//            photo = split[0];
//        }else {
//            photo = course_photo;
//        }
        Glide.with(context)
                .load(Internet.BASE_URL + picture_one)
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(mList.get(position).getCourse_name());
        holder.tvPrice.setText("价格： ¥" + mList.get(position).getPreferential_price());
        holder.popularity.setText("(" + mList.get(position).getPopularity_num() + "人已报名)");

        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseHomePagerActivity.class);
                intent.putExtra("school_id", mList.get(position).getSchool_id());
                intent.putExtra("course_id", mList.get(position).getCourse_id());
                intent.putExtra("schooluid", mList.get(position).getUser_id());
                context.startActivity(intent);
            }
        });

        if (locations != null) {

            double latitude1 = locations[0];
            double longitude1 = locations[1];
            String school_wei = mList.get(position).getSchool_wei();
            String school_jing = mList.get(position).getSchool_jing();

            double distance = DistanceUtil.getDistance(new LatLng(latitude1, longitude1), new LatLng(Double.parseDouble(school_wei), Double.parseDouble(school_jing)));
//            Double distance = MyUtiles.Distance(latitude1, longitude1, school_wei, school_jing);
            distance = (distance / 1000);
            double v = new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
            holder.tvDistance.setText("距离：" + v + "km");

        }else {
            holder.tvDistance.setText("距离：" + "定位失败");
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
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
