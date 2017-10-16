package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.handschoolapplication.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CostAdapter extends BaseAdapter {
    private Context context;
    private List<String> mlist;
    private int size = 0;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public CostAdapter(Context context, List<String> mlist) {
        this.context = context;
        this.mlist = mlist;
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
        final ViewHolder finalHolder = holder;
        holder.tvTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                states.put(String.valueOf(position), finalHolder.tvTime.isChecked());
                notifyDataSetChanged();
            }
        });

        boolean res = false;
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;
        holder.tvTime.setText(mlist.get(position));
        holder.tvTime.setChecked(res);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.cb_time)
        CheckBox tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
