package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.TimeHourBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class TimeAdapter extends BaseAdapter {
    private Context context;
    private List<TimeHourBean> mlist;
    private int size = 0;
    private ChooseItem chooseItem;

    public TimeAdapter(Context context, List<TimeHourBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    public void setChooseItem(ChooseItem chooseItem) {
        this.chooseItem = chooseItem;
    }

    @Override
    public int getCount() {

        if (mlist != null) {
            size = mlist.size();
        }
        return mlist.size();
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
    public View getView(final int position, View view, final ViewGroup parent) {

        ViewHolder holder = null;

        if (view == null) {
            view = View.inflate(context, R.layout.time_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvTime.setText(mlist.get(position).getTime());
        holder.tvTime.setChecked(mlist.get(position).isChecked());
        Log.e("aaa",
                "(TimeAdapter.java:71)" + mlist.toString());
        holder.tvTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Log.e("aaa",
                            "(TimeAdapter.java:79)" + parent.getTag());
                    chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), true);
                    mlist.get(position).setChecked(true);
                } else {
                    chooseItem.cbCheck(position, Integer.parseInt(parent.getTag() + ""), false);
                    mlist.get(position).setChecked(false);
                }
            }

        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.cb_time)
        CheckBox tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    interface ChooseItem {
        public void cbCheck(int position, int parentPosition, boolean cb);
    }
}
