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
 * Created by Administrator on 2017/9/2.
 */

public class ChooseTimeAdapter extends BaseAdapter {


    private Context context;
    private List<TimeBean> mList;
    private List<TimeHourBean> mHourList;
    private int size = 0;

    public ChooseTimeAdapter(Context context, List<TimeBean> mList, List<TimeHourBean> mHourList) {
        this.context = context;
        this.mList = mList;
        this.mHourList = mHourList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_classtime_lv, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        TimeAdapter timeAdapter = new TimeAdapter(context, mHourList);
        holder.timechooseGv.setAdapter(timeAdapter);

        return convertView;
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
