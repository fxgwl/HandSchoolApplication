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
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private Context context;
    private List<String> mlist;
    private CbClick cbClick;
    private String class_money;

    public CostAdapter(Context context, List<String> mlist, String class_money) {
        this.context = context;
        this.mlist = mlist;
        this.class_money = class_money;
    }

    public void setCbClick(CbClick cbClick) {
        this.cbClick = cbClick;
    }

    @Override
    public int getCount() {
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

        boolean res = false;
        if (mlist.get(position).equals(class_money)) {
            states.put(String.valueOf(position), true);
        }
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {
            res = true;
        }
        holder.tvTime.setText(mlist.get(position));
        holder.tvTime.setChecked(res);
        holder.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class_money="";
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                notifyDataSetChanged();
                states.put(String.valueOf(position), finalHolder.tvTime.isChecked());
                notifyDataSetChanged();
                cbClick.onCBClick(position);
            }
        });
        return view;
    }

    public interface CbClick {
        void onCBClick(int postion);
    }

    static class ViewHolder {
        @BindView(R.id.cb_time)
        CheckBox tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
