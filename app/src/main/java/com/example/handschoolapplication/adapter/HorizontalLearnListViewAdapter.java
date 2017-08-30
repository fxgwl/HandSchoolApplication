package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;

import java.util.List;

public class HorizontalLearnListViewAdapter extends BaseAdapter {
    List<String> mData;
    Context mContext;
    private int selectedPosition = 0;// 选中的位置

    public HorizontalLearnListViewAdapter(Context mContext, List<String> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    private ViewHolder vh = new ViewHolder();

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    private static class ViewHolder {
        private TextView file;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hor_list_item, null);
            vh.file = (TextView) convertView.findViewById(R.id.file);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.file.setText(mData.get(position));
        if (selectedPosition==position){
            vh.file.setSelected(true);
            vh.file.setPressed(true);
            vh.file.setBackgroundResource(R.drawable.textview_pressed);
            vh.file.setTextColor(Color.parseColor("#ffffff"));
        }
        return convertView;
    }
}
