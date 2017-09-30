package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.TimeBean;
import com.example.handschoolapplication.bean.TimeHourBean;
import com.example.handschoolapplication.view.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ClassTimeAdapter extends BaseAdapter {
    private Context context;
    private List<TimeBean> mlist;
    private List<TimeHourBean> mHourList;
    private int size = 0;

    public ClassTimeAdapter(Context context, List<TimeBean> mlist,List<TimeHourBean> mHourList) {
        this.context = context;
        this.mlist = mlist;
        this.mHourList=mHourList;
    }

    @Override
    public int getCount() {

        if (mlist != null) {
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
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.classtime_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        TimeAdapter timeAdapter = new TimeAdapter(context,mHourList );
        holder.timechooseGv.setAdapter(timeAdapter);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.timechoose_week)
        TextView timechooseWeek;
        @BindView(R.id.timechoose_gv)
        MyGridView timechooseGv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
