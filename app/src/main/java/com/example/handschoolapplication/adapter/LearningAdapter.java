package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.LearningCourseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/16.
 */

public class LearningAdapter extends BaseAdapter {

    private List<LearningCourseBean> mList;
    private int size = 0;
    private Context context;

    public LearningAdapter(List<LearningCourseBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_learning_lv, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.llLearn.setVisibility(View.VISIBLE);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_learn_state)
        TextView tvLearnState;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.tv_course_name)
        TextView tvCourseName;
        @BindView(R.id.tv_learn_time)
        TextView tvLearnTime;
        @BindView(R.id.tv_tolearn)
        TextView tvTolearn;
        @BindView(R.id.ll_learn)
        LinearLayout llLearn;
        @BindView(R.id.tv_teacher)
        TextView tvTeacher;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_class_hour)
        TextView tvClassHour;
        @BindView(R.id.tv_learn_code)
        TextView tvLearnCode;
        @BindView(R.id.ll_learn_code)
        LinearLayout llLearnCode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
