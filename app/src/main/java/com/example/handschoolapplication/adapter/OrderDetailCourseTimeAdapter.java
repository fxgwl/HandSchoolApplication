package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.OrderDetailsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhw on 2018/8/22.
 */

public class OrderDetailCourseTimeAdapter extends BaseAdapter {

    private List<OrderDetailsBean.DataBean.CourseInfoBean.CourseTimeInfosBean> mList;
    private Context context;

    public OrderDetailCourseTimeAdapter(List<OrderDetailsBean.DataBean.CourseInfoBean.CourseTimeInfosBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
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
            convertView = View.inflate(context, R.layout.item_my_course_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String ctime_week = mList.get(position).getCtime_week();
        if (ctime_week.contains("星期")) {
            holder.tvCourseTime.setText(ctime_week + "  " + mList.get(position).getCtime_times());
        } else {
            StringBuffer sb = new StringBuffer(ctime_week);
            StringBuffer sbs = sb.replace(0, 5, "");
            String replace = sbs.replace(6, 11, "").toString();
            String week = replace.replaceAll(",", "至");
            holder.tvCourseTime.setText(week + "  " + mList.get(position).getCtime_times());
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_course_time)
        TextView tvCourseTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
