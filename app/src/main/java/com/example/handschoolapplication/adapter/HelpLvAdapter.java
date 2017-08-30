package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;

/**
 * Created by axehome on 2017/8/28.
 */

public class HelpLvAdapter extends BaseAdapter {
    private Context mContext;

    public HelpLvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
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
       ViewHolder holder;
        if (convertView == null) {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_lv_help,null);
            holder=new ViewHolder();
            holder.tvQuestion= (TextView) convertView.findViewById(R.id.tv_itemhelp_question);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvQuestion;
    }
}
