package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.bean.InfoBean;

import java.util.ArrayList;

/**
 * Created by axehome on 2017/8/28.
 */

public class HelpLvAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<InfoBean.DataBean> list;

    public HelpLvAdapter(Context mContext, ArrayList<InfoBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_help, null);
            holder = new ViewHolder();
            holder.tvQuestion = (TextView) convertView.findViewById(R.id.tv_itemhelp_question);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InfoBean.DataBean question = list.get(position);
        holder.tvQuestion.setText(question.getHelp_title());
        return convertView;
    }

    class ViewHolder {
        TextView tvQuestion;
    }
}
