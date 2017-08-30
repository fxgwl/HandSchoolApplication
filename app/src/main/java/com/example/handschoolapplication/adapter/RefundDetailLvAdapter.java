package com.example.handschoolapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.activity.RefundDetailActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by axehome on 2017/8/24.
 */

public class RefundDetailLvAdapter extends BaseAdapter {
    private Context mContext;

    public RefundDetailLvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 1;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_refunddetail, null);
            holder=new ViewHolder();
            holder.mTvClassId=((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_classid));
            holder.mTvClassName=((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_classname));
            holder.mTvClassprice=((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_classprice));
            holder.mTvNum=((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_num));
            holder.mTvRefundPrice=((TextView) convertView.findViewById(R.id.tv_itemrefunddetail_refundprice));
            holder.ivClasslogo=((ImageView) convertView.findViewById(R.id.iv_itemrefunddetail_classlogo));
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }



    class ViewHolder {
        TextView mTvClassId,mTvClassName,mTvClassprice,mTvNum,mTvRefundPrice;
       ImageView ivClasslogo;


    }
}
