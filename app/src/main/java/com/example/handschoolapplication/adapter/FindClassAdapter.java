package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.bean.ClassSortBean;
import com.example.handschoolapplication.utils.Internet;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/24.
 */

public class FindClassAdapter extends BaseAdapter {

    private Context context;
    private List<ClassSortBean> mList;
    private int size = 0;
    private double locations[];

    public FindClassAdapter(Context context, List<ClassSortBean> mList) {
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
            view = View.inflate(context, R.layout.item_hf_class_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ClassSortBean classSortBean = mList.get(position);
        Glide.with(context)
                .load(Internet.BASE_URL + classSortBean.getHead_photo())
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(classSortBean.getMechanism_name());
        holder.popularity.setText("(" + classSortBean.getUser_renqi() + "人)");
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassActivity.class);
                intent.putExtra("school_id", classSortBean.getSchool_id());
                context.startActivity(intent);
            }
        });
        String user_dengji = classSortBean.getPingjia();
        if (TextUtils.isEmpty(user_dengji)) {

        } else {
            switch (user_dengji) {
                case "1":
                    holder.star1.setImageResource(R.drawable.wujiaoxing);
                    holder.star2.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star3.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                    break;
                case "2":
                    holder.star1.setImageResource(R.drawable.wujiaoxing);
                    holder.star2.setImageResource(R.drawable.wujiaoxing);
                    holder.star3.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                    break;
                case "3":
                    holder.star1.setImageResource(R.drawable.wujiaoxing);
                    holder.star2.setImageResource(R.drawable.wujiaoxing);
                    holder.star3.setImageResource(R.drawable.wujiaoxing);
                    holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                    break;
                case "4":
                    holder.star1.setImageResource(R.drawable.wujiaoxing);
                    holder.star2.setImageResource(R.drawable.wujiaoxing);
                    holder.star3.setImageResource(R.drawable.wujiaoxing);
                    holder.star4.setImageResource(R.drawable.wujiaoxing);
                    holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                    break;
                case "5":
                    holder.star1.setImageResource(R.drawable.wujiaoxing);
                    holder.star2.setImageResource(R.drawable.wujiaoxing);
                    holder.star3.setImageResource(R.drawable.wujiaoxing);
                    holder.star4.setImageResource(R.drawable.wujiaoxing);
                    holder.star5.setImageResource(R.drawable.wujiaoxing);
                    break;
                default:
                    holder.star1.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star2.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star3.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star4.setImageResource(R.drawable.wujiaoxinghuise);
                    holder.star5.setImageResource(R.drawable.wujiaoxinghuise);
                    break;

            }
        }
        if (locations != null) {
            String user_area = classSortBean.getSchool_wei();//纬度
            String user_name = classSortBean.getSchool_jing();//经度
            double latitude = locations[0];//纬度
            double longitude = locations[1];//经度
//            DistanceUtil.getDistance(new LatLng(latitude,longitude));
            if (TextUtils.isEmpty(classSortBean.getSchool_wei()) || "null".equals(classSortBean.getSchool_wei())) {
                user_area = "0";
            }
            if (TextUtils.isEmpty(classSortBean.getSchool_jing()) || "null".equals(classSortBean.getSchool_jing())) {
                user_name = "0";
            }
            double distance = DistanceUtil.getDistance(new LatLng(latitude, longitude), new LatLng(Double.parseDouble(user_area), Double.parseDouble(user_name)));
            distance = distance / 1000;
            double v = new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            holder.tvDistance.setText("距离：" + v + "km");
        }

        return view;
    }

    public void setLocations(double locations[]) {
        this.locations = locations;
    }

    static class ViewHolder {
        @BindView(R.id.iv_course)
        ImageView ivCourse;
        @BindView(R.id.tv_course)
        TextView tvCourse;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.popularity)
        TextView popularity;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.iv_star1)
        ImageView star1;
        @BindView(R.id.iv_star2)
        ImageView star2;
        @BindView(R.id.iv_star3)
        ImageView star3;
        @BindView(R.id.iv_star4)
        ImageView star4;
        @BindView(R.id.iv_star5)
        ImageView star5;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
