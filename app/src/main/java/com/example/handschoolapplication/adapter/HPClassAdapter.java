package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.ClassActivity;
import com.example.handschoolapplication.bean.ClassBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/2.
 */

public class HPClassAdapter extends BaseAdapter {

    private Context context;
    private List<ClassBean> mList;
    private int size = 0;

    public HPClassAdapter(Context context, List<ClassBean> mList) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder=null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_hf_class_lv, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }

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
