package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.ListItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ListItemAdapter extends BaseAdapter {

    private Context context;
    private List<ListItemBean> mlist;
    private int num;

    public ListItemAdapter(Context context, List<ListItemBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
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
            view = View.inflate(context, R.layout.item_listitem_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ViewHolder finalHolder = holder;
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = finalHolder.tvNum.getText().toString();
                num = Integer.parseInt(string);
                num++;
                finalHolder.tvNum.setText(num + "");
            }
        });

        holder.tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = finalHolder.tvNum.getText().toString();
                num = Integer.parseInt(string);
                num--;
                finalHolder.tvNum.setText(num + "");
            }
        });

        holder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("aaa",
                    "(ListItemAdapter.java:86)"+"点击了单选按钮");
                if (finalHolder.flag == 0) {
                    finalHolder.ivSelect.setImageResource(R.drawable.hongquan);
                    finalHolder.flag = 1;
                } else {
                    finalHolder.ivSelect.setImageResource(R.drawable.baiquan);
                    finalHolder.flag = 0;
                }
            }
        });


        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.iv_imag)
        ImageView ivImag;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_sub)
        TextView tvSub;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_add)
        TextView tvAdd;

        int flag = 0;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
