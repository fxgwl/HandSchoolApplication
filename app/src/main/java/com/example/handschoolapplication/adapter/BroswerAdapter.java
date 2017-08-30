package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.BroCourseBean;
import com.example.handschoolapplication.bean.BroswerBean;
import com.example.handschoolapplication.utils.MyUtiles;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/29.
 */

public class BroswerAdapter extends BaseAdapter {

    private Context context;
    private List<BroswerBean> mList;
    private LayoutInflater inflater;

    private List<BroCourseBean> mBroCourseList;

    public BroswerAdapter(Context context, List<BroswerBean> mList, List<BroCourseBean> mBroCourseList) {
        this.context = context;
        this.mList = mList;
        this.mBroCourseList = mBroCourseList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
//        return mList.size();
        return 5;
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
            convertView = inflater.inflate(R.layout.item_bro_lv, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyAdapter myAdapter = new MyAdapter();
        holder.lvBroCourse.setAdapter(myAdapter);
        MyUtiles.setListViewHeightBasedOnChildren(holder.lvBroCourse);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.lv_bro_course)
        ListView lvBroCourse;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
//            return mBroCourseList.size();
            return 5;
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
            ViewHolder holder = null;

            if (view == null) {
                view = View.inflate(context, R.layout.item_bro_course_lv, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            return view;
        }

        class ViewHolder {
            @BindView(R.id.iv_course)
            ImageView ivCourse;
            @BindView(R.id.tv_course)
            TextView tvCourse;
            @BindView(R.id.tv_price)
            TextView tvPrice;
            @BindView(R.id.iv_share)
            ImageView ivShare;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
