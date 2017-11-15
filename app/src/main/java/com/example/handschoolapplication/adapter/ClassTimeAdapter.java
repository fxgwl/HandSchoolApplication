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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ClassTimeAdapter extends BaseAdapter {
    private Context context;
    private List<TimeBean> mlist;
    private int size = 0;
    private CostAdapter timeAdapter;

    public ClassTimeAdapter(Context context, List<TimeBean> mlist) {
        this.context = context;
        this.mlist = mlist;
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
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        holder.timechooseWeek.setText(mlist.get(position).getName());
        List<TimeHourBean> mlist = this.mlist.get(position).getMlist();
        ArrayList<String> time = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            time.add(mlist.get(i).getTime());
        }
        timeAdapter = new CostAdapter(context, time);
        timeAdapter.setCbClick(new CostAdapter.CbClick() {
            @Override
            public void onCBClick(int postion) {
//                class_money = costs.get(postion);
            }
        });
        holder.timechooseGv.setAdapter(timeAdapter);
        holder.timechooseGv.setTag(position);
//        timeAdapter.setChooseItem(new TimeAdapter.ChooseItem() {
//            @Override
//            public void cbCheck(int position, int parentPosition, boolean cb) {
//                ClassTimeAdapter.this.mlist.get(parentPosition).getMlist().get(position).setChecked(cb);
//            }
//        });
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
