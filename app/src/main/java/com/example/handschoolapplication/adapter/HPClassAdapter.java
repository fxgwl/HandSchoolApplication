package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.bean.ClassBean;
import com.example.handschoolapplication.utils.Internet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/2.
 */

public class HPClassAdapter extends BaseAdapter {

    private Context context;
    private List<ClassBean.DataBean> mList;
    private int size = 0;

    public HPClassAdapter(Context context, List<ClassBean.DataBean> mList) {
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
        ClassBean.DataBean classBean = mList.get(position);
        Glide.with(context)
                .load(Internet.BASE_URL + classBean.getHead_photo())
                .centerCrop()
                .error(R.drawable.kecheng)
                .into(holder.ivCourse);
        holder.tvCourse.setText(classBean.getMechanism_name());
        holder.popularity.setText("(" + classBean.getUser_renqi() + "人已报名)");
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ClassActivity.class));
            }
        });
        return view;
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
