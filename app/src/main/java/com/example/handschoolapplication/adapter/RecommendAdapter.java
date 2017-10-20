package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.CourseHomePagerActivity;
import com.example.handschoolapplication.bean.RecommendBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/20.
 */

public class RecommendAdapter extends BaseAdapter{

    private Context context;
    private List<RecommendBean> mList;
    private int size = 0;

    public RecommendAdapter(Context context, List<RecommendBean> mList) {
        this.context = context;
        this.mList = mList;
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
        Log.e("aaa",
                "(RecommendAdapter.java:72)" + mList.get(position).toString());
        Glide.with(context)
                .load(Internet.BASE_URL + mList.get(position).getCourse_photo())
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(mList.get(position).getCourse_name());
        holder.tvPrice.setText("价格： ¥" + mList.get(position).getPreferential_price());
        holder.popularity.setText("(" + mList.get(position).getCourse_name() + "人已报名)");

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
